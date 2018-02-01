package com.howell.protocol.turn;

import android.content.Context;

import com.howell.bean.turnbean.Subscribe;
import com.howell.bean.turnbean.TurnGetRecordedFileAckBean;
import com.howell.bean.turnbean.TurnGetRecordedFilesBean;
import com.howell.bean.turnbean.TurnPtzCtrlBean;
import com.howell.bean.turnbean.TurnSubScribe;
import com.howell.jni.JniUtil;
import com.howell.protocol.utils.FileUtil;
import com.howell.protocol.utils.SDKDebugLog;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/20.
 * Turn protocol manager
 * @author howell
 */
public class TurnManager {
    private static final String TAG = TurnManager.class.getName();
    private static TurnManager mInstance = null;
    public static TurnManager getInstance(){
        if (mInstance==null){
            mInstance = new TurnManager();
        }
        return mInstance;
    }
    private TurnManager(){}
    private String mSessionID;

    private String mIp;
    private int mPort;
    private boolean mIsSSL;
    private int mType;
    private String mIMEI;
    private String mName;
    private String mPassword;

    public TurnManager setIP(String ip){
        mIp = ip;
        return this;
    }

    public TurnManager setPort(int port){
        mPort = port;
        return this;
    }

    public TurnManager setIsSSL(boolean isSSL){
        mIsSSL = isSSL;
        return this;
    }

    public TurnManager setType(int type){
        mType = type;
        return this;
    }

    public TurnManager setIMEI(String imei){
        mIMEI = imei;
        return this;
    }

    public TurnManager setName(String name){
        mName = name;
        return this;
    }

    public TurnManager setPassword(String password){
        mPassword = password;
        return this;
    }

    ArrayList<ITurn> mCBs=null;


    public TurnManager registResultCallback(ITurn cb){
        if (mCBs==null){
            mCBs = new ArrayList<>();
        }
        for (ITurn i:mCBs){
            if (i.equals(cb)){
                SDKDebugLog.logE(TAG+":registResultCallback","turn manager regist error already register");
                return this;
            }
        }
        mCBs.add(cb);
        return this;
    }

    public void unregistResultCallback(ITurn cb){
        if (mCBs==null)return;
        mCBs.remove(cb);
        if (mCBs.size()==0)mCBs=null;
    }

    /**
     * get session id ;when connect to turn server ,it will send back session id
     * @return session id
     */
    public String getSessionId(){return mSessionID;}

    private void sendOnConnectResult(String sessionId){
        if (mCBs==null)return;
        for (ITurn i:mCBs){
            i.onConnect(sessionId);
        }
    }

    private void sendOnDisconnectResult(){
        if (mCBs==null)return;
        for (ITurn i:mCBs){
            i.onDisconnect();
        }
    }

    private void sendOnDisconnectUnexpectResult(int flag){
        if (mCBs==null)return;
        for (ITurn i:mCBs){
            i.onDisconnectUnexpect(flag);
        }
    }

    private void sendOnSubscribeResult(String jsonStr){
        if (mCBs == null)return;
        for (ITurn i:mCBs){
            i.onSubscribe(jsonStr);
        }
    }

    private void sendOnRecordFileListResult(TurnGetRecordedFileAckBean fileList){
        if (mCBs==null)return;
        for (ITurn i:mCBs){
            i.onRecordFileList(fileList);
        }
    }
    private void sendOnUnsubscribeResult(String jsonStr){
        if (mCBs==null){
            return;
        }
        for (ITurn i:mCBs){
            i.onUnsubscribe(jsonStr);
        }
    }


    private void onConnect(String sessionId){mSessionID=sessionId;sendOnConnectResult(sessionId);}
    private void onDisconnect(){sendOnDisconnectResult();}
    private void onDisconnectUnexpect(int flag){//0 socket 1 sync  2 packet receive false   3  http !=200
        SDKDebugLog.logE(TAG+":onDisconnectUnexpect","disconnectUnexpect");
        sendOnDisconnectUnexpectResult(flag);}
    private void onRecordFileList(String jsonStr){SDKDebugLog.logI(TAG+":onRecordFileList",jsonStr);sendOnRecordFileListResult(TurnJsonUtil.getTurnRecordAckFromJsonStr(jsonStr));}
    private void onSubscribe(String jsonStr){sendOnSubscribeResult(jsonStr);}
    private void onUnsubscribe(String jsonStr){sendOnUnsubscribeResult(jsonStr);}


    /**
     * turn manager init and make socket connect to server
     * @param context
     * @return TurnManager
     */
    public TurnManager turnInit(Context context){
        synchronized (this) {
            JniUtil.transInit();
            JniUtil.transSetCallBackObj(this, 0);
            JniUtil.transSetCallbackMethodName("onConnect", 0);
            JniUtil.transSetCallbackMethodName("onDisconnect", 1);
            JniUtil.transSetCallbackMethodName("onRecordFileList", 2);
            JniUtil.transSetCallbackMethodName("onDisconnectUnexpect", 3);
            JniUtil.transSetCallbackMethodName("onSubscribe", 4);
            JniUtil.transSetCallbackMethodName("onUnsubscribe", 6);//fixme 5 is no use
            setCrtPath(context);
        }
        return this;
    }

