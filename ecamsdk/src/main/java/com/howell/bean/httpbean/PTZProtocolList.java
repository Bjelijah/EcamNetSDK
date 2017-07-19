package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/14.
 */

public class PTZProtocolList {
    Page page;
    ArrayList<PTZProtocol> ptzProtocols;

    @Override
    public String toString() {
        return "PTZProtocolList{" +
                "page=" + page +
                ", ptzProtocols=" + ptzProtocols +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<PTZProtocol> getPtzProtocols() {
        return ptzProtocols;
    }

    public void setPtzProtocols(ArrayList<PTZProtocol> ptzProtocols) {
        this.ptzProtocols = ptzProtocols;
    }

    public PTZProtocolList() {

    }

    public PTZProtocolList(Page page, ArrayList<PTZProtocol> ptzProtocols) {

        this.page = page;
        this.ptzProtocols = ptzProtocols;
    }
}
