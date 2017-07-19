package com.howell.ecamnetsdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.howell.bean.turnbean.Subscribe;
import com.howell.bean.turnbean.TurnDisSubscribeAckBean;
import com.howell.bean.turnbean.TurnGetRecordedFileAckBean;
import com.howell.bean.turnbean.TurnGetRecordedFilesBean;
import com.howell.bean.turnbean.TurnSubScribe;
import com.howell.bean.turnbean.TurnSubScribeAckBean;
import com.howell.jni.JniUtil;
import com.howell.player.BasePlayer;
import com.howell.protocol.turn.TurnJsonUtil;
import com.howell.protocol.turn.TurnManager;
import com.howell.utils.DemoDebugLog;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import static com.howell.ecamnetsdk.PlayManager.PlayCmd.PLAY_BACK;
import static com.howell.ecamnetsdk.PlayManager.PlayCmd.PLAY_VIEW;
import static com.howell.ecamnetsdk.PlayManager.PlayCmd.RECORDED_LIST;


/**
 * Created by Administrator on 2017/4/21.<br/>
 * 播放管理类<br/>
 * 通过 TurnManager 收发协议与流转服务器通信及传输数据<br/>
 * TurnManager为异步回调<br/>
 *
 * @author howell
 */

public class PlayManager extends BasePlayer{
    private static final String TAG = PlayManager.class.getName();
    public static final int MSG_RECONNECT = 0x00;
    private static final int F_TIME = 1;

    private Timer timer = null;
    private MyTimerTask myTimerTask = null;
    TimeCallback cb=null;

    public void setTimeCallBack(TimeCallback cb){
        this.cb = cb;
    }
    public enum PlayCmd{
        PLAY_VIEW,
        PLAY_BACK,
        RECORDED_LIST
    }
    private PlayCmd mCmdType;
    TurnManager mgr;

    private int dialogId = 0;
    private int getDialogId(){
        return dialogId++;
    }

    boolean misPlayback;

