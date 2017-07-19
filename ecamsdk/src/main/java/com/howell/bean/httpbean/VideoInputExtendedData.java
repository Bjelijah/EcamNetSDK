package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/17.
 */

public class VideoInputExtendedData {
    String id;
    String name;
    int extendedType;
    ArrayList<VideoInputChannel> channels;

    public VideoInputExtendedData(String id, String name, int extendedType, ArrayList<VideoInputChannel> channels) {
        this.id = id;
        this.name = name;
        this.extendedType = extendedType;
        this.channels = channels;
    }

    @Override
    public String toString() {
        return "VideoInputEXtendedData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", extendedType=" + extendedType +
                ", channels=" + channels +
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

    public int getExtendedType() {
        return extendedType;
    }

    public void setExtendedType(int extendedType) {
        this.extendedType = extendedType;
    }

    public ArrayList<VideoInputChannel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<VideoInputChannel> channels) {
        this.channels = channels;
    }

    public VideoInputExtendedData() {

    }
}
