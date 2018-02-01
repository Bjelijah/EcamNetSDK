package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class IOOutputPortData {
    String IOState;

    @Override
    public String toString() {
        return "IOOutputPortData{" +
                "IOState='" + IOState + '\'' +
                '}';
    }

    public String getIOState() {
        return IOState;
    }

    public void setIOState(String IOState) {
        this.IOState = IOState;
    }

    public IOOutputPortData() {

    }

    public IOOutputPortData(String IOState) {

        this.IOState = IOState;
    }
}
