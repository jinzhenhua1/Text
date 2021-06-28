package com.jzh.basemodule.view;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * 按下自动变暗
 * @author jinzhenhua
 * @version 1.0  ,create at:2021/3/22 16:29
 */
public class MaskableImageView extends AppCompatImageView {

    private boolean touchEffect = true;
    public final float[] BG_PRESSED = new float[]{1, 0, 0, 0, -50, 0, 1,
            0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0};
    public final float[] BG_NOT_PRESSED = new float[]{1, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};

    public MaskableImageView(Context context) {
        super(context);
    }

    public MaskableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setPressed(boolean pressed) {
        updateView(pressed);
        super.setPressed(pressed);
    }

    /**
     * 根据是否按下去来刷新bg和src
     * created by minghao.zl at 2014-09-18
     *
     * @param pressed
     */
    private void updateView(boolean pressed) {
        //如果没有点击效果
        if (!touchEffect) {
            return;
        }//end if
        if (pressed) {//点击
            /**
             * 通过设置滤镜来改变图片亮度@minghao
             */
            this.setDrawingCacheEnabled(true);
            this.setColorFilter(new ColorMatrixColorFilter(BG_PRESSED));
            this.getDrawable().setColorFilter(new ColorMatrixColorFilter(BG_PRESSED));
        } else {//未点击
            this.setColorFilter(new ColorMatrixColorFilter(BG_NOT_PRESSED));
            this.getDrawable().setColorFilter(
                    new ColorMatrixColorFilter(BG_NOT_PRESSED));
        }
    }
}
