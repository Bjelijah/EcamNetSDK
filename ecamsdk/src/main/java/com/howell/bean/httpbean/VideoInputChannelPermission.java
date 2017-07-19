package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/10.
 */

public class VideoInputChannelPermission {
    String id;
    String name;
    String permission;
    VideoInputChannel videoInputChannel;
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

    public VideoInputChannel getVideoInputChannel() {
        return videoInputChannel;
    }

    public void setVideoInputChannel(VideoInputChannel videoInputChannel) {
        this.videoInputChannel = videoInputChannel;
    }

    public boolean isFromDepartment() {
        return isFromDepartment;
    }

    public void setFromDepartment(boolean fromDepartment) {
        isFromDepartment = fromDepartment;
    }

    public VideoInputChannelPermission(String id, String name, String permission, VideoInputChannel videoInputChannel, boolean isFromDepartment) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.videoInputChannel = videoInputChannel;
        this.isFromDepartment = isFromDepartment;
    }

    public VideoInputChannelPermission() {
    }

    @Override
    public String toString() {
        return "VideoInputChannelPermission{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                ", videoInputChannel=" + videoInputChannel +
                ", isFromDepartment=" + isFromDepartment +
                '}';
    }
}