    /**
     * uninitialize and socket disconnect to server
     * release buffer in jni
     */
    public void turnDeinit(){
        synchronized (this) {
            JniUtil.transSetCallBackObj(null, 0);
            JniUtil.transDeinit();
        }
    }

    /**
     * "connect" more like login to server with name and password
     */
    public void connect(){
        JniUtil.transConnect(mIp,mPort,mIsSSL,mType,mIMEI,mName,mPassword);
    }

    /**
     * logout
     */
    public void disconnect(){
        JniUtil.transDisconnect();
    }

    /**
     * @param subscribe
     * @Deprectated
     */
    @Deprecated
    public void subscribe(Subscribe subscribe){
        subscribe.setSessionId(mSessionID);
        String jsonStr = TurnJsonUtil.subScribeJson(subscribe);
        JniUtil.transSubscribe(jsonStr,jsonStr.length());
    }

    /**
     * subscribe video stream,after call this fun,video stream will be received in jni
     * @param subscribe
     */
    public void subscribe(TurnSubScribe subscribe){
        subscribe.setSessionId(mSessionID);
        String jsonStr = TurnJsonUtil.getTurnSubScribe(subscribe);
        SDKDebugLog.logI(TAG+":subscribe","jsonStr="+jsonStr);
        JniUtil.transSubscribe(jsonStr,jsonStr.length());
    }

    /**
     * get recorded files list,
     * @param bean
     */
    public void getRecordedFiles(TurnGetRecordedFilesBean bean){
        String jsonStr = TurnJsonUtil.getTurnRecordFilesJsonStr(bean);
        SDKDebugLog.logI(TAG+":getRecordedFiles","jsonStr="+jsonStr);
        JniUtil.transGetRecordFiles(jsonStr,jsonStr.length());
    }

    /**
     * unSubscribe video stream
     */
    public void unSubscribe(){
        JniUtil.transUnsubscribe();
    }

    /**
     * ptz
     */
    public void ptzControl(TurnPtzCtrlBean bean){
        String jsonStr = TurnJsonUtil.getTurnPtzJsonStr(bean);
        SDKDebugLog.logI(TAG+":ptzControl","jsonStr="+jsonStr);
        JniUtil.transPTZControl(jsonStr,jsonStr.length());
    }




    /**
     * catch picture in encoder
     * @param path path which that picture will save
     */
    public void catchPicture(String path){
        JniUtil.catchPic(path);
    }

    /**
     * get stream length since last call this method
     * @return stream len
     */
    public int getStreamLen(){
        return JniUtil.transGetStreamLenSomeTime();
    }

    /**
     * save ca.crt client.crt client.key files to cach so that they could be found in jni<br/>
     * in default,these files are put in /assets/
     * @param context
     */
    public void setCrtPath(Context context){
        InputStream ca = getClass().getResourceAsStream("/assets/ca.crt");
        InputStream client = getClass().getResourceAsStream("/assets/client.crt");
        InputStream key = getClass().getResourceAsStream("/assets/client.key");
        String caPath = new String(FileUtil.saveCreateCertificate(ca,"ca.crt",context));
        String clPath = new String(FileUtil.saveCreateCertificate(client,"client.crt",context));
        String keyPath = new String(FileUtil.saveCreateCertificate(key,"client.key",context));
        JniUtil.transSetCrtPaht(caPath,clPath,keyPath);
        try {
            ca.close();
            client.close();
            key.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * save ca.crt client.crt client.key files to cach so that they could be found in jni<br/>
     * in default,these files are put in /assets/
     * @param caPath the ca.crt path in cach
     * @param clientPath the client.crt path in cach
     * @param keyPath this client.key path in cach
     */
    public void setCrtPath(String caPath,String clientPath,String keyPath){
        JniUtil.transSetCrtPaht(caPath,clientPath,keyPath);
    }

    /**
     * get stream len receive;<br/>
     * when call this fun stream len will be set to zero <br/>
     * it will plus itself until next time u call this fun <br/>
     * @return
     */
    public int getTransStreamLenSomeTime(){
        return JniUtil.transGetStreamLenSomeTime();
    }

    public long getFirstTimeStamp(){
        return JniUtil.getFirstTimeStamp();
    }

    public long getTimeStamp(){
        return JniUtil.getTimeStamp();
    }

    /**
     * interface: result call back
     */
    public interface ITurn{
        void onConnect(String sessionId);
        void onDisconnect();
        void onDisconnectUnexpect(int flag);
        void onRecordFileList(TurnGetRecordedFileAckBean fileList);
        void onSubscribe(String jsonStr);
        void onUnsubscribe(String jsonStr);
    }

}
