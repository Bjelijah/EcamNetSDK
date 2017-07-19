package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/10.
 */

public class StreamingChannel {
    int No;
    String videoCodecType;
    String videoResolution;
    String videoQualityControlType;
    double videoBitrateupperCap;
    double frameRate;
    double fixedQuality;
    String url;
    NetworkStreamingAssociation networkStreamingAssociation;

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    public String getVideoCodecType() {
        return videoCodecType;
    }

    public void setVideoCodecType(String videoCodecType) {
        this.videoCodecType = videoCodecType;
    }

    public String getVideoResolution() {
        return videoResolution;
    }

    public void setVideoResolution(String videoResolution) {
        this.videoResolution = videoResolution;
    }

    public String getVideoQualityControlType() {
        return videoQualityControlType;
    }

    public void setVideoQualityControlType(String videoQualityControlType) {
        this.videoQualityControlType = videoQualityControlType;
    }

    public double getVideoBitrateupperCap() {
        return videoBitrateupperCap;
    }

    public void setVideoBitrateupperCap(double videoBitrateupperCap) {
        this.videoBitrateupperCap = videoBitrateupperCap;
    }

    public double getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }

    public double getFixedQuality() {
        return fixedQuality;
    }

    public void setFixedQuality(double fixedQuality) {
        this.fixedQuality = fixedQuality;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NetworkStreamingAssociation getNetworkStreamingAssociation() {
        return networkStreamingAssociation;
    }

    public void setNetworkStreamingAssociation(NetworkStreamingAssociation networkStreamingAssociation) {
        this.networkStreamingAssociation = networkStreamingAssociation;
    }

    public StreamingChannel(int no, String videoCodecType, String videoResolution, String videoQualityControlType, double videoBitrateupperCap, double frameRate, double fixedQuality, String url, NetworkStreamingAssociation networkStreamingAssociation) {
        No = no;
        this.videoCodecType = videoCodecType;
        this.videoResolution = videoResolution;
        this.videoQualityControlType = videoQualityControlType;
        this.videoBitrateupperCap = videoBitrateupperCap;
        this.frameRate = frameRate;
        this.fixedQuality = fixedQuality;
        this.url = url;
        this.networkStreamingAssociation = networkStreamingAssociation;
    }

    public StreamingChannel() {
    }

    @Override
    public String toString() {
        return "StreamingChannel{" +
                "No=" + No +
                ", videoCodecType='" + videoCodecType + '\'' +
                ", videoResolution='" + videoResolution + '\'' +
                ", videoQualityControlType='" + videoQualityControlType + '\'' +
                ", videoBitrateupperCap=" + videoBitrateupperCap +
                ", frameRate=" + frameRate +
                ", fixedQuality=" + fixedQuality +
                ", url='" + url + '\'' +
                ", networkStreamingAssociation=" + networkStreamingAssociation +
                '}';
    }
}
