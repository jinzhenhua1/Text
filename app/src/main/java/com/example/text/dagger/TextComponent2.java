package com.example.text.dagger;

import dagger.Component;

@Component(modules = {TestModel2.class})
public interface TextComponent2 {


//    TestBean getTestBean();
//    TestBean2 getTestBean2();
//    TestBean3 getTestBean3();
    TestBean4 getTestBean4();
}
