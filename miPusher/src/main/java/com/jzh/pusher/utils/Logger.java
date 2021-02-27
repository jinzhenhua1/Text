package com.jzh.pusher.utils;

import android.util.Log;

import com.jzh.pusher.BuildConfig;


public class Logger {

    public static void d(String msg) {
        if (BuildConfig.DEBUG) Log.d("FlutterPush", msg);
    }
}
