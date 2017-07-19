package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/19.
 */

public class QueryDeviceBondedRes {
    String result;
    Boolean hasBonded;

    @Override
    public String toString() {
        return "QueryDeviceBondedRes{" +
                "result='" + result + '\'' +
                ", hasBonded=" + hasBonded +
                '}';
    }

    public QueryDeviceBondedRes() {
    }

    public QueryDeviceBondedRes(String result, Boolean hasBonded) {

        this.result = result;
        this.hasBonded = hasBonded;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean getHasBonded() {
        return hasBonded;
    }

    public void setHasBonded(Boolean hasBonded) {
        this.hasBonded = hasBonded;
    }
}
