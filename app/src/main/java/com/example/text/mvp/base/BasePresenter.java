package com.example.text.mvp.base;

import com.example.text.mvp.IBaseContract;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<T extends IBaseContract.IBaseView,P extends IBaseContract.IBaseModel> implements IBaseContract.IBasePresenter {
    // 防止 Activity 不走 onDestory() 方法，所以采用弱引用来防止内存泄漏
    private WeakReference<T> mViewRef;
    protected P model;


    public BasePresenter(T view){
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
    public void onDestory() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
        if(model != null){
            //清除调用中的接口
            model.onDestory();
        }
    }

    @Override
    public boolean isViewAttach() {
        return false;
    }

    public T getView() {
        return mViewRef.get();
    }


    /**
     * 拦截回调方法，中间插入取消loading操作
     * @param <T>
     */
    protected class ApiCallbackWrapper<T> implements HttpResponseListener<T>{
        private HttpResponseListener callback;
        private boolean showLoading;

        public ApiCallbackWrapper(HttpResponseListener callback,boolean showLoading){
            this.callback = callback;
            this.showLoading = showLoading;
        }

        @Override
        public void onSuccess(Object tag, T o) {
            callback.onSuccess(tag,o);
            if(showLoading){
                getView().dismissLoading();
            }
        }

        @Override
        public void onFailure(Throwable throwable) {
            callback.onFailure(throwable);
            if(showLoading){
                getView().dismissLoading();
            }
        }
    }
}
