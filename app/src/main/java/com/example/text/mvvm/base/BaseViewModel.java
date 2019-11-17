package com.example.text.mvvm.base;

import android.content.Context;

public abstract class BaseViewModel {
    protected Context mContext;
    protected IBaseView mBaseView;

    public void attachView(Context context, IBaseView baseView) {
        this.mContext = context;
        this.mBaseView = baseView;
        init();
    }

    public void detachView() {
        this.mContext = null;
        this.mBaseView = null;
        destroy();
    }

    public abstract void init();

    public abstract void destroy();


}
