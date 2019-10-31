package com.example.text.mvp;

import com.example.text.bean.WeatherData;
import com.example.text.mvp.base.HttpResponseListener;

public class IWeatherContract {

    public interface IWeatherPresenter extends IBaseContract.IBasePresenter{

        public void getWeather(String city);
    }

    public interface IWeatherView extends IBaseContract.IBaseView{
        public void showWeahter(WeatherData weatherData);
    }

    public interface IWeatherModel extends IBaseContract.IBaseModel{
        public void getWeather(String city, HttpResponseListener listener);
    }
}
