package com.howell.action;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.howell.bean.httpbean.VideoInputChannelPermissionList;
import com.howell.bean.turnbean.TurnGetRecordedFileAckBean;
import com.howell.bean.turnbean.TurnGetRecordedFilesBean;
import com.howell.protocol.http.HttpManager;
import com.howell.protocol.http.Util;
import com.howell.protocol.turn.TurnManager;
import com.howell.protocol.utils.SDKDebugLog;


import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


/**
 * 设备列表，回放列表 接口
 * Created by Administrator on 2017/7/6.
 * @author howell
 */
public class VideoListAction implements TurnManager.ITurn {
    private static final String TAG = VideoListAction.class.getName();
    private static VideoListAction mInstance = null;
    public static VideoListAction getInstance(){
        if (mInstance == null){
            mInstance = new VideoListAction();
        }
        return mInstance;
    }
    private VideoListAction(){}
    HttpManager hMgr ;
    TurnManager tMgr ;
    IResultCallback cb;
    String deviceID;
    int channel;
    String begTime,endTime;


    /**
     * 初始化
     * @param context 上下文
     * @param serverIP 服务器ip
     * @param serverPort 服务器端口  默认ssl 8850  非ssl8800
     * @param turnIP 流转服务器ip 通常与服务器ip 相同
     * @param turnPort 流转服务器端口  默认ssl 8862 非ssl8812
     * @param isSSL  是否使用ssl
     * @param account  用户名
     * @param password 密码
     * @param imei  手机序列号
     */
    public void init(Context context, String serverIP, int serverPort,String turnIP,int turnPort,boolean isSSL, String account, String password,String imei){
        hMgr = HttpManager.getInstance().initURL(context,serverIP,serverPort,isSSL);
        tMgr = TurnManager.getInstance().turnInit(context)
                .setIP(turnIP)
                .setPort(turnPort)
                .setIsSSL(isSSL)
                .setType(101)
                .setIMEI(imei)
                .setName(account)
                .setPassword(password);
    }

    /**
     * 释放
     */
    public void deInit(){
        cb = null;
        tMgr.unregistResultCallback(this);
        tMgr.turnDeinit();
    }

    /**
     * 获取输入设备列表 （输入针对服务器而言）
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws JSONException
     */
    public VideoInputChannelPermissionList getDeviceList() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        return hMgr.queryBusinessVideoInputChannel();
    }

    /**
     * 获取设备id
     * @param videoInputChannelId 输入设备id
     * @return 输入设备所属的上级设备id
     */
    public String getDeviceId(String videoInputChannelId){
        return Util.transformItemId2DeviceId(videoInputChannelId);
    }

    /**
     * 获取设备通道号
     * @param videoInputChannelId 输入设备id
     * @return 输入设备所属的上级设备通道号
     */
    public int getChannel(String videoInputChannelId){
        return Util.getChannelFromItemId(videoInputChannelId);
    }

    /**
     * 获取回放列表
     * @param deviceId 设备id
     * @param channel 设备通道号
     * @param begTime 查询回放的开始时间
     * @param endTime 查询回放的结束时间
     * @param cb 回放列表回调
     */
    public void getRecordFileList(String deviceId,int channel,String begTime,String endTime,IResultCallback cb){
        this.cb = cb;
        this.deviceID = deviceId;
        this.channel = channel;
        this.begTime = begTime;
        this.endTime = endTime;
        tMgr.registResultCallback(this).connect();
    }

    @Override
    public void onConnect(String sessionId) {
        TurnGetRecordedFilesBean bar = new TurnGetRecordedFilesBean(deviceID,channel,begTime,endTime);
        tMgr.getRecordedFiles(bar);
    }

    @Override
    public void onDisconnect() {
        tMgr.unregistResultCallback(this);
    }

    @Override
    public void onDisconnectUnexpect(int flag) {
        SDKDebugLog.logE(TAG,"onDisconnectUnexpect: error flag="+flag);
        if (cb!=null){
            cb.onConnectError();
        }
    }

    @Override
    public void onRecordFileList(TurnGetRecordedFileAckBean fileList) {
        tMgr.disconnect();
        if (cb!=null && fileList!=null){
            cb.onRecordFileList(fileList.getRecordedFiles());
        }
    }

    @Override
    public void onSubscribe(String jsonStr) {

    }

    @Override
    public void onUnsubscribe(String jsonStr) {

    }

    public interface IResultCallback{
        /**
         * 连接失败
         */
        void onConnectError();

        /**
         * 回放列表
         * @param files 列表
         */
        void onRecordFileList(ArrayList<TurnGetRecordedFileAckBean.RecordedFile> files);
    }

}
