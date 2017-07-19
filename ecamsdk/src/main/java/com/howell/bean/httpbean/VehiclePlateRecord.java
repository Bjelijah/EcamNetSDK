package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/26.
 */

public class VehiclePlateRecord {
    String deviceId;
    String createTime;
    String accessId;
    String name;
    String plate;
    String plateColor;
    String VehicleColor;
    String brand;
    String childBrand;
    int credibility;
    double speed;
    Rectangle platePosition;
    String laneId;
    double latitude;
    double longitude;
    String description;
    ArrayList<String> imageIds;

    @Override
    public String toString() {
        return "VehiclePlateRecord{" +
                "deviceId='" + deviceId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", accessId='" + accessId + '\'' +
                ", name='" + name + '\'' +
                ", plate='" + plate + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", VehicleColor='" + VehicleColor + '\'' +
                ", brand='" + brand + '\'' +
                ", childBrand='" + childBrand + '\'' +
                ", credibility=" + credibility +
                ", speed=" + speed +
                ", platePosition=" + platePosition +
                ", laneId='" + laneId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description + '\'' +
                ", imageIds=" + imageIds +
                '}';
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getVehicleColor() {
        return VehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        VehicleColor = vehicleColor;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getChildBrand() {
        return childBrand;
    }

    public void setChildBrand(String childBrand) {
        this.childBrand = childBrand;
    }

    public int getCredibility() {
        return credibility;
    }

    public void setCredibility(int credibility) {
        this.credibility = credibility;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Rectangle getPlatePosition() {
        return platePosition;
    }

    public void setPlatePosition(Rectangle platePosition) {
        this.platePosition = platePosition;
    }

    public String getLaneId() {
        return laneId;
    }

    public void setLaneId(String laneId) {
        this.laneId = laneId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getImageIds() {
        return imageIds;
    }

    public void setImageIds(ArrayList<String> imageIds) {
        this.imageIds = imageIds;
    }

    public VehiclePlateRecord() {

    }

    public VehiclePlateRecord(String deviceId, String createTime, String accessId, String name, String plate, String plateColor, String vehicleColor, String brand, String childBrand, int credibility, double speed, Rectangle platePosition, String laneId, double latitude, double longitude, String description, ArrayList<String> imageIds) {

        this.deviceId = deviceId;
        this.createTime = createTime;
        this.accessId = accessId;
        this.name = name;
        this.plate = plate;
        this.plateColor = plateColor;
        VehicleColor = vehicleColor;
        this.brand = brand;
        this.childBrand = childBrand;
        this.credibility = credibility;
        this.speed = speed;
        this.platePosition = platePosition;
        this.laneId = laneId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.imageIds = imageIds;
    }
}
