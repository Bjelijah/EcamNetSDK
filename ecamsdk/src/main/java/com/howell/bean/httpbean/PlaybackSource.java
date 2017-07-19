package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/14.
 */

public class PlaybackSource {
    String id;
    String playbackFromId;
    String playbakcFromUrl;

    @Override
    public String toString() {
        return "PlaybackSource{" +
                "id='" + id + '\'' +
                ", playbackFromId='" + playbackFromId + '\'' +
                ", playbakcFromUrl='" + playbakcFromUrl + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaybackFromId() {
        return playbackFromId;
    }

    public void setPlaybackFromId(String playbackFromId) {
        this.playbackFromId = playbackFromId;
    }

    public String getPlaybakcFromUrl() {
        return playbakcFromUrl;
    }

    public void setPlaybakcFromUrl(String playbakcFromUrl) {
        this.playbakcFromUrl = playbakcFromUrl;
    }

    public PlaybackSource() {

    }

    public PlaybackSource(String id, String playbackFromId, String playbakcFromUrl) {

        this.id = id;
        this.playbackFromId = playbackFromId;
        this.playbakcFromUrl = playbakcFromUrl;
    }
}
