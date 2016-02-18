package com.zhangzhun.way.bean;

/**
 * Created by lenovo on 2015/12/9.
 */
public class WeatherPreinfo {

    private String TmpMax;
    private String TmpMin;
    private String CondDay;
    private String CondNight;
    private String Hum;
    private String WindSc;
    private String TmpAll;

    public String getTmpMax() {
        return TmpMax;
    }

    public void setTmpMax(String tmpMax) {
        TmpMax = tmpMax;
    }

    public String getTmpAll() {
        return TmpAll;
    }

    public void setTmpAll(String tmpAll) {
        TmpAll = tmpAll;
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
}
