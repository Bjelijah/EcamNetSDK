package com.howell.bean.soap;

/**
 * Created by Administrator on 2017/6/16.
 */

public class QueryPUOnOffLogRes {
    String result;
    int pageNo;
    int pageCount;
    int recordCount;
    Log log;

    public QueryPUOnOffLogRes() {
    }

    public QueryPUOnOffLogRes(String result, int pageNo, int pageCount, int recordCount, Log log) {

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
        String time;
        String offTime;
        String offReason;

        @Override
        public String toString() {
            return "Log{" +
                    "devID='" + devID + '\'' +
                    ", time='" + time + '\'' +
                    ", offTime='" + offTime + '\'' +
                    ", offReason='" + offReason + '\'' +
                    '}';
        }

        public Log() {
        }

        public Log(String devID, String time, String offTime, String offReason) {

            this.devID = devID;
            this.time = time;
            this.offTime = offTime;
            this.offReason = offReason;
        }

        public String getDevID() {

            return devID;
        }

        public void setDevID(String devID) {
            this.devID = devID;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getOffTime() {
            return offTime;
        }

        public void setOffTime(String offTime) {
            this.offTime = offTime;
        }

        public String getOffReason() {
            return offReason;
        }

        public void setOffReason(String offReason) {
            this.offReason = offReason;
        }
    }
}
