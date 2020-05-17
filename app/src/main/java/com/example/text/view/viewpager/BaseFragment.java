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
 *  懒加载 fragment
 * @author jinzhenhua
 * @version 1.0  ,create at:2019/11/27 9:02
 */
public abstract class BaseFragment extends Fragment {
    //双重判定，保证懒加载
    protected boolean isVisible;//这个，标记，当前Fragment是否可见，防止重复分发事件，如可见时还重复加载数据
    private boolean isViewCreate = false;//view
    //只有两个标记同时满足，才进行数据加载

    protected View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(),null);
        initView(view);
        isViewCreate = true;
        if(getUserVisibleHint()){
            dispatchVisibleHint(true);//加载默认第一个页面时，setUserVisibleHint会在onCreateView前执行，所以会不执行页面的加载
        }
        return view;
    }

    /**
     * 在viewPager中会调用该方法，来设置fragment是否可见（即状态改变时调用，）
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //当VIEW已经初始化，才能分发事件
        if(isViewCreate){
            if(isVisibleToUser && !isVisible){//本来不可见时，才会分发加载事件
                dispatchVisibleHint(true);
            }else if(!isVisibleToUser && isVisible){
                dispatchVisibleHint(false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isVisible && getUserVisibleHint() && !isHidden()){
            dispatchVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(isVisible && getUserVisibleHint()){
            dispatchVisibleHint(false);//跳转Activity时或者回到桌面时需要停止加载
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected abstract void initView(View view);

    protected abstract int getLayoutId();


    /**
     * 事件分发
     * @param visibleState
     */
    private void dispatchVisibleHint(boolean visibleState){
        if(isVisible == visibleState){///在跨table显示时会分发多次事件
            return;
        }

        isVisible = visibleState;
        if(visibleState){
            onFragmentLoad();
        }else{
            onFragmentLoadStop();
        }
    }

    /**
     * 加载数据
     */
    public void onFragmentLoad(){

    }

    /**
     * 停止加载
     */
    public void onFragmentLoadStop(){

    }
}
