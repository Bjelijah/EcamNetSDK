package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/13.
 */

public class DeviceClassificationCapabilities {
    String classification;
    ArrayList<DeviceProtocolCapabilities> capabilities;

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public ArrayList<DeviceProtocolCapabilities> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(ArrayList<DeviceProtocolCapabilities> capabilities) {
        this.capabilities = capabilities;
    }

    public DeviceClassificationCapabilities(String classification, ArrayList<DeviceProtocolCapabilities> capabilities) {
        this.classification = classification;
        this.capabilities = capabilities;
    }

    public DeviceClassificationCapabilities() {
    }

    @Override
    public String toString() {
        return "DeviceClassificationCapabilities{" +
                "classification='" + classification + '\'' +
                ", capabilities=" + capabilities +
                '}';
    }
}
