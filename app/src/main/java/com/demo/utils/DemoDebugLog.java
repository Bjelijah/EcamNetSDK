package com.demo.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/6/13.
 */

public class DemoDebugLog {
    private static boolean sEnable = false;
    private static final String TAG = "DemoDebug";

    public static void LogEnable(boolean enable){
        sEnable = enable;
    }
    public static void logI(String tag,String str){
        if (!sEnable)return;
        Log.i(TAG,"["+tag+"] : "+str);
    }

    public static void logD(String tag,String str){
        if (!sEnable)return;
        Log.d(TAG,"["+tag+"] : "+str);
    }

    public static void logW(String tag,String str){
        if (!sEnable)return;
        Log.w(TAG,"["+tag+"] : "+str);

    }
    public static void logE(String tag,String str){
        if (!sEnable)return;
        Log.e(TAG,"["+tag+"] : "+str);
    }
}
