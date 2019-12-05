package com.example.helloworld;

import com.example.helloworld.model.WeatherModel;

import java.util.ArrayList;

public interface WeatherProviderListener {

    void updateWeather(WeatherModel model, ArrayList<String> time);
}
