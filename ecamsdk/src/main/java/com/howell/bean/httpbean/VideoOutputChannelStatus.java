package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/17.
 */

public class VideoOutputChannelStatus {
    String id;
    String state;
    ArrayList<DecodingChannelStatus> status;

    @Override
    public String toString() {
        return "VideoOutputChannelStatus{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", status=" + status +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<DecodingChannelStatus> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<DecodingChannelStatus> status) {
        this.status = status;
    }

    public VideoOutputChannelStatus() {

    }

    public VideoOutputChannelStatus(String id, String state, ArrayList<DecodingChannelStatus> status) {

        this.id = id;
        this.state = state;
        this.status = status;
    }
}
