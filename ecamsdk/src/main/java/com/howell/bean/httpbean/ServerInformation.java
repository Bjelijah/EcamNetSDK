package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/19.
 */

public class ServerInformation {
    String address;
    int port;
    boolean timeSynchronization;
    String protocolVersion;

    @Override
    public String toString() {
        return "ServerInformation{" +
                "address='" + address + '\'' +
                ", port=" + port +
                ", timeSynchronization=" + timeSynchronization +
                ", protocolVersion='" + protocolVersion + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isTimeSynchronization() {
        return timeSynchronization;
    }

    public void setTimeSynchronization(boolean timeSynchronization) {
        this.timeSynchronization = timeSynchronization;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public ServerInformation() {

    }

    public ServerInformation(String address, int port, boolean timeSynchronization, String protocolVersion) {

        this.address = address;
        this.port = port;
        this.timeSynchronization = timeSynchronization;
        this.protocolVersion = protocolVersion;
    }
}
