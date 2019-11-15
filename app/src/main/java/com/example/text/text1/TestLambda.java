package com.example.text.text1;

import android.annotation.SuppressLint;

import java.util.ArrayList;

public class TestLambda {
    private ArrayList<String> lists = new ArrayList();

    @SuppressLint("NewApi")
    public void test(){
        lists.removeIf(t -> t.equals("1"));//匿名类的写法

        //匿名类的写法，只有一行代码时，可以省略大括号
        Thread thread = new Thread(() -> {
            // TODO: 2019/11/13 do what you want
        });

        Student student = new Student("张三");
        //匿名类的写法，表示在新线程中会调用test方法
        Thread thread1 = new Thread(this::test);
        Thread thread2 = new Thread(student::getName);
    }

    public static class Student{
        private String name;
        public Student(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }
    }

}
