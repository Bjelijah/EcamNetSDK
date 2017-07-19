package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */

public class IOInputChannelPermissionList {
    Page page;
    ArrayList<IOInputChannelPermission> ioInputChannelPermissions;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<IOInputChannelPermission> getIoInputChannelPermissions() {
        return ioInputChannelPermissions;
    }

    public void setIoInputChannelPermissions(ArrayList<IOInputChannelPermission> ioInputChannelPermissions) {
        this.ioInputChannelPermissions = ioInputChannelPermissions;
    }

    public IOInputChannelPermissionList(Page page, ArrayList<IOInputChannelPermission> ioInputChannelPermissions) {
        this.page = page;
        this.ioInputChannelPermissions = ioInputChannelPermissions;
    }

    public IOInputChannelPermissionList() {
    }

    @Override
    public String toString() {
        return "IOInputChannelPermissionList{" +
                "page=" + page +
                ", ioInputChannelPermissions=" + ioInputChannelPermissions +
                '}';
    }
}
