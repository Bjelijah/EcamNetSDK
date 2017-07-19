package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class EventLinkageTemplate {
    String id;
    String Name;
    ArrayList<EventLinkageParameter> parameters;
    EventLinkage eventLinkage;

    @Override
    public String toString() {
        return "EventLinkageTemplate{" +
                "id='" + id + '\'' +
                ", Name='" + Name + '\'' +
                ", parameters=" + parameters +
                ", eventLinkage=" + eventLinkage +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<EventLinkageParameter> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<EventLinkageParameter> parameters) {
        this.parameters = parameters;
    }

    public EventLinkage getEventLinkage() {
        return eventLinkage;
    }

    public void setEventLinkage(EventLinkage eventLinkage) {
        this.eventLinkage = eventLinkage;
    }

    public EventLinkageTemplate() {

    }

    public EventLinkageTemplate(String id, String name, ArrayList<EventLinkageParameter> parameters, EventLinkage eventLinkage) {

        this.id = id;
        Name = name;
        this.parameters = parameters;
        this.eventLinkage = eventLinkage;
    }
}
