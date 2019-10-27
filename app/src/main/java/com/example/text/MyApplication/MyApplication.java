package com.example.text.MyApplication;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.xiaoyehai.landsurvey.greendao.DaoMaster;
import com.xiaoyehai.landsurvey.greendao.DaoSession;

public class MyApplication extends Application {

    public static final String DB_NAME = "text.db";

    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        initGreenDao();

    }

    private void initGreenDao(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }
}
