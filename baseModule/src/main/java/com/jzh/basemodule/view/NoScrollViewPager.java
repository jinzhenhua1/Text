package com.jzh.basemodule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 禁止左右滑动
 * @author jinzhenhua
 * @version 1.0  ,create at:2021/3/12 15:57
 */
public class NoScrollViewPager extends ViewPager {
    private boolean canSwipe = true;

    public NoScrollViewPager(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public void setCanSwipe(boolean canSwipe)
    {
        this.canSwipe = canSwipe;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canSwipe && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canSwipe && super.onInterceptTouchEvent(ev);
    }

}
