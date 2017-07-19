package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/12.
 */

public class VideoInputChannelList {
    Page page;
    ArrayList<VideoInputChannel> channels;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<VideoInputChannel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<VideoInputChannel> channels) {
        this.channels = channels;
    }

    public VideoInputChannelList(Page page, ArrayList<VideoInputChannel> channels) {
        this.page = page;
        this.channels = channels;
    }

    public VideoInputChannelList() {
    }

    @Override
    public String toString() {
        return "VideoInputChannelList{" +
                "page=" + page +
                ", channels=" + channels +
                '}';
    }
}
