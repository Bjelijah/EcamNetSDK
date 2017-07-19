package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/19.
 */

public class RFIDAntennaList {
    Page page;
    ArrayList<RFIDAntenna> rfidAntennas;

    @Override
    public String toString() {
        return "RFIDAntennaList{" +
                "page=" + page +
                ", rfidAntennas=" + rfidAntennas +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<RFIDAntenna> getRfidAntennas() {
        return rfidAntennas;
    }

    public void setRfidAntennas(ArrayList<RFIDAntenna> rfidAntennas) {
        this.rfidAntennas = rfidAntennas;
    }

    public RFIDAntennaList(Page page, ArrayList<RFIDAntenna> rfidAntennas) {
        this.page = page;
        this.rfidAntennas = rfidAntennas;
    }

    public RFIDAntennaList() {
    }
}
