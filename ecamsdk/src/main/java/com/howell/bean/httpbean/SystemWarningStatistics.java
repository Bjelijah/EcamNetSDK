package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/11.
 */

public class SystemWarningStatistics {
    int warnimgNumber;
    int CPUUsageNumber;
    int memoryUsageNumber;
    int netWrokusageNumber;
    int superheatNumber;
    int voltageInstabilityNumber;
    int videoHighLoadNumber;
    int videoNetworkinstabilityNumber;
    int teardownNumber;
    int videoConnectionFailureNumber;

    public int getWarnimgNumber() {
        return warnimgNumber;
    }

    public void setWarnimgNumber(int warnimgNumber) {
        this.warnimgNumber = warnimgNumber;
    }

    public int getCPUUsageNumber() {
        return CPUUsageNumber;
    }

    public void setCPUUsageNumber(int CPUUsageNumber) {
        this.CPUUsageNumber = CPUUsageNumber;
    }

    public int getMemoryUsageNumber() {
        return memoryUsageNumber;
    }

    public void setMemoryUsageNumber(int memoryUsageNumber) {
        this.memoryUsageNumber = memoryUsageNumber;
    }

    public int getNetWrokusageNumber() {
        return netWrokusageNumber;
    }

    public void setNetWrokusageNumber(int netWrokusageNumber) {
        this.netWrokusageNumber = netWrokusageNumber;
    }

    public int getSuperheatNumber() {
        return superheatNumber;
    }

    public void setSuperheatNumber(int superheatNumber) {
        this.superheatNumber = superheatNumber;
    }

    public int getVoltageInstabilityNumber() {
        return voltageInstabilityNumber;
    }

    public void setVoltageInstabilityNumber(int voltageInstabilityNumber) {
        this.voltageInstabilityNumber = voltageInstabilityNumber;
    }

    public int getVideoHighLoadNumber() {
        return videoHighLoadNumber;
    }

    public void setVideoHighLoadNumber(int videoHighLoadNumber) {
        this.videoHighLoadNumber = videoHighLoadNumber;
    }

    public int getVideoNetworkinstabilityNumber() {
        return videoNetworkinstabilityNumber;
    }

    public void setVideoNetworkinstabilityNumber(int videoNetworkinstabilityNumber) {
        this.videoNetworkinstabilityNumber = videoNetworkinstabilityNumber;
    }

    public int getTeardownNumber() {
        return teardownNumber;
    }

    public void setTeardownNumber(int teardownNumber) {
        this.teardownNumber = teardownNumber;
    }

    public int getVideoConnectionFailureNumber() {
        return videoConnectionFailureNumber;
    }

    public void setVideoConnectionFailureNumber(int videoConnectionFailureNumber) {
        this.videoConnectionFailureNumber = videoConnectionFailureNumber;
    }

    public SystemWarningStatistics(int warnimgNumber, int CPUUsageNumber, int memoryUsageNumber, int netWrokusageNumber, int superheatNumber, int voltageInstabilityNumber, int videoHighLoadNumber, int videoNetworkinstabilityNumber, int teardownNumber, int videoConnectionFailureNumber) {
        this.warnimgNumber = warnimgNumber;
        this.CPUUsageNumber = CPUUsageNumber;
        this.memoryUsageNumber = memoryUsageNumber;
        this.netWrokusageNumber = netWrokusageNumber;
        this.superheatNumber = superheatNumber;
        this.voltageInstabilityNumber = voltageInstabilityNumber;
        this.videoHighLoadNumber = videoHighLoadNumber;
        this.videoNetworkinstabilityNumber = videoNetworkinstabilityNumber;
        this.teardownNumber = teardownNumber;
        this.videoConnectionFailureNumber = videoConnectionFailureNumber;
    }

    public SystemWarningStatistics() {
    }

    @Override
    public String toString() {
        return "SystemWarningStatistics{" +
                "warnimgNumber=" + warnimgNumber +
                ", CPUUsageNumber=" + CPUUsageNumber +
                ", memoryUsageNumber=" + memoryUsageNumber +
                ", netWrokusageNumber=" + netWrokusageNumber +
                ", superheatNumber=" + superheatNumber +
                ", voltageInstabilityNumber=" + voltageInstabilityNumber +
                ", videoHighLoadNumber=" + videoHighLoadNumber +
                ", videoNetworkinstabilityNumber=" + videoNetworkinstabilityNumber +
                ", teardownNumber=" + teardownNumber +
                ", videoConnectionFailureNumber=" + videoConnectionFailureNumber +
                '}';
    }
}
