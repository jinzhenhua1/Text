package com.example.text.util;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;


interface ScreenStateListener{
    public void onScreenOn();

    public void onScreenOff();
}

public class OnePixelService extends Service implements ScreenStateListener {

    private ScreenBroadcastReceiver screenBroadcastReceiver;

    /**
     * screen状态广播接收者
     *
     * @author zhangyg
     */
    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                onScreenOn();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                onScreenOff();
            }
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        screenBroadcastReceiver = new ScreenBroadcastReceiver();
        //动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        if (getApplication() == null) return;
        getApplication().registerReceiver(screenBroadcastReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getApplication().unregisterReceiver(screenBroadcastReceiver);
    }

    /**
     * 屏幕打开
     */
    @Override
    public void onScreenOn() {
//        //屏幕关闭之后->启动一个activity一个像素的留在界面上
//        if (getApplication() == null) return;
//        WeakReference<Activity> mActivityWref = OnePixelLive.getmActivityWref();
//        if (mActivityWref != null) {
//            Activity activity = mActivityWref.get();
//            if (activity != null) {
//                activity.finish();
//            }
//        }
    }

    /**
     * 屏幕关闭
     */
    @Override
    public void onScreenOff() {
        //屏幕关闭之后->启动一个activity一个像素的留在界面上
        //当app在后台,或者按下home键之后,创建一个activity会慢,可能会在再次解锁之后创建
        if (getApplication() == null) return;
        //boolean runBackground = BackFogetUtil.isRunBackground(getApplicationContext());
        getApplication().startActivity(new Intent(getApplication(), OnePixelActvity.class));
    }


}
