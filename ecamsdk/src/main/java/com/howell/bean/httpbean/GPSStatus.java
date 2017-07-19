package com.howell.bean.httpbean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/25.
 */

public class GPSStatus implements Serializable{
    String time;
    boolean isOnline;
    int status;
    double latitude;
    double longitude;
    double speed;
    double course;
    double magneticVariation;
    double altitude;
    long systemUpTime;
    int battery;
    int signalIntensity;

    @Override
    public String toString() {
        return "GPSStatus{" +
                "time='" + time + '\'' +
                ", isOnline=" + isOnline +
                ", status=" + status +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", speed=" + speed +
                ", course=" + course +
                ", magneticVariation=" + magneticVariation +
                ", altitude=" + altitude +
                ", systemUpTime=" + systemUpTime +
                ", battery=" + battery +
                ", signalIntensity=" + signalIntensity +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
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

    public long getSystemUpTime() {
        return systemUpTime;
    }

    public void setSystemUpTime(long systemUpTime) {
        this.systemUpTime = systemUpTime;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(int signalIntensity) {
        this.signalIntensity = signalIntensity;
    }

    public GPSStatus() {

    }

    public GPSStatus(String time, boolean isOnline, int status, double latitude, double longitude, double speed, double course, double magneticVariation, double altitude, long systemUpTime, int battery, int signalIntensity) {

        this.time = time;
        this.isOnline = isOnline;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.course = course;
        this.magneticVariation = magneticVariation;
        this.altitude = altitude;
        this.systemUpTime = systemUpTime;
        this.battery = battery;
        this.signalIntensity = signalIntensity;
    }
}
