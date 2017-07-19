package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/12.
 */

public class DeviceList {
    Page page;
    ArrayList<Device> devices;

    public DeviceList(Page page, ArrayList<Device> devices) {
        this.page = page;
        this.devices = devices;
    }

    public DeviceList() {
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    @Override
    public String toString() {
        return "DeviceList{" +
                "page=" + page +
                ", devices=" + devices +
                '}';
    }
}
