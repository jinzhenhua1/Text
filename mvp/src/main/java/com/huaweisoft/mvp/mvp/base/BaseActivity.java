package com.huaweisoft.mvp.mvp.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.huaweisoft.mvp.R;
import com.huaweisoft.mvp.helper.DialogHelper;
import com.huaweisoft.mvp.mvp.listener.IListener;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author 金振华 2019年10月31日14:19:11
 * @param <T>
 */
public abstract class BaseActivity<T extends IBaseContract.IBasePresenter> extends AppCompatActivity
        implements IBaseContract.IBaseView, IListener,View.OnClickListener {
    @Inject
    protected T presenter;
    @Inject
    protected Context context;
    /**
     * 进度框
     */
    protected Dialog loadingDialog;
    protected Toast toast;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
//        presenter = createPresenter();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initViewAndData(savedInstanceState);
        attachView();
        loadingDialog = DialogHelper.loadingDialog(context);
        inquiryDialog = DialogHelper.inquiryDialog(context, this);
        confirmDialog = DialogHelper.confirmDialog(context, confirmCallback, cancelCallback);
    }

    private void attachView() {
        if(null != presenter){
            presenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void showToast(String msg) {
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
    public void showInquiryDialog(String message) {
        if (null != inquiryDialog && !inquiryDialog.isShowing()) {
            runOnUiThread(() -> {
                if (null != inquiryDialog.getCustomView()) {
                    TextView tvMessage = inquiryDialog.getCustomView().findViewById(R.id.dialog_common_inquiry_tv_message);
                    tvMessage.setText(message);
                    inquiryDialog.show();
                } else {
                    Log.e(this.getClass().getName(),"inquiry dialog custom view is null.");
                }
            });
        }
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

    /**
     * 初始化数据
     * @param savedInstanceState
     */
    protected abstract void initViewAndData(Bundle savedInstanceState);

    /**
     * 注入P层对象
     * @return
     */
//    protected abstract T createPresenter();

    /**
     * 设置布局文件ID
     * @return
     */
    protected abstract int getLayoutId();
}
