package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class NetworkInterfaceStatus {
    String id;
    double inputRate;
    double outputRate;

    @Override
    public String toString() {
        return "NetworkInterfaceStatus{" +
                "id='" + id + '\'' +
                ", inputRate=" + inputRate +
                ", outputRate=" + outputRate +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getInputRate() {
        return inputRate;
    }

    public void setInputRate(double inputRate) {
        this.inputRate = inputRate;
    }

    public double getOutputRate() {
        return outputRate;
    }

    public void setOutputRate(double outputRate) {
        this.outputRate = outputRate;
    }

    public NetworkInterfaceStatus() {

    }

    public NetworkInterfaceStatus(String id, double inputRate, double outputRate) {

        this.id = id;
        this.inputRate = inputRate;
        this.outputRate = outputRate;
    }
}
