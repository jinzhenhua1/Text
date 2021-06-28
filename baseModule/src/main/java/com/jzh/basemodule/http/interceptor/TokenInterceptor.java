package com.jzh.basemodule.http.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.jzh.basemodule.config.HttpConfig;
import com.jzh.basemodule.http.ApiServiceImpl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;

/**
 * 刷新token的拦截器
 * @author jinzhenhua
 * @version 1.0  ,create at:2021/3/2 11:16
 */
//public class TokenInterceptor implements Interceptor {
//    private static String TAG = TokenInterceptor.class.getSimpleName();
//    private Charset UTF8 = StandardCharsets.UTF_8;
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Log.e(TAG, "------------>当前线程为：" + Thread.currentThread().getName());
//        Request original = chain.request();
//        if (TextUtils.isEmpty(MyApplication.getToken()) || original.url().toString().contains(HttpConfig.LOGIN)) {
//            return chain.proceed(original);
//        }
//        Request.Builder requestBuilder = original.newBuilder().addHeader("Authorization", "bearer " + MyApplication.getToken());
//        Request request = requestBuilder.build();
//
//        Response originalResponse = chain.proceed(request);
//        ResponseBody responseBody = originalResponse.body();
//        BufferedSource source = responseBody.source();
//        source.request(Long.MAX_VALUE);
//        Buffer buffer = source.buffer();
//        Charset charset = UTF8;
//        MediaType contentType = responseBody.contentType();
//        if (contentType != null) {
//            charset = contentType.charset(UTF8);
//        }
//        String bodyStr = buffer.clone().readString(charset);
//        Gson gson = new Gson();
//        ResponseWrapper responseWrapper = gson.fromJson(bodyStr, ResponseWrapper.class);
//        //同步代码块，当在刷新token的时候暂停其他的request，锁为当前类的单例对象
//        synchronized (this) {
//            //10003是服务器与客户端约定token过期的标识，在这里就执行刷新token的操作
//            if (responseWrapper.getState() == ResponseWrapper.TOKEN_TIME_OUT) {
//                Log.e(TAG, "------------>token 过期");
//
//                String refresh_toke = "";
//                if(MyApplication.getLoginInfo() != null){
//                    refresh_toke = MyApplication.getLoginInfo().getRefresh_token();
//                }
//                Call<ResponseWrapper<LoginInfo>> call = ApiServiceImpl.getApiService().refreshToken(refresh_toke);
//                Log.e(TAG, "------------>开始重新获取token");
//                //刷新token必须使用同步请求
//                retrofit2.Response<ResponseWrapper<LoginInfo>> response = call.clone().execute();
//                LoginInfo loginInfo = response.body().getData();
//                Log.e(TAG, "------------>token刷新成功  access_token=" + loginInfo.getAccess_token());
//                MyApplication.setToken(loginInfo.getAccess_token());
//                MyApplication.setLoginInfo(loginInfo);
//                //重新拼装请求头
//                Request newRequest = request.newBuilder().header("Authorization", loginInfo.getAccess_token()).build();
//                //重试request
//                return chain.proceed(newRequest);
//            }
//        }
//        return chain.proceed(request);
//    }
//}
