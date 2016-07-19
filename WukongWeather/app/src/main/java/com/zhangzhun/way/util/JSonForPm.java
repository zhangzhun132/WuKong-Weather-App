package com.zhangzhun.way.util;

import com.zhangzhun.way.bean.Pm2d5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2015/12/15.
 */
public class JSonForPm {
    public  static Pm2d5 getPm2d5Object(String json) throws JSONException {
        Pm2d5 pmObject=new Pm2d5();
        //JSONObject jsonObject=new JSONObject(json);
        JSONObject jsonObject=new JSONObject(json);
        JSONArray jsonArray=jsonObject.getJSONArray("HeWeather data service 3.0");
        JSONObject data=(JSONObject) jsonArray.get(0);
        JSONObject aqi=data.getJSONObject("aqi");
        JSONObject city_aqi=aqi.getJSONObject("city");
        String pm25_city=city_aqi.getString("pm25");
        String aqi_city=city_aqi.getString("aqi");
        String qlty_city=city_aqi.getString("qlty");
        String so2_city=city_aqi.getString("so2");
        pmObject.setAQI(aqi_city);
        pmObject.setPm(pm25_city);
        pmObject.setQuality(qlty_city);
        pmObject.setSo2(so2_city);
        return pmObject;
    }
}
