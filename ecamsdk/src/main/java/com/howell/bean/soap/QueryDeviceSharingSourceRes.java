package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/19.
 */

public class QueryDeviceSharingSourceRes {
    String result;
    String sharingSourceAccount;

    @Override
    public String toString() {
        return "QueryDeviceSharingSourceRes{" +
                "result='" + result + '\'' +
                ", sharingSourceAccount='" + sharingSourceAccount + '\'' +
                '}';
    }

    public QueryDeviceSharingSourceRes() {
    }

    public QueryDeviceSharingSourceRes(String result, String sharingSourceAccount) {

        this.result = result;
        this.sharingSourceAccount = sharingSourceAccount;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSharingSourceAccount() {
        return sharingSourceAccount;
    }

    public void setSharingSourceAccount(String sharingSourceAccount) {
        this.sharingSourceAccount = sharingSourceAccount;
    }
}
