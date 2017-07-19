package com.howell.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/5/11.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ecam_sdk.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists heartbeat"+
                "("+
                "_id integer primary key autoincrement,"+
                "time varchar,"+
                "interval integer"+
                ");"
        );

        db.execSQL("create table if not exists event"+
                "("+
                "_id integer primary key autoincrement,"+
                "id varchar,"+
                "name varchar,"+
                "eventType varchar,"+
                "eventState varchar,"+
                "time varchar,"+
                "path varchar,"+
                "description varchar,"+
                "extendInformation varchar,"+
                "eventId varchar,"+
                "imageUrl varchar"+
                ");"
        );

        db.execSQL("create table if not exists notice"+
                "("+
                "_id integer primary key autoincrement,"+
                "id varchar,"+
                "message varchar,"+
                "classification varchar,"+
                "time varchar,"+
                "status varchar,"+
                "sender varchar,"+
                "componentId varchar,"+
                "componentName varchar"+
                ");"
        );



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
