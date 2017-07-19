package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/12.
 */

public class StorageMedium {
    String id;
    int storagePort;
    String mediumType;
    String manufacturer;
    String model;
    long capacity;
    long freespace;
    String storageType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStoragePort() {
        return storagePort;
    }

    public void setStoragePort(int storagePort) {
        this.storagePort = storagePort;
    }

    public String getMediumType() {
        return mediumType;
    }

    public void setMediumType(String mediumType) {
        this.mediumType = mediumType;
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

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public long getFreespace() {
        return freespace;
    }

    public void setFreespace(long freespace) {
        this.freespace = freespace;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public StorageMedium(String id, int storagePort, String mediumType, String manufacturer, String model, long capacity, long freespace, String storage) {
        this.id = id;
        this.storagePort = storagePort;
        this.mediumType = mediumType;
        this.manufacturer = manufacturer;
        this.model = model;
        this.capacity = capacity;
        this.freespace = freespace;
        this.storageType = storage;
    }

    public StorageMedium() {
    }

    @Override
    public String toString() {
        return "StorageMedium{" +
                "id='" + id + '\'' +
                ", storagePort=" + storagePort +
                ", mediumType='" + mediumType + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                ", freespace=" + freespace +
                ", storageType='" + storageType + '\'' +
                '}';
    }
}
