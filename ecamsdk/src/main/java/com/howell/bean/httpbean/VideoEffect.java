package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/14.
 */

public class VideoEffect {
    double brightness;
    double contrast;
    double saturation;
    double hue;

    @Override
    public String toString() {
        return "VideoEffect{" +
                "brightness=" + brightness +
                ", contrast=" + contrast +
                ", saturation=" + saturation +
                ", hue=" + hue +
                '}';
    }

    public double getBrightness() {
        return brightness;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
    }

    public double getContrast() {
        return contrast;
    }

    public void setContrast(double contrast) {
        this.contrast = contrast;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public double getHue() {
        return hue;
    }

    public void setHue(double hue) {
        this.hue = hue;
    }

    public VideoEffect() {

    }

    public VideoEffect(double brightness, double contrast, double saturation, double hue) {

        this.brightness = brightness;
        this.contrast = contrast;
        this.saturation = saturation;
        this.hue = hue;
    }
}
