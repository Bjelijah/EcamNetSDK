package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class TeardownLog {
    String time;
    String description;

    @Override
    public String toString() {
        return "TeardownLog{" +
                "time='" + time + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TeardownLog() {

    }

    public TeardownLog(String time, String description) {

        this.time = time;
        this.description = description;
    }
}
