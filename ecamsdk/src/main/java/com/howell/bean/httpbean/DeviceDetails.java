package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/12.
 */

public class DeviceDetails {
    String id;
    String name;
    String manufacturer;
    String model;
    String firmware;
    String serialNumber;
    String pointOfSale;
    String information;
    String username;
    String passwork;
    String uri;
    String classification;
    String protocolType;
    String parentDeviceId;
    String busAddress;
    boolean hasSubDevice;
    VideoInputChannelList videoInputChannelList;
    VideoOutputChannelList videoOutputChannelList;
    IOInputChannelList ioInputChannelList;
    IOOutputChannelList ioOutputChannelList;
    NetworkInterfaceList networkInterfaceList;
    StoragemediumList storagemediumList;
    DecodingChannelList decodingChannelList;
    String abilities;
    double ratedVoltage;
    int maximumUserConnectionsNumber;
    int maximumVideoConnectionsNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswork() {
        return passwork;
    }

    public void setPasswork(String passwork) {
        this.passwork = passwork;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getParentDeviceId() {
        return parentDeviceId;
    }

    public void setParentDeviceId(String parentDeviceId) {
        this.parentDeviceId = parentDeviceId;
    }

    public String getBusAddress() {
        return busAddress;
    }

    public void setBusAddress(String busAddress) {
        this.busAddress = busAddress;
    }

    public boolean isHasSubDevice() {
        return hasSubDevice;
    }

    public void setHasSubDevice(boolean hasSubDevice) {
        this.hasSubDevice = hasSubDevice;
    }

    public VideoInputChannelList getVideoInputChannelList() {
        return videoInputChannelList;
    }

    public void setVideoInputChannelList(VideoInputChannelList videoInputChannelList) {
        this.videoInputChannelList = videoInputChannelList;
    }

    public VideoOutputChannelList getVideoOutputChannelList() {
        return videoOutputChannelList;
    }

    public void setVideoOutputChannelList(VideoOutputChannelList videoOutputChannelList) {
        this.videoOutputChannelList = videoOutputChannelList;
    }

    public IOInputChannelList getIoInputChannelList() {
        return ioInputChannelList;
    }

    public void setIoInputChannelList(IOInputChannelList ioInputChannelList) {
        this.ioInputChannelList = ioInputChannelList;
    }

    public IOOutputChannelList getIoOutputChannelList() {
        return ioOutputChannelList;
    }

    public void setIoOutputChannelList(IOOutputChannelList ioOutputChannelList) {
        this.ioOutputChannelList = ioOutputChannelList;
    }

    public NetworkInterfaceList getNetworkInterfaceList() {
        return networkInterfaceList;
    }

    public void setNetworkInterfaceList(NetworkInterfaceList networkInterfaceList) {
        this.networkInterfaceList = networkInterfaceList;
    }

    public StoragemediumList getStoragemediumList() {
        return storagemediumList;
    }

    public void setStoragemediumList(StoragemediumList storagemediumList) {
        this.storagemediumList = storagemediumList;
    }

    public DecodingChannelList getDecodingChannelList() {
        return decodingChannelList;
    }

    public void setDecodingChannelList(DecodingChannelList decodingChannelList) {
        this.decodingChannelList = decodingChannelList;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
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

    public DeviceDetails(String id, String name, String manufacturer, String model, String firmware, String serialNumber, String pointOfSale, String information, String username, String passwork, String uri, String classification, String protocolType, String parentDeviceId, String busAddress, boolean hasSubDevice, VideoInputChannelList videoInputChannelList, VideoOutputChannelList videoOutputChannelList, IOInputChannelList ioInputChannelList, IOOutputChannelList ioOutputChannelList, NetworkInterfaceList networkInterfaceList, StoragemediumList storagemediumList, DecodingChannelList decodingChannelList, String abilities, double ratedVoltage, int maximumUserConnectionsNumber, int maximumVideoConnectionsNumber) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.model = model;
        this.firmware = firmware;
        this.serialNumber = serialNumber;
        this.pointOfSale = pointOfSale;
        this.information = information;
        this.username = username;
        this.passwork = passwork;
        this.uri = uri;
        this.classification = classification;
        this.protocolType = protocolType;
        this.parentDeviceId = parentDeviceId;
        this.busAddress = busAddress;
        this.hasSubDevice = hasSubDevice;
        this.videoInputChannelList = videoInputChannelList;
        this.videoOutputChannelList = videoOutputChannelList;
        this.ioInputChannelList = ioInputChannelList;
        this.ioOutputChannelList = ioOutputChannelList;
        this.networkInterfaceList = networkInterfaceList;
        this.storagemediumList = storagemediumList;
        this.decodingChannelList = decodingChannelList;
        this.abilities = abilities;
        this.ratedVoltage = ratedVoltage;
        this.maximumUserConnectionsNumber = maximumUserConnectionsNumber;
        this.maximumVideoConnectionsNumber = maximumVideoConnectionsNumber;
    }

    public DeviceDetails() {
    }

    @Override
    public String toString() {
        return "DeviceDetails{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", firmware='" + firmware + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", pointOfSale='" + pointOfSale + '\'' +
                ", information='" + information + '\'' +
                ", username='" + username + '\'' +
                ", passwork='" + passwork + '\'' +
                ", uri='" + uri + '\'' +
                ", classification='" + classification + '\'' +
                ", protocolType='" + protocolType + '\'' +
                ", parentDeviceId='" + parentDeviceId + '\'' +
                ", busAddress='" + busAddress + '\'' +
                ", hasSubDevice=" + hasSubDevice +
                ", videoInputChannelList=" + videoInputChannelList +
                ", videoOutputChannelList=" + videoOutputChannelList +
                ", ioInputChannelList=" + ioInputChannelList +
                ", ioOutputChannelList=" + ioOutputChannelList +
                ", networkInterfaceList=" + networkInterfaceList +
                ", storagemediumList=" + storagemediumList +
                ", decodingChannelList=" + decodingChannelList +
                ", abilities='" + abilities + '\'' +
                ", ratedVoltage=" + ratedVoltage +
                ", maximumUserConnectionsNumber=" + maximumUserConnectionsNumber +
                ", maximumVideoConnectionsNumber=" + maximumVideoConnectionsNumber +
                '}';
    }
}
