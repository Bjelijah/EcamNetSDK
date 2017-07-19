package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/25.
 */

public class Capabilities {
    boolean video;
    boolean Howell5198;
    boolean GIS;
    boolean GPS;
    boolean vehiclePlate;
    boolean faceRecognitiion;
    boolean RFID;
    boolean ultrasonic;

    public Capabilities(boolean video, boolean howell5198, boolean GIS, boolean GPS, boolean vehiclePlate, boolean faceRecognitiion, boolean RFID, boolean ultrasonic) {
        this.video = video;
        Howell5198 = howell5198;
        this.GIS = GIS;
        this.GPS = GPS;
        this.vehiclePlate = vehiclePlate;
        this.faceRecognitiion = faceRecognitiion;
        this.RFID = RFID;
        this.ultrasonic = ultrasonic;
    }

    public Capabilities() {
    }

    @Override
    public String toString() {
        return "Capabilities{" +
                "video=" + video +
                ", Howell5198=" + Howell5198 +
                ", GIS=" + GIS +
                ", GPS=" + GPS +
                ", vehiclePlate=" + vehiclePlate +
                ", faceRecognitiion=" + faceRecognitiion +
                ", RFID=" + RFID +
                ", ultrasonic=" + ultrasonic +
                '}';
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isHowell5198() {
        return Howell5198;
    }

    public void setHowell5198(boolean howell5198) {
        Howell5198 = howell5198;
    }

    public boolean isGIS() {
        return GIS;
    }

    public void setGIS(boolean GIS) {
        this.GIS = GIS;
    }

    public boolean isGPS() {
        return GPS;
    }

    public void setGPS(boolean GPS) {
        this.GPS = GPS;
    }

    public boolean isVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(boolean vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public boolean isFaceRecognitiion() {
        return faceRecognitiion;
    }

    public void setFaceRecognitiion(boolean faceRecognitiion) {
        this.faceRecognitiion = faceRecognitiion;
    }

    public boolean isRFID() {
        return RFID;
    }

    public void setRFID(boolean RFID) {
        this.RFID = RFID;
    }

    public boolean isUltrasonic() {
        return ultrasonic;
    }

    public void setUltrasonic(boolean ultrasonic) {
        this.ultrasonic = ultrasonic;
    }
}
