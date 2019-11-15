package com.example.text.mvvm.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.text.MyApplication.MyApplication;
import com.example.text.R;
import com.example.text.helper.DialogHelper;
import com.example.text.mvvm.listener.IListener;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

public class BaseActivity<T extends ViewDataBinding, E extends BaseViewModel> extends AppCompatActivity
        implements IBaseView, IListener {
    protected Context context;
    protected Activity activity;
    protected T dataBinding;
    protected E viewModel;
    protected Toast toast;
    /**
     * 进度框
     */
    protected Dialog loadingDialog;
    /**
     * 确认框
     */
    protected MaterialDialog confirmDialog;
    /**
     * Inquiry对话框
     */
    protected MaterialDialog inquiryDialog;
    private MaterialDialog.SingleButtonCallback confirmCallback = (dialog, which) -> onConfirmCallback();
    private MaterialDialog.SingleButtonCallback cancelCallback = (dialog, which) -> onCancelCallback();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        context = this;
        activity = this;
        loadingDialog = DialogHelper.loadingDialog(context);
        confirmDialog = DialogHelper.confirmDialog(context, confirmCallback, cancelCallback);
        Map<String, Activity> map = ((MyApplication) getApplication()).getLruActivityMaps();
        map.put(activity.getClass().getSimpleName(), activity);

//        initInjector();
        if(viewModel != null){
            viewModel.attachView(this,this);//绑定
            bindViewModel();//注入viewModel
        }
    }

    @Override
    public void showError(String msg) {
        runOnUiThread(() -> {
            if (toast == null) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            }
            toast.setText(msg);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        });
    }

    @Override
    public void showContent(String message, int duration) {
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
    public void showLoading() {
        if (null != loadingDialog && !loadingDialog.isShowing()) {
            runOnUiThread(() -> loadingDialog.show());
        }
    }

    @Override
    public void showLoadingDialog(String loadingText) {
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
    public void showInquiryDialog(String msg) {

    }

    @Override
    public void showConfirmDialog(String title, String message, String buttonConfirm, String buttonCancel) {
        if(confirmDialog != null && !confirmDialog.isShowing()){
            runOnUiThread(() -> {
                MaterialDialog.Builder builder = confirmDialog.getBuilder();
                if (StringUtils.isNotEmpty(title)) {
                    builder.title(title);
                }
                if (StringUtils.isNotEmpty(message)) {
                    builder.content(message);
                }
                if (StringUtils.isNotEmpty(buttonConfirm)) {
                    builder.positiveText(buttonConfirm);
                }
                if (StringUtils.isNotEmpty(buttonCancel)) {
                    builder.negativeText(buttonCancel);
                }
                builder.show();
            });
        }
    }

    @Override
    public void exit() {
        Map<String, Activity> map = ((MyApplication) getApplication()).getLruActivityMaps();
        for (Activity activity : map.values()) {
            activity.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != dataBinding) {
            dataBinding = null;
        }
        if (null != viewModel) {
            viewModel.detachView();
        }
        if (null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }

    @Override
    public void onConfirmCallback() {
        if (null != confirmDialog && confirmDialog.isShowing()) {
            confirmDialog.dismiss();
        }
    }

    @Override
    public void onCancelCallback() {
        if (null != confirmDialog && confirmDialog.isShowing()) {
            confirmDialog.dismiss();
        }
    }
}
