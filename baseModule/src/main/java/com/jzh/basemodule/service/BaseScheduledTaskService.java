package com.jzh.basemodule.service;

import android.app.Service;


import com.jzh.basemodule.manager.AbstractThreadPoolRunnable;
import com.jzh.basemodule.manager.ScheduledThreadPoolManager;

import java.util.concurrent.TimeUnit;

/**
 * @author macc
 * @date 2019/1/8
 * @description 定时器服务，多个子类的情况，通过taskId区别任务
 */

public abstract class BaseScheduledTaskService extends Service {

    public BaseScheduledTaskService() {
    }

    /**
     * 启动定时任务
     *
     * @param taskId    任务id，值可以随意填写，为了标识任务，任务id
     * @param treadName 所在线程名称
     * @param interval  定时间隔
     */
    protected void addScheduledTask(final int taskId, String treadName, int interval) {
        ScheduledThreadPoolManager.getInstance().
                addScheduleTask(taskId, new AbstractThreadPoolRunnable(treadName) {
                    @Override
                    protected void doRun() {
                        doScheduledTask(taskId);
                    }
                }, 0, interval, TimeUnit.MILLISECONDS);
    }

    /**
     * 移除定时任务
     *
     * @param taskId 任务id
     */
    protected void removeScheduleTask(int taskId) {
        ScheduledThreadPoolManager.getInstance().removeTask(taskId);
    }


    /**
     * 处理定时任务
     *
     * @param taskId 任务id
     */
    protected abstract void doScheduledTask(int taskId);

}
