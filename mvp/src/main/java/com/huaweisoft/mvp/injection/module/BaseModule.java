package com.huaweisoft.mvp.injection.module;

import android.content.Context;


import com.huaweisoft.mvp.injection.scope.PerApplication;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Dagger2 BaseModule.</p>
 *
 * @author zhangyz , gdutzyz@126.com
 * @version 1.0 , create at 2019/03/01 11:12
 */
@Module
public class BaseModule {
	
	public BaseModule() {
		// Default non-parameters constructor
	}
	
//	@Provides
//	@PerApplication
//	DaoSession provideDaoSession(Context context) {
//		return DaoManager.getInstance(context).getDaoSession();
//	}

//	@Provides
//	@PerApplication
//	Context provideApplicationContext() {
//		return BaseApplication.application.getApplicationContext();
//	}
}
