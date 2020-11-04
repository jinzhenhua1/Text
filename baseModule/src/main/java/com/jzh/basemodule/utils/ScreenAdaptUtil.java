package com.jzh.basemodule.utils;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/9/25 9:54
 */
public class ScreenAdaptUtil {

    /**
     * 固定页面的宽高与设计图相等
     * 像素比 density = px/dp，所以我们设计图的页面固定宽高DP的时候，比如 设计图的宽度为 360，
     * 那么可以用屏幕的 宽度像素/360 = 新的像素比。
     * @param activity
     * @param application
     */
    public void setCustomDensity(Activity activity, Application application) {
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        //那么可以用屏幕的 宽度像素/360 = 新的像素比。
        float density = (float) displayMetrics.widthPixels / 360;
        //根据像素比，获取新的像素密度
        float newDpi = density * 160;

        displayMetrics.density = density;
        displayMetrics.scaledDensity = density;
        displayMetrics.densityDpi = (int) newDpi;

        DisplayMetrics activityMetrics = activity.getResources().getDisplayMetrics();
        activityMetrics.density = density;
        activityMetrics.scaledDensity = density;
        activityMetrics.densityDpi = (int) newDpi;
    }
}
