package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/12.
 */

public class MapItem {
    String id;
    String itemType;
    String componentId;
    Coordinate coordinate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public MapItem(String id, String itemType, String componentId, Coordinate coordinate) {
        this.id = id;
        this.itemType = itemType;
        this.componentId = componentId;
        this.coordinate = coordinate;
    }

    public MapItem() {
    }

    @Override
    public String toString() {
        return "MapItem{" +
                "id='" + id + '\'' +
                ", itemType='" + itemType + '\'' +
                ", componentId='" + componentId + '\'' +
                ", coordinate=" + coordinate +
                '}';
    }
}
