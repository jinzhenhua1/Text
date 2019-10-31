package com.example.text.bean;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Data中的数据
 */
public class ContextData {
    private Yesterday yesterday;
    private String city = "";
    private List<Forecast> forecast;
    private String ganmao = "";
    private String wendu = "";

    public void setYesterday(Yesterday yesterday) {
        this.yesterday = yesterday;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public Yesterday getYesterday() {
        return yesterday;
    }

    public String getCity() {
        return city;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    public String getGanmao() {
        return ganmao;
    }

    public String getWendu() {
        return wendu;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuffer str = new StringBuffer("{");
        str.append("yesterday:").append(yesterday).append(",");
        str.append("city:").append(city).append(",");
        str.append("ganmao:").append(ganmao).append(",");
        str.append("wendu:").append(wendu).append(",");
        str.append("forecast:").append(forecast.toString()).append("}");

        return str.toString();
    }
}
