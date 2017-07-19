package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/17.
 */

public class DecodingChannelStus {
    String id;
    String signalSate;
    ArrayList<DecodingChannelStatus> statuses;

    @Override
    public String toString() {
        return "DecodingChannelStus{" +
                "id='" + id + '\'' +
                ", signalSate='" + signalSate + '\'' +
                ", statuses=" + statuses +
                '}';
    }

    public String getSignalSate() {
        return signalSate;
    }

    public void setSignalSate(String signalSate) {
        this.signalSate = signalSate;
    }

    public ArrayList<DecodingChannelStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<DecodingChannelStatus> statuses) {
        this.statuses = statuses;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DecodingChannelStus() {

    }

    public DecodingChannelStus(String id, String signalSate, ArrayList<DecodingChannelStatus> statuses) {

        this.id = id;
        this.signalSate = signalSate;
        this.statuses = statuses;
    }
}
