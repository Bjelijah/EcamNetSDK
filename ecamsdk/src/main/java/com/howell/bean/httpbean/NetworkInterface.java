package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/12.
 */

public class NetworkInterface {
    String id;
    int interfacePort;
    String ipVersion;
    String addressType;
    String ipAddress;
    String physcialAddress;
    String cableType;
    String speedDuplex;
    String workMode;
    String wireless;
    int MTU;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInterfacePort() {
        return interfacePort;
    }

    public void setInterfacePort(int interfacePort) {
        this.interfacePort = interfacePort;
    }

    public String getIpVersion() {
        return ipVersion;
    }

    public void setIpVersion(String ipVersion) {
        this.ipVersion = ipVersion;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPhyscialAddress() {
        return physcialAddress;
    }

    public void setPhyscialAddress(String physcialAddress) {
        this.physcialAddress = physcialAddress;
    }

    public String getCableType() {
        return cableType;
    }

    public void setCableType(String cableType) {
        this.cableType = cableType;
    }

    public String getSpeedDuplex() {
        return speedDuplex;
    }

    public void setSpeedDuplex(String speedDuplex) {
        this.speedDuplex = speedDuplex;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public String getWireless() {
        return wireless;
    }

    public void setWireless(String wireless) {
        this.wireless = wireless;
    }

    public int getMTU() {
        return MTU;
    }

    public void setMTU(int MTU) {
        this.MTU = MTU;
    }

    public NetworkInterface(String id, int interfacePort, String ipVersion, String addressType, String ipAddress, String physcialAddress, String cableType, String speedDuplex, String workMode, String wireless, int MTU) {
        this.id = id;
        this.interfacePort = interfacePort;
        this.ipVersion = ipVersion;
        this.addressType = addressType;
        this.ipAddress = ipAddress;
        this.physcialAddress = physcialAddress;
        this.cableType = cableType;
        this.speedDuplex = speedDuplex;
        this.workMode = workMode;
        this.wireless = wireless;
        this.MTU = MTU;
    }

    public NetworkInterface() {
    }

    @Override
    public String toString() {
        return "NetworkInterface{" +
                "id='" + id + '\'' +
                ", interfacePort=" + interfacePort +
                ", ipVersion='" + ipVersion + '\'' +
                ", addressType='" + addressType + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", physcialAddress='" + physcialAddress + '\'' +
                ", cableType='" + cableType + '\'' +
                ", speedDuplex='" + speedDuplex + '\'' +
                ", workMode='" + workMode + '\'' +
                ", wireless='" + wireless + '\'' +
                ", MTU=" + MTU +
                '}';
    }
}
