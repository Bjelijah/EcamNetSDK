package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class VideoNetworkStateLog {
    String networkState;
    String dataTime;
    String description;

    @Override
    public String toString() {
        return "VideoNetworkStateLog{" +
                "networkState='" + networkState + '\'' +
                ", dataTime='" + dataTime + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getNetworkState() {
        return networkState;
    }

    public void setNetworkState(String networkState) {
        this.networkState = networkState;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VideoNetworkStateLog() {

    }

    public VideoNetworkStateLog(String networkState, String dataTime, String description) {

        this.networkState = networkState;
        this.dataTime = dataTime;
        this.description = description;
    }
}
