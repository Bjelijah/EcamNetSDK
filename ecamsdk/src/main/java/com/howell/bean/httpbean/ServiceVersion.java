package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/25.
 */

public class ServiceVersion {
    String version;
    String buildDate;
    String company;

    @Override
    public String toString() {
        return "ServiceVersion{" +
                "version='" + version + '\'' +
                ", buildDate='" + buildDate + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public ServiceVersion() {

    }

    public ServiceVersion(String version, String buildDate, String company) {

        this.version = version;
        this.buildDate = buildDate;
        this.company = company;
    }
}
