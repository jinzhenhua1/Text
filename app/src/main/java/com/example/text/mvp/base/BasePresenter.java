package com.example.text.mvp.base;

import com.example.text.mvp.IBaseContract;

import java.lang.ref.WeakReference;

public class BasePresenter<T extends IBaseContract.IBaseView> implements IBaseContract.IBasePresenter {
    // 防止 Activity 不走 onDestory() 方法，所以采用弱引用来防止内存泄漏
    private WeakReference<T> mViewRef;

    public BasePresenter(T view){
        mViewRef = new WeakReference<T>(view);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestory() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    @Override
    public boolean isViewAttach() {
        return false;
    }

    public T getView() {
        return mViewRef.get();
    }
}
