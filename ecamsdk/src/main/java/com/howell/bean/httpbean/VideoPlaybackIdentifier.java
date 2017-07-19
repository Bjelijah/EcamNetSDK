package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/11.
 */

public class VideoPlaybackIdentifier {
    String videoInputChannelId;
    int streamNo;
    String protocol;
    int beginTime;
    int endTime;

    public String getVideoInputChannelId() {
        return videoInputChannelId;
    }

    public void setVideoInputChannelId(String videoInputChannelId) {
        this.videoInputChannelId = videoInputChannelId;
    }

    public int getStreamNo() {
        return streamNo;
    }

    public void setStreamNo(int streamNo) {
        this.streamNo = streamNo;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public VideoPlaybackIdentifier(String videoInputChannelId, int streamNo, String protocol, int beginTime, int endTime) {
        this.videoInputChannelId = videoInputChannelId;
        this.streamNo = streamNo;
        this.protocol = protocol;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public VideoPlaybackIdentifier() {
    }

    @Override
    public String toString() {
        return "VideoPlaybackIdentifier{" +
                "videoInputChannelId='" + videoInputChannelId + '\'' +
                ", streamNo=" + streamNo +
                ", protocol='" + protocol + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }
}
