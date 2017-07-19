package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/11.
 */

public class SystemFaultReportList {
    Page page;
    ArrayList<SystemFaultReport> reports;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<SystemFaultReport> getReports() {
        return reports;
    }

    public void setReports(ArrayList<SystemFaultReport> reports) {
        this.reports = reports;
    }

    public SystemFaultReportList(Page page, ArrayList<SystemFaultReport> reports) {
        this.page = page;
        this.reports = reports;
    }

    public SystemFaultReportList() {
    }

    @Override
    public String toString() {
        return "SystemFaultReportList{" +
                "page=" + page +
                ", reports=" + reports +
                '}';
    }
}
