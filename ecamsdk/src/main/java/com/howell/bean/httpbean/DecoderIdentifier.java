package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/11.
 */

public class DecoderIdentifier {
    String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DecoderIdentifier(String deviceId) {
        this.deviceId = deviceId;
    }

    public DecoderIdentifier() {
    }

    @Override
    public String toString() {
        return "DecoderIdentifier{" +
                "deviceId='" + deviceId + '\'' +
                '}';
    }
}
