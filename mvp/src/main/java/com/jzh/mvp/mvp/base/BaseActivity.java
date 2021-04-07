package com.jzh.mvp.mvp.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jzh.mvp.R;
import com.jzh.mvp.helper.DialogHelper;
import com.jzh.mvp.mvp.listener.IListener;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * @param <T>
 * @author 金振华 2019年10月31日14:19:11
 */
public abstract class BaseActivity<T extends IBaseContract.IBasePresenter> extends AppCompatActivity
        implements IBaseContract.IBaseView, IListener, View.OnClickListener,IBase {
    @Inject
    protected T presenter;

    protected Context context;
    /**
     * 进度框
     */
    protected Dialog loadingDialog;
    protected Toast toast;


    Unbinder unbinder;
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

    /**
     * 仿IOS的弹窗
     */
    private PromptDialog promptDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        inquiryDialog = DialogHelper.inquiryDialog(context, this);
        confirmDialog = DialogHelper.confirmDialog(context, confirmCallback, cancelCallback);
//        presenter = createPresenter();
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initInjector();
        attachView();
        initViewAndData(savedInstanceState);
    }

    private void attachView() {
        if (null != presenter) {
            presenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.onDestroy();
        }
        unbinder.unbind();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(presenter != null){
            presenter.onStop();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
            toast.getView().setBackgroundColor(Color.GRAY);
        }
        toast.show();
    }

    @Override
    public void showProgressLoadingWithMessage(String loadingText) {
        if(loadingDialog == null){
            loadingDialog = DialogHelper.loadingDialog(context);
        }
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
    public void showProgressLoading() {
        if(loadingDialog == null){
            loadingDialog = DialogHelper.loadingDialog(context);
        }
        if (null != loadingDialog && !loadingDialog.isShowing()) {
            runOnUiThread(() -> loadingDialog.show());
        }
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
    public void dismissProgressLoading() {
        if (null != loadingDialog && loadingDialog.isShowing()) {
            runOnUiThread(() -> loadingDialog.dismiss());
        }
    }


    @Override
    public void showInquiryDialog(String message) {
        if (null != inquiryDialog && !inquiryDialog.isShowing()) {
            runOnUiThread(() -> {
                if (null != inquiryDialog.getCustomView()) {
                    TextView tvMessage = inquiryDialog.getCustomView().findViewById(R.id.dialog_common_inquiry_tv_message);
                    tvMessage.setText(message);
                    inquiryDialog.show();
                } else {
                    Log.e(this.getClass().getName(), "inquiry dialog custom view is null.");
                }
            });
        }
    }

    @Override
    public void showConfirmDialog(String title, String message, String buttonConfirm, String buttonCancel) {
        if (confirmDialog != null && !confirmDialog.isShowing()) {
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
    public void onClick(View v) {
        if (R.id.dialog_common_inquiry_btn_confirm == v.getId()) {
            onInquiryConfirm();
        } else if (R.id.dialog_common_inquiry_btn_cancel == v.getId()) {
            onInquiryCancel();
        }
    }

    @Override
    public void onInquiryConfirm() {
        if (null != inquiryDialog && inquiryDialog.isShowing()) {
            inquiryDialog.dismiss();
        }
    }

    @Override
    public void onInquiryCancel() {
        if (null != inquiryDialog && inquiryDialog.isShowing()) {
            inquiryDialog.dismiss();
        }
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


    protected void startActivity(Class clazz) {
        super.startActivity(new Intent(this,clazz));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isAllGranted = true;
        for (int grantResult : grantResults) {
            isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
        }
        afterRequestPermission(requestCode, isAllGranted);
    }

    protected void afterRequestPermission(int requestCode, boolean isAllGranted) {

    }

    /**
     * 加载动画
     *
     * @param type 动画类型 1成功   2 失败 3加载中
     * @param text 内容
     */
    public void showPromptDialog(int type, String text) {
        if (promptDialog == null) {
            promptDialog = new PromptDialog(this);
        }
        switch (type) {
            case 1:
                promptDialog.showSuccess(text);
                break;
            case 2:
                promptDialog.showError(text);
                break;
            case 3:
                promptDialog.showLoading(text);
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoading(String content) {
        showPromptDialog(3,content);
    }

    @Override
    public void hideLoading() {
        if (promptDialog != null) {
            promptDialog.dismiss();
        }
    }
}
