package com.example.text.bean;

import com.google.gson.annotations.SerializedName;

public class TestStringResponse {
    private String status = "";
    private String desc = "";

    @SerializedName("data")
    private String data = "";

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public String getData() {
        return data;
    }
}
