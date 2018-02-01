package com.howell.protocol.utils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/25.
 */

public class Util {

    public static String mixIds(ArrayList<String> ids){
        String str = "";
        for (int i=0;i<ids.size();i++){
            if (i>0){
                str += ",";
            }
            str += ids.get(i);
        }
        return str;
    }







}
