package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/13.
 */

public class DeviceProtocolCapabilities {
    String protocolType;
    String comment;
    String uriFormat;
    boolean hasVideoInputChannel;
    boolean hasVideoOutputChannel;
    boolean hasIOInputChannel;
    boolean hasIOOutputchannel;
    boolean hasNetworkInterface;
    boolean hasStorageMedium;
    boolean supportedSearch;

    public String getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = protocolType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUriFormat() {
        return uriFormat;
    }

    public void setUriFormat(String uriFormat) {
        this.uriFormat = uriFormat;
    }

    public boolean isHasVideoInputChannel() {
        return hasVideoInputChannel;
    }

    public void setHasVideoInputChannel(boolean hasVideoInputChannel) {
        this.hasVideoInputChannel = hasVideoInputChannel;
    }

    public boolean isHasVideoOutputChannel() {
        return hasVideoOutputChannel;
    }

    public void setHasVideoOutputChannel(boolean hasVideoOutputChannel) {
        this.hasVideoOutputChannel = hasVideoOutputChannel;
    }

    public boolean isHasIOInputChannel() {
        return hasIOInputChannel;
    }

    public void setHasIOInputChannel(boolean hasIOInputChannel) {
        this.hasIOInputChannel = hasIOInputChannel;
    }

    public boolean isHasIOOutputchannel() {
        return hasIOOutputchannel;
    }

    public void setHasIOOutputchannel(boolean hasIOOutputchannel) {
        this.hasIOOutputchannel = hasIOOutputchannel;
    }

    public boolean isHasNetworkInterface() {
        return hasNetworkInterface;
    }

    public void setHasNetworkInterface(boolean hasNetworkInterface) {
        this.hasNetworkInterface = hasNetworkInterface;
    }

    public boolean isHasStorageMedium() {
        return hasStorageMedium;
    }

    public void setHasStorageMedium(boolean hasStorageMedium) {
        this.hasStorageMedium = hasStorageMedium;
    }

    public boolean isSupportedSearch() {
        return supportedSearch;
    }

    public void setSupportedSearch(boolean supportedSearch) {
        this.supportedSearch = supportedSearch;
    }

    public DeviceProtocolCapabilities(String protocolType, String comment, String uriFormat, boolean hasVideoInputChannel, boolean hasVideoOutputChannel, boolean hasIOInputChannel, boolean hasIOOutputchannel, boolean hasNetworkInterface, boolean hasStorageMedium, boolean supportedSearch) {
        this.protocolType = protocolType;
        this.comment = comment;
        this.uriFormat = uriFormat;
        this.hasVideoInputChannel = hasVideoInputChannel;
        this.hasVideoOutputChannel = hasVideoOutputChannel;
        this.hasIOInputChannel = hasIOInputChannel;
        this.hasIOOutputchannel = hasIOOutputchannel;
        this.hasNetworkInterface = hasNetworkInterface;
        this.hasStorageMedium = hasStorageMedium;
        this.supportedSearch = supportedSearch;
    }

    public DeviceProtocolCapabilities() {
    }

    @Override
    public String toString() {
        return "DeviceProtocolCapabilities{" +
                "protocolType='" + protocolType + '\'' +
                ", comment='" + comment + '\'' +
                ", uriFormat='" + uriFormat + '\'' +
                ", hasVideoInputChannel=" + hasVideoInputChannel +
                ", hasVideoOutputChannel=" + hasVideoOutputChannel +
                ", hasIOInputChannel=" + hasIOInputChannel +
                ", hasIOOutputchannel=" + hasIOOutputchannel +
                ", hasNetworkInterface=" + hasNetworkInterface +
                ", hasStorageMedium=" + hasStorageMedium +
                ", supportedSearch=" + supportedSearch +
                '}';
    }
}
