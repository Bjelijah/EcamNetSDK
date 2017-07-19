package com.howell.bean.httpbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/11.
 */

public class EventLinkage {
    String componentId;
    String eventType;
    String eventState;
    ArrayList<VideoPreviewIdentifier> videoPreviewIdentifiers;
    ArrayList<VideoPlaybackIdentifier> videoPlaybackIdentifiers;
    String textIdentifier;
    AudioPlayerIdentifier audioPlayerIdentifier;

    public AudioPlayerIdentifier getAudioPlayerIdentifier() {
        return audioPlayerIdentifier;
    }

    public void setAudioPlayerIdentifier(AudioPlayerIdentifier audioPlayerIdentifier) {
        this.audioPlayerIdentifier = audioPlayerIdentifier;
    }

    ArrayList<VideoSnapIdentifier> videoSnapIdentifiers;
    ArrayList<String> executors;
    ArrayList<DecoderIdentifier> decoderIdentifiers;

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventState() {
        return eventState;
    }

    public void setEventState(String eventState) {
        this.eventState = eventState;
    }

    public ArrayList<VideoPreviewIdentifier> getVideoPreviewIdentifiers() {
        return videoPreviewIdentifiers;
    }

    public void setVideoPreviewIdentifiers(ArrayList<VideoPreviewIdentifier> videoPreviewIdentifiers) {
        this.videoPreviewIdentifiers = videoPreviewIdentifiers;
    }

    public ArrayList<VideoPlaybackIdentifier> getVideoPlaybackIdentifiers() {
        return videoPlaybackIdentifiers;
    }

    public void setVideoPlaybackIdentifiers(ArrayList<VideoPlaybackIdentifier> videoPlaybackIdentifiers) {
        this.videoPlaybackIdentifiers = videoPlaybackIdentifiers;
    }

    public String getTextIdentifier() {
        return textIdentifier;
    }

    public void setTextIdentifier(String textIdentifier) {
        this.textIdentifier = textIdentifier;
    }

    public ArrayList<VideoSnapIdentifier> getVideoSnapIdentifiers() {
        return videoSnapIdentifiers;
    }

    public void setVideoSnapIdentifiers(ArrayList<VideoSnapIdentifier> videoSnapIdentifiers) {
        this.videoSnapIdentifiers = videoSnapIdentifiers;
    }

    public ArrayList<String> getExecutors() {
        return executors;
    }

    public void setExecutors(ArrayList<String> executors) {
        this.executors = executors;
    }

    public ArrayList<DecoderIdentifier> getDecoderIdentifiers() {
        return decoderIdentifiers;
    }

    public void setDecoderIdentifiers(ArrayList<DecoderIdentifier> decoderIdentifiers) {
        this.decoderIdentifiers = decoderIdentifiers;
    }

    public EventLinkage(String componentId, String eventType, String eventState, ArrayList<VideoPreviewIdentifier> videoPreviewIdentifiers, ArrayList<VideoPlaybackIdentifier> videoPlaybackIdentifiers, String textIdentifier, AudioPlayerIdentifier audioPlayerIdentifier, ArrayList<VideoSnapIdentifier> videoSnapIdentifiers, ArrayList<String> executors, ArrayList<DecoderIdentifier> decoderIdentifiers) {
        this.componentId = componentId;
        this.eventType = eventType;
        this.eventState = eventState;
        this.videoPreviewIdentifiers = videoPreviewIdentifiers;
        this.videoPlaybackIdentifiers = videoPlaybackIdentifiers;
        this.textIdentifier = textIdentifier;
        this.audioPlayerIdentifier = audioPlayerIdentifier;
        this.videoSnapIdentifiers = videoSnapIdentifiers;
        this.executors = executors;
        this.decoderIdentifiers = decoderIdentifiers;
    }

    public EventLinkage() {
    }

    @Override
    public String toString() {
        return "EventLinkage{" +
                "componentId='" + componentId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventState='" + eventState + '\'' +
                ", videoPreviewIdentifiers=" + videoPreviewIdentifiers +
                ", videoPlaybackIdentifiers=" + videoPlaybackIdentifiers +
                ", textIdentifier='" + textIdentifier + '\'' +
                ", audioPlayerIdentifier=" + audioPlayerIdentifier +
                ", videoSnapIdentifiers=" + videoSnapIdentifiers +
                ", executors=" + executors +
                ", decoderIdentifiers=" + decoderIdentifiers +
                '}';
    }
}
