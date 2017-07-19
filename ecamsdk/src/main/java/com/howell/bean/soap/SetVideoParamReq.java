package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/14.
 */

public class SetVideoParamReq {
    String account;
    String session;
    String devId;
    int channelNo;
    String videoStandard;
    int rotationDegree;
    int brightness;
    int contrast;
    int saturation;
    int hue;
    Boolean infrared;

    @Override
    public String toString() {
        return "SetVideoParamReq{" +
                "account='" + account + '\'' +
                ", session='" + session + '\'' +
                ", devId='" + devId + '\'' +
                ", channelNo=" + channelNo +
                ", videoStandard='" + videoStandard + '\'' +
                ", rotationDegree=" + rotationDegree +
                ", brightness=" + brightness +
                ", contrast=" + contrast +
                ", saturation=" + saturation +
                ", hue=" + hue +
                ", infrared=" + infrared +
                '}';
    }

    public SetVideoParamReq() {
    }

    public SetVideoParamReq(String account, String session, String devId, int channelNo, String videoStandard, int rotationDegree, int brightness, int contrast, int saturation, int hue, Boolean infrared) {

        this.account = account;
        this.session = session;
        this.devId = devId;
        this.channelNo = channelNo;
        this.videoStandard = videoStandard;
        this.rotationDegree = rotationDegree;
        this.brightness = brightness;
        this.contrast = contrast;
        this.saturation = saturation;
        this.hue = hue;
        this.infrared = infrared;
    }

    public String getAccount() {

        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    public String getVideoStandard() {
        return videoStandard;
    }

    public void setVideoStandard(String videoStandard) {
        this.videoStandard = videoStandard;
    }

    public int getRotationDegree() {
        return rotationDegree;
    }

    public void setRotationDegree(int rotationDegree) {
        this.rotationDegree = rotationDegree;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getContrast() {
        return contrast;
    }

    public void setContrast(int contrast) {
        this.contrast = contrast;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public Boolean getInfrared() {
        return infrared;
    }

    public void setInfrared(Boolean infrared) {
        this.infrared = infrared;
    }
}
