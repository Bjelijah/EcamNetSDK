package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/17.
 */

public class BitrateStatus {
    String id;
    double bitrate;
    long totalPacket;
    long lostPacket;
    long errorPacket;
    long receivedPacket;
    String sessionID;

    @Override
    public String toString() {
        return "BitrateStatus{" +
                "id='" + id + '\'' +
                ", bitrate=" + bitrate +
                ", totalPacket=" + totalPacket +
                ", lostPacket=" + lostPacket +
                ", errorPacket=" + errorPacket +
                ", receivedPacket=" + receivedPacket +
                ", sessionID='" + sessionID + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBitrate() {
        return bitrate;
    }

    public void setBitrate(double bitrate) {
        this.bitrate = bitrate;
    }

    public long getTotalPacket() {
        return totalPacket;
    }

    public void setTotalPacket(long totalPacket) {
        this.totalPacket = totalPacket;
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

    public long getReceivedPacket() {
        return receivedPacket;
    }

    public void setReceivedPacket(long receivedPacket) {
        this.receivedPacket = receivedPacket;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public BitrateStatus() {

    }

    public BitrateStatus(String id, double bitrate, long totalPacket, long lostPacket, long errorPacket, long receivedPacket, String sessionID) {

        this.id = id;
        this.bitrate = bitrate;
        this.totalPacket = totalPacket;
        this.lostPacket = lostPacket;
        this.errorPacket = errorPacket;
        this.receivedPacket = receivedPacket;
        this.sessionID = sessionID;
    }
}
