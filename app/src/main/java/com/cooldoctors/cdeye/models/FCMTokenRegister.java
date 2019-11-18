package com.cooldoctors.cdeye.models;

public class FCMTokenRegister {

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public int getDeviceOSVersion() {
        return deviceOSVersion;
    }

    public void setDeviceOSVersion(int deviceOSVersion) {
        this.deviceOSVersion = deviceOSVersion;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    private String deviceToken;
    private String platform;
    private int appVersion;
    private int deviceOSVersion;
    private String deviceName;

}
