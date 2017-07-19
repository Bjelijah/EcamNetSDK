package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/14.
 */

public class GetCodingParamRes {
    String result;
    String frameSize;
    int frameRate;
    String rateType;
    int bitRate;
    int imageQuality;
    boolean audioInput;

    @Override
    public String toString() {
        return "GetCodingParamRes{" +
                "result='" + result + '\'' +
                ", frameSize='" + frameSize + '\'' +
                ", frameRate=" + frameRate +
                ", rateType='" + rateType + '\'' +
                ", bitRate=" + bitRate +
                ", imageQuality=" + imageQuality +
                ", audioInput=" + audioInput +
                '}';
    }

    public GetCodingParamRes() {
    }

    public GetCodingParamRes(String result, String frameSize, int frameRate, String rateType, int bitRate, int imageQuality, boolean audioInput) {

        this.result = result;
        this.frameSize = frameSize;
        this.frameRate = frameRate;
        this.rateType = rateType;
        this.bitRate = bitRate;
        this.imageQuality = imageQuality;
        this.audioInput = audioInput;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public boolean isAudioInput() {
        return audioInput;
    }

    public void setAudioInput(boolean audioInput) {
        this.audioInput = audioInput;
    }
}
