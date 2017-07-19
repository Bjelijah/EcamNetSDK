package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/12.
 */

public class NetworkInterfaceList {
    Page page;
    ArrayList<NetworkInterface> interfaces;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<NetworkInterface> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(ArrayList<NetworkInterface> interfaces) {
        this.interfaces = interfaces;
    }

    public NetworkInterfaceList(Page page, ArrayList<NetworkInterface> interfaces) {
        this.page = page;
        this.interfaces = interfaces;
    }

    public NetworkInterfaceList() {
    }

    @Override
    public String toString() {
        return "NetworkInterfaceList{" +
                "page=" + page +
                ", interfaces=" + interfaces +
                '}';
    }
}
