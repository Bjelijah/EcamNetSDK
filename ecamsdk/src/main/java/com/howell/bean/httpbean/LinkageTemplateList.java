package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class LinkageTemplateList {
    Page page;
    ArrayList<LinkageTemplate> linkageTemplates;

    @Override
    public String toString() {
        return "LinkageTemplateList{" +
                "page=" + page +
                ", linkageTemplates=" + linkageTemplates +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<LinkageTemplate> getLinkageTemplates() {
        return linkageTemplates;
    }

    public void setLinkageTemplates(ArrayList<LinkageTemplate> linkageTemplates) {
        this.linkageTemplates = linkageTemplates;
    }

    public LinkageTemplateList() {

    }

    public LinkageTemplateList(Page page, ArrayList<LinkageTemplate> linkageTemplates) {

        this.page = page;
        this.linkageTemplates = linkageTemplates;
    }
}
