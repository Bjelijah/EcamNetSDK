package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/11.
 */

public class SystemWarningReport {
    String id;
    String creationTime;
    String warningType;
    String componentId;
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

    public String getWarningType() {
        return warningType;
    }

    public void setWarningType(String warningType) {
        this.warningType = warningType;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
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

    public SystemWarningReport(String id, String creationTime, String warningType, String componentId, String description, String componentName) {
        this.id = id;
        this.creationTime = creationTime;
        this.warningType = warningType;
        this.componentId = componentId;
        this.description = description;
        this.componentName = componentName;
    }

    public SystemWarningReport() {
    }

    @Override
    public String toString() {
        return "SystemWarningReport{" +
                "id='" + id + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", warningType='" + warningType + '\'' +
                ", componentId='" + componentId + '\'' +
                ", description='" + description + '\'' +
                ", componentName='" + componentName + '\'' +
                '}';
    }
}
