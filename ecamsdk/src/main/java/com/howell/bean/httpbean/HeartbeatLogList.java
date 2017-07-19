package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/19.
 */

public class HeartbeatLogList {
    Page page;
    ArrayList<HeartbeatLog> logs;

    @Override
    public String toString() {
        return "HeartbeatLogList{" +
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

    public ArrayList<HeartbeatLog> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<HeartbeatLog> logs) {
        this.logs = logs;
    }

    public HeartbeatLogList() {

    }

    public HeartbeatLogList(Page page, ArrayList<HeartbeatLog> logs) {

        this.page = page;
        this.logs = logs;
    }
}
