package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/12.
 */

public class EventRecordedFile {
    String recordedFileId;
    long recordedFileTimestamp;

    public String getRecordedFileId() {
        return recordedFileId;
    }

    public void setRecordedFileId(String recordedFileId) {
        this.recordedFileId = recordedFileId;
    }

    public long getRecordedFileTimestamp() {
        return recordedFileTimestamp;
    }

    public void setRecordedFileTimestamp(long recordedFileTimestamp) {
        this.recordedFileTimestamp = recordedFileTimestamp;
    }

    public EventRecordedFile(String recordedFileId, long recordedFileTimestamp) {
        this.recordedFileId = recordedFileId;
        this.recordedFileTimestamp = recordedFileTimestamp;
    }

    public EventRecordedFile() {
    }

    @Override
    public String toString() {
        return "EventRecordedFile{" +
                "recordedFileId='" + recordedFileId + '\'' +
                ", recordedFileTimestamp=" + recordedFileTimestamp +
                '}';
    }
}
