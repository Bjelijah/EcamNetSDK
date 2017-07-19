package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/25.
 */

public class GISMapLayer {
    String id;
    String parentLayerId;
    String name;
    int iconType;
    String description;

    public GISMapLayer() {
    }

    public GISMapLayer(String id, String parentLayerId, String name, int iconType, String description) {

        this.id = id;
        this.parentLayerId = parentLayerId;
        this.name = name;
        this.iconType = iconType;
        this.description = description;
    }

    @Override
    public String toString() {
        return "GISMapLayer{" +
                "id='" + id + '\'' +
                ", parentLayerId='" + parentLayerId + '\'' +
                ", name='" + name + '\'' +
                ", iconType=" + iconType +
                ", description='" + description + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentLayerId() {
        return parentLayerId;
    }

    public void setParentLayerId(String parentLayerId) {
        this.parentLayerId = parentLayerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconType() {
        return iconType;
    }

    public void setIconType(int iconType) {
        this.iconType = iconType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
