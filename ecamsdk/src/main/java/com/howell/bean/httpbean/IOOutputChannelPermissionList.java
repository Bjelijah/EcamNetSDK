package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/11.
 */

public class IOOutputChannelPermissionList {
    Page page;
    ArrayList<IOOutputChannelPermission> ioOutputChannelPermissions;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<IOOutputChannelPermission> getIoOutputChannelPermissions() {
        return ioOutputChannelPermissions;
    }

    public void setIoOutputChannelPermissions(ArrayList<IOOutputChannelPermission> ioOutputChannelPermissions) {
        this.ioOutputChannelPermissions = ioOutputChannelPermissions;
    }

    public IOOutputChannelPermissionList(Page page, ArrayList<IOOutputChannelPermission> ioOutputChannelPermissions) {
        this.page = page;
        this.ioOutputChannelPermissions = ioOutputChannelPermissions;
    }

    public IOOutputChannelPermissionList() {
    }

    @Override
    public String toString() {
        return "IOOutputChannelPermissionList{" +
                "page=" + page +
                ", ioOutputChannelPermissions=" + ioOutputChannelPermissions +
                '}';
    }
}
