package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/25.
 */

public class VehiclePlateDeviceStatus {
    String time;
    boolean isOnline;
    int status;
    double latitude;
    double longitude;
    long systemUpTime;
    int battery;
    int signalIntensity;

    @Override
    public String toString() {
        return "VehiclePlateDeviceStatus{" +
                "time='" + time + '\'' +
                ", isOnline=" + isOnline +
                ", status=" + status +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", systemUpTime=" + systemUpTime +
                ", battery=" + battery +
                ", signalIntensity=" + signalIntensity +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getSystemUpTime() {
        return systemUpTime;
    }

    public void setSystemUpTime(long systemUpTime) {
        this.systemUpTime = systemUpTime;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(int signalIntensity) {
        this.signalIntensity = signalIntensity;
    }

    public VehiclePlateDeviceStatus() {

    }

    public VehiclePlateDeviceStatus(String time, boolean isOnline, int status, double latitude, double longitude, long systemUpTime, int battery, int signalIntensity) {

        this.time = time;
        this.isOnline = isOnline;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.systemUpTime = systemUpTime;
        this.battery = battery;
        this.signalIntensity = signalIntensity;
    }
}
