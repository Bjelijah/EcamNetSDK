package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/19.
 */

public class RFIDAntenna {
    String id;
    int maxDistance;
    String manufacturer;
    String model;
    String description;

    @Override
    public String toString() {
        return "RFIDAntenna{" +
                "id='" + id + '\'' +
                ", maxDistance=" + maxDistance +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RFIDAntenna() {

    }

    public RFIDAntenna(String id, int maxDistance, String manufacturer, String model, String description) {

        this.id = id;
        this.maxDistance = maxDistance;
        this.manufacturer = manufacturer;
        this.model = model;
        this.description = description;
    }
}
