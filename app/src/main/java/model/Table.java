package model;

public class Table {
    private int tableId;
    private String tableKey;
    private String tableName;
    private int  storeId;
    private String storeName;

    public int getTableId() {
        return tableId;
    }

    public String getTableKey() {
        return tableKey;
    }

    public String getTableName() {
        return tableName;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Table(int tableId, String tableKey, String tableName, int storeId, String storeName) {
        this.tableId = tableId;
        this.tableKey = tableKey;
        this.tableName = tableName;
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public Table() {
    }
}
