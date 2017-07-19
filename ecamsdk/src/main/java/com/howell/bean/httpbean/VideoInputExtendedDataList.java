package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/17.
 */

public class VideoInputExtendedDataList {
    Page page;
    ArrayList<VideoInputExtendedData> extendedDatas;

    @Override
    public String toString() {
        return "VideoInputExtendedDataList{" +
                "page=" + page +
                ", extendedDatas=" + extendedDatas +
                '}';
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public ArrayList<VideoInputExtendedData> getExtendedDatas() {
        return extendedDatas;
    }

    public void setExtendedDatas(ArrayList<VideoInputExtendedData> extendedDatas) {
        this.extendedDatas = extendedDatas;
    }

    public VideoInputExtendedDataList() {

    }

    public VideoInputExtendedDataList(Page page, ArrayList<VideoInputExtendedData> extendedDatas) {

        this.page = page;
        this.extendedDatas = extendedDatas;
    }
}
