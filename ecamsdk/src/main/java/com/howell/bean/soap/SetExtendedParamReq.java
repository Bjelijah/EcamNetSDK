package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/19.
 */

public class SetExtendedParamReq {
    String account;
    String session;
    String devID;
    int lightingDuration;

    @Override
    public String toString() {
        return "SetExtendedParamReq{" +
                "account='" + account + '\'' +
                ", session='" + session + '\'' +
                ", devID='" + devID + '\'' +
                ", lightingDuration=" + lightingDuration +
                '}';
    }

    public SetExtendedParamReq() {
    }

    public SetExtendedParamReq(String account, String session, String devID, int lightingDuration) {

        this.account = account;
        this.session = session;
        this.devID = devID;
        this.lightingDuration = lightingDuration;
    }

    public String getAccount() {

        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public int getLightingDuration() {
        return lightingDuration;
    }

    public void setLightingDuration(int lightingDuration) {
        this.lightingDuration = lightingDuration;
    }
}
