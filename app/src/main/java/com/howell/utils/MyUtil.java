package com.howell.utils;

import org.codehaus.jackson.map.util.ISO8601DateFormat;
import org.codehaus.jackson.map.util.ISO8601Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2017/4/12.
 */

public class MyUtil {
    public static String Date2ISODate(Date date){
        ISO8601DateFormat isoDate = new ISO8601DateFormat();
        String isoString = isoDate.format(date);
//        Log.i("123", "isoDate:"+isoString);
        return isoString;
    }

    public static Date ISODateString2ISODate(String isoDate){
        return ISO8601Utils.parse(isoDate);
    }

    public static String ISODateString2Date(String isoDate){
        String str = null;
        try {
            Date date = ISO8601Utils.parse(isoDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }




    public static String ISODateString2ISOString(String isoDate){
        String str = null;
        try{
            Date date = ISO8601Utils.parse(isoDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            str = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String createClientNonce(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<length;i++){
            int number =random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }



}
