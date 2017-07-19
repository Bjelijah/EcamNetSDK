package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/26.
 */

public class Size {
    int width;
    int height;

    @Override
    public String toString() {
        return "Size{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Size() {

    }

    public Size(int width, int height) {

        this.width = width;
        this.height = height;
    }
}
