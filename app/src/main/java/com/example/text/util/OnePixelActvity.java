package com.example.text.util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class OnePixelActvity extends Activity {

    public static final String TAG = OnePixelActvity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        Window window = getWindow();
        //放在左上角
        window.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams attributes = window.getAttributes();
        //宽高设计为1个像素
        attributes.width = 1;
        attributes.height = 1;
        //起始坐标
        attributes.x = 0;
        attributes.y = 0;
        window.setAttributes(attributes);
//        boolean screenon = BackFogetUtil.isScreenon(this);
//        if (screenon) {
//            finish();
//        } else {
//            OnePixelLive.setActivity(this);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }
}