package com.example.text.mvp.persenter;

import com.example.text.bean.ResponseData;
import com.example.text.bean.WeatherData;
import com.example.text.mvp.IWeatherContract;
import com.example.text.mvp.base.BasePresenter;
import com.example.text.mvp.base.HttpResponseListener;
import com.example.text.mvp.model.WeatherModel;

public class WeatherPresenter extends BasePresenter<IWeatherContract.IWeatherView> implements IWeatherContract.IWeatherPresenter {
    private WeatherModel model;

    public WeatherPresenter(IWeatherContract.IWeatherView view){
        super(view);
        model = new WeatherModel();
    }

    @Override
    public void getWeather(String city) {
        model.getWeather(city, new HttpResponseListener<WeatherData>() {
            @Override
            public void onSuccess(Object tag, WeatherData weatherData) {
                getView().showWeahter(weatherData);
            }

            @Override
            public void onFailure(Throwable throwable) {
                getView().showError(throwable.toString());
            }
        });
    }
}
