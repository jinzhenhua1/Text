package com.jzh.pusher;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;


import com.jzh.pusher.utils.Logger;

import java.util.List;

public class MiProcessCheck {
    /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Logger.d(String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Logger.d(String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param mContext
     * @param className  使用.class.getCanoicalName获取
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
    /**
     * 判断当前应用任务栈是否为空
     *
     *
     * @param context
     */
    public static boolean isTaskEmpty(Context context) {

        /**获取ActivityManager*/
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        /**获得当前运行的task(任务)*/
        List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
            Logger.d("num:"+taskInfo.numActivities+"topclassname:"+taskInfo.topActivity.getClassName());
            if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断本应用是否已经位于最前端
     *
     * @param context
     * @return 本应用已经位于最前端时，返回 true；否则返回 false
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();
        /**枚举进程*/
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfoList) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(context.getApplicationInfo().processName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将本应用置顶到最前端
     * 当本应用位于后台时，则将它切换到最前端
     *
     * @param context
     */
    public static void setTopApp(Context context) {
        if (!isRunningForeground(context)) {
            /**获取ActivityManager*/
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

            /**获得当前运行的task(任务)*/
            List<ActivityManager.RunningTaskInfo> taskInfoList = activityManager.getRunningTasks(100);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                /**找到本应用的 task，并将它切换到前台*/
                if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
                    activityManager.moveTaskToFront(taskInfo.id, 0);
                    break;
                }

            }
        }
    }

    /**
     * 重新打开app
     * @param context
     */
    public static void reOpenApp(Context context, String data, String brand){

        Intent launchIntent = context.getPackageManager().
                getLaunchIntentForPackage(context.getPackageName());
//        launchIntent.putExtra(HWPushActivity._data, data);
//        launchIntent.putExtra(HWPushActivity._type, brand);
        launchIntent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(launchIntent);
    }


}
