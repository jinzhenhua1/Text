package com.example.text.dagger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.text.R;

import javax.inject.Inject;

public class DaggerActivity extends AppCompatActivity {
    private String TAG = "DaggerActivity";

    @Inject
    //使用@Inject时，不能用private修饰符修饰类的成员属性
    TestBean bean;


    @Inject
    TestBean2 bean2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);

        DaggerTextComponent.builder()
                //手动设置参数
                .testModel(new TestModel(this))
                .build().inject(this);
        Toast.makeText(this,bean.anme,Toast.LENGTH_SHORT).show();

        Log.d(TAG,String.valueOf(bean2.bean3.context == this));
    }
}
