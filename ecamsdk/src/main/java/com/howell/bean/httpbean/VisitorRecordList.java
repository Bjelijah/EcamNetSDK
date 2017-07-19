package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/12.
 */

public class VisitorRecordList {
    Page page;
    ArrayList<VisitorRecord> visitorRecords;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<VisitorRecord> getVisitorRecords() {
        return visitorRecords;
    }

    public void setVisitorRecords(ArrayList<VisitorRecord> visitorRecords) {
        this.visitorRecords = visitorRecords;
    }

    public VisitorRecordList(Page page, ArrayList<VisitorRecord> visitorRecords) {
        this.page = page;
        this.visitorRecords = visitorRecords;
    }

    public VisitorRecordList() {
    }

    @Override
    public String toString() {
        return "VisitorRecordList{" +
                "page=" + page +
                ", visitorRecords=" + visitorRecords +
                '}';
    }
}
