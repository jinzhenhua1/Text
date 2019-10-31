package com.example.text.mvp;

import android.app.Activity;
import android.os.Bundle;

abstract class BaseMvpActivity<T extends IBaseContract.IBasePresenter> extends Activity implements IBaseContract.IBaseView {
    private T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        setContentView(getLayoutId());
        initViewAndData(savedInstanceState);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showLoading(String loadingText) {

    }

    @Override
    public void showContent() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showDialog(String msg) {

    }

    /**
     * 初始化数据
     * @param savedInstanceState
     */
    abstract void initViewAndData(Bundle savedInstanceState);

    abstract T createPresenter();

    abstract int getLayoutId();
}
