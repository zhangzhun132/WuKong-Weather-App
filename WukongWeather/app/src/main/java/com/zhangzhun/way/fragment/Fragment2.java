package com.zhangzhun.way.fragment;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.timqi.sectorprogressview.ColorfulRingProgressView;
import com.zhangzhun.way.bean.Pm2d5;
import com.zhangzhun.way.bean.Weatherinfo;
import com.zhangzhun.way.weather.MainActivity;
import com.zhangzhun.way.weather.R;

/**
 * Created by lenovo on 2015/12/9.
 */
public class Fragment2 extends Fragment implements View.OnClickListener{
   private boolean isVisible;
    private TextView pmNumber;
    private boolean isPrepared;
    private ColorfulRingProgressView colorfulRingProgressView;
    private View mainView;
    protected LayoutInflater mInflater;
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




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    protected void onVisible(){
        lazyLoad();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected  void lazyLoad(){
        if(!isPrepared || !isVisible) {
            return;
        }
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
            progressOne.setProgress(shidu+ 10);
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

    protected void onInvisible(){}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment2, container,
                    false);
        }
        isPrepared = true;
        lazyLoad();
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


        return mainView;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sectorProgressView:

                break;
        }
    }
}
