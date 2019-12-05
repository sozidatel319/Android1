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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class WeatherProvider {
    private Set<WeatherProviderListener> listeners;
    private Timer timer;
    private static WeatherProvider instance = null;
    private Handler handler = new Handler();
    private SimpleDateFormat hours = new SimpleDateFormat("HH", Locale.ENGLISH);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private WeatherModel weatherModel;
    private ArrayList<String> dates;

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return model;
    }

    private ArrayList<String> getDate(WeatherModel model) {
        ArrayList<String> result = new ArrayList<>();
        try {
            for (int i = 0; i < model.getList().size(); i++) {
                Date d = simpleDateFormat.parse(model.getList().get(i).getDtTxt());
                int a = Integer.valueOf(hours.format(d));
                result.add(String.valueOf(a));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void startData() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //final WeatherModel model;
                weatherModel = getWeather(City_changerPresenter.getInstance().getCityName());
                dates = getDate(weatherModel);
                if (weatherModel == null) {
                    City_changerPresenter.getInstance().setMistake(1);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (WeatherProviderListener listener : listeners) {
                            listener.updateWeather(weatherModel, dates);
                        }
                    }
                });

            }
        }, 2000, 1000);
    }

    void stop() {
        if (timer != null) {
            timer.cancel();
        }
        listeners.clear();
    }
}
