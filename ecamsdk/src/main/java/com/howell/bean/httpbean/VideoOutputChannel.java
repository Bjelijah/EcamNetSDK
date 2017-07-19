package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */

public class VideoOutputChannel {
    String id;
    String name;
    boolean terminal;
    boolean networked;
    boolean interfaceEquipped;
    String resolution;
    int frequency;
    String videoInterfaceType;
    int pseudoCode;
    ArrayList<DecodingChannel> decodingChannels;

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

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    public boolean isNetworked() {
        return networked;
    }

    public void setNetworked(boolean networked) {
        this.networked = networked;
    }

    public boolean isInterfaceEquipped() {
        return interfaceEquipped;
    }

    public void setInterfaceEquipped(boolean interfaceEquipped) {
        this.interfaceEquipped = interfaceEquipped;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getVideoInterfaceType() {
        return videoInterfaceType;
    }

    public void setVideoInterfaceType(String videoInterfaceType) {
        this.videoInterfaceType = videoInterfaceType;
    }

    public int getPseudoCode() {
        return pseudoCode;
    }

    public void setPseudoCode(int pseudoCode) {
        this.pseudoCode = pseudoCode;
    }

    public ArrayList<DecodingChannel> getDecodingChannels() {
        return decodingChannels;
    }

    public void setDecodingChannels(ArrayList<DecodingChannel> decodingChannels) {
        this.decodingChannels = decodingChannels;
    }

    public VideoOutputChannel(String id, String name, boolean terminal, boolean networked, boolean interfaceEquipped, String resolution, int frequency, String videoInterfaceType, int pseudoCode, ArrayList<DecodingChannel> decodingChannels) {
        this.id = id;
        this.name = name;
        this.terminal = terminal;
        this.networked = networked;
        this.interfaceEquipped = interfaceEquipped;
        this.resolution = resolution;
        this.frequency = frequency;
        this.videoInterfaceType = videoInterfaceType;
        this.pseudoCode = pseudoCode;
        this.decodingChannels = decodingChannels;
    }

    public VideoOutputChannel() {
    }

    @Override
    public String toString() {
        return "VideoOutputChannel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", terminal=" + terminal +
                ", networked=" + networked +
                ", interfaceEquipped=" + interfaceEquipped +
                ", resolution='" + resolution + '\'' +
                ", frequency=" + frequency +
                ", videoInterfaceType='" + videoInterfaceType + '\'' +
                ", pseudoCode=" + pseudoCode +
                ", decodingChannels=" + decodingChannels +
                '}';
    }
}
