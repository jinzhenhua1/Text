package com.example.text.mvvm.model;

import com.example.text.bean.Student;

import javax.inject.Inject;

/**
 * <p></p >
 * <p></p >
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2019/11/16 14:57
 */
public class StudentModel {

    private Student student;

    @Inject
    public StudentModel() {
        this.student = new Student();
        student.setName("张三");
        student.setClassName("三班");
        student.setLevel("高一");
    }

    public Student getStudent(){
        return student;
    }
}
