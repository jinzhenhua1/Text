package com.jzh.basemodule.http;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author huangqj
 * @version 1.0 , create at 2020/4/2 11:01
 */
class RetrofitServiceManager {

    private static final int CONNECT_TIME_OUT = 10;
    private static final int WRITE_TIME_OUT = 15;
    private static final int READ_TIME_OUT = 15;
    private static RetrofitServiceManager manager;
    private final Retrofit retrofit;

    private static String BASE_URL;

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static synchronized RetrofitServiceManager getInstance() {
        if (manager == null) {
            manager = new RetrofitServiceManager();
        }
        return manager;
    }

    private RetrofitServiceManager() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(httpLoggingInterceptor);
//                .addInterceptor(new ResponseCodeInterceptor());
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    /**
     * 创建retrofit
     *
     * @param service 对应的http接口service
     * @param <T>     接口
     * @return retrofit 实例
     */
    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

}
