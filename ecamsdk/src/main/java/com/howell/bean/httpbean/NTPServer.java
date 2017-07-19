package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/13.
 */

public class NTPServer {
    String id;
    String hostName;
    String ipAddress;
    String ipv6Address;
    int port;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpv6Address() {
        return ipv6Address;
    }

    public void setIpv6Address(String ipv6Address) {
        this.ipv6Address = ipv6Address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public NTPServer(String id, String hostName, String ipAddress, String ipv6Address, int port) {
        this.id = id;
        this.hostName = hostName;
        this.ipAddress = ipAddress;
        this.ipv6Address = ipv6Address;
        this.port = port;
    }

    public NTPServer() {
    }

    @Override
    public String toString() {
        return "NTPServer{" +
                "id='" + id + '\'' +
                ", hostName='" + hostName + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", ipv6Address='" + ipv6Address + '\'' +
                ", port=" + port +
                '}';
    }
}
