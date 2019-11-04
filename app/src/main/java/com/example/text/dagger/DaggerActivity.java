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

    //构造方法中需要依赖其他类，但其他类没有@Inject注解，需要通过model类来提供依赖
    @Inject
    TestBean2 bean2;

    //构造方法没有@Inject注解，需要通过model来提供
    @Inject
    TestBean3 bean3;

    @Inject
    TestBean4 bean4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);

        /**
         * 为其他的Component提供参数。其中依赖的TestModel2 含有无参构造方法，所以不必显式去创建
         */
        TextComponent2 component2 = DaggerTextComponent2.builder()
                .build();

        DaggerTextComponent.builder()
                //手动设置参数。Component中有依赖的model，但是无需其他依赖就可以自动创建的无需手动创建model对象
                .testModel(new TestModel(this))
                .textComponent2(component2)
                .build()
                .inject(this);

        Toast.makeText(this,bean.anme,Toast.LENGTH_SHORT).show();

        Log.d(TAG,String.valueOf(bean2.bean3.context == this));
        Log.d(TAG,bean4.name);
        Log.d(TAG,bean3.name);
    }
}
