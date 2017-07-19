package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/14.
 */

public class PTZDecoder {
    int baudRate;
    int dataBit;
    String stopBit;
    String parity;
    boolean dtrEanble;
    boolean rtsEnable;
    String protocol;
    int address;

    @Override
    public String toString() {
        return "PTZDecoder{" +
                "baudRate=" + baudRate +
                ", dataBit=" + dataBit +
                ", stopBit='" + stopBit + '\'' +
                ", parity='" + parity + '\'' +
                ", dtrEanble=" + dtrEanble +
                ", rtsEnable=" + rtsEnable +
                ", protocol='" + protocol + '\'' +
                ", address=" + address +
                '}';
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDataBit() {
        return dataBit;
    }

    public void setDataBit(int dataBit) {
        this.dataBit = dataBit;
    }

    public String getStopBit() {
        return stopBit;
    }

    public void setStopBit(String stopBit) {
        this.stopBit = stopBit;
    }

    public String getParity() {
        return parity;
    }

    public void setParity(String parity) {
        this.parity = parity;
    }

    public boolean isDtrEanble() {
        return dtrEanble;
    }

    public void setDtrEanble(boolean dtrEanble) {
        this.dtrEanble = dtrEanble;
    }

    public boolean isRtsEnable() {
        return rtsEnable;
    }

    public void setRtsEnable(boolean rtsEnable) {
        this.rtsEnable = rtsEnable;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public PTZDecoder() {

    }

    public PTZDecoder(int baudRate, int dataBit, String stopBit, String parity, boolean dtrEanble, boolean rtsEnable, String protocol, int address) {

        this.baudRate = baudRate;
        this.dataBit = dataBit;
        this.stopBit = stopBit;
        this.parity = parity;
        this.dtrEanble = dtrEanble;
        this.rtsEnable = rtsEnable;
        this.protocol = protocol;
        this.address = address;
    }
}
