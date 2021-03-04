package com.jzh.basemodule.http.bean;

/**
 * @author jinzhenhua
 * @version 1.0  ,create at:2021/3/4 18:52
 */
public abstract class ReturnBean<T> {

    /**
     * 获取返回的真实
     * @return 真实数据的实体
     */
    public abstract  T getData();
}
