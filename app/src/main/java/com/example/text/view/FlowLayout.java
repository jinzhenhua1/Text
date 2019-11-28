package com.example.text.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * <p></p >
 * <p></p >
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2019/11/28 20:33
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);//测量所有小孩的宽高，否则取不到子控件的大小和边距等

        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//经测试与getMeasuredWidth方法返回值一致
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);//控件的总高度


        int totalHeight = 0;//控件总高
        int totalWidth = 0;//控件总宽

        if (heightMode == MeasureSpec.EXACTLY) {//固定宽高 宽高宽高
            Log.e("test", "onMeasure:heightSize="+heightSize);
            totalHeight = heightSize;
        }

        int currentLineWidth = paddingLeft + paddingRight;//当前行前边所有孩子的宽度之和 （包括左右边距）
        int currentLineHeight = paddingTop + paddingBottom;//当前行的高度（取决于本行最高的孩子的高度加上下间距）

        int childCount = getChildCount();
        for(int i = 0; i < childCount; i++){
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

            //加上当前子控件后，是否不超越父控件的宽度
            int testWidth = currentLineWidth + childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            if(testWidth > widthSize){
                totalWidth = Math.max(totalWidth,currentLineWidth);
                currentLineWidth = paddingLeft + paddingRight;//重置默认的最小宽度
            }

            currentLineWidth += childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;//每次插入一个子空间，增加当前行的宽度

            currentLineHeight += childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;//高度

        }



        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
