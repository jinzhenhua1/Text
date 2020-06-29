package com.example.text.text1.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/5/19 20:52
 */
public class TextBinderService extends Service {
    private DownLoadBinder downLoadBinder = new DownLoadBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downLoadBinder;
    }

    /**
     * 内部类继承Binder
     * @author lenovo
     *
     */
    public class DownLoadBinder extends Binder {
        public void startDownLoad(){
            System.out.println("=====startDownLoad()=====");
        }
        public void getProgress(){
            System.out.println("=====getProgress()=====");
        }
    }
}
