package com.example.text;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by idea on 2016/9/29.
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 回答我需要多宽多高的问题
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);//测量所有小孩的宽高

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int desiredWidth = 0, desiredHeight = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            desiredWidth = widthSize;
        } else {
            desiredWidth = getResources().getDisplayMetrics().widthPixels;//如果宽度设置为wrap_content，则宽度指定为屏幕的宽度
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            Log.e("test", "onMeasure:heightSize="+heightSize);
            desiredHeight = heightSize;
        } else {
            //高度为wrap_content时：
            int currentLineWidth = 0;//当前行前边所有孩子的宽度之和 （包括左右边距）
            int totalHeight = 0;//之前所有行的总高度（包括上下边距）；
            int currentLineHeight = 0;//当前行的高度（取决于本行最高的孩子的高度加上下间距）

            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
//                measureChild(child,widthMeasureSpec,heightMeasureSpec);//在这才测量小孩，也太晚了。。。
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                //测试一下新来的小孩，能否插入当前行
                int testWidth = currentLineWidth + child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                if(testWidth > getMeasuredWidth()){
                    totalHeight += currentLineHeight;//准备换行，总行高要增加当前行的高度
                    currentLineWidth = 0;//准备换行，重新开始计算行宽
                }

                //每插完一个小孩，刷新当前行的行宽与行高
                currentLineWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;//每摆完一个小孩，更新当前行的总宽度
                currentLineHeight = Math.max(currentLineHeight,child.getMeasuredHeight()+lp.topMargin + lp.bottomMargin);//每摆完一个小孩，检查本行行高是否被刷新
            }

            totalHeight += currentLineHeight;//最后一个小孩插完，刷新总行高
            desiredHeight = totalHeight;
        }

        setMeasuredDimension(desiredWidth, desiredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int currentLineWidth = l;//当前行前边所有孩子的宽度之和 （包括左右边距）
        int totalHeight = t;//之前所有行的总高度（包括上下边距）；
        int currentLineHeight = 0;//当前行的高度（取决于本行最高的孩子的高度加上下间距）

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            //测试一下新来的小孩，能否插入当前行
            int testWidth = currentLineWidth + child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            if(testWidth > getMeasuredWidth()){
                totalHeight += currentLineHeight;//准备换行，总行高要增加当前行的高度
                currentLineWidth = l;//准备换行，重新开始计算行宽
                currentLineHeight = 0;//准备换行，当前行高清零
            }

            //插入小孩
            int left = currentLineWidth + lp.leftMargin;
            int right = left + child.getMeasuredWidth();
            int top = totalHeight + lp.topMargin;
            int bottom = top + child.getMeasuredHeight();
            child.layout(left, top, right, bottom);
//            child.layout(50, 50, 300, 300);

            //每插完一个小孩，刷新当前行的行宽与行高
            currentLineWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;//每摆完一个小孩，更新当前行的总宽度
            currentLineHeight = Math.max(currentLineHeight,child.getMeasuredHeight()+lp.topMargin + lp.bottomMargin);//每摆完一个小孩，检查本行行高是否被刷新
        }

        totalHeight += currentLineHeight;//最后一个小孩插完，刷新总行高

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
