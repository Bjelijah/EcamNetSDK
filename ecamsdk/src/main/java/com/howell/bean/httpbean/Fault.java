package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/6.
 */

public class Fault {
    int faultCode;
    String faultReason;
    Exception exceptionData;
    String id;

    public int getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(int faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason;
    }

    public Exception getExceptionData() {
        return exceptionData;
    }

    public void setExceptionData(Exception exceptionData) {
        this.exceptionData = exceptionData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Fault(int faultCode, String faultReason, Exception exceptionData, String id) {
        this.faultCode = faultCode;
        this.faultReason = faultReason;
        this.exceptionData = exceptionData;
        this.id = id;
    }

    public Fault(int faultCode, String faultReason) {
        this.faultCode = faultCode;
        this.faultReason = faultReason;
    }

    public Fault() {
    }

    @Override
    public String toString() {
        return "Fault{" +
                "faultCode=" + faultCode +
                ", faultReason='" + faultReason + '\'' +
                ", exceptionData=" + exceptionData +
                ", id='" + id + '\'' +
                '}';
    }

    public static class Exception{
        String message;
        String execptionType;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getExecptionType() {
            return execptionType;
        }

        public void setExecptionType(String execptionType) {
            this.execptionType = execptionType;
        }

        public Exception(String message, String execptionType) {
            this.message = message;
            this.execptionType = execptionType;
        }

        public Exception() {
        }

        @Override
        public String toString() {
            return "Exception{" +
                    "message='" + message + '\'' +
                    ", execptionType='" + execptionType + '\'' +
                    '}';
        }
    }

}
