package com.example.text.view.viewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2019/11/27 9:02
 */
public abstract class BaseFragment extends Fragment {
    //双重判定，保证懒加载
    protected boolean isVisible;//这个，标记，当前Fragment是否可见
    private boolean isPrepared = false;//这个，标记当前Fragment是否已经执行了onCreateView
    //只有两个标记同时满足，才进行数据加载

    protected View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(),container,false);
        initView(view);
        isPrepared = true;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;//这个方法和onCreateView存在先后顺序，如果这个方法先，那么isVisible就会先变成true，但是这个时候，isPrepared还不是true，所以，懒加载不会进行。而要等到onCreateView执行的时候。
            onLazyLoad();
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 懒加载
     */
    private void onLazyLoad() {
        if (isPrepared && isVisible) {
//                    processBar.setVisibility(View.GONE);
            isPrepared = false;//懒加载，只加载一次,这句话要不要，就具体看需求
            initData();
        } else {
            Log.d("onLazyLoadTag","拒绝执行initData，因为条件不满足");
        }
    }

    protected abstract void initView(View view);

    protected abstract int getLayoutId();

    protected abstract void initData();
}
