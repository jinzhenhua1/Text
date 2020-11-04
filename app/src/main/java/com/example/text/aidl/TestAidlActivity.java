package com.example.text.aidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.text.R;
import com.jzh.aidlservice.aidl.Person;
import com.jzh.aidlservice.aidl.PersonController;

import java.util.List;

public class TestAidlActivity extends AppCompatActivity {
    private final String TAG = "Client";
    private boolean connected;
    private PersonController personController;
    private List<Person> personList;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "已连接");
            personController = PersonController.Stub.asInterface(service);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "已断开连接");
            connected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_aidl);
        findViewById(R.id.btn_getBookList).setOnClickListener(clickListener);
        findViewById(R.id.btn_addBook_inOut).setOnClickListener(clickListener);

        bindService();
    }

    private void bindService() {

//        Intent intent = getPackageManager().getLaunchIntentForPackage("com.jzh.aidlservice");
        Intent intent = new Intent();
        intent.setPackage("com.jzh.aidlservice");
        intent.setAction("com.jzh.aidlservice.aidl.AIDLService");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connected) {
            unbindService(serviceConnection);
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_getBookList:
                    if (connected) {
                        try {
                            personList = personController.getBookList();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        log();
                    }
                    break;
                case R.id.btn_addBook_inOut:
                    if (connected) {
                        Person book = new Person("这是一个新人 InOut");
                        try {
                            personController.addBookInOut(book);
                            Log.e(TAG, "向服务器以InOut方式添加了一个新人");
                            Log.e(TAG, "新书名：" + book.getName());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    private void log() {
        for (Person person : personList) {
            Log.e(TAG, person.toString());
        }
    }
}