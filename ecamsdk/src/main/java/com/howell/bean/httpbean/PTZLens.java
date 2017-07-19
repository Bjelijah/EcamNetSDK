package com.howell.bean.httpbean;

import com.howell.bean.enumerations.ProtocolEnum;

/**
 * Created by Administrator on 2017/4/17.
 */

public class PTZLens {
    ProtocolEnum.PtzLens ptzLen;
    int speed;

    @Override
    public String toString() {
        return "PTZLens{" +
                "ptzLen=" + ptzLen +
                ", speed=" + speed +
                '}';
    }

    public ProtocolEnum.PtzLens getPtzLen() {
        return ptzLen;
    }

    public void setPtzLen(ProtocolEnum.PtzLens ptzLen) {
        this.ptzLen = ptzLen;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public PTZLens() {

    }

    public PTZLens(ProtocolEnum.PtzLens ptzLen, int speed) {

        this.ptzLen = ptzLen;
        this.speed = speed;
    }
}
