package com.example.text.view.viewpager;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.text.R;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

/**
 * @author jinzhenhua
 * @version 1.0  ,create at:2021-9-30 11:51
 */
public class FragmentPageTransformer implements ViewPager.PageTransformer{
    private static final float MIN_SCALE = 0.75f;
    public static final float MAX_SCALE = 1.3F;

    private TabLayout tabLayout;

    private HashMap<Integer,Float> mLastMap = new HashMap<Integer, Float>();

    public FragmentPageTransformer(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    /**
     * 页面切换的时候调用，一次切换会调用很多次该方法
     * @param page
     * @param position 表示切换的进度。如，从页面1切换到右边页面2，页面1 的position 会从0渐渐变成-1，而页面2会从1慢慢变成0
     *                 切换到左边，则左边的页面会从-1 渐渐变成0，当前页面从0渐渐变成1。总结，当前的页面永远是0，往右一个则加1，往左一个则减1
     */
    @Override
    public void transformPage(@NonNull View page, float position) {
        //只有跟当前页面有切换的才执行动画
/*        if(position < -1 || position > 1){
            return;
        }

        int curPosition = (int)page.getTag();
        final float currV = Math.abs(position);
        if(!mLastMap.containsKey(curPosition)){
            mLastMap.put(curPosition,currV);
            return;
        }

        float lastV = mLastMap.get(curPosition);
        TabLayout.Tab tab = tabLayout.getTabAt(curPosition);
        TextView textView = tab.getCustomView().findViewById(R.id.tab_name);

        //判断当前跟上一次的值，当前的比上一次的大，那么证明view是从当前页变为不是当前页，那么textview要缩小
        if(currV > lastV){
            //currV 变化是从 0 到 1
            textView.setScaleX(MAX_SCALE - (MAX_SCALE - 1.0F) * currV);
            textView.setScaleY(MAX_SCALE - (MAX_SCALE - 1.0F) * currV);
        }else{

            //currV 变化是从 1 到 0
            textView.setScaleX(1.0f + (MAX_SCALE - 1.0F) * currV);
            textView.setScaleY(1.0f + (MAX_SCALE - 1.0F) * currV);
        }
        mLastMap.put(curPosition,currV);
        */

        int pageHeight = page.getHeight();

        if (position <= 0) {
            // 当前页向下一页滑动的时候，当前页不做变化
            //或者是下一页变成了当前页，那么把状态恢复
            page.setAlpha(1);
            page.setTranslationY(0);
            page.setScaleX(1);
            page.setScaleY(1);

            // (0,1]
        } else if (position <= 1) {
            // 即将到来的下一页 position 从 1变 0
            page.setAlpha(1 - position);

            // 移动下一页到屏幕下方
            page.setTranslationY(pageHeight * -position);

            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

        }
    }
}
