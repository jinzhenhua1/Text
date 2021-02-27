package com.example.text.text1;

import com.example.text.bean.ContextData;
import com.example.text.bean.ResponseData;
import com.example.text.bean.TestStringResponse;
import com.example.text.bean.WeatherData;
import com.example.text.mvp.http.HttpRespondData;

import org.json.JSONObject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TextService {
    //获取启动页大图
    @GET("/weather_mini")
    Call<ResponseBody> getStartImage(@Query("city") String str);

    @GET("/weather_mini")
    Call<ResponseData<ContextData>> getStartImage1(@Query("city") String str);

    @GET("/weather_mini")
    Observable<ResponseData<ContextData>> getResponseBody(@Query("city") String str);

    /**
     * MVP模式中使用的数据格式
     * @param str
     * @return ContextData可以替换为任意类型，但是HttpRespondData 为固定的数据格式
     */
    @GET("/weather_mini")
    Observable<HttpRespondData<ContextData>> getWeatherData(@Query("city") String str);



    /**
     * 测试返回数据中是否可以有字符串
     * @param str
     * @return
     */
    @GET("/weather_mini")
    Observable<TestStringResponse> getStringResponse(@Query("city") String str);



    @GET("http://quan.suning.com/getSysTime.do")
    Observable<ResponseBody> getTestTime();
}
