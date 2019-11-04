package com.example.text.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class TestModel2 {
    private TestBean4 bean4;

    public TestModel2(){
        this.bean4 = new TestBean4();
    }

    @Provides
    TestBean4 getBean(){
        return bean4;
    }

}
