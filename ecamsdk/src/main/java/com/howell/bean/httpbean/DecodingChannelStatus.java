package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/17.
 */

public class DecodingChannelStatus {
    String id;
    String state;
    String url;
    double fps;
    double birate;

    public DecodingChannelStatus() {
    }

    public DecodingChannelStatus(String id, String state, String url, double fps, double birate) {

        this.id = id;
        this.state = state;
        this.url = url;
        this.fps = fps;
        this.birate = birate;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    public double getBirate() {
        return birate;
    }

    public void setBirate(double birate) {
        this.birate = birate;
    }

    @Override
    public String toString() {
        return "DecodingChannelStatus{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", url='" + url + '\'' +
                ", fps=" + fps +
                ", birate=" + birate +
                '}';
    }
}
