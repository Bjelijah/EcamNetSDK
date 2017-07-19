package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/16.
 */

public class GetAuxiliaryRes {
    String result;
    String auxiliaryState;

    @Override
    public String toString() {
        return "GetAuxiliaryRes{" +
                "result='" + result + '\'' +
                ", auxiliaryState='" + auxiliaryState + '\'' +
                '}';
    }

    public GetAuxiliaryRes() {
    }

    public GetAuxiliaryRes(String result, String auxiliaryState) {

        this.result = result;
        this.auxiliaryState = auxiliaryState;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAuxiliaryState() {
        return auxiliaryState;
    }

    public void setAuxiliaryState(String auxiliaryState) {
        this.auxiliaryState = auxiliaryState;
    }
}
