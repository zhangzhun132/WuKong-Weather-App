package com.zhangzhun.way.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArraySet;

import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.zhangzhun.way.app.Application;
import com.zhangzhun.way.bean.City;
import com.zhangzhun.way.weather.MainActivity;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by lenovo on 2016/1/26.
 */
public class Setting {


    public static final String XML_NAME = "settings";



    private static Setting sInstance;

    private SharedPreferences mPrefs;

    public static Setting getInstance() {
        if (sInstance == null) {
            sInstance = new Setting(Application.getInstance());
        }
        return sInstance;
    }

    private Setting(Context context) {
        mPrefs = context.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
    }

    public Setting putBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).commit();
        return this;
    }

    public boolean getBoolean(String key, boolean def) {
        return mPrefs.getBoolean(key, def);
    }

    public Setting putInt(String key, int value) {
        mPrefs.edit().putInt(key, value).commit();
        return this;
    }

    public int getInt(String key, int defValue) {
        return mPrefs.getInt(key, defValue);
    }

    public Setting putString(String key, String value) {
        mPrefs.edit().putString(key, value).commit();
        return this;
    }

    public String getString(String key, String defValue) {
        return mPrefs.getString(key, defValue);
    }

    public Setting putCity(String key,City value){
        Set<String> mCity=mPrefs.getStringSet(key, null);
        if (mCity==null){
            mCity=new HashSet<String>() {
            };
        }
        Set<String> newCity=new HashSet<>();
        mCity.add(value.getCity()+","+value.getNumber());

        newCity.addAll(mCity);
        if (true){
            mPrefs.edit().putStringSet(key,newCity).apply();
        }
        return this;
    }

    public Set<String> getCity(String key,Set<String> defValue){
        return mPrefs.getStringSet(key,defValue);
    }


}
