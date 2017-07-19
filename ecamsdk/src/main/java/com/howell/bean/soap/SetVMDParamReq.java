package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/15.
 */

public class SetVMDParamReq {
    String account;
    String session;
    String devId;
    int channelId;
    Boolean enable;
    int sensitivity;
    int startTriggerTime;
    int endTriggerTime;
    VMDGrid vmdGrid;
    WorkSheet workSheet;

    @Override
    public String toString() {
        return "SetVMDParamReq{" +
                "account='" + account + '\'' +
                ", session='" + session + '\'' +
                ", devId='" + devId + '\'' +
                ", channelId=" + channelId +
                ", enable=" + enable +
                ", sensitivity=" + sensitivity +
                ", startTriggerTime=" + startTriggerTime +
                ", endTriggerTime=" + endTriggerTime +
                ", vmdGrid=" + vmdGrid.toString() +
                ", workSheet=" + workSheet.toString() +
                '}';
    }

    public SetVMDParamReq() {
    }

    public SetVMDParamReq(String account, String session, String devId, int channelId, Boolean enable, int sensitivity, int startTriggerTime, int endTriggerTime, VMDGrid vmdGrid, WorkSheet workSheet) {

        this.account = account;
        this.session = session;
        this.devId = devId;
        this.channelId = channelId;
        this.enable = enable;
        this.sensitivity = sensitivity;
        this.startTriggerTime = startTriggerTime;
        this.endTriggerTime = endTriggerTime;
        this.vmdGrid = vmdGrid;
        this.workSheet = workSheet;
    }

    public String getAccount() {

        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public int getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
    }

    public int getStartTriggerTime() {
        return startTriggerTime;
    }

    public void setStartTriggerTime(int startTriggerTime) {
        this.startTriggerTime = startTriggerTime;
    }

    public int getEndTriggerTime() {
        return endTriggerTime;
    }

    public void setEndTriggerTime(int endTriggerTime) {
        this.endTriggerTime = endTriggerTime;
    }

    public VMDGrid getVmdGrid() {
        return vmdGrid;
    }

    public void setVmdGrid(VMDGrid vmdGrid) {
        this.vmdGrid = vmdGrid;
    }

    public WorkSheet getWorkSheet() {
        return workSheet;
    }

    public void setWorkSheet(WorkSheet workSheet) {
        this.workSheet = workSheet;
    }

    public static class VMDGrid{
        String row;

        @Override
        public String toString() {
            return "VMDGrid{" +
                    "row='" + row + '\'' +
                    '}';
        }

        public VMDGrid() {
        }

        public VMDGrid(String row) {

            this.row = row;
        }

        public String getRow() {

            return row;
        }

        public void setRow(String row) {
            this.row = row;
        }
    }

    public static class WorkSheet{
        Boolean enable;
        String bitString;

        @Override
        public String toString() {
            return "WorkSheet{" +
                    "enable=" + enable +
                    ", bitString='" + bitString + '\'' +
                    '}';
        }

        public WorkSheet() {
        }

        public WorkSheet(Boolean enable, String bitString) {

            this.enable = enable;
            this.bitString = bitString;
        }

        public Boolean getEnable() {

            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public String getBitString() {
            return bitString;
        }

        public void setBitString(String bitString) {
            this.bitString = bitString;
        }
    }
}
