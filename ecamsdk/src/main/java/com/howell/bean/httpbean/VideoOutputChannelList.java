package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/12.
 */

public class VideoOutputChannelList {
    Page page;
    ArrayList<VideoOutputChannel> channels;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<VideoOutputChannel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<VideoOutputChannel> channels) {
        this.channels = channels;
    }

    public VideoOutputChannelList(Page page, ArrayList<VideoOutputChannel> channels) {
        this.page = page;
        this.channels = channels;
    }

    public VideoOutputChannelList() {
    }

    @Override
    public String toString() {
        return "VideoOutputChannelList{" +
                "page=" + page +
                ", channels=" + channels +
                '}';
    }
}
