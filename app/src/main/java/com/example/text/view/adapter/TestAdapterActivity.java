package com.example.text.view.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.text.MainActivity;
import com.example.text.R;

import java.util.ArrayList;

public class TestAdapterActivity extends AppCompatActivity {
    private RecyclerView activity_adapter_test_rv;
    private WrapRecyclerAdapter wrAdapter;
    private BluetoothAdapter btAdapter;
    private TestAdapterHelperAdapter testAdapterHelperAdapter;
    private ArrayList<String> titles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_adapter);

        for(int i = 0; i < 50; i++){
            titles.add("这是标题" + i);
        }

        initTestAdapterHelperAdapter();

        activity_adapter_test_rv = findViewById(R.id.activity_adapter_test_rv);
        btAdapter = new BluetoothAdapter(this,titles);

        ArrayList<View> heads = new ArrayList<View>();
        ArrayList<View> foods = new ArrayList<View>();
//
        LinearLayout headView = new LinearLayout(this);
        TextView tv1 = new TextView(this);
        tv1.setText("头部");
        headView.setBackgroundColor(Color.RED);
        headView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,400));
        headView.addView(tv1);
        heads.add(headView);

        LinearLayout foodView = new LinearLayout(this);
        TextView tv2 = new TextView(this);
        tv2.setText("尾部");
        foodView.setBackgroundColor(Color.RED);
        foodView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,400));
        foodView.addView(tv2);
        foods.add(foodView);

        wrAdapter = new WrapRecyclerAdapter(testAdapterHelperAdapter,heads,foods);
//        wrAdapter = new WrapRecyclerAdapter(btAdapter,heads,foods);
        activity_adapter_test_rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        activity_adapter_test_rv.setAdapter(wrAdapter);
    }

    /**
     * 初始化适配器框架的适配器
     */
    private void initTestAdapterHelperAdapter(){
        testAdapterHelperAdapter = new TestAdapterHelperAdapter(titles);
        testAdapterHelperAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //这个是整个item的点击事件，我们可以在这里做一些操作
                Toast.makeText(TestAdapterActivity.this, position + "位置", Toast.LENGTH_SHORT).show();
            }
        });

        testAdapterHelperAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //具体的子view的点击事件，如果有多个子view添加了该事件，则需要判断具体是哪个
//                view.getId()
            }
        });
    }
}