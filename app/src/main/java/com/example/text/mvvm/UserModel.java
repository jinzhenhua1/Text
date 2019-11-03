package com.example.text.mvvm;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableInt;


/**
 * 单向绑定 继承BaseObservable 后可
 */
public class UserModel extends BaseObservable {

    //如果是 public 修饰符，则可以直接在成员变量上方加上 @Bindable 注解
//    @Bindable
    private String name = "";
    //如果是 private 修饰符，则在成员变量的 get 方法上添加 @Bindable 注解
    private String age = "";



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
}
