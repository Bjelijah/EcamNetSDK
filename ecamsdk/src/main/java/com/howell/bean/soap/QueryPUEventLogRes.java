package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/16.
 */

public class QueryPUEventLogRes {
    String result;
    int pageNo;
    int pageCount;
    int recordCount;
    Log log;

    @Override
    public String toString() {
        return "QueryPUEventLogRes{" +
                "result='" + result + '\'' +
                ", pageNo=" + pageNo +
                ", pageCount=" + pageCount +
                ", recordCount=" + recordCount +
                ", log=" + log +
                '}';
    }

    public QueryPUEventLogRes() {
    }

    public QueryPUEventLogRes(String result, int pageNo, int pageCount, int recordCount, Log log) {

        this.result = result;
        this.pageNo = pageNo;
        this.pageCount = pageCount;
        this.recordCount = recordCount;
        this.log = log;
    }

    public String getResult() {

        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public static class Log{
        String devID;
        int channelNo;
        String eventType;
        String eventState;
        String eventDesc;
        String time;

        @Override
        public String toString() {
            return "Log{" +
                    "devID='" + devID + '\'' +
                    ", channelNo=" + channelNo +
                    ", eventType='" + eventType + '\'' +
                    ", eventState='" + eventState + '\'' +
                    ", eventDesc='" + eventDesc + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }

        public Log() {
        }

        public Log(String devID, int channelNo, String eventType, String eventState, String eventDesc, String time) {

            this.devID = devID;
            this.channelNo = channelNo;
            this.eventType = eventType;
            this.eventState = eventState;
            this.eventDesc = eventDesc;
            this.time = time;
        }

        public String getDevID() {

            return devID;
        }

        public void setDevID(String devID) {
            this.devID = devID;
        }

        public int getChannelNo() {
            return channelNo;
        }

        public void setChannelNo(int channelNo) {
            this.channelNo = channelNo;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public String getEventState() {
            return eventState;
        }

        public void setEventState(String eventState) {
            this.eventState = eventState;
        }

        public String getEventDesc() {
            return eventDesc;
        }

        public void setEventDesc(String eventDesc) {
            this.eventDesc = eventDesc;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
