package com.jzh.basemodule.utils;

import android.content.Context;

/**
 * <p><p>
 *
 * @author huangqj
 * @version 1.0 , create at 2020/4/7 17:59
 */
public class WidgetUtil {

    private static float scale;

    private WidgetUtil() {
    }

    /**
     * dp装px
     *
     * @param dp      值
     * @param context context
     * @return px
     */
    public static int dpToPixel(float dp, Context context) {
        if (scale == 0) {
            scale = context.getResources().getDisplayMetrics().density;
        }
        return (int) (dp * scale);
    }
}
