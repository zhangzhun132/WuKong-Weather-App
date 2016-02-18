package com.zhangzhun.way.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
