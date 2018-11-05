package model;

import java.io.Serializable;

public class Wifi implements Serializable {
   private String wifiName;
   private String wifiPassword;

    public Wifi(String wifiName, String wifiPassword) {
        this.wifiName = wifiName;
        this.wifiPassword = wifiPassword;
    }

    public Wifi() {
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getWifiPassword() {
        return wifiPassword;
    }

    public void setWifiPassword(String wifiPassword) {
        this.wifiPassword = wifiPassword;
    }
}
