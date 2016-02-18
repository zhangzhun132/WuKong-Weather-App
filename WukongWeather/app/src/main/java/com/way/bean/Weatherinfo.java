package com.way.bean;


public class Weatherinfo {

	private String SunUp;
	private String SunDown;
	private String TmpMax;
	private String TmpMin;
	private String CondDay;
	private String CondNight;
	private String Hum;
	private String WindSc;
	private String LocTime;
	private String TmpAll;
	private String City;

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getTmpAll() {
		return TmpAll;
	}

	public void setTmpAll(String tmpAll) {
		TmpAll = tmpAll;
	}

	public String getLocTime() {
		return LocTime;
	}

	public void setLocTime(String locTime) {
		LocTime = locTime;
	}

	public String getWindSc() {
		return WindSc;
	}

	public void setWindSc(String windSc) {
		WindSc = windSc;
	}

	public String getHum() {
		return Hum;
	}

	public void setHum(String hum) {
		Hum = hum;
	}

	public String getCondNight() {
		return CondNight;
	}

	public void setCondNight(String condNight) {
		CondNight = condNight;
	}

	public String getCondDay() {
		return CondDay;
	}

	public void setCondDay(String condDay) {
		CondDay = condDay;
	}

	public String getTmpMin() {
		return TmpMin;
	}

	public void setTmpMin(String tmpMin) {
		TmpMin = tmpMin;
	}

	public String getTmpMax() {
		return TmpMax;
	}

	public void setTmpMax(String tmpMax) {
		TmpMax = tmpMax;
	}

	public String getSunDown() {
		return SunDown;
	}

	public void setSunDown(String sunDown) {
		SunDown = sunDown;
	}

	public String getSunUp() {
		return SunUp;
	}

	public void setSunUp(String sunUp) {
		SunUp = sunUp;
	}
}
