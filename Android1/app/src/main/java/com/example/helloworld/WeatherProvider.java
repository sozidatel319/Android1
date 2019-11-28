package com.example.helloworld;

import android.os.Build;
import android.os.Handler;

import com.example.helloworld.model.WeatherModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class WeatherProvider {
    private Set<WeatherProviderListener> listeners;
    private Timer timer;
    private static WeatherProvider instance = null;
    private Handler handler = new Handler();

    public WeatherProvider() {
        listeners = new HashSet<>();
        startData();
    }

    public static WeatherProvider getInstance() {
        instance = instance == null ? new WeatherProvider() : instance;
        return instance;
    }

    public void addListener(WeatherProviderListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(WeatherProviderListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    private WeatherModel getWeather(String cityname) {
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + cityname + ",RU&appid=0507febdbdf6a636ec6bdcdfe0b909fc";
        WeatherModel model = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                final String result = in.lines().collect(Collectors.joining("\n"));
                Gson gson = new Gson();
                model = gson.fromJson(result, WeatherModel.class);
            }
            City_changerPresenter.getInstance().setMistake(0);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            City_changerPresenter.getInstance().setMistake(1);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return model;
    }

    private void startData() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final WeatherModel model;
                model = getWeather(City_changerPresenter.getInstance().getCityName());
                if (model == null) {
                    return;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (WeatherProviderListener listener : listeners) {
                            listener.updateWeather(model);
                        }
                    }
                });

            }
        }, 2000, 10000);
    }

    void stop() {
        if (timer != null) {
            timer.cancel();
        }
        listeners.clear();
    }
}
