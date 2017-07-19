package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/26.
 */

public class VehicleDetectionResult {
    int result;
    boolean existedInDataBase;
    boolean existedInBlackList;
    String plate;
    String Id;
    String plateColor;
    String vehicleColor;
    String brand;
    String childBrand;
    String description;

    @Override
    public String toString() {
        return "VehicleDetectionResult{" +
                "result=" + result +
                ", existedInDataBase=" + existedInDataBase +
                ", existedInBlackList=" + existedInBlackList +
                ", plate='" + plate + '\'' +
                ", Id='" + Id + '\'' +
                ", plateColor='" + plateColor + '\'' +
                ", vehicleColor='" + vehicleColor + '\'' +
                ", brand='" + brand + '\'' +
                ", childBrand='" + childBrand + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isExistedInDataBase() {
        return existedInDataBase;
    }

    public void setExistedInDataBase(boolean existedInDataBase) {
        this.existedInDataBase = existedInDataBase;
    }

    public boolean isExistedInBlackList() {
        return existedInBlackList;
    }

    public void setExistedInBlackList(boolean existedInBlackList) {
        this.existedInBlackList = existedInBlackList;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getChildBrand() {
        return childBrand;
    }

    public void setChildBrand(String childBrand) {
        this.childBrand = childBrand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VehicleDetectionResult() {

    }

    public VehicleDetectionResult(int result, boolean existedInDataBase, boolean existedInBlackList, String plate, String id, String plateColor, String vehicleColor, String brand, String childBrand, String description) {

        this.result = result;
        this.existedInDataBase = existedInDataBase;
        this.existedInBlackList = existedInBlackList;
        this.plate = plate;
        Id = id;
        this.plateColor = plateColor;
        this.vehicleColor = vehicleColor;
        this.brand = brand;
        this.childBrand = childBrand;
        this.description = description;
    }
}
