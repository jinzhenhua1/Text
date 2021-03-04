package com.huaweisoft.mvp.injection.component;

import android.content.Context;


import com.huaweisoft.mvp.injection.module.BaseModule;
import com.huaweisoft.mvp.injection.scope.PerApplication;

import dagger.Component;

/**
 * <p></p>
 *
 * @author zhangyz , gdutzyz@126.com
 * @version 1.0 , create at 2019/03/01 10:15
 */
@PerApplication
@Component(modules = {BaseModule.class})
public interface AppComponent {

//	@SuppressWarnings("unused")
//    Context provideApplicationContext();

}
