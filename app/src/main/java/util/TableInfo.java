package util;

import model.Table;
import model.Wifi;

public class TableInfo {
    private static Table table = new Table();
    private static Wifi wifi = new Wifi();

    public static Table getTableInfo() {
        if(table == null){
            return table;
        }
        return table;
    }

    public static void setTable(Table newTable){
        TableInfo.table = newTable;
    }

    public static Wifi getWifi() {
        if(wifi == null){
            return wifi;
        }
        return wifi;
    }

    public static void setWifi(Wifi wifi) {
        TableInfo.wifi = wifi;
    }
}
