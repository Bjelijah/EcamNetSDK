package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class AlertHandle {
    String handleType;
    ArrayList<String> IOOutput;
    ArrayList<String> capturePicture;
    ArrayList<String> record;

    @Override
    public String toString() {
        return "AlertHandle{" +
                "handleType='" + handleType + '\'' +
                ", IOOutput=" + IOOutput +
                ", capturePicture=" + capturePicture +
                ", record=" + record +
                '}';
    }

    public String getHandleType() {
        return handleType;
    }

    public void setHandleType(String handleType) {
        this.handleType = handleType;
    }

    public ArrayList<String> getIOOutput() {
        return IOOutput;
    }

    public void setIOOutput(ArrayList<String> IOOutput) {
        this.IOOutput = IOOutput;
    }

    public ArrayList<String> getCapturePicture() {
        return capturePicture;
    }

    public void setCapturePicture(ArrayList<String> capturePicture) {
        this.capturePicture = capturePicture;
    }

    public ArrayList<String> getRecord() {
        return record;
    }

    public void setRecord(ArrayList<String> record) {
        this.record = record;
    }

    public AlertHandle() {

    }

    public AlertHandle(String handleType, ArrayList<String> IOOutput, ArrayList<String> capturePicture, ArrayList<String> record) {

        this.handleType = handleType;
        this.IOOutput = IOOutput;
        this.capturePicture = capturePicture;
        this.record = record;
    }
}
