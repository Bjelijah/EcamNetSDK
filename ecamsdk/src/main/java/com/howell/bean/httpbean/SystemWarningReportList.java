package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/11.
 */

public class SystemWarningReportList {
    Page page;
    ArrayList<SystemWarningReport> reports;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<SystemWarningReport> getReports() {
        return reports;
    }

    public void setReports(ArrayList<SystemWarningReport> reports) {
        this.reports = reports;
    }

    public SystemWarningReportList(Page page, ArrayList<SystemWarningReport> reports) {
        this.page = page;
        this.reports = reports;
    }

    public SystemWarningReportList() {
    }

    @Override
    public String toString() {
        return "SystemWarningReportList{" +
                "page=" + page +
                ", reports=" + reports +
                '}';
    }
}
