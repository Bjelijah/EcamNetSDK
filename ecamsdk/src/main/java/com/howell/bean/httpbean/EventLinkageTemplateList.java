package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class EventLinkageTemplateList {
    Page page;
    ArrayList<EventLinkageTemplate> templates ;

    @Override
    public String toString() {
        return "EventLinkageTemplateList{" +
                "page=" + page +
                ", templates=" + templates +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<EventLinkageTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(ArrayList<EventLinkageTemplate> templates) {
        this.templates = templates;
    }

    public EventLinkageTemplateList() {

    }

    public EventLinkageTemplateList(Page page, ArrayList<EventLinkageTemplate> templates) {

        this.page = page;
        this.templates = templates;
    }
}
