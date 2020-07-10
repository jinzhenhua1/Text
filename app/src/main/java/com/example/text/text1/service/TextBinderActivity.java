package com.example.text.text1.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.example.text.R;

public class TextBinderActivity extends AppCompatActivity implements View.OnClickListener {
    private TextBinderService.DownLoadBinder downLoadBinder;
    private TextBinderService myService;  //我们自己的service

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_binder);
        Button btn_start = (Button) findViewById(R.id.activity_text_binder_start_service);
        Button btn_stop = (Button) findViewById(R.id.activity_text_binder_stop_service);
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            /*
             * 绑定服务点击事件
             */
            case R.id.activity_text_binder_start_service:
                Intent bindIntent = new Intent(this, TextBinderService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            /*
             * 解除绑定服务点击事件
             */
            case R.id.activity_text_binder_stop_service:
                unbindService(connection);
                break;

            default:
                break;
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        /**
         * 服务解除绑定时候调用
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub

        }
        /**
         * 绑定服务的时候调用
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            //获取到binder类，可随时操作
            downLoadBinder=(TextBinderService.DownLoadBinder) service;
            /*
             * 调用DownLoadBinder的方法实现参数的传递
             */
            downLoadBinder.startDownLoad();
            downLoadBinder.getProgress();
        }
    };
}
