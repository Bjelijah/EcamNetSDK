package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class UltrasonicInformation {
    int interval;
    int range;
    int maxRange;

    @Override
    public String toString() {
        return "UltrasonicInformation{" +
                "interval=" + interval +
                ", range=" + range +
                ", maxRange=" + maxRange +
                '}';
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    public UltrasonicInformation() {

    }

    public UltrasonicInformation(int interval, int range, int maxRange) {

        this.interval = interval;
        this.range = range;
        this.maxRange = maxRange;
    }
}
