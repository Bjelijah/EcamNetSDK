package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/11.
 */

public class EventLinkageList {
    Page page;
    ArrayList<EventLinkage> eventLinkages;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<EventLinkage> getEventLinkages() {
        return eventLinkages;
    }

    public void setEventLinkages(ArrayList<EventLinkage> eventLinkages) {
        this.eventLinkages = eventLinkages;
    }

    public EventLinkageList(Page page, ArrayList<EventLinkage> eventLinkages) {
        this.page = page;
        this.eventLinkages = eventLinkages;
    }

    public EventLinkageList() {
    }

    @Override
    public String toString() {
        return "EventLinkageList{" +
                "page=" + page +
                ", eventLinkages=" + eventLinkages +
                '}';
    }
}
