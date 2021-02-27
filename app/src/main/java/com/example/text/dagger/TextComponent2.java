package com.example.text.dagger;

import dagger.Component;

@Component(modules = {TestModel2.class})
public interface TextComponent2 {

    /**
     * 通过依赖别的Component中的 module时，需要在被依赖的Component 中提供get方法，方法名不限，参数要对应
     * @return
     */
    TestBean4 getTestBean4();
}
