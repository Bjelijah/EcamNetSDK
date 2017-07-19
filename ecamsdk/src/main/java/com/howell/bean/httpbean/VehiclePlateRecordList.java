package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/26.
 */

public class VehiclePlateRecordList {
    Page page;
    ArrayList<VehiclePlateRecord> records;

    @Override
    public String toString() {
        return "VehiclePlateRecordList{" +
                "page=" + page +
                ", records=" + records +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<VehiclePlateRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<VehiclePlateRecord> records) {
        this.records = records;
    }

    public VehiclePlateRecordList() {

    }

    public VehiclePlateRecordList(Page page, ArrayList<VehiclePlateRecord> records) {

        this.page = page;
        this.records = records;
    }
}
