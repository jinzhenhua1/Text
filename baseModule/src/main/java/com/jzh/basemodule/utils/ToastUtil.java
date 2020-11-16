package com.jzh.basemodule.utils;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;


/**
 * toast 工具类
 * Created by huangqj on 2018-02-01.
 */
public class ToastUtil {

    private static ToastUtil toastUtil;
    private Application context;
    private Toast toast;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    private ToastUtil(Application context) {
        this.context = context;
    }

    public static synchronized ToastUtil getInstance(Application context) {
        if (null == toastUtil) {
            toastUtil = new ToastUtil(context);
        }
        return toastUtil;
    }

    /**
     * 显示长提示信息
     *
     * @param msg 要显示的信息
     */
    public void showToastLong(String msg) {
        showCusToast(msg, Toast.LENGTH_LONG, Gravity.CENTER);
    }

    /**
     * 显示短提示信息
     *
     * @param msg 要显示的信息
     */
    public void showToastShort(String msg) {
        showToastCenter(msg);
    }

    /**
     * 显示短提示信息
     *
     * @param msg 要显示的信息
     */
    public void showToastCenter(String msg) {
        showCusToast(msg, Toast.LENGTH_SHORT, Gravity.CENTER);
    }

    public void showToastBottom(String msg) {
        showCusToast(msg, Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }

    public void showCusToast(String msg, final int LENGTH, final int GRAVITY) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, LENGTH);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(GRAVITY, 0, 0);
        toast.show();
    }

    /**
     * 在主线程执行
     * @param msg
     */
    public void showToastOnMain(String msg){
        if (Looper.getMainLooper() == Looper.myLooper()) {
            showToastShort(msg);
        } else {
            mMainHandler.post(() -> {
                showToastShort(msg);
            });
        }
    }
}
