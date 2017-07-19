package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/25.
 */

public class GISMapItem {
    String id;
    String parentLayerId;
    String itemId;
    String name;
    int iconType;
    boolean online;
    double longitude;
    double latitude;
    double course;
    String status;
    String gpsId;
    String vehiclePlateId;
    String faceRecognitionId;
    String description;
    String updatadTime;

    @Override
    public String toString() {
        return "GISMapItem{" +
                "id='" + id + '\'' +
                ", parentLayerId='" + parentLayerId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", name='" + name + '\'' +
                ", iconType=" + iconType +
                ", online=" + online +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", course=" + course +
                ", status='" + status + '\'' +
                ", gpsId='" + gpsId + '\'' +
                ", vehiclePlateId='" + vehiclePlateId + '\'' +
                ", faceRecognitionId='" + faceRecognitionId + '\'' +
                ", description='" + description + '\'' +
                ", updatadTime='" + updatadTime + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentLayerId() {
        return parentLayerId;
    }

    public void setParentLayerId(String parentLayerId) {
        this.parentLayerId = parentLayerId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconType() {
        return iconType;
    }

    public void setIconType(int iconType) {
        this.iconType = iconType;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getCourse() {
        return course;
    }

    public void setCourse(double course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGpsId() {
        return gpsId;
    }

    public void setGpsId(String gpsId) {
        this.gpsId = gpsId;
    }

    public String getVehiclePlateId() {
        return vehiclePlateId;
    }

    public void setVehiclePlateId(String vehiclePlateId) {
        this.vehiclePlateId = vehiclePlateId;
    }

    public String getFaceRecognitionId() {
        return faceRecognitionId;
    }

    public void setFaceRecognitionId(String faceRecognitionId) {
        this.faceRecognitionId = faceRecognitionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdatadTime() {
        return updatadTime;
    }

    public void setUpdatadTime(String updatadTime) {
        this.updatadTime = updatadTime;
    }

    public GISMapItem() {
    }

    public GISMapItem(String id, String parentLayerId, String itemId, String name, int iconType, boolean online, double longitude, double latitude, double course, String status, String gpsId, String vehiclePlateId, String faceRecognitionId, String description, String updatadTime) {
        this.id = id;
        this.parentLayerId = parentLayerId;
        this.itemId = itemId;
        this.name = name;
        this.iconType = iconType;
        this.online = online;
        this.longitude = longitude;
        this.latitude = latitude;
        this.course = course;
        this.status = status;
        this.gpsId = gpsId;
        this.vehiclePlateId = vehiclePlateId;
        this.faceRecognitionId = faceRecognitionId;
        this.description = description;
        this.updatadTime = updatadTime;
    }
}
