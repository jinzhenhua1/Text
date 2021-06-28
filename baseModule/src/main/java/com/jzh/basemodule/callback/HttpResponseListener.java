package com.jzh.basemodule.callback;

/**
 * <p>网络请求接口回调类</p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/5/26 17:21
 */
public interface HttpResponseListener<T> {
    /**
     * 网络请求成功
     *
     * @param t   返回的数据
     */
    void onSuccess(T t);

    /**
     * 网络请求失败()
     * @param throwable 错误信息
     */
    void onFailure(Throwable throwable);
}
