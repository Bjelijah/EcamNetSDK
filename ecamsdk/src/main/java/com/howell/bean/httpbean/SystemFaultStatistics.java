package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/11.
 */

public class SystemFaultStatistics {
    int faultNumber;
    int offlineNumber;
    int storageMediumAbnormalNumber;
    int videoLossNumber;

    public int getFaultNumber() {
        return faultNumber;
    }

    public void setFaultNumber(int faultNumber) {
        this.faultNumber = faultNumber;
    }

    public int getOfflineNumber() {
        return offlineNumber;
    }

    public void setOfflineNumber(int offlineNumber) {
        this.offlineNumber = offlineNumber;
    }

    public int getStorageMediumAbnormalNumber() {
        return storageMediumAbnormalNumber;
    }

    public void setStorageMediumAbnormalNumber(int storageMediumAbnormalNumber) {
        this.storageMediumAbnormalNumber = storageMediumAbnormalNumber;
    }

    public int getVideoLossNumber() {
        return videoLossNumber;
    }

    public void setVideoLossNumber(int videoLossNumber) {
        this.videoLossNumber = videoLossNumber;
    }

    public SystemFaultStatistics(int faultNumber, int offlineNumber, int storageMediumAbnormalNumber, int videoLossNumber) {
        this.faultNumber = faultNumber;
        this.offlineNumber = offlineNumber;
        this.storageMediumAbnormalNumber = storageMediumAbnormalNumber;
        this.videoLossNumber = videoLossNumber;
    }

    public SystemFaultStatistics() {
    }

    @Override
    public String toString() {
        return "SystemFaultStatistics{" +
                "faultNumber=" + faultNumber +
                ", offlineNumber=" + offlineNumber +
                ", storageMediumAbnormalNumber=" + storageMediumAbnormalNumber +
                ", videoLossNumber=" + videoLossNumber +
                '}';
    }
}
