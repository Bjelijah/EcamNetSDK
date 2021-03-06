package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/26.
 */

public class VehiclePlateDeviceList {
    Page page;
    ArrayList<VehiclePlateDevice> devices;

    @Override
    public String toString() {
        return "VehiclePlateDeviceList{" +
                "page=" + page +
                ", devices=" + devices +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<VehiclePlateDevice> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<VehiclePlateDevice> devices) {
        this.devices = devices;
    }

    public VehiclePlateDeviceList() {

    }

    public VehiclePlateDeviceList(Page page, ArrayList<VehiclePlateDevice> devices) {

        this.page = page;
        this.devices = devices;
    }
}
