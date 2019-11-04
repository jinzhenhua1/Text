package com.example.text.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class TestModel {
    private TestBean3 bean3;
//    private TestBean bean;
//    private TestBean2 bean2;

    public TestModel(Context context){
        this.bean3 = new TestBean3(context);
//        this.bean = new TestBean();
//        this.bean2 = new TestBean2(bean3);
    }

    @Provides
    TestBean3 getBean3(){
        return bean3;
    }

//    @Provides
//    TestBean getBean(){
//        return bean;
//    }
//
//    @Provides
//    TestBean2 getBean2(){
//        return bean2;
//    }
}
