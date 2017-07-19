package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/13.
 */

public class WifiInformation {
    String name;
    String password;
    int channel;
    int intensity;

    public WifiInformation(String name, String password, int channel, int intensity) {
        this.name = name;
        this.password = password;
        this.channel = channel;
        this.intensity = intensity;
    }

    public WifiInformation() {
    }

    @Override
    public String toString() {
        return "WifiInformation{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", channel=" + channel +
                ", intensity=" + intensity +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }
}
