package com.jzh.basemodule.callback;

/**
 * <p>文件下载 回调</p>
 *
 * @author huangqj
 * @version 1.0 , create at 2020/9/9 17:57
 */

public interface FileDownloadCallback<T> {
    /**
     * 执行成功
     *
     * @param t obj
     */
    void onSuccess(T t);

    /**
     * 执行失败
     *
     * @param throwable 异常
     */
    void onFail(Throwable throwable);

    /**
     * 进度
     *
     * @param current 当前进度
     * @param total   总大小
     */
    void onProgress(long current, long total);
}
