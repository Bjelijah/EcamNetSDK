package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/13.
 */

public class StreamingSessionStatus {
    ClientAddress clientAddress;
    String clientUserName;
    String startDateTime;
    int elapsedSeconds;
    double bandwidth;

    public StreamingSessionStatus(ClientAddress clientAddress, String clientUserName, String startDateTime, int elapsedSeconds, double bandwidth) {
        this.clientAddress = clientAddress;
        this.clientUserName = clientUserName;
        this.startDateTime = startDateTime;
        this.elapsedSeconds = elapsedSeconds;
        this.bandwidth = bandwidth;
    }

    public StreamingSessionStatus() {
    }

    public ClientAddress getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(ClientAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    public void setElapsedSeconds(int elapsedSeconds) {
        this.elapsedSeconds = elapsedSeconds;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }

    @Override
    public String toString() {
        return "StreamingSessionStatus{" +
                "clientAddress=" + clientAddress +
                ", clientUserName='" + clientUserName + '\'' +
                ", startDateTime='" + startDateTime + '\'' +
                ", elapsedSeconds=" + elapsedSeconds +
                ", bandwidth=" + bandwidth +
                '}';
    }
}
