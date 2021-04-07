package com.jzh.mvp.injection.module;


import dagger.Module;

/**
 * <p>Dagger2 BaseModule.</p>
 *
 * @author zhangyz , gdutzyz@126.com
 * @version 1.0 , create at 2019/03/01 11:12
 */
@Module
public class BMBaseModule {
	
	public BMBaseModule() {
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
