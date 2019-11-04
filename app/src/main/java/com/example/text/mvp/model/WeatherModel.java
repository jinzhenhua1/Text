package com.example.text.mvp.model;

import com.example.text.mvp.IWeatherContract;
import com.example.text.mvp.base.BaseModel;
import com.example.text.mvp.base.HttpResponseListener;
import com.example.text.text1.TextService;

/**
 * 调用获取天气接口
 */
public class WeatherModel extends BaseModel implements IWeatherContract.IWeatherModel {

    @Override
    public void getWeather(String city, HttpResponseListener listener) {
        sendRequestUntilStop(getService(TextService.class).getWeatherData(city),listener);

    }
}
