package com.zhangzhun.way.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 2016/1/26.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    public final String CREATE_TABLE_SQL="create table if not exists menu(cityId integer primary key,cityName varchar(10),cityNumber varchar(10))";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
