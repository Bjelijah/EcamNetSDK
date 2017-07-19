package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */

public class VideoOutputChannelPermissionList {
    Page page;
    ArrayList<VideoOutputChannelPermission> videoOutputChannelPermissions;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<VideoOutputChannelPermission> getVideoOutputChannelPermissions() {
        return videoOutputChannelPermissions;
    }

    public void setVideoOutputChannelPermissions(ArrayList<VideoOutputChannelPermission> videoOutputChannelPermissions) {
        this.videoOutputChannelPermissions = videoOutputChannelPermissions;
    }

    public VideoOutputChannelPermissionList(Page page, ArrayList<VideoOutputChannelPermission> videoOutputChannelPermissions) {
        this.page = page;
        this.videoOutputChannelPermissions = videoOutputChannelPermissions;
    }

    public VideoOutputChannelPermissionList() {
    }

    @Override
    public String toString() {
        return "VideoOutputChannelPermissionList{" +
                "page=" + page +
                ", videoOutputChannelPermissions=" + videoOutputChannelPermissions +
                '}';
    }
}
