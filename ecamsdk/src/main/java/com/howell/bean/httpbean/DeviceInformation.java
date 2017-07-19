package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/13.
 */

public class DeviceInformation {
    String id;
    String authenticationCode;
    String systemTime;
    String classification;
    String manufacturer;
    String model;
    String firmware;
    String software;
    String serialNumber;
    boolean infrared;
    boolean wireless;
    boolean temperatureSensor;
    boolean pressureSensor;
    boolean storable;
    double ratedVoltage;
    int maximumUserConnectionsNumber;
    int maximumVideoConnectionsNumber;
    String serviceUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthenticationCode() {
        return authenticationCode;
    }

    public void setAuthenticationCode(String authenticationCode) {
        this.authenticationCode = authenticationCode;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isInfrared() {
        return infrared;
    }

    public void setInfrared(boolean infrared) {
        this.infrared = infrared;
    }

    public boolean isWireless() {
        return wireless;
    }

    public void setWireless(boolean wireless) {
        this.wireless = wireless;
    }

    public boolean isTemperatureSensor() {
        return temperatureSensor;
    }

    public void setTemperatureSensor(boolean temperatureSensor) {
        this.temperatureSensor = temperatureSensor;
    }

    public boolean isPressureSensor() {
        return pressureSensor;
    }

    public void setPressureSensor(boolean pressureSensor) {
        this.pressureSensor = pressureSensor;
    }

    public boolean isStorable() {
        return storable;
    }

    public void setStorable(boolean storable) {
        this.storable = storable;
    }

    public double getRatedVoltage() {
        return ratedVoltage;
    }

    public void setRatedVoltage(double ratedVoltage) {
        this.ratedVoltage = ratedVoltage;
    }

    public int getMaximumUserConnectionsNumber() {
        return maximumUserConnectionsNumber;
    }

    public void setMaximumUserConnectionsNumber(int maximumUserConnectionsNumber) {
        this.maximumUserConnectionsNumber = maximumUserConnectionsNumber;
    }

    public int getMaximumVideoConnectionsNumber() {
        return maximumVideoConnectionsNumber;
    }

    public void setMaximumVideoConnectionsNumber(int maximumVideoConnectionsNumber) {
        this.maximumVideoConnectionsNumber = maximumVideoConnectionsNumber;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public DeviceInformation(String id, String authenticationCode, String systemTime, String classification, String manufacturer, String model, String firmware, String software, String serialNumber, boolean infrared, boolean wireless, boolean temperatureSensor, boolean pressureSensor, boolean storable, double ratedVoltage, int maximumUserConnectionsNumber, int maximumVideoConnectionsNumber, String serviceUrl) {
        this.id = id;
        this.authenticationCode = authenticationCode;
        this.systemTime = systemTime;
        this.classification = classification;
        this.manufacturer = manufacturer;
        this.model = model;
        this.firmware = firmware;
        this.software = software;
        this.serialNumber = serialNumber;
        this.infrared = infrared;
        this.wireless = wireless;
        this.temperatureSensor = temperatureSensor;
        this.pressureSensor = pressureSensor;
        this.storable = storable;
        this.ratedVoltage = ratedVoltage;
        this.maximumUserConnectionsNumber = maximumUserConnectionsNumber;
        this.maximumVideoConnectionsNumber = maximumVideoConnectionsNumber;
        this.serviceUrl = serviceUrl;
    }

    public DeviceInformation() {
    }

    @Override
    public String toString() {
        return "DeviceInformation{" +
                "id='" + id + '\'' +
                ", authenticationCode='" + authenticationCode + '\'' +
                ", systemTime='" + systemTime + '\'' +
                ", classification='" + classification + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", firmware='" + firmware + '\'' +
                ", software='" + software + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", infrared=" + infrared +
                ", wireless=" + wireless +
                ", temperatureSensor=" + temperatureSensor +
                ", pressureSensor=" + pressureSensor +
                ", storable=" + storable +
                ", ratedVoltage=" + ratedVoltage +
                ", maximumUserConnectionsNumber=" + maximumUserConnectionsNumber +
                ", maximumVideoConnectionsNumber=" + maximumVideoConnectionsNumber +
                ", serviceUrl='" + serviceUrl + '\'' +
                '}';
    }
}
