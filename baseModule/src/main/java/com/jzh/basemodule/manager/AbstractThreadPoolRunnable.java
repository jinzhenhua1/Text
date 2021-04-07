package com.jzh.basemodule.manager;

/**
 * @author macc
 * @date 2018/10/17
 * @description 用于设置线程名称，方便查找问题
 */
public abstract class AbstractThreadPoolRunnable implements Runnable {

    private String threadName;
    private static final String THREAD_POOL_PREFIX = "";

    public AbstractThreadPoolRunnable(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(THREAD_POOL_PREFIX + threadName);
        doRun();
    }

    /**
     * 子类重写该方法执行线程
     */
    protected abstract void doRun();
}
