package com.example.helloworld;

import com.example.helloworld.model.WeatherModel;

public interface WeatherProviderListener {

    void updateWeather(WeatherModel model);
}
