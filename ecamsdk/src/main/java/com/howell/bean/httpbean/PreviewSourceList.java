package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/14.
 */

public class PreviewSourceList {
    Page page;
    ArrayList<PreviewSource> sources;

    @Override
    public String toString() {
        return "PreviewSourceList{" +
                "page=" + page +
                ", sources=" + sources +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<PreviewSource> getSources() {
        return sources;
    }

    public void setSources(ArrayList<PreviewSource> sources) {
        this.sources = sources;
    }

    public PreviewSourceList() {

    }

    public PreviewSourceList(Page page, ArrayList<PreviewSource> sources) {

        this.page = page;
        this.sources = sources;
    }
}
