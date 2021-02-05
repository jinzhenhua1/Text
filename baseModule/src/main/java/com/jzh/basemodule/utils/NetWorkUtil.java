package com.jzh.basemodule.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * @author baiaj
 */
public class NetWorkUtil {

    private NetWorkUtil(){}

    /**
     * 网络是否可用
     *
     * @param context 上下文
     * @return 结果
     */
    public static boolean isNetWorkEnable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }


    /**
     * 判断当前4G网络是否可用
     *
     * @param context 上下文对象
     * @return 4G网络是否可用
     */
    public static boolean is4GAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            TelephonyManager telephonyManager = (TelephonyManager)
                    context.getSystemService(
                            Context.TELEPHONY_SERVICE);
            int networkType = telephonyManager.getNetworkType();
            if (networkType == 13) {
                return true;
            }
        }
        return false;
    }
}
