package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/12.
 */

public class Visitor {
    String id;
    String name;
    String idNumberType;
    String idNumber;
    String sex;
    int age;
    String nationality;
    String nation;
    String description;
    String mobile;
    int visitedCount;

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

    public String getIdNumberType() {
        return idNumberType;
    }

    public void setIdNumberType(String idNumberType) {
        this.idNumberType = idNumberType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getVisitedCount() {
        return visitedCount;
    }

    public void setVisitedCount(int visitedCount) {
        this.visitedCount = visitedCount;
    }

    public Visitor(String id, String name, String idNumberType, String idNumber, String sex, int age, String nationality, String nation, String description, String mobile, int visitedCount) {
        this.id = id;
        this.name = name;
        this.idNumberType = idNumberType;
        this.idNumber = idNumber;
        this.sex = sex;
        this.age = age;
        this.nationality = nationality;
        this.nation = nation;
        this.description = description;
        this.mobile = mobile;
        this.visitedCount = visitedCount;
    }

    public Visitor() {
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", idNumberType='" + idNumberType + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", nationality='" + nationality + '\'' +
                ", nation='" + nation + '\'' +
                ", description='" + description + '\'' +
                ", mobile='" + mobile + '\'' +
                ", visitedCount=" + visitedCount +
                '}';
    }
}
