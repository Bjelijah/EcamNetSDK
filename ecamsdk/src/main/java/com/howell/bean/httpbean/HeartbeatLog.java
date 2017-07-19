package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/19.
 */

public class HeartbeatLog {
    String id;
    String time;
    double cpuUsage;
    double memoryUsage;
    long workingSeconds;
    double temperature;
    double pressure;
    double voltage;
    double networkSpeedRate;
    double networkUsage;
    int videoConnectionNumber;
    int storageMediumAbnormalNumber;

    @Override
    public String toString() {
        return "HeartbeatLog{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", cpuUsage=" + cpuUsage +
                ", memoryUsage=" + memoryUsage +
                ", workingSeconds=" + workingSeconds +
                ", temperature=" + temperature +
                ", pressure=" + pressure +
                ", voltage=" + voltage +
                ", networkSpeedRate=" + networkSpeedRate +
                ", networkUsage=" + networkUsage +
                ", videoConnectionNumber=" + videoConnectionNumber +
                ", storageMediumAbnormalNumber=" + storageMediumAbnormalNumber +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public long getWorkingSeconds() {
        return workingSeconds;
    }

    public void setWorkingSeconds(long workingSeconds) {
        this.workingSeconds = workingSeconds;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getNetworkSpeedRate() {
        return networkSpeedRate;
    }

    public void setNetworkSpeedRate(double networkSpeedRate) {
        this.networkSpeedRate = networkSpeedRate;
    }

    public double getNetworkUsage() {
        return networkUsage;
    }

    public void setNetworkUsage(double networkUsage) {
        this.networkUsage = networkUsage;
    }

    public int getVideoConnectionNumber() {
        return videoConnectionNumber;
    }

    public void setVideoConnectionNumber(int videoConnectionNumber) {
        this.videoConnectionNumber = videoConnectionNumber;
    }

    public int getStorageMediumAbnormalNumber() {
        return storageMediumAbnormalNumber;
    }

    public void setStorageMediumAbnormalNumber(int storageMediumAbnormalNumber) {
        this.storageMediumAbnormalNumber = storageMediumAbnormalNumber;
    }

    public HeartbeatLog() {

    }

    public HeartbeatLog(String id, String time, double cpuUsage, double memoryUsage, long workingSeconds, double temperature, double pressure, double voltage, double networkSpeedRate, double networkUsage, int videoConnectionNumber, int storageMediumAbnormalNumber) {

        this.id = id;
        this.time = time;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.workingSeconds = workingSeconds;
        this.temperature = temperature;
        this.pressure = pressure;
        this.voltage = voltage;
        this.networkSpeedRate = networkSpeedRate;
        this.networkUsage = networkUsage;
        this.videoConnectionNumber = videoConnectionNumber;
        this.storageMediumAbnormalNumber = storageMediumAbnormalNumber;
    }
}
