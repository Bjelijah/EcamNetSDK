package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/19.
 */

public class QueryMCUDeviceAuthenticatedReq {
    String UUID;

    @Override
    public String toString() {
        return "QueryMCUDeviceAuthenticatedReq{" +
                "UUID='" + UUID + '\'' +
                '}';
    }

    public QueryMCUDeviceAuthenticatedReq() {
    }

    public QueryMCUDeviceAuthenticatedReq(String UUID) {

        this.UUID = UUID;
    }

    public String getUUID() {

        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
