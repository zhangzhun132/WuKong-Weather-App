package com.zhangzhun.way.fragment;

import com.zhangzhun.way.app.Application;
import com.zhangzhun.way.bean.WeatherPreinfo;
import com.zhangzhun.way.util.TimeUtil;
import com.zhangzhun.way.weather.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FirstWeatherFragment extends Fragment {
	private TextView weekTv1, weekTv2, weekTv3;
	private ImageView weather_imgIv1, weather_imgIv2, weather_imgIv3;
	private TextView temperatureTv1, temperatureTv2, temperatureTv3;
	private TextView climateTv1, climateTv2, climateTv3;
	private TextView windTv1, windTv2, windTv3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.biz_plugin_weather_item,
				container, false);
		View view1 = view.findViewById(R.id.subitem1);
		View view2 = view.findViewById(R.id.subitem2);
		View view3 = view.findViewById(R.id.subitem3);

		weekTv1 = (TextView) view1.findViewById(R.id.week);
		weekTv2 = (TextView) view2.findViewById(R.id.week);
		weekTv3 = (TextView) view3.findViewById(R.id.week);
		
		weekTv1.setText(TimeUtil.getWeek(1, TimeUtil.XING_QI));
		weekTv2.setText(TimeUtil.getWeek(2, TimeUtil.XING_QI));
		weekTv3.setText(TimeUtil.getWeek(3, TimeUtil.XING_QI));

		weather_imgIv1 = (ImageView) view1.findViewById(R.id.weather_img);
		weather_imgIv2 = (ImageView) view2.findViewById(R.id.weather_img);
		weather_imgIv3 = (ImageView) view3.findViewById(R.id.weather_img);
		temperatureTv1 = (TextView) view1.findViewById(R.id.temperature);
		temperatureTv2 = (TextView) view2.findViewById(R.id.temperature);
		temperatureTv3 = (TextView) view3.findViewById(R.id.temperature);

		climateTv1 = (TextView) view1.findViewById(R.id.climate);
		climateTv2 = (TextView) view2.findViewById(R.id.climate);
		climateTv3 = (TextView) view3.findViewById(R.id.climate);

		windTv1 = (TextView) view1.findViewById(R.id.wind);
		windTv2 = (TextView) view2.findViewById(R.id.wind);
		windTv3 = (TextView) view3.findViewById(R.id.wind);
		return view;
	}

	public void updateWeather(List<WeatherPreinfo> weatherinfo) {
		if (weatherinfo != null) {

			weather_imgIv1.setImageResource(Application.getInstance()
					.getWeatherIcon(weatherinfo.get(0).getCondDay()));
			weather_imgIv2.setImageResource(Application.getInstance()
					.getWeatherIcon(weatherinfo.get(1).getCondDay()));
			weather_imgIv3.setImageResource(Application.getInstance()
					.getWeatherIcon(weatherinfo.get(2).getCondDay()));
			climateTv1.setText(weatherinfo.get(0).getCondDay());
			climateTv2.setText(weatherinfo.get(1).getCondDay());
			climateTv3.setText(weatherinfo.get(2).getCondDay());

			temperatureTv1.setText(weatherinfo.get(0).getTmpAll());
			temperatureTv2.setText(weatherinfo.get(1).getTmpAll());
			temperatureTv3.setText(weatherinfo.get(2).getTmpAll());

			windTv1.setText(weatherinfo.get(0).getWindSc());
			windTv2.setText(weatherinfo.get(1).getWindSc());
			windTv3.setText(weatherinfo.get(2).getWindSc());
		} else {
//			weather_imgIv1.setImageResource(R.drawable.na);
//			weather_imgIv2.setImageResource(R.drawable.na);
//			weather_imgIv3.setImageResource(R.drawable.na);
			
//			climateTv1.setText("N/A");
//			climateTv2.setText("N/A");
//			climateTv3.setText("N/A");
//
//			temperatureTv1.setText("N/A");
//			temperatureTv2.setText("N/A");
//			temperatureTv3.setText("N/A");
//
//			windTv1.setText("N/A");
//			windTv2.setText("N/A");
//			windTv3.setText("N/A");
		}
	}

}
