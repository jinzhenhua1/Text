package com.jzh.basemodule.http.rx;



import com.jzh.basemodule.callback.FileDownloadCallback;
import com.jzh.basemodule.utils.FileUtil;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * <p>文件下载 观察者</p>
 *
 * @author huangqj
 * @version 1.0 , create at 2020/9/9 17:57
 */
public class FileDownLoadObserver<T> implements Observer<T> {

    private FileDownloadCallback fileDownloadCallback;

    public FileDownLoadObserver(FileDownloadCallback fileDownloadCallback) {
        this.fileDownloadCallback = fileDownloadCallback;
    }

    @Override
    public void onNext(T t) {
        fileDownloadCallback.onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        fileDownloadCallback.onFail(e);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    /**
     * 将文件写入本地
     *
     * @param responseBody 请求结果全体
     * @param filePath     文件路径（文件路径+文件名+后缀） 例如xxx/xxx/xxx/test.txt
     * @return 写入完成的文件
     * @throws IOException IO异常
     */
    public File saveFile(ResponseBody responseBody, String filePath) throws IOException {
        return FileUtil.saveFile(responseBody.byteStream(),
                filePath,
                currentPro -> fileDownloadCallback.onProgress(currentPro, responseBody.contentLength()));
    }
}
