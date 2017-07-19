package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/13.
 */

public class SystemTime {
    String timeMode;
    String localTime;
    String timeZone;

    public String getTimeMode() {
        return timeMode;
    }

    public void setTimeMode(String timeMode) {
        this.timeMode = timeMode;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public SystemTime(String timeMode, String localTime, String timeZone) {
        this.timeMode = timeMode;
        this.localTime = localTime;
        this.timeZone = timeZone;
    }

    public SystemTime() {
    }

    @Override
    public String toString() {
        return "SystemTime{" +
                "timeMode='" + timeMode + '\'' +
                ", localTime='" + localTime + '\'' +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
