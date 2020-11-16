package com.jzh.pusher.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

public abstract class PushApplication extends Application {
    // 小米push　设置
    private static final String BRAND_MI = "xiaomi";
    private static final String BRAND_HUAWEI = "huawei";

    @Override
    public void onCreate() {
        super.onCreate();
        // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
        // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
//        if (!BRAND_HUAWEI.equalsIgnoreCase(DeviceUtils.getDeviceManufacturer())) {
//            if (shouldInit()) {
//                MiPushClient.registerPush(this, getMiAppId(), getMiAppKey());
//            }
//        }

        if (shouldInit()) {
            Log.e("PushApplication","初始化小米推送");
            MiPushClient.registerPush(this, getMiAppId(), getMiAppKey());
        }
    }

    public abstract String getMiAppId();

    public abstract String getMiAppKey();

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果为华为手机
     * 华为读取配置文件
     * @param context
     */
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
//        if (BRAND_HUAWEI.equalsIgnoreCase(DeviceUtils.getDeviceManufacturer())) {
//            reloadHuaWeiAppId(context);
//        }
    }

//    private void reloadHuaWeiAppId(Context context) {
//        AGConnectServicesConfig config = AGConnectServicesConfig.fromContext(context);
//        config.overlayWith(new LazyInputStream(context) {
//            public InputStream get(Context context) {
//                try {
//                    return context.getAssets().open("agconnect-services.json");
//                } catch (IOException e) {
//                    return null;
//                }
//            }
//        });
//    }
}
