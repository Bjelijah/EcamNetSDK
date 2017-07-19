package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/19.
 */

public class QueryClientVersionReq {
    String clientType;
    String ApplicationID;

    @Override
    public String toString() {
        return "QueryClientVersionReq{" +
                "clientType='" + clientType + '\'' +
                ", ApplicationID='" + ApplicationID + '\'' +
                '}';
    }

    public QueryClientVersionReq() {
        this.clientType = "Android";
    }

    public QueryClientVersionReq(String clientType, String applicationID) {

        this.clientType = clientType;
        ApplicationID = applicationID;
    }

    public String getClientType() {

        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getApplicationID() {
        return ApplicationID;
    }

    public void setApplicationID(String applicationID) {
        ApplicationID = applicationID;
    }
}
