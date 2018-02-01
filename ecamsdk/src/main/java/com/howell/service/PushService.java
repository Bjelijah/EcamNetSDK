package com.howell.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.howell.bean.httpbean.WSRes;
import com.howell.protocol.http.HttpManager;
import com.howell.protocol.utils.SDKDebugLog;
import com.howell.protocol.websocket.WebSocketManager;
import com.howell.utils.SystemUpTimeUtil;

import org.json.JSONException;

import de.tavendo.autobahn.WebSocketException;

/**
 * 推送服务
 * Created by Administrator on 2017/7/7.
 * @author howell
 */
public class PushService extends Service implements WebSocketManager.IMessage {
    public static final String BROADCAST_NAME_EVENT = "event";
    public static final String BROADCAST_NAME_NOTICE = "notice";
    private static final String TAG = PushService.class.getName();
    WebSocketManager mgr = new WebSocketManager();
    boolean mWsIsOpen = false;
    int mCseq=0;
    private String mIp;
    private String mAccount;
    private static boolean bStart = false;
    Handler mHandler = new Handler();
    Runnable heartRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                mgr.alarmAlive(getCseq(), SystemUpTimeUtil.getInstance().getSystemUptime(),0,0,false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mHandler.postDelayed(this,60*1000);
        }
    };

    private int getCseq(){
        return mCseq++;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        SDKDebugLog.logI(TAG,"onBind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        SDKDebugLog.logI(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        SDKDebugLog.logE(TAG,"onTaskRemoved");
        bStart = false;
        mgr.deInit();
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        SDKDebugLog.logI(TAG,"onDestroy");
        bStart = false;
        mgr.deInit();
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        SDKDebugLog.logI(TAG,"onStart");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        SDKDebugLog.logI(TAG,"onStartCommand");
        if (!bStart) {
            mIp = intent.getStringExtra("ip");
            mAccount = intent.getStringExtra("account");
            try {
                mgr.registMessage(this).initURL(mIp);
            } catch (WebSocketException e) {
                e.printStackTrace();
            }
            bStart = true;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        SDKDebugLog.logI(TAG,"onCreate");
        bStart = false;
        super.onCreate();
    }




    @Override
    public ComponentName startService(Intent service) {
        SDKDebugLog.logI(TAG,"startService");
        return super.startService(service);
    }

    @Override
    public void onWebSocketOpen() {
        SDKDebugLog.logI(TAG+":onWebSocketOpen","ws open");
        mWsIsOpen = true;
        // link to server
        try {
            mgr.alarmLink(getCseq(), HttpManager.getInstance().getSession(),mAccount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // heartbeat
        mHandler.postDelayed(heartRunnable,60*1000);
    }

    @Override
    public void onWebSocketClose() {
        SDKDebugLog.logI(TAG+":onWebSocketClose","ws close");
        mWsIsOpen = false;
        mHandler.removeCallbacks(heartRunnable);
    }

    @Override
    public void onGetMessage(WSRes res) {
        if (res==null){
            Log.e("123","WSRes=null");
            SDKDebugLog.logE(TAG+":onGetMessage","WSRes=null");
            return;
        }
        processMsg(res);
    }

    @Override
    public void onError(int error) {
        SDKDebugLog.logE(TAG+":onError","WS error ="+error);
    }

    private void processMsg(WSRes res){
        switch (res.getType()){
            case ALARM_LINK://连接
                WSRes.AlarmLinkRes linkRes = (WSRes.AlarmLinkRes) res.getResultObject();
                SDKDebugLog.logI(TAG+":processMsg","link:"+linkRes.toString());
                break;
            case ALARM_ALIVE://心跳
                WSRes.AlarmAliveRes aliveRes = (WSRes.AlarmAliveRes) res.getResultObject();
                SDKDebugLog.logI(TAG+":processMsg","alive:"+aliveRes.toString());
//                mDB.insert(aliveRes); //// FIXME: 2017/6/13  should not insert heartbeat to db
                break;
            case ALARM_EVENT://事件
                WSRes.AlarmEvent eventRes = (WSRes.AlarmEvent) res.getResultObject();
                SDKDebugLog.logI(TAG+":processMsg","event:"+eventRes.toString());
//                mDB.insert(eventRes);
                //todo do work
                doEvent(eventRes);
                //todo send res
                try {
                    mgr.ADCEventRes(eventRes.getCseq());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case ALARM_NOTICE://消息
                WSRes.AlarmNotice noticeRes = (WSRes.AlarmNotice) res.getResultObject();
                SDKDebugLog.logI(TAG+":processMsg","notice:"+noticeRes.toString());
//                mDB.insert(noticeRes);
                //todo do work
                doNotice(noticeRes);
                //todo send res
                try {
                    mgr.ADCNoticeRes(noticeRes.getCseq());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    private void doEvent(WSRes.AlarmEvent event){
        Intent intent = new Intent("com.howell.sdk.alarmEvent");
        intent.putExtra(BROADCAST_NAME_EVENT,event);
        sendBroadcast(intent);
    }

    private void doNotice( WSRes.AlarmNotice notice){
        Intent intent = new Intent("com.howell.sdk.alarmNotice");
        intent.putExtra(BROADCAST_NAME_NOTICE,notice);
        sendBroadcast(intent);
    }

}
