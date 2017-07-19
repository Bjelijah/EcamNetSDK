package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/19.
 */

public class Notice {
    String id;
    String creationTime;
    String message;
    String classification;
    String status;
    String sender;
    String componentId;
    String componentName;
    String pictureIds;
    String noticeType;
    String userId;

    @Override
    public String toString() {
        return "Notice{" +
                "id='" + id + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", message='" + message + '\'' +
                ", classification='" + classification + '\'' +
                ", status='" + status + '\'' +
                ", sender='" + sender + '\'' +
                ", componentId='" + componentId + '\'' +
                ", componentName='" + componentName + '\'' +
                ", pictureIds='" + pictureIds + '\'' +
                ", noticeType='" + noticeType + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(String pictureIds) {
        this.pictureIds = pictureIds;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Notice() {

    }

    public Notice(String id, String creationTime, String message, String classification, String status, String sender, String componentId, String componentName, String pictureIds, String noticeType, String userId) {

        this.id = id;
        this.creationTime = creationTime;
        this.message = message;
        this.classification = classification;
        this.status = status;
        this.sender = sender;
        this.componentId = componentId;
        this.componentName = componentName;
        this.pictureIds = pictureIds;
        this.noticeType = noticeType;
        this.userId = userId;
    }
}
