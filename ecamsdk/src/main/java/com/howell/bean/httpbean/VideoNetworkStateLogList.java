package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class VideoNetworkStateLogList {
    Page page;
    ArrayList<VideoNetworkStateLog> logs;

    @Override
    public String toString() {
        return "VideoNetworkStateLogList{" +
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

    public ArrayList<VideoNetworkStateLog> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<VideoNetworkStateLog> logs) {
        this.logs = logs;
    }

    public VideoNetworkStateLogList() {

    }

    public VideoNetworkStateLogList(Page page, ArrayList<VideoNetworkStateLog> logs) {

        this.page = page;
        this.logs = logs;
    }
}
