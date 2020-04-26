package cn.strong.leke.util;

import cn.strong.leke.model.ColumnModel;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author shaowenxing@cnstrong.cn
 * @since 15:04
 */
public class SynchronizationModel {
//    List<ColumnModel> columns,String table, String shardingTable, String key, String shardingKey,DataSource targetSource,
//    javax.sql.DataSource dataSource, int size, int sleep
    private String table;
    private String shardingTable;
    private String key;
    private String shardingKey;
    private DataSource targetSource;
    private DataSource source;
    // 是否有分片，没有填0
    private int shardingSize;
    private int size;
    private int sleep;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getShardingTable() {
        return shardingTable;
    }

    public void setShardingTable(String shardingTable) {
        this.shardingTable = shardingTable;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getShardingKey() {
        return shardingKey;
    }

    public void setShardingKey(String shardingKey) {
        this.shardingKey = shardingKey;
    }

    public DataSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(DataSource targetSource) {
        this.targetSource = targetSource;
    }

    public DataSource getSource() {
        return source;
    }

    public void setSource(DataSource source) {
        this.source = source;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSleep() {
        return sleep;
    }

    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

    public int getShardingSize() {
        return shardingSize;
    }

    public void setShardingSize(int shardingSize) {
        this.shardingSize = shardingSize;
    }
}
