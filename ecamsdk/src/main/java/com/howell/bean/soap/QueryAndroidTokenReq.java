package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/19.
 */

public class QueryAndroidTokenReq {
    String account;
    String session;
    String UDID;

    @Override
    public String toString() {
        return "QueryAndroidTokenReq{" +
                "account='" + account + '\'' +
                ", session='" + session + '\'' +
                ", UDID='" + UDID + '\'' +
                '}';
    }

    public QueryAndroidTokenReq() {
    }

    public QueryAndroidTokenReq(String account, String session, String UDID) {

        this.account = account;
        this.session = session;
        this.UDID = UDID;
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

    public String getUDID() {
        return UDID;
    }

    public void setUDID(String UDID) {
        this.UDID = UDID;
    }
}
