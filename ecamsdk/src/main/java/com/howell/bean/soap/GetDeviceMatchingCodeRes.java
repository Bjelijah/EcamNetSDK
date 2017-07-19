package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/19.
 */

public class GetDeviceMatchingCodeRes {
    String result;
    String matchCode;

    public GetDeviceMatchingCodeRes() {
    }

    @Override

    public String toString() {
        return "GetDeviceMatchingCodeRes{" +
                "result='" + result + '\'' +
                ", matchCode='" + matchCode + '\'' +
                '}';
    }

    public GetDeviceMatchingCodeRes(String result, String matchCode) {
        this.result = result;
        this.matchCode = matchCode;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }
}
