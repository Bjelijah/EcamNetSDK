package com.howell.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/11.
 */

public class SystemUpTimeUtil {
    private static SystemUpTimeUtil mInstance = null;
    public static SystemUpTimeUtil getInstance(){
        if (mInstance==null){
            mInstance = new SystemUpTimeUtil();
        }
        return mInstance;
    }
    private long systemUptime;
    private Timer timer;
    private SystemUpTimeUtil(){
        systemUptime = 0;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                systemUptime++;
            }
        },0,1000);
    }
    public long getSystemUptime(){
        return systemUptime;
    }
    public void stopTimer(){
        if (timer!=null){
            timer.cancel();
            timer=null;
        }
    }
}
