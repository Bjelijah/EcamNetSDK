package com.howell.bean.httpbean;

/**
 * Created by Administrator on 2017/4/14.
 */

public class PTZControl {
    public enum PTZ_DIRECTION{
        STOP("Stop"),
        UP("Up"),
        DOWN("Down"),
        LEFT("Left"),
        RIGHT("Right");
        private final String s;
        PTZ_DIRECTION(String str){
            this.s = str;
        }
        public String getVal(){
            return s;
        }
    }

    PTZ_DIRECTION control;
    int speed;

    public PTZControl(PTZ_DIRECTION control, int speed) {
        this.control = control;
        this.speed = speed;
    }

    public PTZControl() {
    }

    public PTZ_DIRECTION getControl() {
        return control;
    }

    @Override
    public String toString() {
        return "PTZControl{" +
                "control=" + control +
                ", speed=" + speed +
                '}';
    }

    public void setControl(PTZ_DIRECTION control) {
        this.control = control;
    }

    public String getControlString(){
        return this.control.getVal();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
