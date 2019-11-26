package com.example.text.bean;

import java.io.Serializable;

/**
 * <p></p >
 * <p></p >
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2019/11/16 21:41
 */
public class Student implements Serializable {
    private String name = "";
    private String className = "";
    private String level = "";

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
