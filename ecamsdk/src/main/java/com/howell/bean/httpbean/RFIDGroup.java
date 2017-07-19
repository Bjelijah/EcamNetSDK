package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/19.
 */

public class RFIDGroup {
    String id;
    String name;
    String description;

    @Override
    public String toString() {
        return "RFIDGroup{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RFIDGroup() {

    }

    public RFIDGroup(String id, String name, String description) {

        this.id = id;
        this.name = name;
        this.description = description;
    }
}
