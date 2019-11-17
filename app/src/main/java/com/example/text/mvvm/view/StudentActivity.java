package com.example.text.mvvm.view;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

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



    }

    @Override
    public void bindViewModel() {
        dataBinding.setStudent(viewModel);
    }

}
