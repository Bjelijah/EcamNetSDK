package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class TimeRange {
    String beginTime;
    String endTime;

    @Override
    public String toString() {
        return "TimeRange{" +
                "beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public TimeRange() {

    }

    public TimeRange(String beginTime, String endTime) {

        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
