package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/14.
 */

public class PlaybackSourceList {
    Page page;
    ArrayList<PlaybackSource> sources;

    @Override
    public String toString() {
        return "PlaybackSourceList{" +
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

    public ArrayList<PlaybackSource> getSources() {
        return sources;
    }

    public void setSources(ArrayList<PlaybackSource> sources) {
        this.sources = sources;
    }

    public PlaybackSourceList() {

    }

    public PlaybackSourceList(Page page, ArrayList<PlaybackSource> sources) {

        this.page = page;
        this.sources = sources;
    }
}
