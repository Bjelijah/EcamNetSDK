package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/11.
 */

public class SystemFaultReport {
    String id;
    String creationTime;
    String faultType;
    String componentId;
    boolean Recovered;
    String recoveryTime;
    String description;
    String componentName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public boolean isRecovered() {
        return Recovered;
    }

    public void setRecovered(boolean recovered) {
        Recovered = recovered;
    }

    public String getRecoveryTime() {
        return recoveryTime;
    }

    public void setRecoveryTime(String recoveryTime) {
        this.recoveryTime = recoveryTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public SystemFaultReport(String id, String creationTime, String faultType, String componentId, boolean recovered, String recoveryTime, String description, String componentName) {
        this.id = id;
        this.creationTime = creationTime;
        this.faultType = faultType;
        this.componentId = componentId;
        Recovered = recovered;
        this.recoveryTime = recoveryTime;
        this.description = description;
        this.componentName = componentName;
    }

    public SystemFaultReport() {
    }

}
