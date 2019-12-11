package com.example.helloworld;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.helloworld.model.WeatherModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class WeatherService extends Service {

    @Override
    public int onStartCommand(@Nullable final Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    getWeather(intent.getStringExtra(Constants.CITY_NAME));
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private WeatherModel getWeather(String cityname) {
        String urlString = "http://api.openweathermap.org/data/2.5/forecast?q=" + cityname + ",RU&appid=0507febdbdf6a636ec6bdcdfe0b909fc";
        WeatherModel model = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                final String result = in.lines().collect(Collectors.joining("\n"));
                Gson gson = new Gson();
                model = gson.fromJson(result, WeatherModel.class);
                City_changerPresenter.getInstance().setMistake(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return model;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
