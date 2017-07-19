package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */

public class VideoInputChannel {
    String id;
    String name;
    boolean ptz;
    boolean infrared;
    String cameraType;
    boolean terminal;
    boolean networked;
    String videoInterfaceType;
    int pseudoCode;
    ArrayList<StreamingChannel> streamingChannels;
    VideoInputAssociation videoInputAssociation;

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

    public boolean isPtz() {
        return ptz;
    }

    public void setPtz(boolean ptz) {
        this.ptz = ptz;
    }

    public boolean isInfrared() {
        return infrared;
    }

    public void setInfrared(boolean infrared) {
        this.infrared = infrared;
    }

    public String getCameraType() {
        return cameraType;
    }

    public void setCameraType(String cameraType) {
        this.cameraType = cameraType;
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

    public ArrayList<StreamingChannel> getStreamingChannels() {
        return streamingChannels;
    }

    public void setStreamingChannels(ArrayList<StreamingChannel> streamingChannels) {
        this.streamingChannels = streamingChannels;
    }

    public VideoInputAssociation getVideoInputAssociation() {
        return videoInputAssociation;
    }

    public void setVideoInputAssociation(VideoInputAssociation videoInputAssociation) {
        this.videoInputAssociation = videoInputAssociation;
    }

    public VideoInputChannel(String id, String name, boolean ptz, boolean infrared, String cameraType, boolean terminal, boolean networked, String videoInterfaceType, int pseudoCode, ArrayList<StreamingChannel> streamingChannels, VideoInputAssociation videoInputAssociation) {
        this.id = id;
        this.name = name;
        this.ptz = ptz;
        this.infrared = infrared;
        this.cameraType = cameraType;
        this.terminal = terminal;
        this.networked = networked;
        this.videoInterfaceType = videoInterfaceType;
        this.pseudoCode = pseudoCode;
        this.streamingChannels = streamingChannels;
        this.videoInputAssociation = videoInputAssociation;
    }

    public VideoInputChannel() {
    }

    @Override
    public String toString() {
        return "VideoInputChannel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ptz=" + ptz +
                ", infrared=" + infrared +
                ", cameraType='" + cameraType + '\'' +
                ", terminal=" + terminal +
                ", networked=" + networked +
                ", videoInterfaceType='" + videoInterfaceType + '\'' +
                ", pseudoCode=" + pseudoCode +
                ", streamingChannels=" + streamingChannels +
                ", videoInputAssociation=" + videoInputAssociation +
                '}';
    }
}
