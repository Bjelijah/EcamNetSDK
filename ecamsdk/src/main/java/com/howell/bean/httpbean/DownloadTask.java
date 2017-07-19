package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/12.
 */

public class DownloadTask {
    String taskId;
    String videoInputChannelId;
    String url;
    String protocol;
    String SDP;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getVideoInputChannelId() {
        return videoInputChannelId;
    }

    public void setVideoInputChannelId(String videoInputChannelId) {
        this.videoInputChannelId = videoInputChannelId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSDP() {
        return SDP;
    }

    public void setSDP(String SDP) {
        this.SDP = SDP;
    }

    public DownloadTask(String taskId, String videoInputChannelId, String url, String protocol, String SDP) {
        this.taskId = taskId;
        this.videoInputChannelId = videoInputChannelId;
        this.url = url;
        this.protocol = protocol;
        this.SDP = SDP;
    }

    public DownloadTask() {
    }

    @Override
    public String toString() {
        return "DownloadTask{" +
                "taskId='" + taskId + '\'' +
                ", videoInputChannelId='" + videoInputChannelId + '\'' +
                ", url='" + url + '\'' +
                ", protocol='" + protocol + '\'' +
                ", SDP='" + SDP + '\'' +
                '}';
    }
}
