package com.jzh.mvp.mvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

/**
 * @author jinzhenhua
 * @version 1.0  ,create at:2021/3/10 19:26
 */
interface IBase {


    /**
     * 返回页面视图的ID，继承BaseActivity类的Activity要返回正确的ID值
     */
    @LayoutRes
    int getLayoutId();


    /**
     * 注入Dagger2组件
     */
    void initInjector();


    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    void initViewAndData(Bundle savedInstanceState);
}
