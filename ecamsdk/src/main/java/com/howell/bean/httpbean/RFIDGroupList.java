package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/19.
 */

public class RFIDGroupList {
    Page page;
    ArrayList<RFIDGroup> groups;

    @Override
    public String toString() {
        return "RFIDGroupList{" +
                "page=" + page +
                ", groups=" + groups +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<RFIDGroup> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<RFIDGroup> groups) {
        this.groups = groups;
    }

    public RFIDGroupList() {

    }

    public RFIDGroupList(Page page, ArrayList<RFIDGroup> groups) {

        this.page = page;
        this.groups = groups;
    }
}
