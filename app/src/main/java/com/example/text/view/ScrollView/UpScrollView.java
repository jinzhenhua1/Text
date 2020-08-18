package com.example.text.view.ScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ScrollView;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/7/15 14:13
 */
public class UpScrollView extends ScrollView {
    /**
     * 屏幕高度
     */
    private int mScreenHeight;
    /**
     * 上一次的坐标
     */
    private float mLastY;
    /**
     * 当前View滑动距离
     */
    private int mScrollY;
    /**
     * 当前View内子控件高度
     */
    private int mChildH;




    public UpScrollView(Context context) {
        super(context);
        init(context);
    }

    public UpScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UpScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        Log.e("UpScrollView","调用onMeasure前");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("UpScrollView","调用onMeasure后");
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //默认设定顶层View不拦截
        getParent().getParent().requestDisallowInterceptTouchEvent(true);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float y = ev.getY();
                float deltaY = y - mLastY;

                //todo 有BUG，如果滑到了底部这里其实拿到的是UpScrollView 的实际高度，根本就不是屏幕的高度，所以不可
                // 能等于屏幕的高度。也就是说，父控件永远拿不到事件。
                int translateY = mChildH - mScrollY;

                // 只有一个子view，所以这里其实就是拿到了整个
                mChildH = this.getChildAt(0).getMeasuredHeight();
                Log.e("UpScrollView","mChildH:" + mChildH + ",mScreenHeight:" + mScreenHeight
                        + ",translateY:" + translateY + ",realHeight:" + getHeight());

                //向上滑动时，如果translateY等于屏幕高度时，即表明滑动到底部，可又顶层View控制滑动
                if (deltaY < 0 && translateY == getHeight()) {//todo 这里修改为等于控件本身高度，可解决上面的BUG
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            default:
                break;

        }

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
