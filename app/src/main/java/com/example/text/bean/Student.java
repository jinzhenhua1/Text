package com.example.text.bean;

/**
 * <p></p >
 * <p></p >
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2019/11/16 21:41
 */
public class Student {
    private String name = "";
    private String className = "";
    private String level = "";

    public Student() {
    }

    public Student(String name, String className, String level) {
        this.name = name;
        this.className = className;
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getLevel() {
        return level;
    }
}
