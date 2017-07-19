package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/19.
 */

public class RFIDCard {
    String id;
    String manufacturer;
    String model;
    String description;

    @Override
    public String toString() {
        return "RFIDCard{" +
                "id='" + id + '\'' +
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

    public RFIDCard() {

    }

    public RFIDCard(String id, String manufacturer, String model, String description) {

        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.description = description;
    }
}
