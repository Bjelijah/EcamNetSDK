package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/17.
 */

public class BitrateStatusList {
    Page page;
    ArrayList<BitrateStatus> status;

    @Override
    public String toString() {
        return "BitrateStatusList{" +
                "page=" + page +
                ", status=" + status +
                '}';
    }

    public ArrayList<BitrateStatus> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<BitrateStatus> status) {
        this.status = status;
    }

    public Page getPage() {

        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public BitrateStatusList() {

    }

    public BitrateStatusList(Page page, ArrayList<BitrateStatus> status) {

        this.page = page;
        this.status = status;
    }
}
