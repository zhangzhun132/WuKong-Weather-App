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
        JSONObject city_aqi=null;
        String pm25_city="75";
        String aqi_city="75";
        String qlty_city="75";
        String so2_city="75";
        //JSONObject jsonObject=new JSONObject(json);
        JSONObject jsonObject=new JSONObject(json);
        JSONArray jsonArray=jsonObject.getJSONArray("HeWeather data service 3.0");
        JSONObject data=(JSONObject) jsonArray.get(0);

        if (data.has("aqi")){
            JSONObject aqi=data.getJSONObject("aqi");
            city_aqi=aqi.getJSONObject("city");
            pm25_city=city_aqi.getString("pm25");
            aqi_city=city_aqi.getString("aqi");
            qlty_city=city_aqi.getString("qlty");
            so2_city=city_aqi.getString("pm10");
        }

        pmObject.setAQI(aqi_city);
        pmObject.setPm(pm25_city);
        pmObject.setQuality(qlty_city);
        pmObject.setSo2(so2_city);
        return pmObject;
    }
}
