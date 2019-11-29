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
    private String TAG = FlowLayout.class.getSimpleName();


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


        int totalHeight = paddingTop + paddingBottom;//控件总高
        Log.e(TAG,"paddingTop:" + paddingTop + ",paddingBottom:" + paddingBottom);
        int totalWidth = 0;//控件总宽，也可以默认取最大值

//        if (heightMode == MeasureSpec.EXACTLY) {//固定宽高
//            Log.e("test", "onMeasure:heightSize="+heightSize);
//            totalHeight = heightSize;
//        }

        int currentLineWidth = paddingLeft + paddingRight;//当前行前边所有孩子的宽度之和 （包括左右边距）
        int currentLineHeight = 0;//当前行的高度（取决于本行最高的孩子的高度加上下间距），高度只有一个上下padding，不用每行都算padding

        int childCount = getChildCount();
        for(int i = 0; i < childCount; i++){
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

            //加上当前子控件后，是否不超越父控件的宽度
            int testWidth = currentLineWidth + childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            if(testWidth > widthSize){//超过最大限制，需要换行
                totalWidth = Math.max(totalWidth,currentLineWidth);//取最大的那一行的宽度
                currentLineWidth = paddingLeft + paddingRight;//重置宽度

                totalHeight += currentLineHeight;//计算总高度
                currentLineHeight = 0;//重置高度
            }

            currentLineWidth += childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;//每次插入一个子控件，增加当前行的宽度
            currentLineHeight = Math.max(currentLineHeight,childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);//当前行，最高的高度
            Log.e(TAG,"childView.getMeasuredHeight():" + childView.getMeasuredHeight() + "," + i + "lp.topMargin:" + lp.topMargin + "," + i +"lp.topMargin:" + lp.bottomMargin);


        }
        totalWidth = Math.max(totalWidth,currentLineWidth);//有可能最后一个元素所在的最后一行，宽度最大
        totalHeight += currentLineHeight;//加上最后一行的高度


        totalHeight = Math.min(totalHeight,heightSize);//使用高度最小的那个
        Log.e(TAG,"totalHeight:" + totalHeight + ",heightSize:" + heightSize);

        setMeasuredDimension(totalWidth,totalHeight);

    }



    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        Log.e(TAG,"i:" + i + ",i1:" + i1 + ",i2:" + i2 + ",i3:" + i3);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int currentLineWidth = paddingLeft;//当前行对应父组件所在的坐标，父组件的左上角为原点
        int totalHeight = paddingTop;//之前所有行的总高度（包括上下边距）；
        int currentLineHeight = 0;//当前行的高度（取决于本行最高的孩子的高度加上下间距）

        int childCount = getChildCount();
        for (int j = 0; j < childCount; j++) {
            View child = getChildAt(j);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            //测试一下新来的小孩，能否插入当前行
            int testWidth = currentLineWidth + child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            if(testWidth > getMeasuredWidth()){
                totalHeight += currentLineHeight;//准备换行，总行高要增加当前行的高度
                currentLineWidth = paddingLeft;//准备换行，重新开始计算行宽
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
}
