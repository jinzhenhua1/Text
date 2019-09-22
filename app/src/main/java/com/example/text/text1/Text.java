package com.example.text.text1;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Text {
    public static void main(String[] args){
        Retrofit retrofit = new Retrofit.Builder()
                //设置baseUrl,注意baseUrl 应该以/ 结尾。
//                .baseUrl("http://wthrcdn.etouch.cn/weather_mini?city=广州")
                .baseUrl("http://wthrcdn.etouch.cn/")
                //使用Gson解析器,可以替换其他的解析器
                .addConverterFactory(GsonConverterFactory.create())
                //设置OKHttpClient,如果不设置会提供一个默认的
                .client(new OkHttpClient())
                .build();



        TextService service = retrofit.create(TextService.class);

//        Call<HashMap<String,String>> call = service.getStartImage("广州");
//
//        call.enqueue(new Callback<HashMap<String, String>>() {
//            @Override
//            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
//                System.out.println(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
//
//            }
//        });
    }
}
