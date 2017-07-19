package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/10.
 */

public class IOOutputChannelPermission {
    String id;
    String name;
    String permission;
    IOOutputChannel ioOutputChannel;
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

    public IOOutputChannel getIoOutputChannel() {
        return ioOutputChannel;
    }

    public void setIoOutputChannel(IOOutputChannel ioOutputChannel) {
        this.ioOutputChannel = ioOutputChannel;
    }

    public boolean isFromDepartment() {
        return isFromDepartment;
    }

    public void setFromDepartment(boolean fromDepartment) {
        isFromDepartment = fromDepartment;
    }

    public IOOutputChannelPermission(String id, String name, String permission, IOOutputChannel ioOutputChannel, boolean isFromDepartment) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.ioOutputChannel = ioOutputChannel;
        this.isFromDepartment = isFromDepartment;
    }

    public IOOutputChannelPermission() {
    }

    @Override
    public String toString() {
        return "IOOutputChannelPermission{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                ", ioOutputChannel=" + ioOutputChannel +
                ", isFromDepartment=" + isFromDepartment +
                '}';
    }
}
