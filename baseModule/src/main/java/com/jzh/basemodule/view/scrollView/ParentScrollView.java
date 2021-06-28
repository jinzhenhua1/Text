package com.jzh.basemodule.view.scrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/7/15 16:49
 */
public class ParentScrollView extends ScrollView {
    private static final String TAG = ParentScrollView.class.getSimpleName();

    private UpScrollView upScrollView;
    private BottomScrollView bottomScrollView;
    private boolean init = false;
    private int mScreenHeight = 0;

    /**
     * 当前View内子控件高度
     */
    private int mChildH;

    /**
     * 当前View滑动距离
     */
    private int mScrollY;

    public ParentScrollView(Context context) {
        super(context);
        init(context);
    }

    public ParentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ParentScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //获取屏幕高度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if(!init){
//            LinearLayout parentView = (LinearLayout) getChildAt(0);
//            //获得内部的两个子view
//            bottomScrollView = (BottomScrollView) parentView.getChildAt(1);
//            //并设定其高度为屏幕高度
//            bottomScrollView.getLayoutParams().height = mScreenHeight;
//            init = true;
//        }
        Log.e("ParentScrollView","调用onMeasure前");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("ParentScrollView","调用onMeasure后");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean flag = super.onInterceptTouchEvent(ev);
        Log.e("ParentScrollView","父view的onInterceptTouchEvent返回值：" + flag);
        return flag;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        // 只有一个子view，所以这里其实就是拿到了整个
        mChildH = this.getChildAt(0).getMeasuredHeight();

        int translateY = mChildH - mScrollY;

        //滑动到了底部（子view的整个高度 - 左上角Y轴坐标 = 自身可现实区域的高度）
        if(translateY == getHeight()){
            //设置自身不拦截事件
            requestDisallowInterceptTouchEvent(true);
        }

        Log.e(TAG,"mChildH：" + mChildH + ",translateY:" + translateY + ",mScrollY:" + mScrollY + ",getHeight:" + getHeight());


        return super.onTouchEvent(ev);

    }




    /**
     * 展示出来的画面的左上角，在整个View中的坐标
     * @param l     X轴坐标
     * @param t     Y轴坐标
     * @param oldl  旧的X轴
     * @param oldt  旧的Y轴
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollY = t;
    }


}
