package com.howell.bean.httpbean;

import com.howell.bean.enumerations.ProtocolEnum;

/**
 * Created by Administrator on 2017/4/17.
 */

public class PTZPreset {
    ProtocolEnum.PtzPreset preset;
    int presetNo;
    int speed;

    public PTZPreset(ProtocolEnum.PtzPreset preset, int presetNo, int speed) {
        this.preset = preset;
        this.presetNo = presetNo;
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "PTZPreset{" +
                "preset=" + preset +
                ", presetNo=" + presetNo +
                ", speed=" + speed +
                '}';
    }

    public ProtocolEnum.PtzPreset getPreset() {
        return preset;
    }

    public void setPreset(ProtocolEnum.PtzPreset preset) {
        this.preset = preset;
    }

    public int getPresetNo() {
        return presetNo;
    }

    public void setPresetNo(int presetNo) {
        this.presetNo = presetNo;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public PTZPreset() {

    }
}
