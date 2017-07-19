package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/13.
 */

public class StreamingStatus {
    int No;
    double bitrate;
    long totalpacket;
    long lostPacket;
    long errorPacket;
    long receivePacket;
    ArrayList <StreamingSessionStatus> sessionStatuses;

    public StreamingStatus(int no, double bitrate, long totalpacket, long lostPacket, long errorPacket, long receivePacket, ArrayList<StreamingSessionStatus> sessionStatuses) {
        No = no;
        this.bitrate = bitrate;
        this.totalpacket = totalpacket;
        this.lostPacket = lostPacket;
        this.errorPacket = errorPacket;
        this.receivePacket = receivePacket;
        this.sessionStatuses = sessionStatuses;
    }

    public StreamingStatus() {
    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    public double getBitrate() {
        return bitrate;
    }

    public void setBitrate(double bitrate) {
        this.bitrate = bitrate;
    }

    public long getTotalpacket() {
        return totalpacket;
    }

    public void setTotalpacket(long totalpacket) {
        this.totalpacket = totalpacket;
    }

    public long getLostPacket() {
        return lostPacket;
    }

    public void setLostPacket(long lostPacket) {
        this.lostPacket = lostPacket;
    }

    public long getErrorPacket() {
        return errorPacket;
    }

    public void setErrorPacket(long errorPacket) {
        this.errorPacket = errorPacket;
    }

    public long getReceivePacket() {
        return receivePacket;
    }

    public void setReceivePacket(long receivePacket) {
        this.receivePacket = receivePacket;
    }

    public ArrayList<StreamingSessionStatus> getSessionStatuses() {
        return sessionStatuses;
    }

    public void setSessionStatuses(ArrayList<StreamingSessionStatus> sessionStatuses) {
        this.sessionStatuses = sessionStatuses;
    }

    @Override
    public String toString() {
        return "StreamingStatus{" +
                "No=" + No +
                ", bitrate=" + bitrate +
                ", totalpacket=" + totalpacket +
                ", lostPacket=" + lostPacket +
                ", errorPacket=" + errorPacket +
                ", receivePacket=" + receivePacket +
                ", sessionStatuses=" + sessionStatuses +
                '}';
    }
}