    boolean mIsRePlay = false;
    TurnResult mTurnResultCb = new TurnResult();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what ){
                case MSG_RECONNECT:
                    mgr.connect();
                    break;
            }
        }
    };

    public void startTimeTask(){
        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 0,F_TIME*1000);
    }

    public void stopTimeTask(){
        if (timer!=null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        if (myTimerTask!=null) {
            myTimerTask.cancel();
            myTimerTask = null;
        }
    }


    private void onConnectFun(String sessionId){

//        subcribePlayView(mDeviceId,0,misSub);
        switch (mCmdType){
            case PLAY_VIEW:
                DemoDebugLog.logI(TAG+":onConnectFun","subscribe play view");
                mgr.subscribe(new TurnSubScribe(sessionId,"media",new TurnSubScribe.media(getDialogId(),new TurnSubScribe.meta(mDeviceId,"live",mChannel,mIsSub?1:0))));
                break;
            case PLAY_BACK:
                DemoDebugLog.logI(TAG+":onConnectFun","subscribe playback");
                mgr.subscribe(new TurnSubScribe(sessionId,"media",new TurnSubScribe.media(getDialogId(),new TurnSubScribe.meta(mDeviceId,"playback",mChannel,mIsSub?1:0,mBeg,mEnd))));
                break;
            case RECORDED_LIST:
                DemoDebugLog.logI(TAG+":onConnectFun","get record Files");
                mgr.getRecordedFiles(new TurnGetRecordedFilesBean(mDeviceId,mChannel,mBeg,mEnd));
                break;
            default:
                break;
        }
    }


    /**
     * 初始化并注册回调
     * @param mgr turnManager
     * @param deviceId 设备id
     * @param channel 通道
     * @param isSub 是否为次码流
     * @return
     */
    @Override
    public BasePlayer init(TurnManager mgr,String deviceId,int channel,boolean isSub) {

        super.init(mgr,deviceId,channel,isSub);
        this.mgr = mgr.registResultCallback(mTurnResultCb);
        return this;
    }

    /**
     * 断连，释放资源，取消回调注册
     *
     */
    @Override
    public void deInit(){

        mgr.unregistResultCallback(mTurnResultCb);
        mgr.disconnect();
        super.deInit();
    }


    /**
     * 播放<br/>
     * 连接后收到回调->连接成功，申请订阅视频流->订阅成功，解码器解码->等待流并播放
     */
    @Override
    public void playView() {
        super.playView();
        misPlayback = false;
        mCmdType = PLAY_VIEW;

        mgr.connect();
    }

    /**
     * 回放<br/>
     * 连接后收到回调->连接成功，申请订阅回放流->订阅成功，解码器解码->等待流并播放
     * @param beg 回放开始时间
     * @param end 回放结束时间
     */
    @Override
    public void playback(String beg,String end) {
        super.playback(beg,end);
        misPlayback = true;
        mCmdType = PLAY_BACK;
        mgr.connect();
    }

    /**
     * 重新回放<br/>
     * 如：滑杆条滑动
     * @param beg 新回放的开始时间
     * @param end 新回放的结束时间
     */
    @Override
    public void rePlayback(String beg, String end) {
        super.rePlayback(beg, end);
        misPlayback = true;
        mCmdType = PLAY_BACK;
        mIsRePlay = true;
        stopView();
    }

    /**
     * 获取回放列表
     * @param beg 列表的开始时间
     * @param end 列表的结束时间
     */
    @Override
    public void getRecoredeFile(String beg,String end) {
        super.getRecoredeFile(beg,end);
        mCmdType = RECORDED_LIST;
        mgr.connect();
    }

    /**
     * 准备解码器及播放<br/>
     * @param bean 订阅后收到的返回包括音视频类型等信息
     * @param isPlayback 是否是回放
     */
    @Override
    public void readyAndPlay(TurnSubScribeAckBean bean,boolean isPlayback) {
        super.readyAndPlay(bean,isPlayback);
    }

    /**
     * 停止播放<br/>
     * 取消订阅 ，停止播放
     */
    @Override
    public void stopView(){
        mgr.unSubscribe();
        super.stopView();
    }


    /**
     * turnManager 回调类
     */
    class TurnResult implements TurnManager.ITurn{

        /**
         * 连接成功的回调
         * @param sessionId 连接获得服务器分配的会话id
         */
        @Override
        public void onConnect(String sessionId) {
            DemoDebugLog.logI(TAG+":onConnect","session id="+sessionId);
            onConnectFun(sessionId);
        }

        /**
         * 断连
         */
        @Override
        public void onDisconnect() {
            DemoDebugLog.logI(TAG+":onDisconnect","disconnect");
            if (mIsRePlay){
                DemoDebugLog.logI(TAG+":onDisconnect","new connect again");
                handler.sendEmptyMessageDelayed(MSG_RECONNECT,1000);
            }
        }

        /**
         * 意外断连
         * @param flag 标志位
         *             <dl>
         *             <dt>值如下：<dt/>
         *             <dd> 0：连接时 socket error 如：select超时（此回调与调用方法可能在同一线程中）<dd/>
         *             <dd> 1: 获取的包同步位不同,若之后连接正常则为视频传输时网络连接状态低下而丢包，若持续则需要重新连接 <dd/>
         *             <dd> 2：socket read <= 0 ,socket 断开了<dd/>
         *             <dd> 3: 收到数据正常，解析后 http code ！=200，或服务器错误或客户端发送的参数错误 <dd/>
         *             </dl>
         *             <br></>
         * 注意：断线从连时:<br/></>
         * mgr.disconnect 是发送disconnect协议到服务器 类似 logout server的功能，mgr.connect则是 login<br></>
         * 根据不同flag需要将socket断开从连 则需要mgr.deinit(断开socket端口+释放内存)  mgr.init（初始化内存  +初始化socket）
         */
        @Override
        public void onDisconnectUnexpect(int flag) {
            //TODO   need reLink
            // send msg in new thread
            //
            DemoDebugLog.logE(TAG+":onDisconnectUnexpect","play manager onDisconnect flag="+flag);
        }

        /**
         * 获取回放列表回调
         * @param fileList 回放列表
         */
        @Override
        public void onRecordFileList(TurnGetRecordedFileAckBean fileList) {

        }

        /**
         * 订阅回调
         * @param jsonStr 回调的json字符串 ，应用层自行解析
         */
        @Override
        public void onSubscribe(String jsonStr) {
            DemoDebugLog.logI(TAG+":onSubscribe","jsonStr="+jsonStr);
            readyAndPlay(TurnJsonUtil.getTurnSubscribeAckAllFromJsonStr(jsonStr),misPlayback);
        }

        /**
         * 退订回调
         * @param jsonStr 退订的json字符串，应用层自行解析
         */
        @Override
        public void onUnsubscribe(String jsonStr) {
            DemoDebugLog.logI(TAG+":onUnsubscribe","jsonStr="+jsonStr);
            TurnDisSubscribeAckBean bean = TurnJsonUtil.getTurnDisSubscribeAckFromJsonStr(jsonStr);
            if (bean.getCode()==200){
                DemoDebugLog.logI(TAG+":onUnsubscribe","now we disconnect");
                mgr.disconnect();
            }

        }
    }

    /**
     * 回放时开启时间任务，获取时间戳及流长度<br/>
     * 流长度：为上次调用到此次调用获取到的流长度，单位char字符<br/>
     * 时间戳：解码后的帧时间戳，单位毫秒
     */
    class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            int streamLen = mgr.getStreamLen();
            int speed = streamLen*8/1024/F_TIME;
            long timeStamp = mgr.getTimeStamp();
            long firstTimeStamp = mgr.getFirstTimeStamp();
            if (cb!=null){
                cb.onTime(speed,timeStamp,firstTimeStamp);
            }
        }
    }

    interface TimeCallback{
        void onTime(int speed,long time,long first);
    }


}
