package com.zhangzhun.way.fragment;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import com.google.gson.Gson;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.timqi.sectorprogressview.ColorfulRingProgressView;
import com.zhangzhun.way.FragmentViewPager.DisallowParentTouchViewPager;
import com.zhangzhun.way.apapter.WeatherPagerAdapter;
import com.zhangzhun.way.app.Application;
import com.zhangzhun.way.bean.City;
import com.zhangzhun.way.bean.Pm2d5;
import com.zhangzhun.way.bean.SimpleWeatherinfo;
import com.zhangzhun.way.bean.WeatherPreinfo;
import com.zhangzhun.way.bean.Weatherinfo;
import com.zhangzhun.way.db.CityDB;
import com.zhangzhun.way.indicator.CirclePageIndicator;
import com.zhangzhun.way.ui.FixRequestDisallowTouchEventPtrFrameLayout;
import com.zhangzhun.way.ui.RentalsSunHeaderView;
import com.zhangzhun.way.util.ConfigCache;
import com.zhangzhun.way.util.IphoneDialog;
import com.zhangzhun.way.util.JSonForPm;
import com.zhangzhun.way.util.JsonForWeather;
import com.zhangzhun.way.util.L;
import com.zhangzhun.way.util.NetUtil;
import com.zhangzhun.way.util.SharePreferenceUtil;
import com.zhangzhun.way.util.T;
import com.zhangzhun.way.util.TimeUtil;
import com.zhangzhun.way.weather.MainActivity;
import com.zhangzhun.way.weather.R;
import com.zhangzhun.way.weather.SelectCtiyActivity;



import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * Created by lenovo on 2015/12/9.
 */
public class Fragment1 extends Fragment implements Application.EventHandler, View.OnClickListener {

    private ImageView share_img;
    private ImageView menu_img;
    public static final String JUHEAPIKEY = "17c6e53fd6b88d35e660f424f780355a";
    public static final String HEWEATHERAPI = "&key=7511d182e71648ba8cfffb2036849795";
    public static final String UPDATE_WIDGET_WEATHER_ACTION = "com.zhangzhun.way.action.update_weather";
    public static final String WEATHER__URL = "https://api.heweather.com/x3/weather?cityid=CN";// 天气信息
    public static final String PM2D5_BASE_URL = "http://api.lib360.net/open/pm2.5.json?city=商丘";
    private static final String WEATHER_INFO_FILENAME = "_weather.json";
    private static final String SIMPLE_WEATHER_INFO_FILENAME = "_simple_weather.json";
    private static final String PM2D5_INFO_FILENAME = "_pm2d5.json";
    private static final int LOACTION_OK = 0;
    private static final int ON_NEW_INTENT = 1;
    private static final int UPDATE_EXISTS_CITY = 2;
    private static final int GET_WEATHER_RESULT = 3;
    private LocationClient mLocationClient;
    private CityDB mCityDB;
    private SharePreferenceUtil mSpUtil;
    private Application mApplication;

    public City getmCurCity() {
        return mCurCity;
    }

    public void setmCurCity(City mCurCity) {
        this.mCurCity = mCurCity;
    }

    private City mCurCity;
    private Weatherinfo mCurWeatherinfo;
    private List<WeatherPreinfo> mPreWeatherinfo;
    private SimpleWeatherinfo mCurSimpleWeatherinfo;
    private Pm2d5 mCurPm2d5;

    public City getmNewIntentCity() {
        return mNewIntentCity;
    }

    public void setmNewIntentCity(City mNewIntentCity) {
        this.mNewIntentCity = mNewIntentCity;
    }
    public void setNewIntentCity(String name,String number){

        mNewIntentCity.setCity(name);
        mNewIntentCity.setNumber(number);
    }

    private  City mNewIntentCity=new City();
    private WeatherPagerAdapter mWeatherPagerAdapter;

    private TextView cityTv, timeTv, humidityTv, weekTv, pmDataTv, pmQualityTv,
            temperatureTv, climateTv, windTv;
    private ImageView weatherImg, pmImg;
    private FixRequestDisallowTouchEventPtrFrameLayout frame;
    private RentalsSunHeaderView header;
    private DisallowParentTouchViewPager mViewPager;
    private List<Fragment> fragments;



