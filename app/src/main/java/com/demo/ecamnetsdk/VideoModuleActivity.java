package com.demo.ecamnetsdk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.demo.utils.DemoDebugLog;
import com.demo.utils.Util;
import com.howell.action.VideoListAction;
import com.howell.bean.httpbean.VideoInputChannelPermission;
import com.howell.bean.httpbean.VideoInputChannelPermissionList;
import com.howell.bean.turnbean.TurnGetRecordedFileAckBean;
import com.howell.ecamnetsdk.R;
import com.howell.protocol.utils.SDKDebugLog;


import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/10.<br/>
 * 视频<br/>
 * @author howell
 */
public class VideoModuleActivity extends AppCompatActivity implements IConfig, VideoListAction.IResultCallback {
    private static final String TAG=VideoModuleActivity.class.getName();

    String mDeviceId;
    int mChannel;

    String recordBegTime,recordEndTime;
    String recordSearchStart,recordSearchEnd;


    @BindView(R.id.btn_device_list) Button mList;
    @BindView(R.id.btn_turn_review) Button mReview;
    @BindView(R.id.btn_turn_list) Button mBackList;
    @BindView(R.id.btn_turn_playback) Button mPlayBack;

    /**
     * 初始化 turnManager<br/>
     * turnManager 为异步响应，即回掉方法在异线程中响应<br/>
     * 注：onDisconnectUnexpect（底层中 onError）方法可能在同线程中回掉（发送协议失败时）<br/>
     * server ip:登入服务器ip<br/>
     * server port:登入服务器端口 ssl：8850 非ssl 8800
     * turn ip:流转服务器 ip，与登入服务器ip一致<br/>
     * turn port：流转服务器端口，默认 ssl 8862 ；非ssl 8812<br/>
     * ssl:<br/>
     * imei: android 唯一码  此demo中使用 imei 也可以使用 androidID 或任意可以标志此设备唯一的码<br/>
     * name: 登入用户名 与登入服务器使用的一致<br/>
     * password：登入密码 与登入服务器使用的一致 <br/>
     */
    private void init(){
        VideoListAction.getInstance().init(getApplicationContext(),
                TEST_IP,
                IS_SSL?8850:8800,
                TEST_IP,
                IS_SSL?8862:8812,
                IS_SSL,
                TEST_ACCOUTN,
                TEST_PASSWORD,
                ((TelephonyManager)VideoModuleActivity.this.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_module);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        VideoListAction.getInstance().deInit();
        super.onDestroy();
    }

    /**
     * 获取设备列表
     */
    @OnClick(R.id.btn_device_list) void clickDeviceList(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    showDeviceList();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 预览 <br/>
     * 需要知道设备ID
     */
    @OnClick(R.id.btn_turn_review) void clickTurnReview(){
        if(mDeviceId==null){
            Toast.makeText(this,"mDeviceId=null，请先获取设备列表",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this,PlayerActivity.class);
        intent.putExtra("id",mDeviceId);
        intent.putExtra("channel",mChannel);
        intent.putExtra("isPlayback",false);
        startActivity(intent);
    }

    /**
     * 获取回放列表<br/>
     * 需要知道设备id
     */
    @OnClick(R.id.btn_turn_list) void clickTurnBackList(){
        if (mDeviceId==null){
            DemoDebugLog.logE(TAG+":clickTurnBackList","deviceId=null");
            return;
        }

        Date dateNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);
        // calendar.add(Calendar.YEAR,-1);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date dateBefore = calendar.getTime();
        recordSearchStart= Util.Date2ISODate(dateBefore);
        recordSearchEnd = Util.Date2ISODate(dateNow);

        new Thread(){
            @Override
            public void run() {

                VideoListAction.getInstance().getRecordFileList(mDeviceId,mChannel,recordSearchStart,recordSearchEnd,VideoModuleActivity.this);
                super.run();
            }
        }.start();


    }

    /**
     * 回放<br/>
     * 需要知道设备id
     * 需要知道回放的开始时间和结束时间
     */
    @OnClick(R.id.btn_turn_playback) void clickTurnPlayback(){

        makeTestRecordTime();

        if (mDeviceId==null){
            Toast.makeText(this,"mDeviceId=null,请先获取设备列表",Toast.LENGTH_SHORT).show();
            return;
        }

        if (recordBegTime==null||recordEndTime==null){
            Toast.makeText(this,"beg=null end=null,请先获取回放列表",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this,PlayerActivity.class);
        intent.putExtra("id",mDeviceId);
        intent.putExtra("channel",mChannel);
        intent.putExtra("isPlayback",true);
        intent.putExtra("beg",recordBegTime);
        intent.putExtra("end",recordEndTime);
        startActivity(intent);
    }

    private void makeTestRecordTime(){

        Date dateNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);
        // calendar.add(Calendar.YEAR,-1);
        calendar.add(Calendar.MINUTE,-5);
        Date dateBefore = calendar.getTime();
        recordBegTime= Util.Date2ISODate(dateBefore);
        recordEndTime = Util.Date2ISODate(dateNow);



    }

    private void showDeviceList() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        VideoInputChannelPermissionList list = VideoListAction.getInstance().getDeviceList();
        String inputChannelId = null;
        ArrayList<VideoInputChannelPermission> array = list.getVideoInputChannelPermissiones();
//        String str = "软件";
        //模拟选择了名称包含  TEST_CAMERA_NAME_KEY 的设备  获得该设备的id；
        String str = TEST_CAMERA_NAME_KEY;
        for (VideoInputChannelPermission p:array){
            if(p.getName().contains(str)){
                inputChannelId = p.getId();
                break;
            }
        }
        DemoDebugLog.logI(TAG+":showDeviceList","inputChannelId="+inputChannelId);
        //根据inputChannelId 获取转换出 其所属的deviceID 及其在device 中 channel
        //之后通过 deviceID 和 channel 来访问该设备
        mDeviceId = VideoListAction.getInstance().getDeviceId(inputChannelId);
        mChannel =  VideoListAction.getInstance().getChannel(inputChannelId);
        Log.e("123","mDeviceId="+mDeviceId+"  mChannel="+mChannel+"~~~~~~~~~~~~~~~~~~~~~~");
    }


    @Override
    public void onConnectError() {
        SDKDebugLog.logE(TAG,"onConnectError");
    }

    @Override
    public void onRecordFileList(ArrayList<TurnGetRecordedFileAckBean.RecordedFile> files) {
        if (files==null||files.size()==0)return;
        SDKDebugLog.logI(TAG,"onRecordFileList="+files.toString());
        //模拟选择了第一个回放
        TurnGetRecordedFileAckBean.RecordedFile f = files.get(0);
        recordBegTime = f.getBeginTime();
        recordEndTime = f.getEndTime();
    }
}
