package com.zhangzhun.way.apapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangzhun.way.weather.R;

import java.util.List;

/**
 * Created by lenovo on 2016/1/25.
 */
public class DrawerCityAdapter extends BaseAdapter{
    private List<String> mCityData;
    private List<String> mTempData;
    private List<Integer> mWeaData;
    private LayoutInflater mInflater;

    public DrawerCityAdapter(Context context,List<String> mCityData, List<String> mTempData, List<Integer> mWeaData) {
        this.mCityData = mCityData;
        this.mTempData = mTempData;
        this.mWeaData = mWeaData;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mCityData.size();
    }

    @Override
    public Object getItem(int position) {
        return mCityData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_list_app,null);
            holder.weatherImg= (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.cityName= (TextView) convertView.findViewById(R.id.iv_name);
            holder.tempNum= (TextView) convertView.findViewById(R.id.iv_temp);
            convertView.setTag(holder);

        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.weatherImg.setBackgroundResource(mWeaData.get(position));
        holder.cityName.setText(mCityData.get(position));
        holder.tempNum.setText(mTempData.get(position));
        return convertView;
    }
    public final class ViewHolder{
        public ImageView weatherImg;
        public TextView cityName;
        public TextView tempNum;
    }
}
