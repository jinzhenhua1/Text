package com.jzh.basemodule.utils;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池 工具类
 * @author jinzhenhua
 * @version 1.0 , create at 2020/4/8 16:47
 */
public class ThreadPoolUtil {

    /**
     * 核心线程池的数量，同时能够执行的线程数量，当前设备可用处理器核心数*2 + 1
     */
    private int corePoolSize = 5;
    /**
     * 最大线程池数量，表示当缓冲队列满的时候能继续容纳的等待任务的数量
     */
    private int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
    /**
     * 存活时间5分钟
     */
    private long keepAliveTime = 5;
    private ThreadPoolExecutor mMultipleExecutor;
    private ThreadPoolExecutor mSingleExecutor;

    private ThreadPoolUtil() {
        //参1:核心线程数;参2:最大线程数;参3:线程休眠时间;参4:时间单位;参5:线程队列;参6:生产线程的工厂;参7:线程异常处理策略
        mMultipleExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(),
                //创建线程的工厂
                new DefaultThreadFactory(Thread.NORM_PRIORITY, "pool-"),
                //用来对超出maximumPoolSize的任务的处理策略
                new ThreadPoolExecutor.DiscardPolicy()
        );

        mSingleExecutor = new ThreadPoolExecutor(
                1,
                1,
                //表示的是maximumPoolSize当中等待任务的存活时间
                keepAliveTime,
                TimeUnit.MINUTES,
                //缓冲队列，用于存放等待任务，Linked的先进先出
                new LinkedBlockingQueue<>(),
                //创建线程的工厂
                new DefaultThreadFactory(Thread.NORM_PRIORITY, "singPool-"),
                //用来对超出maximumPoolSize的任务的处理策略
                new ThreadPoolExecutor.DiscardPolicy());
    }

    private static class Inner {
        static ThreadPoolUtil THREAD_POOL_UTIL = new ThreadPoolUtil();
    }

    public static ThreadPoolUtil getInstance() {
        return Inner.THREAD_POOL_UTIL;
    }

    /**
     * 执行任务
     *
     * @param runnable 任务
     */
    public void execute(Runnable runnable) {
        if (runnable != null) {
            mMultipleExecutor.execute(runnable);
        }
    }

    /**
     * 移除任务
     *  @param runnable 任务
     */
    public void remove(Runnable runnable) {
        if (runnable != null) {
            mMultipleExecutor.remove(runnable);
        }
    }

    /**
     * 执行任务
     *
     * @param runnable 任务
     */
    public void executeSingle(Runnable runnable) {
        if (runnable != null) {
            mSingleExecutor.execute(runnable);
        }
    }

    /**
     * 移除任务
     *  @param runnable 任务
     */
    public void removeSingle(Runnable runnable) {
        if (runnable != null) {
            mSingleExecutor.remove(runnable);
        }
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        /**
         * 线程池的计数
         */
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

        /**
         * 线程的计数
         */
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final ThreadGroup group;
        private final String namePrefix;
        private final int threadPriority;

        DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
            this.threadPriority = threadPriority;
            this.group = Thread.currentThread().getThreadGroup();
            namePrefix = threadNamePrefix + POOL_NUMBER.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            t.setPriority(threadPriority);
            return t;
        }
    }
}
