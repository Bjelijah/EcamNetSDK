package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/10.
 */

public class DecodingChannel {
    String id;
    boolean enable;
    String supportedCodecFormats;
    int pseudoCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getSupportedCodecFormats() {
        return supportedCodecFormats;
    }

    public void setSupportedCodecFormats(String supportedCodecFormats) {
        this.supportedCodecFormats = supportedCodecFormats;
    }

    public int getPseudoCode() {
        return pseudoCode;
    }

    public void setPseudoCode(int pseudoCode) {
        this.pseudoCode = pseudoCode;
    }

    public DecodingChannel(String id, boolean enable, String supportedCodecFormats, int pseudoCode) {
        this.id = id;
        this.enable = enable;
        this.supportedCodecFormats = supportedCodecFormats;
        this.pseudoCode = pseudoCode;
    }

    public DecodingChannel() {
    }

    @Override
    public String toString() {
        return "DecodingChannel{" +
                "id='" + id + '\'' +
                ", enable=" + enable +
                ", supportedCodecFormats='" + supportedCodecFormats + '\'' +
                ", pseudoCode=" + pseudoCode +
                '}';
    }
}
