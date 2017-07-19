package com.howell.action;

import android.content.Context;
import android.content.Intent;

import com.howell.service.PushService;

/**
 * 推送接口
 * Created by Administrator on 2017/7/7.
 * @author howell
 */

public class PushAction {
    private static PushAction mInstance = null;
    public static PushAction getInstance(){
        if (mInstance==null){
            mInstance = new PushAction();
        }
        return mInstance;
    }
    private PushAction(){}

    /**
     * 开启推送服务
     * @param c 上下文
     * @param ip 服务器ip
     * @param account 用户名
     */
    public void startPushServer(Context c,String ip,String account){
        Intent intent = new Intent(c, PushService.class);
        intent.putExtra("ip",ip).putExtra("account",account);
        c.startService(intent);
    }

    /**
     * 停止推送服务
     * @param c 上下文
     */
    public void stopPushServer(Context c){
        Intent intent = new Intent(c, PushService.class);
        c.stopService(intent);
    }



}
