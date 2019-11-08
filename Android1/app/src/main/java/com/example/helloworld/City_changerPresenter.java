package com.example.helloworld;

import android.widget.AutoCompleteTextView;

public final class City_changerPresenter {

    String cityName = "Moscow";

    private static City_changerPresenter instance;

    public static City_changerPresenter getInstance() {
        instance = instance == null ? new City_changerPresenter() : instance;
        return instance;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
