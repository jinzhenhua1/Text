package com.example.text.text1;

import com.example.text.bean.ContextData;
import com.example.text.bean.ResponseData;
import com.example.text.bean.WeatherData;

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

    @GET("/weather_mini")
    Observable<WeatherData> getWeatherData(@Query("city") String str);

}
