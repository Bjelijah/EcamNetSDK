package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class TimeBlock {
    String dayOfWeek;
    TimeRange timeRange;

    @Override
    public String toString() {
        return "TimeBlock{" +
                "dayOfWeek='" + dayOfWeek + '\'' +
                ", timeRange=" + timeRange +
                '}';
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    public TimeBlock() {

    }

    public TimeBlock(String dayOfWeek, TimeRange timeRange) {

        this.dayOfWeek = dayOfWeek;
        this.timeRange = timeRange;
    }
}
