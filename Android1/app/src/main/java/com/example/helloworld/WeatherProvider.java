package com.example.helloworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.example.helloworld.model.WeatherModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherProvider{
    private Set<WeatherProviderListener> listeners;
    private Timer timer;
    private static WeatherProvider instance = null;
    private Handler handler = new Handler();
    private SimpleDateFormat hours = new SimpleDateFormat("HH", Locale.ENGLISH);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private WeatherModel weatherModel;
    private ArrayList<String> dates;
    private Retrofit retrofit;
    private OpenWeather weatherApi;

    public WeatherProvider() {
        listeners = new HashSet<>();
        retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").
                addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()).build();
        weatherApi = retrofit.create(OpenWeather.class);
        startData();
    }

    interface OpenWeather {
        @GET("data/2.5/forecast")
        Call<WeatherModel> getWeather(@Query("q") String q, @Query("appid") String key);
    }

    public WeatherModel getWeatherModel() {
        return weatherModel;
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

    /*private WeatherModel getWeather(String cityname) {
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
    }*/


    private WeatherModel getWeather(String cityname) throws Exception {
        Call<WeatherModel> call = weatherApi.getWeather(cityname + ",RU", "0507febdbdf6a636ec6bdcdfe0b909fc");
        Response<WeatherModel> response = call.execute();
        if (response.isSuccessful()) return response.body();
        else throw new Exception(response.errorBody().string(), null);
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
                try {
                    weatherModel = getWeather(City_changerPresenter.getInstance().getCityName());
                    if (weatherModel == null) return;
                    dates = getDate(weatherModel);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for (WeatherProviderListener listener : listeners) {
                                listener.updateWeather(weatherModel, dates);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2000);
    }

    void stop() {
        if (timer != null) {
            timer.cancel();
        }
        listeners.clear();
    }

    public String tempInGradus(int numberofmodel) {
        double temp = (weatherModel.getList().get(numberofmodel).getMain().getTemp() - 273.15);
        double tempInCelvin = new BigDecimal(Double.toString(temp)).setScale(0, RoundingMode.HALF_UP).doubleValue();
        return ((int) tempInCelvin) + "°";
    }

    public String[] tempMinToWeek() {
        String[] result = new String[weatherModel.getList().size()];
        int i = 0;
        for (int j = 0; j < result.length; j += 8) {
            result[i] = getMinTempToday(j);
            i++;
        }
        return result;
    }

    private String getMinTempToday(int counter) {
        double minTemp = 273.15;
        double tempnow;
        for (int i = counter; i < counter + 8; i++) {
            tempnow = weatherModel.getList().get(i).getMain().getTempMin();
            if (tempnow < minTemp)
                minTemp = tempnow;
        }
        minTemp = minTemp - 273.15;
        double tempInCelvin = new BigDecimal(Double.toString(minTemp)).setScale(0, RoundingMode.HALF_UP).doubleValue();
        return ((int) tempInCelvin) + "°";
    }

    public String[] tempMaxToWeek() {
        String[] result = new String[weatherModel.getList().size()];
        int i = 0;
        for (int j = 0; j < result.length; j += 8) {
            result[i] = getMaxTempToday(j);
            i++;
        }
        return result;
    }

    private String getMaxTempToday(int counter) {
        double maxtemp = 273.15;
        double tempnow;
        for (int i = counter; i < counter + 8; i++) {
            tempnow = weatherModel.getList().get(i).getMain().getTempMax();
            if (tempnow > maxtemp)
                maxtemp = tempnow;
        }
        maxtemp = maxtemp - 273.15;
        double tempInCelvin = new BigDecimal(Double.toString(maxtemp)).setScale(0, RoundingMode.HALF_UP).doubleValue();
        return ((int) tempInCelvin) + "°";
    }
}
