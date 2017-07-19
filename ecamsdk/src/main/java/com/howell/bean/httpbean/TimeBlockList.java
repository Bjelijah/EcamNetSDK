package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class TimeBlockList {
    ArrayList<TimeBlock> timeBlocks;

    @Override
    public String toString() {
        return "TimeBlockList{" +
                "timeBlocks=" + timeBlocks +
                '}';
    }

    public ArrayList<TimeBlock> getTimeBlocks() {
        return timeBlocks;
    }

    public void setTimeBlocks(ArrayList<TimeBlock> timeBlocks) {
        this.timeBlocks = timeBlocks;
    }

    public TimeBlockList() {

    }

    public TimeBlockList(ArrayList<TimeBlock> timeBlocks) {

        this.timeBlocks = timeBlocks;
    }
}
