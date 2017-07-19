package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/19.
 */

public class GetExtendedParamRes {
    String result;
    int lightingDuration;

    @Override
    public String toString() {
        return "GetExtendedParamRes{" +
                "result='" + result + '\'' +
                ", lightingDuration=" + lightingDuration +
                '}';
    }

    public GetExtendedParamRes() {
    }

    public GetExtendedParamRes(String result, int lightingDuration) {

        this.result = result;
        this.lightingDuration = lightingDuration;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getLightingDuration() {
        return lightingDuration;
    }

    public void setLightingDuration(int lightingDuration) {
        this.lightingDuration = lightingDuration;
    }
}
