package com.zhangzhun.way.apapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zhangzhun.way.fragment.Fragment1;
import com.zhangzhun.way.fragment.Fragment2;

/**
 * Created by lenovo on 2015/12/9.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    int COUNT = 2;
    private Fragment f;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0) {
            f = new Fragment1();
        }else if(position == 1) {
            f = new Fragment2();
        }
        return f;
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
