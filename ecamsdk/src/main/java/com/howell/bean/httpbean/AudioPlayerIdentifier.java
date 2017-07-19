package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/11.
 */

public class AudioPlayerIdentifier {
    String audioUrl;
    int repeatTimes;
    int duration;

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public int getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(int repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public AudioPlayerIdentifier(String audioUrl, int repeatTimes, int duration) {
        this.audioUrl = audioUrl;
        this.repeatTimes = repeatTimes;
        this.duration = duration;
    }

    public AudioPlayerIdentifier() {
    }

    @Override
    public String toString() {
        return "AudioPlayerIdentifier{" +
                "audioUrl='" + audioUrl + '\'' +
                ", repeatTimes=" + repeatTimes +
                ", duration=" + duration +
                '}';
    }
}
