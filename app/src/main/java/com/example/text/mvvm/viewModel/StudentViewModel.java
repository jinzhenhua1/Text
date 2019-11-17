package com.example.text.mvvm.viewModel;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.example.text.bean.Student;
import com.example.text.mvvm.base.BaseViewModel;
import com.example.text.mvvm.injection.DaggerAppComponent;
import com.example.text.mvvm.model.StudentModel;

import javax.inject.Inject;

public class StudentViewModel extends BaseViewModel {
    public ObservableField<String> studentName = new ObservableField<>("");
    public ObservableField<String> className = new ObservableField<>("");
    public ObservableField<String> level = new ObservableField<>("");
    public ObservableInt age = new ObservableInt();

    @Inject
    StudentModel studentModel;

    private Student student;


    @Override
    public void init() {
        DaggerAppComponent.create().inject(this);
        student = studentModel.getStudent();

        studentName.set(student.getName());
        className.set(student.getClassName());
        level.set(student.getLevel());

    }

    @Override
    public void destroy() {

    }

    public void clickChange(){
        studentName.set("李四");
        className.set("9班");
        level.set("初三");
    }



}
