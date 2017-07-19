package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class VideoConnectionLogList {
    Page page;

    @Override
    public String toString() {
        return "VideoConnectionLogList{" +
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

    public ArrayList<VideoConnectionLog> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<VideoConnectionLog> logs) {
        this.logs = logs;
    }

    public VideoConnectionLogList() {

    }

    public VideoConnectionLogList(Page page, ArrayList<VideoConnectionLog> logs) {

        this.page = page;
        this.logs = logs;
    }

    ArrayList<VideoConnectionLog> logs;
}
