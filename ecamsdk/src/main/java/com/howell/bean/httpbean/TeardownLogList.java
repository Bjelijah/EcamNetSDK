package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class TeardownLogList {
    Page page;

    @Override
    public String toString() {
        return "TeardownLogList{" +
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

    public ArrayList<TeardownLog> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<TeardownLog> logs) {
        this.logs = logs;
    }

    public TeardownLogList() {

    }

    public TeardownLogList(Page page, ArrayList<TeardownLog> logs) {

        this.page = page;
        this.logs = logs;
    }

    ArrayList<TeardownLog> logs;
}
