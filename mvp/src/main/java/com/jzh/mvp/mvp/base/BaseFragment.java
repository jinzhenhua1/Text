package com.jzh.mvp.mvp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jzh.mvp.R;
import com.jzh.mvp.mvp.listener.IListener;

import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author jinzhenhua
 * @version 1.0  ,create at:2021/3/10 19:24
 */
public abstract class BaseFragment<T extends IBaseContract.IBasePresenter> extends Fragment implements
        IBaseContract.IBaseView, IBase, IListener {
    @Inject
    protected T presenter;
    protected BaseActivity baseActivity;
    Unbinder unbinder;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        baseActivity = (BaseActivity) getActivity();
    }

    /**
     * 在onCreateView 后面执行
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInjector();
        attachView();
        initViewAndData(savedInstanceState);
    }

    private void attachView() {
        if (null != presenter) {
            presenter.attachView(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        if(null != presenter){
            presenter.onDestroy();
        }
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void showToast(String msg) {
        baseActivity.showToast(msg);
    }

    @Override
    public void showProgressLoadingWithMessage(String loadingText) {
        baseActivity.showProgressLoadingWithMessage(loadingText);
    }

    @Override
    public void showProgressLoading() {
        baseActivity.showProgressLoading();
    }

    @Override
    public void showContent(String message, int duration) {
        baseActivity.showContent(message,duration);
    }

    @Override
    public void dismissProgressLoading() {
        baseActivity.dismissProgressLoading();
    }


    @Override
    public void showInquiryDialog(String message) {
        baseActivity.showInquiryDialog(message);
    }

    @Override
    public void showConfirmDialog(String title, String message, String buttonConfirm, String buttonCancel) {
        baseActivity.showConfirmDialog(title,message,buttonConfirm,buttonCancel);
    }

    @Override
    public void onInquiryConfirm() {
        baseActivity.onInquiryConfirm();
    }

    @Override
    public void onInquiryCancel() {
        baseActivity.onInquiryCancel();
    }


    @Override
    public void onConfirmCallback() {
        baseActivity.onConfirmCallback();
    }

    @Override
    public void onCancelCallback() {
        baseActivity.onCancelCallback();
    }


    @Override
    public void showLoading(String content) {
        baseActivity.showLoading(content);
    }

    @Override
    public void hideLoading() {
        baseActivity.hideLoading();
    }


    protected void startActivity(Class clazz) {
        super.startActivity(new Intent(getContext(),clazz));
    }

}
