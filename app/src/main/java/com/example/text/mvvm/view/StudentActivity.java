package com.example.text.mvvm.view;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.text.R;
import com.example.text.databinding.ActivityStudentBinding;
import com.example.text.mvvm.base.BaseActivity;
import com.example.text.mvvm.viewModel.StudentViewModel;

public class StudentActivity extends BaseActivity<ActivityStudentBinding,StudentViewModel> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_student);
        viewModel = new StudentViewModel();
        super.onCreate(savedInstanceState);

        initData();

    }

    @Override
    public void bindViewModel() {
        dataBinding.setStudent(viewModel);
    }

    private void initData(){
        /// 让xml内绑定的LiveData和Observer建立连接，也正是因为这段代码，让LiveData能感知Activity的生命周期
        //加上这句代码，才能改变控件中的值。前提是XML中是用MutableLiveData来赋值给控件的
        dataBinding.setLifecycleOwner(this);

        //同时可以监听改变值时的操作，可以更改别的控件的UI
        viewModel.getmCurrentName().observe(this,student -> {
            viewModel.studentObservableField.set(student);
        });
    }
}
