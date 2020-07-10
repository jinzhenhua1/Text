package com.example.text.mvp.http;

import com.example.text.bean.ContextData;

/**
 * <p>请求返回的 数据格式类</p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/5/26 17:21
 */
public class HttpRespondData<T> {
    private T data;
    private int status = 0;
    private String desc = "";

    public T getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
