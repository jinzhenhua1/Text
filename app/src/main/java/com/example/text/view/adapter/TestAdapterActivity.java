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

import com.example.text.R;

import java.util.ArrayList;

public class TestAdapterActivity extends AppCompatActivity {
    private RecyclerView activity_adapter_test_rv;
    private WrapRecyclerAdapter wrAdapter;
    private BluetoothAdapter btAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_adapter);

        ArrayList<String> titles = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            titles.add("这是标题" + i);
        }

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

        wrAdapter = new WrapRecyclerAdapter(btAdapter,heads,foods);
        activity_adapter_test_rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        activity_adapter_test_rv.setAdapter(wrAdapter);
    }
}