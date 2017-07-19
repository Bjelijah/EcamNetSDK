package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/15.
 */

public class SetOSDParamReq {
    String account;
    String session;
    String devId;
    int channelNo;
    Boolean enable;
    Boolean timestanpEnable;
    String dateTimeFormat;
    String displayText;
    int fontSize;
    int fontColor;
    int textPositionX;
    int textPositionY;
    int timestampPositionX;
    int timestampPositionY;

    @Override
    public String toString() {
        return "SetOSDParamReq{" +
                "account='" + account + '\'' +
                ", session='" + session + '\'' +
                ", devId='" + devId + '\'' +
                ", channelNo=" + channelNo +
                ", enable=" + enable +
                ", timestanpEnable=" + timestanpEnable +
                ", dateTimeFormat='" + dateTimeFormat + '\'' +
                ", displayText='" + displayText + '\'' +
                ", fontSize=" + fontSize +
                ", fontColor=" + fontColor +
                ", textPositionX=" + textPositionX +
                ", textPositionY=" + textPositionY +
                ", timestampPositionX=" + timestampPositionX +
                ", timestampPositionY=" + timestampPositionY +
                '}';
    }

    public SetOSDParamReq() {
    }

    public SetOSDParamReq(String account, String session, String devId, int channelNo, Boolean enable, Boolean timestanpEnable, String dateTimeFormat, String displayText, int fontSize, int fontColor, int textPositionX, int textPositionY, int timestampPositionX, int timestampPositionY) {

        this.account = account;
        this.session = session;
        this.devId = devId;
        this.channelNo = channelNo;
        this.enable = enable;
        this.timestanpEnable = timestanpEnable;
        this.dateTimeFormat = dateTimeFormat;
        this.displayText = displayText;
        this.fontSize = fontSize;
        this.fontColor = fontColor;
        this.textPositionX = textPositionX;
        this.textPositionY = textPositionY;
        this.timestampPositionX = timestampPositionX;
        this.timestampPositionY = timestampPositionY;
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

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getTimestanpEnable() {
        return timestanpEnable;
    }

    public void setTimestanpEnable(Boolean timestanpEnable) {
        this.timestanpEnable = timestanpEnable;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    public int getTextPositionX() {
        return textPositionX;
    }

    public void setTextPositionX(int textPositionX) {
        this.textPositionX = textPositionX;
    }

    public int getTextPositionY() {
        return textPositionY;
    }

    public void setTextPositionY(int textPositionY) {
        this.textPositionY = textPositionY;
    }

    public int getTimestampPositionX() {
        return timestampPositionX;
    }

    public void setTimestampPositionX(int timestampPositionX) {
        this.timestampPositionX = timestampPositionX;
    }

    public int getTimestampPositionY() {
        return timestampPositionY;
    }

    public void setTimestampPositionY(int timestampPositionY) {
        this.timestampPositionY = timestampPositionY;
    }
}
