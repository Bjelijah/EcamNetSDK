package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/19.
 */

public class StorageMediumAbnormalLog {
    String abnormalType;
    String time;
    String report;

    @Override
    public String toString() {
        return "StorageMediumAbnormalLog{" +
                "abnormalType='" + abnormalType + '\'' +
                ", time='" + time + '\'' +
                ", report='" + report + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getAbnormalType() {
        return abnormalType;
    }

    public void setAbnormalType(String abnormalType) {
        this.abnormalType = abnormalType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StorageMediumAbnormalLog() {

    }

    public StorageMediumAbnormalLog(String abnormalType, String time, String report, String description) {

        this.abnormalType = abnormalType;
        this.time = time;
        this.report = report;
        this.description = description;
    }

    String description;
}
