package com.jzh.basemodule.view.scrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author jinzhenhua
 * @version 1.0  ,create at:2021/3/23 15:47
 */
public class BottomScrollView extends ScrollView {
    private static final String TAG = BottomScrollView.class.getSimpleName();

    /**
     * 上一次的坐标
     */
    private float mLastY;
    /**
     * 当前View滑动距离
     */
    private int mScrollY;
    /**
     * 之前的Y轴距离
     */
    private int oldY;
    /**
     * 当前View内子控件高度
     */
    private int mChildH;


    public BottomScrollView(Context context) {
        super(context);
    }

    public BottomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //控件的总高度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "调用onMeasure:" + heightMeasureSpec + ",heightSize:" + heightSize);


        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),heightSize);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //默认设定顶层View拦截
        getParent().getParent().requestDisallowInterceptTouchEvent(false);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float y = ev.getY();
                float deltaY = y - mLastY;


                //滑动到顶部的时候，mScrollY的值 等于 mChildH
                int translateY = mChildH - mScrollY;

                // 只有一个子view，所以这里其实就是拿到了整个
                mChildH = this.getChildAt(0).getMeasuredHeight();

                //向上滑动时，刚开始滑动，那么则交给父view
                if (deltaY < 0 && oldY == 0) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }

                //向下滑动时，并且是滑动到了顶部，那么将事件交给父view
                if (deltaY > 0 && translateY == 0) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
                Log.e(TAG, "mChildH:" + mChildH + ",mScrollY:" + mScrollY + ",getHeight:" + getHeight() + ",oldY:" + oldY + ",deltaY；" + deltaY);
                break;
            default:
                break;

        }

        return super.onTouchEvent(ev);
    }


    /**
     * 展示出来的画面的左上角，在整个View中的坐标
     *
     * @param l    X轴坐标
     * @param t    Y轴坐标
     * @param oldl 旧的X轴
     * @param oldt 旧的Y轴
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mScrollY = t;
        oldY = oldt;
    }

}
