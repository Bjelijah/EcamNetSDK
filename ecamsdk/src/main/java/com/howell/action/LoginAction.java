package com.howell.action;

import android.content.Context;

import com.howell.bean.httpbean.Fault;
import com.howell.bean.httpbean.UserClientCredential;
import com.howell.bean.httpbean.UserNonce;
import com.howell.bean.httpbean.UserTeardownCredential;
import com.howell.protocol.http.HttpManager;
import com.howell.utils.Util;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * 登入接口
 * Created by Administrator on 2017/7/6.
 * @author howell
 */

public class LoginAction {
    private static LoginAction mInstance = null;
    public static LoginAction getInstance(){
        if (mInstance==null){
            mInstance = new LoginAction();
        }
        return mInstance;
    }
    private LoginAction(){}

    HttpManager hMgr = HttpManager.getInstance();
    String mAccount;
    String mPassword;

    /**
     * 初始化
     * @param context 上下文
     * @param serverIP 服务器ip
     * @param serverPort 服务器端口 默认 ssl 8850  非ssl 8800
     * @param isSSL 是否使用ssl
     * @param account 用户名
     * @param password 密码
     * @return
     */
    public LoginAction init(Context context, String serverIP, int serverPort,boolean isSSL, String account, String password){
        hMgr.initURL(context,serverIP,serverPort,isSSL);
        mAccount = account;
        mPassword = password;
        return this;
    }

    /**
     * 登入到服务器
     * @return
     * @throws JSONException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public Fault login2Server() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException {
        UserNonce sn = hMgr.userNonce(mAccount);//从服务器获取随机码
        String clientNonce = Util.createClientNonce(32);//客户端  本地随机码
        //用户认证
        return hMgr.doUserAuthenticate(new UserClientCredential(sn.getDomain(),mAccount,mPassword,sn.getNonce(),clientNonce));

    }

    /**
     * 退出登入
     * @return
     * @throws JSONException
     */
    public Fault logoutFromServer() throws JSONException {
       String session = hMgr.getSession();
       return hMgr.doUserTeardown(new UserTeardownCredential(mAccount,session,""));
    }





}
