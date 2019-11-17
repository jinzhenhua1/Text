package com.example.text.mvvm.viewModel;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableInt;

import com.example.text.mvvm.view.StudentActivity;


/**
 * 单向绑定 继承BaseObservable 后可
 */
public class UserModel extends BaseObservable {
    private Context context;

    //如果是 public 修饰符，则可以直接在成员变量上方加上 @Bindable 注解
//    @Bindable
    private String name = "";
    //如果是 private 修饰符，则在成员变量的 get 方法上添加 @Bindable 注解
    private String age = "";


    public UserModel(Context context) {
        this.context = context;
    }

    public void setName(String name) {
        this.name = name;
        //更新指定的某个字段
        notifyPropertyChanged(com.example.text.BR.name);
    }

    public void setAge(String age) {
        this.age = age;
        //更新所有字段
        notifyChange();
    }

    @Bindable
    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public void clickJump(){
        context.startActivity(new Intent(context, StudentActivity.class));
    }
}
