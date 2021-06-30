package com.jzh.basemodule.manager;

import android.app.Activity;

import java.util.LinkedList;

/**
 * activity 管理器
 * @author jinzhenhua
 * @version 1.0  ,create at:2021/4/28 12:33
 */
public class ActivityManager {
    private static ActivityManager instance;

    private LinkedList<Activity> allActivity = new LinkedList<>();

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        if(instance == null){
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 新的activity
     * @param activity
     */
    public void addActivity(Activity activity){
        allActivity.add(activity);
    }

    /**
     * activity 关闭
     * @param activity
     */
    public void popActivity(Activity activity){
        if(allActivity.contains(activity)){
            allActivity.remove(activity);
        }
    }

    /**
     * 关闭所有的页面
     */
    public void finishAllActivity(){
        for(Activity activity : allActivity){
            activity.finish();
        }
        allActivity.clear();
    }

    public LinkedList<Activity> getAllActivity() {
        return allActivity;
    }

    public void setAllActivity(LinkedList<Activity> allActivity) {
        this.allActivity = allActivity;
    }
}
