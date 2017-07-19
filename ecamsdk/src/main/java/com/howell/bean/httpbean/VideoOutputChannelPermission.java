package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/10.
 */

public class VideoOutputChannelPermission {
    String id;
    String name;
    String permission;
    VideoOutputChannel videoOutputChannel;
    boolean isFromDepartment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public VideoOutputChannel getVideoOutputChannel() {
        return videoOutputChannel;
    }

    public void setVideoOutputChannel(VideoOutputChannel videoOutputChannel) {
        this.videoOutputChannel = videoOutputChannel;
    }

    public boolean isFromDepartment() {
        return isFromDepartment;
    }

    public void setFromDepartment(boolean fromDepartment) {
        isFromDepartment = fromDepartment;
    }

    public VideoOutputChannelPermission(String id, String name, String permission, VideoOutputChannel videoOutputChannel, boolean isFromDepartment) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.videoOutputChannel = videoOutputChannel;
        this.isFromDepartment = isFromDepartment;
    }

    public VideoOutputChannelPermission() {
    }

    @Override
    public String toString() {
        return "VideoOutputChannelPermission{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                ", videoOutputChannel=" + videoOutputChannel +
                ", isFromDepartment=" + isFromDepartment +
                '}';
    }
}
