package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class StorageMediumStatus {
    String id;
    String mediumState;
    long writingBps;
    long readingBps;
    long badBlockNumber;
    long totalBlockNumber;

    @Override
    public String toString() {
        return "StorageMediumStatus{" +
                "id='" + id + '\'' +
                ", mediumState='" + mediumState + '\'' +
                ", writingBps=" + writingBps +
                ", readingBps=" + readingBps +
                ", badBlockNumber=" + badBlockNumber +
                ", totalBlockNumber=" + totalBlockNumber +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMediumState() {
        return mediumState;
    }

    public void setMediumState(String mediumState) {
        this.mediumState = mediumState;
    }

    public long getWritingBps() {
        return writingBps;
    }

    public void setWritingBps(long writingBps) {
        this.writingBps = writingBps;
    }

    public long getReadingBps() {
        return readingBps;
    }

    public void setReadingBps(long readingBps) {
        this.readingBps = readingBps;
    }

    public long getBadBlockNumber() {
        return badBlockNumber;
    }

    public void setBadBlockNumber(long badBlockNumber) {
        this.badBlockNumber = badBlockNumber;
    }

    public long getTotalBlockNumber() {
        return totalBlockNumber;
    }

    public void setTotalBlockNumber(long totalBlockNumber) {
        this.totalBlockNumber = totalBlockNumber;
    }

    public StorageMediumStatus() {

    }

    public StorageMediumStatus(String id, String mediumState, long writingBps, long readingBps, long badBlockNumber, long totalBlockNumber) {

        this.id = id;
        this.mediumState = mediumState;
        this.writingBps = writingBps;
        this.readingBps = readingBps;
        this.badBlockNumber = badBlockNumber;
        this.totalBlockNumber = totalBlockNumber;
    }
}
