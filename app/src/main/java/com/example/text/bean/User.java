package com.example.text.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity //@Entity 将我们的java普通类变为一个能够被greenDAO识别的数据库类型的实体类
public class User {
    private String name = "";

    private String age = "";

    @Generated(hash = 2102286658)
    public User(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

/**
 * @Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
 *
 * @Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值 。
 *
 * @Property：可以自定义字段名，注意外键不能使用该属性
 *
 * @NotNull：属性不能为空 @Transient：使用该注释的属性不会被存入数据库的字段中
 *
 * @Unique：该属性值必须在数据库中是唯一值
 *
 * @Transient：表明这个字段不会被写入数据库，只是作为一个普通的java类字段，用来临时存储数据的，不会被持久化
 *
 * @Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改
 **/