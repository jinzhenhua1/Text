package com.jzh.basemodule.http;

import java.util.List;

import okhttp3.Interceptor;

/**
 * <p>http 请求接口实现<p>
 *
 * @author huangqj
 * @version 1.0 , create at 2020/4/2 12:31
 */
public class ApiServiceImpl {

    private ApiServiceImpl() {
    }

    /**
     * 单例模式
     *
     * @return ApiServiceImpl唯一实例
     */
    public  static synchronized <T> T getApiService(Class<T> clazz) {
        return RetrofitServiceManager.getInstance().create(clazz);
    }

    /**
     * 初始化
     * @param BASE_URL
     * @param interceptorList
     */
    public static void init(String BASE_URL, List<Interceptor> interceptorList){
        RetrofitServiceManager.init(BASE_URL,interceptorList);
    }
}
