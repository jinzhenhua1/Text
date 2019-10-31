package com.example.text.bean;

import androidx.annotation.NonNull;


public class WeatherData {
    private ContextData data;
    private String status = "";
    private String desc = "";

    public ContextData getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public void setData(ContextData data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{");
        stringBuffer
                .append("desc:").append(desc).append(",")
                .append("status:").append(status).append(",")
                .append("data:").append(data.toString()).append("}");


        return stringBuffer.toString();
    }
}
