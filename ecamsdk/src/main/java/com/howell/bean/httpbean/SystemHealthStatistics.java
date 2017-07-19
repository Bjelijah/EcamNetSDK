package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/11.
 */

public class SystemHealthStatistics {
    String id;
    double percentage;
    SystemFaultStatistics faults;
    SystemWarningStatistics warning;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public SystemFaultStatistics getFaults() {
        return faults;
    }

    public void setFaults(SystemFaultStatistics faults) {
        this.faults = faults;
    }

    public SystemWarningStatistics getWarning() {
        return warning;
    }

    public void setWarning(SystemWarningStatistics warning) {
        this.warning = warning;
    }

    public SystemHealthStatistics(String id, double percentage, SystemFaultStatistics faults, SystemWarningStatistics warning) {
        this.id = id;
        this.percentage = percentage;
        this.faults = faults;
        this.warning = warning;
    }

    public SystemHealthStatistics() {
    }

    @Override
    public String toString() {
        return "SystemHealthStatistics{" +
                "id='" + id + '\'' +
                ", percentage=" + percentage +
                ", faults=" + faults +
                ", warning=" + warning +
                '}';
    }
}
