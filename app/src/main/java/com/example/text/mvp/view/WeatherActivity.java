package com.example.text.mvp.view;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.example.text.R;
import com.example.text.bean.WeatherData;
import com.example.text.mvp.IWeatherContract;
import com.example.text.mvp.base.BaseMvpActivity;
import com.example.text.mvp.persenter.WeatherPresenter;

public class WeatherActivity extends BaseMvpActivity<WeatherPresenter> implements IWeatherContract.IWeatherView {
    private Button btn_weather;
    private TextView tv_weather;

    @Override
    public void showWeahter(WeatherData weatherData) {
        tv_weather.setText(weatherData.toString());
    }

    @Override
    protected void initViewAndData(Bundle savedInstanceState) {
        btn_weather = findViewById(R.id.btn_weather);
        tv_weather = findViewById(R.id.tv_weather);

        btn_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getWeather("广州");
            }
        });
    }

    @Override
    protected WeatherPresenter createPresenter() {
        return new WeatherPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weather;
    }
}
