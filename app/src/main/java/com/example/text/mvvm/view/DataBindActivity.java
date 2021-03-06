package com.example.text.mvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.text.R;
import com.example.text.databinding.ActivityDataBindBinding;
import com.example.text.mvvm.viewModel.UserModel;

public class DataBindActivity extends AppCompatActivity implements View.OnClickListener{
    private boolean changeName = true;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_bind);

        userModel = new UserModel(this);
        userModel.setName("张三");
        binding.setUserModel(userModel);
        binding.setListener(this);
    }

    @Override
    public void onClick(View v) {
        changeName = !changeName;
        if(changeName){
            userModel.setName("张三");
        }else{
            userModel.setName("李四");
        }
    }
}
