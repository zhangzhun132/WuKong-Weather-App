package com.zhangzhun.way.util;

import com.zhangzhun.way.bean.WeatherPreinfo;
import com.zhangzhun.way.bean.Weatherinfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2015/12/9.
 */
public class JsonForWeather {
    public static Weatherinfo getCurrentWeather(String json) throws JSONException {
        Weatherinfo weatherinfo=null;
        String wind_sc=null;
        JSONObject jsonObject=new JSONObject(json);
        JSONArray jsonArray=jsonObject.getJSONArray("HeWeather data service 3.0");
        JSONObject data=(JSONObject) jsonArray.get(0);
        JSONObject basic=data.getJSONObject("basic");
        String city=basic.getString("city");
        JSONObject update=basic.getJSONObject("update");

        String loctime=update.getString("loc");
        JSONArray forecast_data=data.getJSONArray("daily_forecast");
        JSONObject today_data= (JSONObject) forecast_data.get(0);
        JSONObject astro=today_data.getJSONObject("astro");
        String sr=astro.getString("sr");
        String ss=astro.getString("ss");
        JSONObject cond=today_data.getJSONObject("cond");
        String cond_day=cond.getString("txt_d");
        String cond_night=cond.getString("txt_n");
        String hum=today_data.getString("hum");
        JSONObject tmp=today_data.getJSONObject("tmp");
        String tmp_max=tmp.getString("max");
        String tmp_min=tmp.getString("min");
        String tmp_maxandmin=tmp_min+"~"+tmp_max;
        JSONObject wind=today_data.getJSONObject("wind");
        wind_sc=wind.getString("sc");
        JSONObject sugObject=data.getJSONObject("suggestion");
        JSONObject comf=sugObject.getJSONObject("comf");
        String tianqi=comf.getString("brf");
        JSONObject flu=sugObject.getJSONObject("flu");
        String ganmao=flu.getString("brf");
        JSONObject cw=sugObject.getJSONObject("cw");
        String xiche=cw.getString("brf");
        JSONObject sport=sugObject.getJSONObject("sport");
        String yundong=sport.getString("brf");
        JSONObject drsg=sugObject.getJSONObject("drsg");
        String chuanyi=drsg.getString("brf");
        JSONObject trav=sugObject.getJSONObject("trav");
        String lvyou=trav.getString("brf");
        if (wind_sc!=null){
            weatherinfo=new Weatherinfo();
            weatherinfo.setCondDay(cond_day);
            weatherinfo.setCondNight(cond_night);
            weatherinfo.setHum(hum);
            weatherinfo.setTmpMax(tmp_max);
            weatherinfo.setTmpMin(tmp_min);
            weatherinfo.setWindSc(wind_sc);
            weatherinfo.setLocTime(loctime);
            weatherinfo.setTmpAll(tmp_maxandmin);
            weatherinfo.setCity(city);

            weatherinfo.setTianqi(tianqi);
            weatherinfo.setGanmao(ganmao);
            weatherinfo.setXiche(xiche);
            weatherinfo.setYundong(yundong);
            weatherinfo.setChuanyi(chuanyi);
            weatherinfo.setLvyou(lvyou);
        }
        return weatherinfo;
    }
    public static List<WeatherPreinfo> getForecastWeather(String json) throws JSONException {
        List<WeatherPreinfo> weatherPreinfos=new ArrayList<WeatherPreinfo>();

        int i=0;
        JSONObject jsonObject=new JSONObject(json);
        JSONArray jsonArray=jsonObject.getJSONArray("HeWeather data service 3.0");
        JSONObject data=(JSONObject) jsonArray.get(0);
        JSONArray forecast=data.getJSONArray("daily_forecast");
        for (i=1;i<forecast.length();i++){
            WeatherPreinfo weatherPreinfo=new WeatherPreinfo();
            JSONObject predata= (JSONObject) forecast.get(i);
            JSONObject tmp=predata.getJSONObject("tmp");
            String tmp_max=tmp.getString("max");
            String tmp_min=tmp.getString("min");
            String tmp_all=tmp_min+"~"+tmp_max;
            JSONObject cond=predata.getJSONObject("cond");
            String cond_day=cond.getString("txt_d");
            JSONObject wind=predata.getJSONObject("wind");
            String wind_sc=wind.getString("sc");
            weatherPreinfo.setTmpAll(tmp_all);
            if (wind_sc.contains("风")){
                weatherPreinfo.setWindSc(wind_sc);

            }
            else{
                weatherPreinfo.setWindSc(wind_sc+"级");
            }
            weatherPreinfo.setCondDay(cond_day);
            weatherPreinfos.add(weatherPreinfo);

        }
        return weatherPreinfos;

    }


}
