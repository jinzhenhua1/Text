package com.example.text.dagger;

import javax.inject.Inject;

public class TestBean2 {
    public TestBean3 bean3;

    @Inject
    public TestBean2(TestBean3 bean3){
        this.bean3 = bean3;
    }
}
