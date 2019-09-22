package com.example.text.text1;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TextService {
    //获取启动页大图
    @GET("/weather_mini")
    Call<ResponseBody> getStartImage(@Query("city") String str);
}
