package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/13.
 */

public class ClientAddress {
    String ipv4;
    String ipv6;

    public ClientAddress(String ipv4, String ipv6) {
        this.ipv4 = ipv4;
        this.ipv6 = ipv6;
    }

    public ClientAddress() {
    }

    @Override
    public String toString() {
        return "ClientAddress{" +
                "ipv4='" + ipv4 + '\'' +
                ", ipv6='" + ipv6 + '\'' +
                '}';
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }
}
