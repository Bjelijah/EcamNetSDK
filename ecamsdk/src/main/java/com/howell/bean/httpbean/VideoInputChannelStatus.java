package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/13.
 */

public class VideoInputChannelStatus {
    String id;
    String irCutState;
    String dayNightState;
    String signalState;
    String recordState;
    int shutter;
    double luminance;
    String networkState;
    ArrayList<StreamingStatus> streamingStatuses;

    @Override
    public String toString() {
        return "VideoInputChannelStatus{" +
                "id='" + id + '\'' +
                ", irCutState='" + irCutState + '\'' +
                ", dayNightState='" + dayNightState + '\'' +
                ", signalState='" + signalState + '\'' +
                ", recordState='" + recordState + '\'' +
                ", shutter=" + shutter +
                ", luminance=" + luminance +
                ", networkState='" + networkState + '\'' +
                ", streamingStatuses=" + streamingStatuses +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIrCutState() {
        return irCutState;
    }

    public void setIrCutState(String irCutState) {
        this.irCutState = irCutState;
    }

    public String getDayNightState() {
        return dayNightState;
    }

    public void setDayNightState(String dayNightState) {
        this.dayNightState = dayNightState;
    }

    public String getSignalState() {
        return signalState;
    }

    public void setSignalState(String signalState) {
        this.signalState = signalState;
    }

    public String getRecordState() {
        return recordState;
    }

    public void setRecordState(String recordState) {
        this.recordState = recordState;
    }

    public int getShutter() {
        return shutter;
    }

    public void setShutter(int shutter) {
        this.shutter = shutter;
    }

    public double getLuminance() {
        return luminance;
    }

    public void setLuminance(double luminance) {
        this.luminance = luminance;
    }

    public String getNetworkState() {
        return networkState;
    }

    public void setNetworkState(String networkState) {
        this.networkState = networkState;
    }

    public ArrayList<StreamingStatus> getStreamingStatuses() {
        return streamingStatuses;
    }

    public void setStreamingStatuses(ArrayList<StreamingStatus> streamingStatuses) {
        this.streamingStatuses = streamingStatuses;
    }

    public VideoInputChannelStatus() {

    }

    public VideoInputChannelStatus(String id, String irCutState, String dayNightState, String signalState, String recordState, int shutter, double luminance, String networkState, ArrayList<StreamingStatus> streamingStatuses) {
        this.id = id;
        this.irCutState = irCutState;
        this.dayNightState = dayNightState;
        this.signalState = signalState;
        this.recordState = recordState;
        this.shutter = shutter;
        this.luminance = luminance;
        this.networkState = networkState;
        this.streamingStatuses = streamingStatuses;
    }
}
