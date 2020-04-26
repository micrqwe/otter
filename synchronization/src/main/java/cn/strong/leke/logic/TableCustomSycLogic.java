package cn.strong.leke.logic;

import cn.strong.leke.config.DataSourceConfig;
import cn.strong.leke.model.ColumnModel;
import cn.strong.leke.service.ThreadCurrentService;
import cn.strong.leke.util.DataBaseUtils;
import cn.strong.leke.util.SynchronizationModel;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;

/**
 * 自定义表之间同步数据
 * @author shaowenxing@cnstrong.cn
 * @since 17:53
 */
@Service
public class TableCustomSycLogic {
    private Logger logger = LoggerFactory.getLogger(TableCustomSycLogic.class);
    @Autowired
    private ThreadCurrentService threadCurrentService;
    @Autowired
    private DataSourceConfig dataSourceConfig;
    @Autowired
    private DataSource initDataSource;
    @Value("${thread.maxpool}")
    private Integer threadPool;
    /**
     * 不同数据源同步
     *
     * @param targetUrl
     * @param targetUsername
     * @param targetPassword
     * @param table
     * @param shardingSize
     * @param size
     * @param sleep
     * @return
     */
    public boolean synchronizationDataSource( String targetUrl, String targetUsername, String targetPassword, String table, int shardingSize, int size, int sleep) {
        String[] tables = table.split(",");
        DataSource targetDataSource = dataSourceConfig.dataSource(targetUrl, targetUsername, targetPassword,threadPool);
        for (String t : tables) {
//            DataSource dataSource = dataSourceConfig.dataSource(url, username, password);
            Pair<List<ColumnModel>, String> columns = DataBaseUtils.getDatabaseTable(initDataSource, t);
            if (CollectionUtils.isEmpty(columns.getKey())) {
                logger.info(t + "当前表中没有数据");
                continue;
            }
            SynchronizationModel synchronizationModel = new SynchronizationModel();
            synchronizationModel.setSource(initDataSource);
            synchronizationModel.setTargetSource(targetDataSource);
            synchronizationModel.setTable(t);
            synchronizationModel.setShardingTable(t);
            synchronizationModel.setKey(columns.getValue());
            synchronizationModel.setSize(size);
            synchronizationModel.setSleep(sleep);
            synchronizationModel.setShardingSize(shardingSize);
            threadCurrentService.synchronizationTable(columns.getKey(), synchronizationModel);
        }
        return false;
    }

    /**
     * 同源同步
     *
     * @param table
     * @param shardingTable
     * @param key
     * @param shardingKey
     * @param shardingSize
     * @param size
     * @param sleep
     * @return
     */
    public boolean synchronization(String table, String shardingTable, String key, String shardingKey, int shardingSize, int size, int sleep) {
        Pair<List<ColumnModel>, String> columns = DataBaseUtils.getDatabaseTable(initDataSource, table);
        if (CollectionUtils.isEmpty(columns.getKey())) {
            logger.info(table + "当前表中没有数据");
            return false;
        }
        SynchronizationModel synchronizationModel = new SynchronizationModel();
        synchronizationModel.setSource(initDataSource);
        synchronizationModel.setTargetSource(initDataSource);
        synchronizationModel.setTable(table);
        synchronizationModel.setShardingTable(shardingTable);
        synchronizationModel.setKey(key);
        synchronizationModel.setShardingKey(shardingKey);
        synchronizationModel.setSize(size);
        synchronizationModel.setSleep(sleep);
        synchronizationModel.setShardingSize(shardingSize);
        return threadCurrentService.synchronizationTable(columns.getKey(), synchronizationModel);
    }

}
