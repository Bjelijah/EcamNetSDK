package com.demo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.howell.bean.httpbean.WSRes;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/11.
 */

public class DBDao {
    DBHelper dbHelper;
    SQLiteDatabase db;
    public DBDao(Context context){
        dbHelper = new DBHelper(context);
    }

    public synchronized void insert(WSRes.AlarmEvent event){
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            Object [] obj = new Object[]{event.getId(),event.getName(),event.getEventType(),
                    event.getEventState(),event.getTime(),event.getPath(),event.getDescription(),event.getExtendInformation(),event.getEventID(),event.getImageurl()==null?null:(event.getImageurl().size()==0?null:event.getImageurl().get(0))};
            Log.i("123","obj="+obj.toString());
            db.execSQL("insert into event values(NULL,?,?,?,?,?,?,?,?,?,?);",obj);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        close();
    }

    public synchronized void insert(WSRes.AlarmAliveRes alive){
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            db.execSQL("insert into heartbeat values(NULL,?,?);",new Object[]{alive.getTime(),alive.getHeartbeatinterval()});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        close();
    }

    public synchronized void insert(WSRes.AlarmNotice notice){
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            db.execSQL("insert into notice values(NULL,?,?,?,?,?,?,?,?)",new Object[]{notice.getId(),notice.getMsg(),notice.getClassification()
            ,notice.getTime(),notice.getState(),notice.getSender(),notice.getComponentId(),notice.getComponentName()});
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        close();
    }

    public synchronized  void delAllEvent(){
        db = dbHelper.getWritableDatabase();
        db.execSQL("delete from event;");
        close();
    }

    public synchronized  void delAllAlive(){
        db = dbHelper.getWritableDatabase();
        db.execSQL("delete from heartbeat;");
        close();
    }

    public synchronized void delAllNotice(){
        db = dbHelper.getWritableDatabase();
        db.execSQL("delete from notice;");
        close();
    }




    public synchronized ArrayList<WSRes.AlarmAliveRes> queryAlive(){
        db =dbHelper.getWritableDatabase();
        ArrayList<WSRes.AlarmAliveRes> alives = new ArrayList<>();
        db.beginTransaction();
        try{
            Cursor c = db.rawQuery("select * from heartbeat;",null);
            while (c.moveToNext()){
                WSRes.AlarmAliveRes alive = new WSRes.AlarmAliveRes();
                alive.setTime(c.getString(c.getColumnIndex("time")));
                alive.setHeartbeatinterval(c.getInt(c.getColumnIndex("interval")));
                alives.add(alive);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        close();
        return alives;
    }


    public synchronized ArrayList<WSRes.AlarmEvent> queryEvent(){
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        ArrayList<WSRes.AlarmEvent> events = new ArrayList<>();
        try{
            Cursor c = db.rawQuery("select * from event;",null);
            while (c.moveToNext()){
                WSRes.AlarmEvent event = new WSRes.AlarmEvent();
                event.setId(c.getString(c.getColumnIndex("id")));
                event.setName(c.getString(c.getColumnIndex("name")));
                event.setEventType(c.getString(c.getColumnIndex("eventType")));
                event.setEventState(c.getString(c.getColumnIndex("eventState")));
                event.setTime(c.getString(c.getColumnIndex("time")));
                event.setPath(c.getString(c.getColumnIndex("path")));
                event.setDescription(c.getString(c.getColumnIndex("description")));
                event.setExtendInformation(c.getString(c.getColumnIndex("extendInformation")));
                event.setEventID(c.getString(c.getColumnIndex("eventId")));
                ArrayList<String> imageUrls = new ArrayList<>();
                imageUrls.add(c.getString(c.getColumnIndex("imageUrl")));
                event.setImageurl(imageUrls);
                events.add(event);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        close();
        return events;
    }

    public synchronized ArrayList<WSRes.AlarmNotice> queryNotice(){
        db = dbHelper.getWritableDatabase();
        ArrayList<WSRes.AlarmNotice> notices = new ArrayList<>();
        db.beginTransaction();
        try{
            Cursor c = db.rawQuery("select * from notice",null);
            while (c.moveToNext()){
                WSRes.AlarmNotice notice = new WSRes.AlarmNotice();
                notice.setId(c.getString(c.getColumnIndex("id")));
                notice.setMsg(c.getString(c.getColumnIndex("message")));
                notice.setClassification(c.getString(c.getColumnIndex("classification")));
                notice.setTime(c.getString(c.getColumnIndex("time")));
                notice.setState(c.getString(c.getColumnIndex("status")));
                notice.setSender(c.getString(c.getColumnIndex("send")));
                notice.setComponentId(c.getString(c.getColumnIndex("componentId")));
                notice.setComponentName(c.getString(c.getColumnIndex("componentName")));
                notices.add(notice);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        close();
        return notices;
    }

    private void close(){
        if (db!=null)db.close();
        if (dbHelper!=null)dbHelper.close();
    }

}
