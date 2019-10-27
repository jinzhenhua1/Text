package com.example.text.bean;

import org.json.JSONObject;

public class ResponseData<T> {
    private T data;
    private String status = "";
    private String desc = "";

    public T getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
