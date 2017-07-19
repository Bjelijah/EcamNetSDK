package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/13.
 */

public class NTPServerList {
    Page page;
    ArrayList<NTPServer> servers;

    @Override
    public String toString() {
        return "NTPServerList{" +
                "page=" + page +
                ", servers=" + servers +
                '}';
    }

    public NTPServerList() {
    }

    public NTPServerList(Page page, ArrayList<NTPServer> servers) {

        this.page = page;
        this.servers = servers;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<NTPServer> getServers() {
        return servers;
    }

    public void setServers(ArrayList<NTPServer> servers) {
        this.servers = servers;
    }
}
