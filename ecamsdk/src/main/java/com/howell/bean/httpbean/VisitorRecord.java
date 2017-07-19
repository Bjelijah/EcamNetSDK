package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/12.
 */

public class VisitorRecord {
    String id;
    String entryTime;
    String departureTime;
    String visitedUnit;
    String visitedStaff;
    String visitedDepartment;
    int permissionLevel;
    String description;
    String visitorRecordNumber;
    int visitorCount;
    Visitor visitor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getVisitedUnit() {
        return visitedUnit;
    }

    public void setVisitedUnit(String visitedUnit) {
        this.visitedUnit = visitedUnit;
    }

    public String getVisitedStaff() {
        return visitedStaff;
    }

    public void setVisitedStaff(String visitedStaff) {
        this.visitedStaff = visitedStaff;
    }

    public String getVisitedDepartment() {
        return visitedDepartment;
    }

    public void setVisitedDepartment(String visitedDepartment) {
        this.visitedDepartment = visitedDepartment;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVisitorRecordNumber() {
        return visitorRecordNumber;
    }

    public void setVisitorRecordNumber(String visitorRecordNumber) {
        this.visitorRecordNumber = visitorRecordNumber;
    }

    public int getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(int visitorCount) {
        this.visitorCount = visitorCount;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public VisitorRecord(String id, String entryTime, String departureTime, String visitedUnit, String visitedStaff, String visitedDepartment, int permissionLevel, String description, String visitorRecordNumber, int visitorCount, Visitor visitor) {
        this.id = id;
        this.entryTime = entryTime;
        this.departureTime = departureTime;
        this.visitedUnit = visitedUnit;
        this.visitedStaff = visitedStaff;
        this.visitedDepartment = visitedDepartment;
        this.permissionLevel = permissionLevel;
        this.description = description;
        this.visitorRecordNumber = visitorRecordNumber;
        this.visitorCount = visitorCount;
        this.visitor = visitor;
    }

    public VisitorRecord() {
    }

    @Override
    public String toString() {
        return "VisitorRecord{" +
                "id='" + id + '\'' +
                ", entryTime='" + entryTime + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", visitedUnit='" + visitedUnit + '\'' +
                ", visitedStaff='" + visitedStaff + '\'' +
                ", visitedDepartment='" + visitedDepartment + '\'' +
                ", permissionLevel=" + permissionLevel +
                ", description='" + description + '\'' +
                ", visitorRecordNumber='" + visitorRecordNumber + '\'' +
                ", visitorCount=" + visitorCount +
                ", visitor=" + visitor +
                '}';
    }
}
