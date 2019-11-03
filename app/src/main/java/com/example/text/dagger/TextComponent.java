package com.example.text.dagger;

import dagger.Component;

@Component(modules = TestModel.class)
public interface TextComponent {
    void inject(DaggerActivity activity);
}
