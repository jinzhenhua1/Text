package com.example.text.mvvm.viewModel;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;

import com.example.text.bean.Student;
import com.example.text.bean.User;
import com.example.text.mvvm.base.BaseViewModel;
import com.example.text.mvvm.injection.DaggerAppComponent;
import com.example.text.mvvm.model.StudentModel;

import javax.inject.Inject;

public class StudentViewModel extends BaseViewModel {
    public ObservableField<String> studentName = new ObservableField<>("");
    public ObservableField<String> className = new ObservableField<>("");
    public ObservableField<String> level = new ObservableField<>("");
    public ObservableField<Student>  studentObservableField = new ObservableField<>();
    public ObservableInt age = new ObservableInt();

    public MutableLiveData<Student> mCurrentName = new MutableLiveData<>();

    @Inject
    StudentModel studentModel;



    @Override
    public void init() {
        DaggerAppComponent.create().inject(this);
        studentObservableField.set(studentModel.getStudent());

//        studentName.set(studentObservableField.get().getName());
//        className.set(studentObservableField.get().getClassName());
//        level.set(studentObservableField.get().getLevel());


        mCurrentName.setValue(studentModel.getStudent());

    }

    @Override
    public void destroy() {

    }

    public void clickChange(){
        ///改变对象里面属性的值，无效
        //因为对象没有改变，所以xml中不会识别改变后的属性值
//        studentObservableField.get().setName("李四");
//        studentObservableField.get().setClassName("9班");
//        studentObservableField.get().setLevel("初三");

        Student student1 = new Student();
        student1.setName("李四");
        student1.setClassName("9班");
        student1.setLevel("初三");

        studentObservableField.set(student1);

    }

    public void liveDataChange(){
        Student student1 = new Student();
        student1.setName("李四");
        student1.setClassName("9班");
        student1.setLevel("初三");

        mCurrentName.setValue(student1);
        mCurrentName.postValue(student1);//可以在子线程中使用
    }

    public MutableLiveData<Student> getmCurrentName() {
        if(mCurrentName == null){
            mCurrentName = new MutableLiveData<>();
        }
        return mCurrentName;
    }
}
