package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/10.
 */

public class IOOutputChannel {
    String id;
    String name;
    String triggeringType;

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

    public String getTriggeringType() {
        return triggeringType;
    }

    public void setTriggeringType(String triggeringType) {
        this.triggeringType = triggeringType;
    }

    public IOOutputChannel(String id, String name, String triggeringType) {
        this.id = id;
        this.name = name;
        this.triggeringType = triggeringType;
    }

    public IOOutputChannel() {
    }

    @Override
    public String toString() {
        return "IOOutputChannel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", triggeringType='" + triggeringType + '\'' +
                '}';
    }
}
