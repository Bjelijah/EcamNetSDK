package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/19.
 */

public class QueryDeviceStatusReq {
    String account;
    String session;
    int pageNo;
    String searchID;
    int pageSize;

    @Override
    public String toString() {
        return "QueryDeviceStatusReq{" +
                "account='" + account + '\'' +
                ", session='" + session + '\'' +
                ", pageNo=" + pageNo +
                ", searchID='" + searchID + '\'' +
                ", pageSize=" + pageSize +
                '}';
    }

    public QueryDeviceStatusReq() {
    }

    public QueryDeviceStatusReq(String account, String session, int pageNo, String searchID, int pageSize) {

        this.account = account;
        this.session = session;
        this.pageNo = pageNo;
        this.searchID = searchID;
        this.pageSize = pageSize;
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

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getSearchID() {
        return searchID;
    }

    public void setSearchID(String searchID) {
        this.searchID = searchID;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
