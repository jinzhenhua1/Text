package com.jzh.basemodule.manager;

import android.util.Log;
import android.util.SparseArray;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时器管理类
 *
 * @author baiaj
 * @date 2018/10/18
 */
public class ScheduledThreadPoolManager {

    private static final String TAG = ScheduledThreadPoolManager.class.getSimpleName();
    private static final int CORE_SIZE = 10;
    private ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;
    private SparseArray<ScheduledFuture> mTaskArray = new SparseArray<>();

    private ScheduledThreadPoolManager() {
        initScheduledThreadPoolExecutor();
    }

    private static class ScheduledThreadPoolManagerHolder {
        private static ScheduledThreadPoolManager instance = new ScheduledThreadPoolManager();
    }

    public static ScheduledThreadPoolManager getInstance() {
        return ScheduledThreadPoolManagerHolder.instance;
    }

    /**
     * 初始化定时器
     */
    private void initScheduledThreadPoolExecutor() {
        mScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(CORE_SIZE);
    }

    /**
     * 添加执行多次的定时任务
     *
     * @param id           任务id
     * @param runnable     任务
     * @param initialDelay 初始延迟
     * @param period       任务间隔
     * @param unit         时间单位
     */
    public void addScheduleTask(int id, AbstractThreadPoolRunnable runnable,
                                long initialDelay,
                                long period,
                                TimeUnit unit) {
        ScheduledFuture<?> scheduledFuture = mScheduledThreadPoolExecutor.scheduleAtFixedRate(runnable, initialDelay, period, unit);
        mTaskArray.put(id, scheduledFuture);

    }

    /**
     * 添加只执行一次的任务
     *
     * @param id       任务id
     * @param runnable 任务
     * @param delay    延迟
     * @param unit     时间单位
     */
    public void addSingleTask(int id, AbstractThreadPoolRunnable runnable, long delay, TimeUnit unit) {
        ScheduledFuture<?> scheduledFuture = mScheduledThreadPoolExecutor.schedule(runnable, delay, unit);
        mTaskArray.put(id, scheduledFuture);
    }

    /**
     * 停止当前定时任务
     *
     * @param id 任务id
     */
    public void removeTask(int id) {
        if (mTaskArray.get(id) != null) {
            boolean result = mTaskArray.get(id).cancel(true);
            if (result) {
                mTaskArray.remove(id);
                Log.d(TAG, "停止任务成功,id:" + id);
            } else {
                Log.d(TAG, "停止任务失败,id:" + id);
            }
        } else {
            Log.d(TAG, "当前无该任务,id:" + id);
        }
    }

}
