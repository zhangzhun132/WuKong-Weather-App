package com.zhangzhun.way.util;

import com.zhangzhun.way.bean.Pm2d5;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2015/12/15.
 */
public class JSonForPm {
    public  static Pm2d5 getPm2d5Object(String json) throws JSONException {
        Pm2d5 pmObject=null;
        JSONObject jsonObject=new JSONObject(json);
        if (jsonObject!=null){
            pmObject=new Pm2d5();
            pmObject.setPm(jsonObject.getString("pm25"));
        }

        return pmObject;
    }
}
