package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/19.
 */

public class StorageMediumAbnormalLogList {
    Page page;
    ArrayList<StorageMediumAbnormalLog> logs;

    @Override
    public String toString() {
        return "StorageMediumAbnormalLogList{" +
                "page=" + page +
                ", logs=" + logs +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<StorageMediumAbnormalLog> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<StorageMediumAbnormalLog> logs) {
        this.logs = logs;
    }

    public StorageMediumAbnormalLogList() {

    }

    public StorageMediumAbnormalLogList(Page page, ArrayList<StorageMediumAbnormalLog> logs) {

        this.page = page;
        this.logs = logs;
    }
}
