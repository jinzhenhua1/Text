package com.example.text.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.text.mvp.IBaseContract;

/**
 * @author 金振华 2019年10月31日14:19:11
 * @param <T>
 */
public abstract class BaseMvpActivity<T extends IBaseContract.IBasePresenter> extends Activity implements IBaseContract.IBaseView {
    protected T presenter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        presenter = createPresenter();
        setContentView(getLayoutId());
        initViewAndData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestory();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
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
    protected abstract void initViewAndData(Bundle savedInstanceState);

    /**
     * 注入P层对象
     * @return
     */
    protected abstract T createPresenter();

    /**
     * 设置布局文件ID
     * @return
     */
    protected abstract int getLayoutId();
}
