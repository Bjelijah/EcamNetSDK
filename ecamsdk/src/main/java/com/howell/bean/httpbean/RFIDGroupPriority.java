package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/19.
 */

public class RFIDGroupPriority {
    String RFIDGroupId;
    String RFIDAntennaId;
    int maxDuration;

    @Override
    public String toString() {
        return "RFIDGroupPriority{" +
                "RFIDGroupId='" + RFIDGroupId + '\'' +
                ", RFIDAntennaId='" + RFIDAntennaId + '\'' +
                ", maxDuration=" + maxDuration +
                '}';
    }

    public String getRFIDGroupId() {
        return RFIDGroupId;
    }

    public void setRFIDGroupId(String RFIDGroupId) {
        this.RFIDGroupId = RFIDGroupId;
    }

    public String getRFIDAntennaId() {
        return RFIDAntennaId;
    }

    public void setRFIDAntennaId(String RFIDAntennaId) {
        this.RFIDAntennaId = RFIDAntennaId;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    public RFIDGroupPriority() {

    }

    public RFIDGroupPriority(String RFIDGroupId, String RFIDAntennaId, int maxDuration) {

        this.RFIDGroupId = RFIDGroupId;
        this.RFIDAntennaId = RFIDAntennaId;
        this.maxDuration = maxDuration;
    }
}
