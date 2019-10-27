package com.example.text.bean;

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
}
