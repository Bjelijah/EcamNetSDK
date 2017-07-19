package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/18.
 */

public class EventLinkageParameter {
    String name;
    String type;
    String defaultValue;

    @Override
    public String toString() {
        return "EventLinkageParameter{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public EventLinkageParameter() {

    }

    public EventLinkageParameter(String name, String type, String defaultValue) {

        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }
}
