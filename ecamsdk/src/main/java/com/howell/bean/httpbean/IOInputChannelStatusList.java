package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/11.
 */

public class IOInputChannelStatusList {
    Page page;
    ArrayList<IOInputChannelStatus> statuses;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<IOInputChannelStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<IOInputChannelStatus> statuses) {
        this.statuses = statuses;
    }

    public IOInputChannelStatusList(Page page, ArrayList<IOInputChannelStatus> statuses) {
        this.page = page;
        this.statuses = statuses;
    }

    public IOInputChannelStatusList() {
    }

    @Override
    public String toString() {
        return "IOInputChannelStatusList{" +
                "page=" + page +
                ", statuses=" + statuses +
                '}';
    }
}
