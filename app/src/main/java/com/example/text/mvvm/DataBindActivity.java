package com.example.text.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.text.R;
import com.example.text.bean.User;
import com.example.text.databinding.ActivityDataBindBinding;

public class DataBindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_bind);
        User user = new User();
        binding.setUser(user);


    }
}
