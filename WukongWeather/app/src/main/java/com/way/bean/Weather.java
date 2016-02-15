package com.way.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Weather {
	@Expose
	@SerializedName("HeWeather data service 3.0")
	private Weatherinfo weatherinfo;

	public Weatherinfo getWeatherinfo() {
		return weatherinfo;
	}



	@Override
	public String toString() {
		return "Weather [weatherinfo=" + weatherinfo + "]";
	}


}
