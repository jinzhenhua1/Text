package com.jzh.basemodule.view.activity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.andexert.library.RippleView;
import com.jzh.basemodule.R;
import com.jzh.basemodule.bean.FileBean;
import com.jzh.basemodule.view.adapter.ShowImagePagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

/**
 * 显示图片
 * @param <T>
 */
public class ShowImageActivity<T extends FileBean> extends AppCompatActivity implements ShowImagePagerAdapter.IOnBackListener {

    public static final String KEY_SHOW_IMAGE_POSITION = "position";
    public static final String KEY_SHOW_IMAGE_PATH = "images";
    public static final String KEY_TEXT_SIZE = "KEY_TEXT_SIZE";

    ViewPager mImageVp;
    TextView mPagerNumTv;

    private ShowImagePagerAdapter<T> mAdapter;
    private List<T> fileList = new ArrayList<>();
    private String currentPageStr = "";
    private int currentPosition = 0;

    private int textSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        initData();
        initView();
    }



    private void initView() {

        mImageVp = findViewById(R.id.show_image_activity_vp);
        mPagerNumTv = findViewById(R.id.show_image_activity_tv_page_number);

        mPagerNumTv.setText(currentPageStr);
        if(textSize != 0){
            mPagerNumTv.setTextSize(textSize);
        }

        mAdapter = new ShowImagePagerAdapter<>(this, fileList);
        mAdapter.setOnBackListener(this);
        mImageVp.setAdapter(mAdapter);
        mImageVp.setCurrentItem(currentPosition);
        mImageVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPageStr = position + 1 + "/" + fileList.size();
                mPagerNumTv.setText(currentPageStr);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        RippleView ibBack = findViewById(R.id.main_activity_toolbar_ripple_menu);
        ibBack.setOnClickListener(v -> finish());
    }

    public void initData() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            currentPosition = bundle.getInt(KEY_SHOW_IMAGE_POSITION);
            fileList.addAll((List<T>) bundle.getSerializable(KEY_SHOW_IMAGE_PATH));
            currentPageStr = currentPosition + 1 + "/" + fileList.size();
            textSize = bundle.getInt(KEY_TEXT_SIZE,0);
        }
    }

    @Override
    public void onBack() {

    }
}