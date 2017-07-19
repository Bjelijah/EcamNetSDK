package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/26.
 */

public class VehiclePlatePicture {
    String id;
    String createTime;
    String md5String;
    String pictureType;
    Size pictureSize;
    int pictureLength;
    String pictureFormat;
    String deviceId;
    String base64data;
    String description;

    @Override
    public String toString() {
        return "VehiclePlatePicture{" +
                "id='" + id + '\'' +
                ", createTime='" + createTime + '\'' +
                ", md5String='" + md5String + '\'' +
                ", pictureType='" + pictureType + '\'' +
                ", pictureSize=" + pictureSize +
                ", pictureLength=" + pictureLength +
                ", pcitureFormat='" + pictureFormat + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", base64data='" + base64data + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMd5String() {
        return md5String;
    }

    public void setMd5String(String md5String) {
        this.md5String = md5String;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public Size getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(Size pictureSize) {
        this.pictureSize = pictureSize;
    }

    public int getPictureLength() {
        return pictureLength;
    }

    public void setPictureLength(int pictureLength) {
        this.pictureLength = pictureLength;
    }

    public String getPcitureFormat() {
        return pictureFormat;
    }

    public void setPcitureFormat(String pcitureFormat) {
        this.pictureFormat = pcitureFormat;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getBase64data() {
        return base64data;
    }

    public void setBase64data(String base64data) {
        this.base64data = base64data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VehiclePlatePicture() {

    }

    public VehiclePlatePicture(String id, String createTime, String md5String, String pictureType, Size pictureSize, int pictureLength, String pcitureFormat, String deviceId, String base64data, String description) {

        this.id = id;
        this.createTime = createTime;
        this.md5String = md5String;
        this.pictureType = pictureType;
        this.pictureSize = pictureSize;
        this.pictureLength = pictureLength;
        this.pictureFormat = pcitureFormat;
        this.deviceId = deviceId;
        this.base64data = base64data;
        this.description = description;
    }
}
