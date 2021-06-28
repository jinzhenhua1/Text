package com.jzh.mvp.mvp.base;


import com.jzh.basemodule.callback.HttpResponseListener;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/5/26 17:21
 */
public class BasePresenter<T extends IBaseContract.IBaseView,P extends IBaseContract.IBaseModel> implements IBaseContract.IBasePresenter<T> {
    /**
     *  防止 Activity 不走 onDestroy() 方法，所以采用弱引用来防止内存泄漏
     */
    private WeakReference<T> mViewRef;

    protected P model;

    public BasePresenter(P model) {
        this.model = model;
    }

    @Override
    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    @Override
    public void onStop() {
        if(model != null){
            //清除调用中的接口
            model.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
        if(model != null){
            //清除调用中的接口
            model.onDestory();
            model = null;
        }
    }

    public T getView() {
        return mViewRef.get();
    }

    /**
     * 获取回调接口的实现，经过一层封装，默认显示加载进度框
     * @param callback
     * @return
     */
    public ApiCallbackWrapper getApiCallBackWithLoading(HttpResponseListener callback,String context){
        return new ApiCallbackWrapper(callback,context);
    }

    /**
     * 拦截回调方法，中间插入oading操作
     * @param <T>
     */
    protected class ApiCallbackWrapper<T> implements HttpResponseListener<T>{
        private HttpResponseListener callback;
        private boolean showLoading;

        public ApiCallbackWrapper(HttpResponseListener callback,String context){
            this.callback = callback;
            getView().showLoading(context);
        }

        @Override
        public void onSuccess(T o) {
            callback.onSuccess(o);
            getView().hideLoading();
        }

        @Override
        public void onFailure(Throwable throwable) {
            callback.onFailure(throwable);
            getView().hideLoading();
        }
    }

}
