package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/19.
 */

public class NoticeList {
    Page page;
    ArrayList<Notice> notices;

    @Override
    public String toString() {
        return "NoticeList{" +
                "page=" + page +
                ", notices=" + notices +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<Notice> getNotices() {
        return notices;
    }

    public void setNotices(ArrayList<Notice> notices) {
        this.notices = notices;
    }

    public NoticeList() {

    }

    public NoticeList(Page page, ArrayList<Notice> notices) {

        this.page = page;
        this.notices = notices;
    }
}
