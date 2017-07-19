package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/7.
 */

public class User {
    String id;
    String userName;
    String nickName;
    String password;
    String mobile;
    String phone;
    String uniformedId;
    String duty;
    String information;
    String sex;
    String permission;
    String detailPermission;
    int deviceCount;
    int videoInputChannelCount;
    int videoOutputChannelCount;
    int IOInputChannelCount;
    int IOOutputChannelCount;
    int departmentCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUniformedId() {
        return uniformedId;
    }

    public void setUniformedId(String uniformedId) {
        this.uniformedId = uniformedId;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public int getDepartmentCount() {
        return departmentCount;
    }

    public void setDepartmentCount(int departmentCount) {
        this.departmentCount = departmentCount;
    }

    public User(String id, String userName, String nickName, String password, String mobile, String phone, String uniformedId, String duty, String information, String sex, String permission, String detailPermission, int deviceCount, int videoInputChannelCount, int videoOutputChannelCount, int IOInputChannelCount, int IOOutputChannelCount, int departmentCount) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.mobile = mobile;
        this.phone = phone;
        this.uniformedId = uniformedId;
        this.duty = duty;
        this.information = information;
        this.sex = sex;
        this.permission = permission;
        this.detailPermission = detailPermission;
        this.deviceCount = deviceCount;
        this.videoInputChannelCount = videoInputChannelCount;
        this.videoOutputChannelCount = videoOutputChannelCount;
        this.IOInputChannelCount = IOInputChannelCount;
        this.IOOutputChannelCount = IOOutputChannelCount;
        this.departmentCount = departmentCount;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", phone='" + phone + '\'' +
                ", uniformedId='" + uniformedId + '\'' +
                ", duty='" + duty + '\'' +
                ", information='" + information + '\'' +
                ", sex='" + sex + '\'' +
                ", permission='" + permission + '\'' +
                ", detailPermission='" + detailPermission + '\'' +
                ", deviceCount=" + deviceCount +
                ", videoInputChannelCount=" + videoInputChannelCount +
                ", videoOutputChannelCount=" + videoOutputChannelCount +
                ", IOInputChannelCount=" + IOInputChannelCount +
                ", IOOutputChannelCount=" + IOOutputChannelCount +
                ", departmentCount=" + departmentCount +
                '}';
    }
}
