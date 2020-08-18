package com.example.text.view.ScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/7/15 16:49
 */
public class ParentScrollView extends ScrollView {
    private UpScrollView upScrollView;
    private boolean init = false;
    private int mScreenHeight = 0;

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
        if(!init){
            LinearLayout parentView = (LinearLayout) getChildAt(0);
            //获得内部的两个子view
            upScrollView = (UpScrollView) parentView.getChildAt(0);
            //并设定其高度为屏幕高度
            upScrollView.getLayoutParams().height = mScreenHeight;
            init = true;
        }
        Log.e("ParentScrollView","调用onMeasure前");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("ParentScrollView","调用onMeasure后");
    }

    /**
     * AES加密
     * @param encryptStr 加密秘钥
     * @param content 加密内容
     * @return
     */
    public static String aesEncrypt(String encryptStr, String content) {
        if (org.apache.commons.lang3.StringUtils.isBlank(encryptStr)) {
            return null;
        } else {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptStr.getBytes(), "AES"));
                byte[] decryptBytes = cipher.doFinal(content.getBytes("utf-8"));
                return Base64.encodeToString(decryptBytes, Base64.DEFAULT);
            } catch (Exception e) {
                return null;
//                throw new ServiceOperationException("加密异常");
            }
        }
    }
}
