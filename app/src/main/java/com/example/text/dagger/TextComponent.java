package com.example.text.dagger;

import dagger.Component;

@Component(modules = {TestModel.class},dependencies = TextComponent2.class)
public interface TextComponent {
    void inject(DaggerActivity activity);


}
