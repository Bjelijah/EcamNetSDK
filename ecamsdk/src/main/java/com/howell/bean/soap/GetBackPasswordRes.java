package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/16.
 */

public class GetBackPasswordRes {
    String result;
    String address;

    @Override
    public String toString() {
        return "GetBackPasswordRes{" +
                "result='" + result + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public GetBackPasswordRes() {
    }

    public GetBackPasswordRes(String result, String address) {

        this.result = result;
        this.address = address;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
