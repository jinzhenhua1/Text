package com.example.text.mvp.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.text.R;
import com.example.text.helper.DialogHelper;
import com.example.text.mvp.IBaseContract;

/**
 * @author 金振华 2019年10月31日14:19:11
 * @param <T>
 */
public abstract class BaseMvpActivity<T extends IBaseContract.IBasePresenter> extends Activity implements IBaseContract.IBaseView {
    protected T presenter;
    protected Context context;
    /**
     * 进度框
     */
    protected Dialog loadingDialog;
    protected Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        presenter = createPresenter();
        setContentView(getLayoutId());
        initViewAndData(savedInstanceState);
        loadingDialog = DialogHelper.loadingDialog(context);
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
    public void showLoadingWithMessage(String loadingText) {
        if (null != loadingDialog && !loadingDialog.isShowing()) {
            runOnUiThread(() -> {
                if (null != loadingDialog.getWindow()) {
                    TextView tvLoad = loadingDialog.getWindow().findViewById(R.id.tv_load_dialog);
                    tvLoad.setText(loadingText);
                    loadingDialog.show();
                }
            });
        }
    }

    @Override
    public void showLoading() {
        if (null != loadingDialog && !loadingDialog.isShowing()) {
            runOnUiThread(() -> loadingDialog.show());
        }
    }

    @Override
    public void showContent(String message,int duration) {
        runOnUiThread(() -> {
            if (toast == null) {
                toast = Toast.makeText(context, message, duration);
            }
            toast.setText(message);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
    }

    @Override
    public void dismissLoading() {
        if (null != loadingDialog && loadingDialog.isShowing()) {
            runOnUiThread(() -> loadingDialog.dismiss());
        }
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
