package com.example.text.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.text.R;
import com.example.text.databinding.ActivityTextGridViewBinding;

public class TestGridLayoutActivity extends AppCompatActivity {
    ActivityTextGridViewBinding binding;
    private GridLayout gridLayout;
//    private String[] mStrings = {"1","2","3"};
    private String[] mStrings = {"1","2","3","4","5","6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_text_grid_view);

        gridLayout = binding.glTest;
        initData();
    }


    private void initData(){
        for(int i = 0; i < mStrings.length; i++){
            TextView textView = new TextView(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            // 设置行列下标， 所占行列  ，比重
            // 对应： layout_row  , layout_rowSpan , layout_rowWeight
            // 如下代表： item坐标（0,0）， 占 1 行，比重为 3 ； 占 4 列，比重为 1
            params.rowSpec = GridLayout.spec(i / 2,1,1f);
            params.columnSpec = GridLayout.spec(i % 2,1,1f);

            if(i == 1){
                params.columnSpec = GridLayout.spec(i % 2,2,2f);
            }

            textView.setGravity(Gravity.CENTER);//设置对齐方式


            // 背景
            textView.setBackgroundColor(Color.WHITE);
            // 设置边距
            params.setMargins(2,2,2,2);

            // 设置文字
            textView.setText(mStrings[i]);

            // 添加item
            gridLayout.addView(textView,params);
        }

    }
}
