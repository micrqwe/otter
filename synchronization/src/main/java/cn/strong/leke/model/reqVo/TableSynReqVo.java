package cn.strong.leke.model.reqVo;

/**
 * @author shaowenxing@cnstrong.cn
 * @since 18:06
 */
public class TableSynReqVo {
    private String sourceUrl;
    private String sourceName;
    private String sourcePassword;
    private String targetUrl;
    private String targetName;
    private String targetPassword;
    private String table;
    private Integer queryPool;
    private Integer insertPool;
    private int size = 5000;
    private int sleep = 0;


    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourcePassword() {
        return sourcePassword;
    }

    public void setSourcePassword(String sourcePassword) {
        this.sourcePassword = sourcePassword;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetPassword() {
        return targetPassword;
    }

    public void setTargetPassword(String targetPassword) {
        this.targetPassword = targetPassword;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Integer getQueryPool() {
        return queryPool;
    }

    public void setQueryPool(Integer queryPool) {
        this.queryPool = queryPool;
    }

    public Integer getInsertPool() {
        return insertPool;
    }

    public void setInsertPool(Integer insertPool) {
        this.insertPool = insertPool;
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
}
