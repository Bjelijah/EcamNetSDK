package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/14.
 */

public class PTZProtocol {
    String protocol;
    String description;

    @Override
    public String toString() {
        return "PTZProtocol{" +
                "protocol='" + protocol + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PTZProtocol() {

    }

    public PTZProtocol(String protocol, String description) {

        this.protocol = protocol;
        this.description = description;
    }
}
