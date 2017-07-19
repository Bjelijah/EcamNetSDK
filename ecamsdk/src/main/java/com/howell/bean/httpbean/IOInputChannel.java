package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/10.
 */

public class IOInputChannel {
    String id;
    String name;
    String probeType;
    String triggeringType;
    String defenceZoneId;

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

    public String getProbeType() {
        return probeType;
    }

    public void setProbeType(String probeType) {
        this.probeType = probeType;
    }

    public String getTriggeringType() {
        return triggeringType;
    }

    public void setTriggeringType(String triggeringType) {
        this.triggeringType = triggeringType;
    }

    public String getDefenceZoneId() {
        return defenceZoneId;
    }

    public void setDefenceZoneId(String defenceZoneId) {
        this.defenceZoneId = defenceZoneId;
    }

    public IOInputChannel(String id, String name, String probeType, String triggeringType, String defenceZoneId) {
        this.id = id;
        this.name = name;
        this.probeType = probeType;
        this.triggeringType = triggeringType;
        this.defenceZoneId = defenceZoneId;
    }

    public IOInputChannel() {
    }

    @Override
    public String toString() {
        return "IOInputChannel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", probeType='" + probeType + '\'' +
                ", triggeringType='" + triggeringType + '\'' +
                ", defenceZoneId='" + defenceZoneId + '\'' +
                '}';
    }
}
