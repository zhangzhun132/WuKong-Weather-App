package com.zhangzhun.way.FragmentViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lenovo on 2015/12/9.
 */
public class FragmentPager extends ViewPager {
    private boolean enabled;

    public FragmentPager(Context context) {
        super(context);
        this.enabled = false;

    }

    public FragmentPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = false;

    }
    //禁止滑动触摸没有反应就可以了
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
