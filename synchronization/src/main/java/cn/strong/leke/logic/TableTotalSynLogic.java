package cn.strong.leke.logic;

import cn.strong.leke.config.DataSourceConfig;
import cn.strong.leke.model.ColumnModel;
import cn.strong.leke.model.SynchronizationModelDTO;
import cn.strong.leke.model.reqVo.CustomTableSynReqVo;
import cn.strong.leke.service.ThreadCurrentService;
import cn.strong.leke.util.ColumnStringUtils;
import cn.strong.leke.util.DataBaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对数据库全量复制处理
 *
 * @author shaowenxing@cnstrong.cn
 * @since 17:52
 */
@Service
public class TableTotalSynLogic {
    private Logger logger = LoggerFactory.getLogger(TableTotalSynLogic.class);
    @Autowired
    private DataSourceConfig dataSourceConfig;
    @Autowired
    private ThreadCurrentService threadCurrentService;

    /**
     * 不同数据源同步
     *
     * @return
     */
    public boolean synchronizationDataSource(CustomTableSynReqVo tableSynReqVo) {
        String[] tables = null;
        // 初始化数据库
        DataSource targetDataSource = dataSourceConfig.dataSource(tableSynReqVo.getTargetUrl(), tableSynReqVo.getTargetName(), tableSynReqVo.getTargetPassword(), tableSynReqVo.getQueryPool() + tableSynReqVo.getInsertPool());
        DataSource sourceDataSource = dataSourceConfig.dataSource(tableSynReqVo.getSourceUrl(), tableSynReqVo.getSourceName(), tableSynReqVo.getSourcePassword(), tableSynReqVo.getQueryPool() + tableSynReqVo.getInsertPool());
        // 判断是全量表还是部分表
        if (StringUtils.isBlank(tableSynReqVo.getTable())) {
            tables = connection(sourceDataSource);
        } else {
            tables = tableSynReqVo.getTable().split(",");
        }
        for (String t : tables) {
//            DataSource dataSource = dataSourceConfig.dataSource(url, username, password);
            Pair<List<ColumnModel>, String> columns = DataBaseUtils.getDatabaseTable(sourceDataSource, t);
            if (CollectionUtils.isEmpty(columns.getKey())) {
                logger.info(t + "当前表中没有数据");
                continue;
            }
            SynchronizationModelDTO synchronizationModel = new SynchronizationModelDTO();
            synchronizationModel.setSource(sourceDataSource);
            synchronizationModel.setTargetSource(targetDataSource);
            synchronizationModel.setTable(t);
            synchronizationModel.setShardingTable(t);
            synchronizationModel.setKey(columns.getValue());
            synchronizationModel.setSize(tableSynReqVo.getSize());
            synchronizationModel.setSleep(tableSynReqVo.getSleep());
            synchronizationModel.setShardingSize(0);
            synchronizationModel.setQueryPool(tableSynReqVo.getQueryPool());
            synchronizationModel.setInsertPool(tableSynReqVo.getInsertPool());
            threadCurrentService.synchronizationTable(columns.getKey(), synchronizationModel);
        }
        return false;
    }

    /**
     * 查询当前库的表
     *
     * @param sourceDataSource
     * @return
     */
    public String[] connection(DataSource sourceDataSource) {
        List<String> str = new ArrayList<>();
        Connection connection = null;
        try {
            connection = sourceDataSource.getConnection();
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getTables(connection.getCatalog(), null, null,
                    new String[]{"TABLE"});
            while (rs.next()) {
                str.add(rs.getString(3));
//                System.out.println("表名：" + rs.getString(3));
//                System.out.println("表所属用户名：" + rs.getString(2));
//                System.out.println("------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ColumnStringUtils.close(connection);
        }
        return (String[]) str.toArray();
    }
}
