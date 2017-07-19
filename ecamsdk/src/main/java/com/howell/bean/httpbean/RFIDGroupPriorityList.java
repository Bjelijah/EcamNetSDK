package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/19.
 */

public class RFIDGroupPriorityList {
    Page page;
    ArrayList<RFIDGroupPriority> prioritys;

    @Override
    public String toString() {
        return "RFIDGroupPriorityList{" +
                "page=" + page +
                ", prioritys=" + prioritys +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<RFIDGroupPriority> getPrioritys() {
        return prioritys;
    }

    public void setPrioritys(ArrayList<RFIDGroupPriority> prioritys) {
        this.prioritys = prioritys;
    }

    public RFIDGroupPriorityList() {

    }

    public RFIDGroupPriorityList(Page page, ArrayList<RFIDGroupPriority> prioritys) {

        this.page = page;
        this.prioritys = prioritys;
    }
}
