package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/11.
 */

public class Map {
    String id;
    String name;
    String comment;
    String mapFormat;
    String md5Code;
    String lastModificationTime;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMapFormat() {
        return mapFormat;
    }

    public void setMapFormat(String mapFormat) {
        this.mapFormat = mapFormat;
    }

    public String getMd5Code() {
        return md5Code;
    }

    public void setMd5Code(String md5Code) {
        this.md5Code = md5Code;
    }

    public String getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(String lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public Map(String id, String name, String comment, String mapFormat, String md5Code, String lastModificationTime) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.mapFormat = mapFormat;
        this.md5Code = md5Code;
        this.lastModificationTime = lastModificationTime;
    }

    public Map() {
    }

    @Override
    public String toString() {
        return "Map{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", mapFormat='" + mapFormat + '\'' +
                ", md5Code='" + md5Code + '\'' +
                ", lastModificationTime='" + lastModificationTime + '\'' +
                '}';
    }
}
