package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/25.
 */

public class GISMap {
    String id;
    String name;
    double longitude;
    double latitude;
    String description;

    @Override
    public String toString() {
        return "GISMap{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", description='" + description + '\'' +
                '}';
    }

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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GISMap() {

    }

    public GISMap(String id, String name, double longitude, double latitude, String description) {

        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
    }
}
