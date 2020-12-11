package com.jzh.basemodule.http.filerequest;


import androidx.annotation.NonNull;

import com.jzh.basemodule.callback.FileDownloadCallback;
import com.jzh.basemodule.rx.FileDownLoadObserver;
import com.jzh.basemodule.utils.FileUtil;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * <p>下载文件请求<p>
 *
 * @author huangqj
 * @version 1.0 , create at 2020/9/9 17:57
 */
public class DownRequest {

    private String url;

    /**
     * 这里之所以需要传url 和 apiService主要是考虑到文件上传、下载应该封装成公共的方法，
     * 传url和 apiService可以在上层自主确定url，公共方法只需接收参数
     *
     * @param url        下载地址，根据retrofit要求传
     * @param apiService ApiService接口类
     */
    public DownRequest(String url) {
        this.url = url;
    }

    /**
     * 执行下载操作
     *
     * @param filePath                 保存的文件路径+文件名
     * @param fileFileDownloadCallback 回调
     */
    public void execute(final String filePath,Observable<ResponseBody> request, FileDownloadCallback<File> fileFileDownloadCallback) {
        final FileDownLoadObserver fileDownLoadObserver = new FileDownLoadObserver(fileFileDownloadCallback);
        request.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // 用于计算任务
                .observeOn(Schedulers.computation())
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(@NonNull ResponseBody responseBody) throws Exception {
                        return fileDownLoadObserver.saveFile(responseBody, filePath);
                    }
                })
                .subscribe(fileDownLoadObserver);
    }

    /**
     * 执行下载操作
     *
     * @param filePath 保存的文件路径+文件名
     */
    public Observable<File> executeObservable(final String filePath,Observable<ResponseBody> request, FileDownloadCallback<File> fileFileDownloadCallback) {
        return request.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // 用于计算任务
                .observeOn(Schedulers.computation())
                .map(responseBody -> FileUtil.saveFile(responseBody.byteStream()
                        , filePath
                        , currentPro -> fileFileDownloadCallback.onProgress(responseBody.contentLength(), currentPro)));
    }
}
