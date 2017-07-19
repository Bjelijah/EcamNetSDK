package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class VideoConnectionLog {
    String clientIP;
    String connectTime;
    String disconnectTime;
    boolean successed;
    String failedReason;
    String disconnectReason;
    String description;

    @Override
    public String toString() {
        return "VideoConnectionLog{" +
                "clientIP='" + clientIP + '\'' +
                ", connectTime='" + connectTime + '\'' +
                ", disconnectTime='" + disconnectTime + '\'' +
                ", successed=" + successed +
                ", failedReason='" + failedReason + '\'' +
                ", disconnectReason='" + disconnectReason + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(String connectTime) {
        this.connectTime = connectTime;
    }

    public String getDisconnectTime() {
        return disconnectTime;
    }

    public void setDisconnectTime(String disconnectTime) {
        this.disconnectTime = disconnectTime;
    }

    public boolean isSuccessed() {
        return successed;
    }

    public void setSuccessed(boolean successed) {
        this.successed = successed;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public String getDisconnectReason() {
        return disconnectReason;
    }

    public void setDisconnectReason(String disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VideoConnectionLog() {

    }

    public VideoConnectionLog(String clientIP, String connectTime, String disconnectTime, boolean successed, String failedReason, String disconnectReason, String description) {

        this.clientIP = clientIP;
        this.connectTime = connectTime;
        this.disconnectTime = disconnectTime;
        this.successed = successed;
        this.failedReason = failedReason;
        this.disconnectReason = disconnectReason;
        this.description = description;
    }
}
