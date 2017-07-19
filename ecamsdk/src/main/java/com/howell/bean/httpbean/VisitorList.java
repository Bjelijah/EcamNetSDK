package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/12.
 */

public class VisitorList {
    Page page;
    ArrayList<Visitor> visitors;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(ArrayList<Visitor> visitors) {
        this.visitors = visitors;
    }

    public VisitorList(Page page, ArrayList<Visitor> visitors) {
        this.page = page;
        this.visitors = visitors;
    }

    public VisitorList() {
    }

    @Override
    public String toString() {
        return "VisitorList{" +
                "page=" + page +
                ", visitors=" + visitors +
                '}';
    }
}