    private boolean isVisible;
    private TextView pmNumber;
    private boolean isPrepared;
    private ColorfulRingProgressView colorfulRingProgressView;
    private Weatherinfo fragment2_weatherinfo;
    private Pm2d5 fragment2_pm;
    private int pm2d5_number;

    private String tianqi;
    private String ganmao;
    private String xiche;
    private String yundong;
    private String chuanyi;
    private String lvyou;

    private TextView textView_tianqi;
    private TextView textview_xiche;
    private TextView textView_chuanyi;
    private TextView textView_ganmao;
    private TextView textView_yundong;
    private TextView textView_lvyou;


    private RoundCornerProgressBar progressOne;
    private RoundCornerProgressBar progressTwo;

    private TextView progress_text1;
    private TextView progress_text2;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case LOACTION_OK:

                    String cityName = (String) msg.obj;
                    L.i("cityName = " + cityName);
                    mCurCity = mCityDB.getCity(cityName);
                    L.i(mCurCity.toString());
                    mSpUtil.setCity(mCurCity.getCity());
                    cityTv.setText(mCurCity.getCity());
                    frame.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            frame.autoRefresh(true);
                        }
                    }, 100);
                    break;
                case ON_NEW_INTENT:
                    mCurCity = mNewIntentCity;
                    mSpUtil.setCity(mCurCity.getCity());
                    cityTv.setText(mCurCity.getCity());
                    frame.autoRefresh();
                    updateWeather(true);
                    break;
                case UPDATE_EXISTS_CITY:
                    String sPCityName = mSpUtil.getCity();
                    mCurCity = mCityDB.getCity(sPCityName);

                    updateWeather(false);
                    break;
                case GET_WEATHER_RESULT:
                    updateWeatherInfo();
                    updatePm2d5Info();
                    updateWidgetWeather();

                    break;
                default:
                    break;
            }
        }

    };

    private View mainView;
    protected LayoutInflater mInflater;
    private boolean isRunning = false;
    private boolean flag = false;
    private LinearLayout scrowLinearLayout;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment1, container,
                    false);
        }
        mCurCity=((MainActivity)getActivity()).getmCity();

        if (mCurCity!=null){
            mHandler.sendEmptyMessage(ON_NEW_INTENT);
        }



        BDLocationListener mLocationListener = new BDLocationListener() {


            public void onReceivePoi(BDLocation arg0) {
                // do nothing
            }

            @Override
            public void onReceiveLocation(BDLocation location) {
                // mActionBar.setProgressBarVisibility(View.GONE);

                if (location == null || TextUtils.isEmpty(location.getCity())) {
                    Holder holder = new ViewHolder(R.layout.content);
                    final DialogPlus dialog = DialogPlus.newDialog(getActivity())
                            .setContentHolder(holder)
                            .setFooter(R.layout.footer)
                            .setCancelable(true)
                            .setHeader(R.layout.header)
                            .setGravity(Gravity.TOP)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(DialogPlus dialog, View view) {
                                    switch (view.getId()) {
                                        case R.id.cancle_it_button:
                                            dialog.dismiss();
                                            break;
                                        case R.id.confirm_it_button:
                                            startActivityForResult();
                                            dialog.dismiss();
                                            break;
                                    }
                                }
                            })
                            .setExpanded(true)
                            .setCancelable(false)
                            .create();
                    dialog.show();
                    return;
                }
                String cityName = location.getCity();
                System.out.println("*************" + cityName);
                mLocationClient.stop();
                Message msg = mHandler.obtainMessage();
                msg.what = LOACTION_OK;
                msg.obj = cityName;
                mHandler.sendMessage(msg);// 更新天气
            }
        };
        share_img= (ImageView) mainView.findViewById(R.id.share_image);
        share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text_share_info));
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.text_share_wukong)));
            }
        });
        menu_img= (ImageView) mainView.findViewById(R.id.menu_city);
        menu_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawer.openDrawer();
            }
        });
        Application.mListeners.add(this);
        mApplication = Application.getInstance();
        mSpUtil = mApplication.getSharePreferenceUtil();
        mLocationClient = mApplication.getLocationClient();
        mLocationClient.registerLocationListener(mLocationListener);
        if (TextUtils.isEmpty(mSpUtil.getCity())) {
            if (NetUtil.getNetworkState(getActivity()) != NetUtil.NETWORN_NONE) {
                mLocationClient.start();
                mLocationClient.requestLocation();
                T.showShort(getActivity(), "正在定位...");

            } else {
                frame.refreshComplete();

                T.showShort(getContext(), R.string.net_err);
            }
        } else {
            mHandler.sendEmptyMessage(UPDATE_EXISTS_CITY);
        }

        mCityDB = mApplication.getCityDB();
        //刷新控件
        frame = (FixRequestDisallowTouchEventPtrFrameLayout) mainView.findViewById(R.id.material_style_ptr_frame);

        // header
        header = new RentalsSunHeaderView(getContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, PtrLocalDisplay.dp2px(15), 0, PtrLocalDisplay.dp2px(10));
        header.setUp(frame);

        frame.setLoadingMinTime(1000);
        frame.setDurationToCloseHeader(1500);
        frame.setHeaderView(header);
        frame.addPtrUIHandler(header);
        // frame.setPullToRefresh(true);


        frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                flag = true;
                if (NetUtil.getNetworkState(getContext()) == NetUtil.NETWORN_NONE) {
                    frame.refreshComplete();

                }

                updateWeather(true);

            }
        });


        cityTv = (TextView) mainView.findViewById(R.id.city);
        timeTv = (TextView) mainView.findViewById(R.id.time);
        timeTv.setText(TimeUtil.getDay(mSpUtil.getTimeSamp())
                + mSpUtil.getTime() + "发布");
        humidityTv = (TextView) mainView.findViewById(R.id.humidity);
        weekTv = (TextView) mainView.findViewById(R.id.week_today);
        weekTv.setText("今天 " + TimeUtil.getWeek(0, TimeUtil.XING_QI));
        pmDataTv = (TextView) mainView.findViewById(R.id.pm_data);
        pmQualityTv = (TextView) mainView.findViewById(R.id.pm2_5_quality);
        pmImg = (ImageView) mainView.findViewById(R.id.pm2_5_img);
        temperatureTv = (TextView) mainView.findViewById(R.id.temperature);
        climateTv = (TextView) mainView.findViewById(R.id.climate);
        windTv = (TextView) mainView.findViewById(R.id.wind);
        weatherImg = (ImageView) mainView.findViewById(R.id.weather_img);
        colorfulRingProgressView= (ColorfulRingProgressView) mainView.findViewById(R.id.sectorProgressView);
        progress_text1= (TextView) mainView.findViewById(R.id.text_aqi);
        progress_text2= (TextView) mainView.findViewById(R.id.text_no2);
        progressOne= (RoundCornerProgressBar) mainView.findViewById(R.id.progress_one);

        progressTwo= (RoundCornerProgressBar) mainView.findViewById(R.id.progress_two);

        pmNumber= (TextView) mainView.findViewById(R.id.pm_number);
        textView_chuanyi= (TextView) mainView.findViewById(R.id.chuanyi);
        textview_xiche= (TextView) mainView.findViewById(R.id.xiche);
        textView_tianqi= (TextView) mainView.findViewById(R.id.tianqi);
        textView_ganmao= (TextView) mainView.findViewById(R.id.ganmao);
        textView_yundong= (TextView) mainView.findViewById(R.id.yundong);
        textView_lvyou= (TextView) mainView.findViewById(R.id.lvyou);
        colorfulRingProgressView.setOnClickListener(this);
        fragments = new ArrayList<Fragment>();
        fragments.add(new FirstWeatherFragment());
        fragments.add(new SecondWeatherFragment());
        mViewPager = (DisallowParentTouchViewPager) mainView.findViewById(R.id.viewpager);
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        frame.disableWhenHorizontalMove(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        frame.disableWhenHorizontalMove(false);
                        break;
                }
                return false;
            }
        });
        mWeatherPagerAdapter = new WeatherPagerAdapter(
                getFragmentManager(), fragments);
        mViewPager.setAdapter(mWeatherPagerAdapter);
        ((CirclePageIndicator) mainView.findViewById(R.id.indicator))
                .setViewPager(mViewPager);

        return mainView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void startActivityForResult() {
        Intent i = new Intent(getContext(), SelectCtiyActivity.class);
        startActivityForResult(i, 0);
    }

    private void updateWeather(final boolean isRefresh) {
        if (NetUtil.getNetworkState(getContext()) == NetUtil.NETWORN_NONE && isRefresh) {
            T.showLong(getActivity(), R.string.net_err);
            return;
        }
        if (mCurCity == null) {
            T.showLong(mApplication, "未找到此城市,请重新定位或选择...");
            return;
        }
        // T.showShort(this, "正在刷新天气...");
        timeTv.setText("同步中...");


        // 启动线程获取天气信息
        new Thread() {
            @Override
            public void run() {
                super.run();
                getWeatherInfo(isRefresh);

                getPm2d5Info(isRefresh);

                if (mCurWeatherinfo != null)
                    L.i(mCurWeatherinfo.toString());
                if (mCurPm2d5 != null)
                    L.i(mCurPm2d5.toString());
                mHandler.sendEmptyMessage(GET_WEATHER_RESULT);
            }

        }.start();
    }

    private void getWeatherInfo(boolean isRefresh) {

        String url = WEATHER__URL + mCurCity.getNumber() + HEWEATHERAPI;
        String result;
        if (!isRefresh) {
            if (mApplication.getmCurWeatherinfo() != null) {// 读取内存中的信息
                mCurWeatherinfo = mApplication.getmCurWeatherinfo();
                ((MainActivity) getActivity()).setFragment_weatherinfo(mCurWeatherinfo);

                L.i("get the weather info from memory");
                return;// 直接返回，不继续执行
            }
            result = ConfigCache.getUrlCache(url);
            if (!TextUtils.isEmpty(result)) {
                parseWeatherInfo(url, result, false);
                L.i("get the weather info from file");
                return;
            }
        }

        // L.i("weather url: " + url);
        String weatherResult = connServerForResult(url);
        if (TextUtils.isEmpty(weatherResult))
            weatherResult = getInfoFromFile(WEATHER_INFO_FILENAME);
        parseWeatherInfo(url, weatherResult, true);
    }

    private void parseWeatherInfo(String url, String result,
                                  boolean isRefreshWeather) {
        mCurWeatherinfo = null;
        mCurWeatherinfo = mApplication.getmCurWeatherinfo();
        mApplication.setmCurWeatherinfo(null);
        if (!TextUtils.isEmpty(result) && !result.contains("页面没有找到")) {
            // L.i(result);
            try {
                mCurWeatherinfo = JsonForWeather.getCurrentWeather(result);
                mPreWeatherinfo = JsonForWeather.getForecastWeather(result);
                ((MainActivity) getActivity()).setFragment_weatherinfo(mCurWeatherinfo);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else {

        }
        ConfigCache.setUrlCache(result, url);
    }

    private void parsePm2d5Info(String url, String result,
                                boolean isRefreshPm2d5) {
        mCurPm2d5 = null;

        System.out.println("---------" + result);

        mApplication.setmCurWeatherinfo(null);
        if (!TextUtils.isEmpty(result)) {
            // L.i(result);
            try {
                mCurPm2d5 = JSonForPm.getPm2d5Object(result);
                ((MainActivity) getActivity()).setFragment_pm(mCurPm2d5);

                System.out.println("++++++++" + mCurPm2d5);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // L.i(mCurPm2d5.toString());
        } else {
            result = "";
        }
        if (isRefreshPm2d5 && !TextUtils.isEmpty(result))
            // save2File(result, PM2D5_INFO_FILENAME);
            ConfigCache.setUrlCache(result, url);
    }

    // 把信息保存到文件中
    private boolean save2File(String result, String fileName) {
        try {
            FileOutputStream fos = getActivity().openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            fos.write(result.toString().getBytes());
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void getPm2d5Info(boolean isRefresh) {

        String urlPm2d5 = PM2D5_BASE_URL.replace("商丘",
                mCurCity.getCity());


        String result;
        if (!isRefresh) {
            if (mApplication.getmCurPm2d5() != null) {// 内存中的信息
                mCurPm2d5 = mApplication.getmCurPm2d5();
                ((MainActivity) getActivity()).setFragment_pm(mCurPm2d5);

                L.i("get the pm2.5 info from memory");
                return;
            }
            result = ConfigCache.getUrlCache(urlPm2d5);
            if (!TextUtils.isEmpty(result)) {
                parsePm2d5Info(urlPm2d5, result, false);
                L.i("get the pm2.5 info from file");
                return;
            }
        }


        // L.i("pm2.5 url: " + urlPm2d5);
        String pmResult = connServerForResult(urlPm2d5);
        if (TextUtils.isEmpty(pmResult) || pmResult.contains("reason")) {// 如果获取失败，则取本地文件中的信息，
            String fileResult = getInfoFromFile(PM2D5_INFO_FILENAME);
            // 只有当本地文件信息与当前城市相匹配时才使用
            if (!TextUtils.isEmpty(fileResult)
                    && fileResult.contains(mCurCity.getCity()))
                pmResult = fileResult;
        }
        // pmResult = getInfoFromFile(PM2D5_INFO_FILENAME);

        parsePm2d5Info(urlPm2d5, pmResult, true);

    }

    // 请求服务器，获取返回数据
    private String connServerForResult(String url) {
        String str = getInfoFromFile(WEATHER_INFO_FILENAME);

        String strResult = "";
        if (NetUtil.getNetworkState(getContext()) != NetUtil.NETWORN_NONE) {
            try {
                strResult = request(url);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (strResult == null || strResult.equals("")) {
            strResult = str;
        }
        return strResult; // 返回结果

    }

    public void setNewIntent() {

            mHandler.sendEmptyMessage(ON_NEW_INTENT);

    }

    /**
     * 更新pm2.5界面
     */
    private void updatePm2d5Info() {
        if (mCurPm2d5 != null) {
            lazyLoad();
            mApplication.setmCurPm2d5(mCurPm2d5);
            pmQualityTv.setText(mCurPm2d5.getQuality());
            pmDataTv.setText(mCurPm2d5.getPm());
            int pm2_5 = Integer.parseInt(mCurPm2d5.getPm());
            int pm_img = R.drawable.biz_plugin_weather_0_50;
            if (pm2_5 > 300) {
                pm_img = R.drawable.biz_plugin_weather_greater_300;
            } else if (pm2_5 > 200) {
                pm_img = R.drawable.biz_plugin_weather_201_300;
            } else if (pm2_5 > 150) {
                pm_img = R.drawable.biz_plugin_weather_151_200;
            } else if (pm2_5 > 100) {
                pm_img = R.drawable.biz_plugin_weather_101_150;
            } else if (pm2_5 > 50) {
                pm_img = R.drawable.biz_plugin_weather_51_100;
            } else {
                pm_img = R.drawable.biz_plugin_weather_0_50;
            }

            pmImg.setImageResource(pm_img);
        } else {
            pmQualityTv.setText("N/A");
            pmDataTv.setText("N/A");
            pmImg.setImageResource(R.drawable.biz_plugin_weather_0_50);
            T.showLong(mApplication, "未获取到PM2.5数据");
        }
    }

    /**
     * 从文件中获取信息
     *
     * @param fileName
     * @return
     */
    private String getInfoFromFile(String fileName) {
        String result = "";
        try {
            FileInputStream fis = getActivity().openFileInput(fileName);
            byte[] buffer = new byte[fis.available()];// 本地文件可以实例化buffer，网络文件不可行
            fis.read(buffer);
            result = new String(buffer);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新天气界面
     */
    private void updateWeatherInfo() {
        if (mCurWeatherinfo != null) {
            mApplication.setmCurWeatherinfo(mCurWeatherinfo);// 保存到全局变量中
            temperatureTv.setText(mCurWeatherinfo.getTmpAll());
            cityTv.setText(mCurWeatherinfo.getCity());

            String wind = mCurWeatherinfo.getWindSc();
            if (wind.contains("转")) {
                String[] strs = wind.split("转");
                wind = strs[0];
            }
            windTv.setText(wind);
            String climate = mCurWeatherinfo.getCondDay();
            climateTv.setText(climate);
            mSpUtil.setSimpleClimate(climate);
            String[] strs = {"晴", "晴"};
            if (climate.contains("转")) {// 天气带转字，取前面那部分
                strs = climate.split("转");
                climate = strs[0];
                if (climate.contains("到")) {// 如果转字前面那部分带到字，则取它的后部分
                    strs = climate.split("到");
                    climate = strs[1];
                }
            }
            L.i("处理后的天气为：" + climate);
            if (mApplication.getWeatherIconMap().containsKey(climate)) {
                int iconRes = mApplication.getWeatherIconMap().get(climate);
                weatherImg.setImageResource(iconRes);
            } else {
                // do nothing 没有这样的天气图片

            }
            if (mCurWeatherinfo != null) {
                if (!mCurWeatherinfo.getLocTime().equals(mSpUtil.getTime())) {
                    mSpUtil.setTime(mCurWeatherinfo.getLocTime());
                    mSpUtil.setTimeSamp(System.currentTimeMillis());// 保存一下更新的时间戳
                }
                mSpUtil.setSimpleTemp(mCurWeatherinfo.getTmpAll());
                timeTv.setText(TimeUtil.getDay(mSpUtil.getTimeSamp())
                        + mCurWeatherinfo.getLocTime() + "发布");
                humidityTv.setText("湿度:" + mCurWeatherinfo.getHum());
                isRunning = true;
                if (isRunning || NetUtil.getNetworkState(getContext()) == NetUtil.NETWORN_NONE) {
                    frame.refreshComplete();

                }
                isRunning = false;

            }
            if (fragments.size() > 0) {
                ((FirstWeatherFragment) mWeatherPagerAdapter.getItem(0))
                        .updateWeather(mPreWeatherinfo);
                ((SecondWeatherFragment) mWeatherPagerAdapter.getItem(1))
                        .updateWeather(mPreWeatherinfo);
            }
        } else {

        }
    }

    private void updateWidgetWeather() {
        getActivity().sendBroadcast(new Intent(UPDATE_WIDGET_WEATHER_ACTION));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {



            default:
                break;
        }


    }

    @Override
    public void onCityComplite() {

    }

    @Override
    public void onNetChange() {

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public static String request(String httpUrl) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    protected  void lazyLoad(){

        fragment2_pm=((MainActivity)(getActivity())).getFragment_pm();
        fragment2_weatherinfo=((MainActivity)getActivity()).getFragment_weatherinfo();
        if (fragment2_pm!=null){
            pm2d5_number=Integer.parseInt(fragment2_pm.getPm());



        }
        if (fragment2_weatherinfo!=null){
            System.out.println("+++++++"+fragment2_weatherinfo);
            tianqi=fragment2_weatherinfo.getTianqi();
            ganmao=fragment2_weatherinfo.getGanmao();
            xiche=fragment2_weatherinfo.getXiche();
            yundong=fragment2_weatherinfo.getYundong();
            chuanyi=fragment2_weatherinfo.getChuanyi();
            lvyou=fragment2_weatherinfo.getLvyou();
            textView_tianqi.setText(tianqi);
            textView_ganmao.setText(ganmao);
            textview_xiche.setText(xiche);
            textView_yundong.setText(yundong);
            textView_chuanyi.setText(chuanyi);
            textView_lvyou.setText(lvyou);

            int wencha_max=Integer.parseInt(fragment2_weatherinfo.getTmpMax());
            int wencha_min=Integer.parseInt(fragment2_weatherinfo.getTmpMin());
            int wencha=wencha_max-wencha_min;
            int shidu=Integer.parseInt(fragment2_weatherinfo.getHum());
            progress_text1.setText("湿度:   "+shidu);
            progress_text2.setText("温差:   "+wencha);
            progressOne.setProgress(shidu- 20);
            progressTwo.setProgress(wencha + 20);
        }
        pmNumber.setText(pm2d5_number+"");
        colorfulRingProgressView.setPercent(pm2d5_number);
        ObjectAnimator anim = ObjectAnimator.ofFloat(colorfulRingProgressView, "percent",
                0, (int)((colorfulRingProgressView).getPercent()*0.4));
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(1000);
        anim.start();
        //填充各控件的数据

    };

}
