package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/14.
 */

public class SetCodingParamReq {
    String account;
    String session;
    String devId;
    int channelNo;
    String streamType;
    String frameSize;
    int frameRate;
    String rateType;
    int bitRate;
    int imageQuality;
    Boolean audioInput;



    public SetCodingParamReq() {
    }

    @Override
    public String toString() {
        return "SetCodingParamReq{" +
                "account='" + account + '\'' +
                ", session='" + session + '\'' +
                ", devId='" + devId + '\'' +
                ", channelNo=" + channelNo +
                ", streamType='" + streamType + '\'' +
                ", frameSize='" + frameSize + '\'' +
                ", frameRate=" + frameRate +
                ", rateType='" + rateType + '\'' +
                ", bitRate=" + bitRate +
                ", imageQuality=" + imageQuality +
                ", audioInput=" + audioInput +
                '}';
    }

    public SetCodingParamReq(String account, String session, String devId, int channelNo, String streamType, String frameSize, int frameRate, String rateType, int bitRate, int imageQuality, Boolean audioInput) {
        this.account = account;
        this.session = session;
        this.devId = devId;
        this.channelNo = channelNo;
        this.streamType = streamType;
        this.frameSize = frameSize;
        this.frameRate = frameRate;
        this.rateType = rateType;
        this.bitRate = bitRate;
        this.imageQuality = imageQuality;
        this.audioInput = audioInput;
    }

    public String getStreamType() {

        return streamType;
    }

    public void setStreamType(String streamType) {
        this.streamType = streamType;
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

    public String getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(String frameSize) {
        this.frameSize = frameSize;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public int getBitRate() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public int getImageQuality() {
        return imageQuality;
    }

    public void setImageQuality(int imageQuality) {
        this.imageQuality = imageQuality;
    }

    public Boolean isAudioInput() {
        return audioInput;
    }

    public void setAudioInput(Boolean audioInput) {
        this.audioInput = audioInput;
    }
}
