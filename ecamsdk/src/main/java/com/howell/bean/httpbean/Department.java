package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/10.
 */

public class Department {
    String id;
    String name;
    String information;
    String permission;
    String detailPermission;
    int deviceCount;
    int videoInputChannelCount;
    int videoOutputChannelCount;
    int IOInputChannelCount;
    int IOOutputChannelCount;
    int userCount;

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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDetailPermission() {
        return detailPermission;
    }

    public void setDetailPermission(String detailPermission) {
        this.detailPermission = detailPermission;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    public int getVideoInputChannelCount() {
        return videoInputChannelCount;
    }

    public void setVideoInputChannelCount(int videoInputChannelCount) {
        this.videoInputChannelCount = videoInputChannelCount;
    }

    public int getVideoOutputChannelCount() {
        return videoOutputChannelCount;
    }

    public void setVideoOutputChannelCount(int videoOutputChannelCount) {
        this.videoOutputChannelCount = videoOutputChannelCount;
    }

    public int getIOInputChannelCount() {
        return IOInputChannelCount;
    }

    public void setIOInputChannelCount(int IOInputChannelCount) {
        this.IOInputChannelCount = IOInputChannelCount;
    }

    public int getIOOutputChannelCount() {
        return IOOutputChannelCount;
    }

    public void setIOOutputChannelCount(int IOOutputChannelCount) {
        this.IOOutputChannelCount = IOOutputChannelCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public Department(String id, String name, String information, String permission, String detailPermission, int deviceCount, int videoInputChannelCount, int videoOutputChannelCount, int IOInputChannelCount, int IOOutputChannelCount, int userCount) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.permission = permission;
        this.detailPermission = detailPermission;
        this.deviceCount = deviceCount;
        this.videoInputChannelCount = videoInputChannelCount;
        this.videoOutputChannelCount = videoOutputChannelCount;
        this.IOInputChannelCount = IOInputChannelCount;
        this.IOOutputChannelCount = IOOutputChannelCount;
        this.userCount = userCount;
    }

    public Department() {
    }

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", permission='" + permission + '\'' +
                ", detailPermission='" + detailPermission + '\'' +
                ", deviceCount=" + deviceCount +
                ", videoInputChannelCount=" + videoInputChannelCount +
                ", videoOutputChannelCount=" + videoOutputChannelCount +
                ", IOInputChannelCount=" + IOInputChannelCount +
                ", IOOutputChannelCount=" + IOOutputChannelCount +
                ", userCount=" + userCount +
                '}';
    }
}
