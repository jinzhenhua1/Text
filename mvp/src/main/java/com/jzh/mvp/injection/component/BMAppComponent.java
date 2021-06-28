package com.jzh.mvp.injection.component;


import com.jzh.mvp.injection.module.BMBaseModule;
import com.jzh.mvp.injection.scope.PerApplication;

import dagger.Component;

/**
 * <p></p>
 *
 * @author zhangyz , gdutzyz@126.com
 * @version 1.0 , create at 2019/03/01 10:15
 */
@PerApplication
@Component(modules = {BMBaseModule.class})
public interface BMAppComponent {

//	@SuppressWarnings("unused")
//    Context provideApplicationContext();

}
