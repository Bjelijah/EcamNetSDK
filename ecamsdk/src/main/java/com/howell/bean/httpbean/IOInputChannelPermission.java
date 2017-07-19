package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/10.
 */

public class IOInputChannelPermission {
    String id;
    String name;
    String permission;
    IOInputChannel ioInputChannel;
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

    public IOInputChannel getIoInputChannel() {
        return ioInputChannel;
    }

    public void setIoInputChannel(IOInputChannel ioInputChannel) {
        this.ioInputChannel = ioInputChannel;
    }

    public boolean isFromDepartment() {
        return isFromDepartment;
    }

    public void setFromDepartment(boolean fromDepartment) {
        isFromDepartment = fromDepartment;
    }

    public IOInputChannelPermission(String id, String name, String permission, IOInputChannel ioInputChannel, boolean isFromDepartment) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.ioInputChannel = ioInputChannel;
        this.isFromDepartment = isFromDepartment;
    }

    public IOInputChannelPermission() {
    }

    @Override
    public String toString() {
        return "IOInputChannelPermission{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", permission='" + permission + '\'' +
                ", ioInputChannel=" + ioInputChannel +
                ", isFromDepartment=" + isFromDepartment +
                '}';
    }
}
