package com.jzh.basemodule.http.interceptor;


import com.jzh.basemodule.config.HttpConfig;
import com.jzh.basemodule.utils.LogUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/9/10 19:35
 */
public class ResponseCodeInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //替换url
        HttpUrl oldUrl = request.url();
        LogUtils.e("ResponseCodeInterceptor old request url=", oldUrl.url().toString());
        Request.Builder newBuilder = request.newBuilder();
        HttpUrl newHttpUrl = oldUrl.newBuilder()
                .host(HttpConfig.getIp())
                .port(Integer.parseInt(HttpConfig.getPort()))
                .build();
        LogUtils.e("ResponseCodeInterceptor new request url=", newHttpUrl.url().toString());
        Request newRequest = newBuilder.url(newHttpUrl).build();
        return chain.proceed(newRequest);

    }
}
