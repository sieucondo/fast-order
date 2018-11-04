package util;

import model.Table;

public class TableInfo {
    private static Table table = new Table();

    public static Table getTableInfo() {
        if(table == null){
            return table;
        }
        return table;
    }

    public static void setTable(Table newTable){
        table = newTable;
    }
}
