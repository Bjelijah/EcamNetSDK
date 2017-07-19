package com.howell.protocol.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/5/17.
 */

public class SDKDebugLog {
    private static boolean sEnable = false;
    private static final String TAG = "SDKDebug";
    public static void LogEnable(boolean enable){
        sEnable = enable;
    }
    public static void logI(String tag,String str){
        if (!sEnable)return;
        Log.i(TAG,"["+tag+"]:"+str);
    }

    public static void logD(String tag,String str){
        if (!sEnable)return;
        Log.d(TAG,"["+tag+"]:"+str);
    }

    public static void logW(String tag,String str){
        if (!sEnable)return;
        Log.w(TAG,"["+tag+"]:"+str);

    }
    public static void logE(String tag,String str){
        if (!sEnable)return;
        Log.e(TAG,"["+tag+"]:"+str);
    }

}
