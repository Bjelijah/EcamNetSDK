package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/25.
 */

public class RMC {
    String deviceId;
    String time;
    int status;
    double latitude;
    double longitude;
    double speed;
    double course;
    double magneticVariation;
    double altitude;
    String description;
    String accessId;

    @Override
    public String toString() {
        return "RMC{" +
                "deviceId='" + deviceId + '\'' +
                ", time='" + time + '\'' +
                ", status=" + status +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", speed=" + speed +
                ", course=" + course +
                ", magneticVariation=" + magneticVariation +
                ", altitude=" + altitude +
                ", description='" + description + '\'' +
                ", accessId='" + accessId + '\'' +
                '}';
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getCourse() {
        return course;
    }

    public void setCourse(double course) {
        this.course = course;
    }

    public double getMagneticVariation() {
        return magneticVariation;
    }

    public void setMagneticVariation(double magneticVariation) {
        this.magneticVariation = magneticVariation;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public RMC() {

    }

    public RMC(String deviceId, String time, int status, double latitude, double longitude, double speed, double course, double magneticVariation, double altitude, String description, String accessId) {

        this.deviceId = deviceId;
        this.time = time;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.course = course;
        this.magneticVariation = magneticVariation;
        this.altitude = altitude;
        this.description = description;
        this.accessId = accessId;
    }
}
