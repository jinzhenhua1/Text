package com.jzh.basemodule.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author macc
 * @date 2016-06-01
 */
public class ThreadManager {

    private int coreSize = Runtime.getRuntime().availableProcessors();
    private int maxSize = coreSize * 2 + 1;
    private long keepAliveTime = 1000L;


    private ExecutorService mExecutorService;
    private static final ThreadManager INSTANCE = new ThreadManager();
    private List<Runnable> mRunnables = new ArrayList<>();

    private ThreadManager() {
        initExecutorService();
    }

    public static ThreadManager getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化executorService
     */
    private void initExecutorService() {
        mExecutorService = new ThreadPoolExecutor(maxSize, maxSize, keepAliveTime, TimeUnit.SECONDS,new LinkedBlockingQueue());
    }

    /**
     * 添加并执行线程
     *
     * @param runnable 线程
     */
    public void executeRunnable(AbstractThreadPoolRunnable runnable) {
        mRunnables.add(runnable);
        mExecutorService.execute(runnable);
    }

    public void executeRunnable(Runnable runnable) {
        mRunnables.add(runnable);
        mExecutorService.execute(runnable);
    }

    /**
     * 停止所有线程
     */
    public void shutdownExecutorService() {
        mExecutorService.shutdownNow();
        mRunnables.clear();
        //TODO:销毁线程之后，这里为什么重新创建
        mExecutorService = Executors.newCachedThreadPool();
    }

    public ExecutorService getExecutorService() {
        return mExecutorService;
    }
}