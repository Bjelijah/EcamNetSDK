package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/13.
 */

public class DecodingChannelList {
    Page page;
    ArrayList<DecodingChannel> channels;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<DecodingChannel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<DecodingChannel> channels) {
        this.channels = channels;
    }

    public DecodingChannelList(Page page, ArrayList<DecodingChannel> channels) {
        this.page = page;
        this.channels = channels;
    }

    public DecodingChannelList() {
    }

    @Override
    public String toString() {
        return "DecodingChannelList{" +
                "page=" + page +
                ", channels=" + channels +
                '}';
    }
}
