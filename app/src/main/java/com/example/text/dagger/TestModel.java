package com.example.text.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class TestModel {
    private TestBean3 bean3;

    public TestModel(Context context){
        this.bean3 = new TestBean3(context);
    }

    @Provides
    TestBean3 getBean(){
        return bean3;
    }
}
