package com.demo.ecamnetsdk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.demo.utils.DemoDebugLog;
import com.howell.action.LoginAction;
import com.howell.bean.httpbean.Fault;
import com.howell.ecamnetsdk.R;
import com.howell.jni.JniUtil;
import com.howell.protocol.utils.SDKDebugLog;
import com.howell.utils.SystemUpTimeUtil;


import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * demo
 * @author howell
 */
public class MainActivity extends AppCompatActivity implements IConfig {
    private static final String TAG = MainActivity.class.getName();


    @BindView(R.id.btn_login_server) Button mLoginServerBtn;
    @BindView(R.id.btn_module_video) Button mModuleVideo;
    @BindView(R.id.btn_module_vehicle) Button mModuleVehicle;
    @BindView(R.id.btn_logout_server) Button mLogoutServerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    logoutFromServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        super.onDestroy();

    }

    /**
     * 登入系统
     */
    @OnClick(R.id.btn_login_server) void clickLoginServer(){

       new Thread(){
           @Override
           public void run() {
               super.run();
               try {
                   login2Server();
               } catch (JSONException e) {
                   e.printStackTrace();
               } catch (UnsupportedEncodingException e) {
                   e.printStackTrace();
               } catch (NoSuchAlgorithmException e) {
                   e.printStackTrace();
               }
           }
       }.start();
    }

    /**
     * 视频模块
     */
    @OnClick(R.id.btn_module_video) void clickModuleVideo(){
        startActivity(new Intent(this,VideoModuleActivity.class));
    }

    /**
     * 车辆模块
     */
    @OnClick(R.id.btn_module_vehicle) void clickModuleVehicle(){
        startActivity(new Intent(this,VehicleModuleActivity.class));
    }

    /**
     * 登出系统
     */
    @OnClick(R.id.btn_logout_server) void clickLogout(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    logoutFromServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 初始化
     * http manager 为同步响应 即调用时在同线程中等待,直到方法返回结果或返回超时 <br/>
     */
    void init(){
        //debug 下 log打印信息
        SDKDebugLog.LogEnable(true);
        DemoDebugLog.LogEnable(true);
        JniUtil.logEnable(true);
        SystemUpTimeUtil.getInstance();


    }

    /**
     * 登入服务器
     * @throws JSONException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    void login2Server() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //http  初始化 默认ssl端口8850 非ssl端口8800
        Fault f =LoginAction.getInstance().init(getApplicationContext(),TEST_IP,IS_SSL?8850:8800,IS_SSL,TEST_ACCOUTN,TEST_PASSWORD)
                .login2Server();
        DemoDebugLog.logI(TAG+":login2Server","Fault="+f.toString());
    }

    /**
     * 登出服务器
     * @throws JSONException
     */
    void logoutFromServer() throws JSONException {
        Fault fault = LoginAction.getInstance().logoutFromServer();
        DemoDebugLog.logI(TAG+":logoutFromServer","Fault="+fault.toString());
    }
}
