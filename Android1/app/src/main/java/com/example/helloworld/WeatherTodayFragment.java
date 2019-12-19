package com.example.helloworld;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.helloworld.database.DataReader;
import com.example.helloworld.database.DataSource;
import com.example.helloworld.model.WeatherModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class WeatherTodayFragment extends Fragment implements WeatherProviderListener {
    private TextView cityName;
    private TextView pressure;
    private TextView wind;
    private TextView temperature_of_day;
    private TextView clouds;
    String tag = "frament_weather_today";

    // private TextView test;
    SharedPreferences sharedPreferences;

    private DataSource dataSource;
    private DataReader dataReader;
    private boolean city_in_db = false;

    public WeatherTodayFragment() {

    }

    private void getnowSharedPreferences(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            String[] keys = {Constants.CITY_NAME};
            String valueFirst = sharedPreferences.getString(keys[0], "Москва");
            cityName.setText(valueFirst);
            City_changerPresenter.getInstance().setCityName(sharedPreferences.getString(Constants.CITY_NAME, "Moscow"));
        }
    }

    private void saveSharedPReferences(SharedPreferences sharedPreferences) {
        String[] keys = {Constants.CITY_NAME};
        String[] values = {cityName.getText().toString()};
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keys[0], values[0]).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        restoreData(savedInstanceState);
        return inflater.inflate(R.layout.fragment_weather_today, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        WeatherProvider.getInstance().addListener(this);
        cityName = getActivity().findViewById(R.id.city);
        pressure = getActivity().findViewById(R.id.pressure);
        wind = getActivity().findViewById(R.id.wind);
        temperature_of_day = getActivity().findViewById(R.id.temperatureOfDay);
        temperature_of_day.setText(!SettingsPresenter.getInstance().getUnitofmeasure() ? R.string.gradus_forengheit : R.string.forengheight);
        clouds = getActivity().findViewById(R.id.weather_type);
        //test = getActivity().findViewById(R.id.test);
        sharedPreferences = getActivity().getSharedPreferences("now", Context.MODE_PRIVATE);
        getnowSharedPreferences(sharedPreferences);
        readFromDB();


        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                getnowSharedPreferences(sharedPreferences);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataSource = new DataSource(context);
        try {
            dataSource.open();
            dataReader = dataSource.getReader();
            dataReader.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //City_changerPresenter.getInstance().setCityName(cityName.getText().toString());
        City_changerPresenter.getInstance().setWind(wind.getText().toString());
        City_changerPresenter.getInstance().setPressure(pressure.getText().toString());
    }

    private void restoreData(Bundle savedInstanceState) {
        if (savedInstanceState == null) return;
        // cityName.setText(City_changerPresenter.getInstance().getCityName());

        if (City_changerPresenter.getInstance().getInfoisChecked()) {
            pressure.setVisibility(View.VISIBLE);
            pressure.setText(City_changerPresenter.getInstance().getPressure());
            wind.setVisibility(View.VISIBLE);
            wind.setText(City_changerPresenter.getInstance().getWind());
        }
    }

    @Override
    public void updateWeather(WeatherModel model, ArrayList<String> ti) {
        // City_changerPresenter.getInstance().setCityName(Objects.requireNonNull(cityName.getText()).toString());
        if (model == null) {
            Intent intent = new Intent(getActivity(), ErrorActivity.class);
            startActivity(intent);
            //cityName.setText("--");
            wind.setText("--");
            pressure.setText("--");
            temperature_of_day.setText("--");
            clouds.setText("--");
        } else {
            if (city_in_db) {
                //readFromDB();
            } else {
                double windSpeed = new BigDecimal(Double.toString(model.getList().get(0).getWind().getSpeed())).setScale(0, RoundingMode.HALF_UP).doubleValue();
                wind.setText("Ветер " + (int) windSpeed + " м/с");
                pressure.setText("Давление " + model.getList().get(0).getMain().getPressure());
                temperature_of_day.setText(WeatherProvider.getInstance().tempInGradus(0) + " C");
                clouds.setText("Облачность " + Integer.valueOf(model.getList().get(0).getClouds().getAll()));
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveToDB();
        saveSharedPReferences(sharedPreferences);
        WeatherProvider.getInstance().removeListener(this);
    }

    public void saveToDB() {
        String cityname = "";
        for (int i = 0; i < dataSource.getReader().getCount(); i++) {
            cityname = dataSource.getReader().getPosition(i).getCityname();
            if (cityname.equals(cityName.getText().toString())) {
                dataSource.edit(dataReader.getPosition(i), cityName.getText().toString(), temperature_of_day.getText().toString(),
                        clouds.getText().toString(), pressure.getText().toString(), wind.getText().toString());
                break;
            }
        }
        if (cityname.isEmpty() || !cityname.equals(cityName.getText().toString())) {
            dataSource.add(cityName.getText().toString(), temperature_of_day.getText().toString(),
                    clouds.getText().toString(), pressure.getText().toString(), wind.getText().toString());
        }
        dataReader.refresh();
        try {
            dataReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromDB() {
        if (dataReader.getCount() > 0) {
            for (int i = 0; i < dataReader.getCount(); i++) {
                if (dataReader.getPosition(i).getCityname().equals(cityName.getText().toString())) {
                    temperature_of_day.setText(dataReader.getPosition(i).getTemperature());
                    clouds.setText(dataReader.getPosition(i).getClouds());
                    pressure.setText(dataReader.getPosition(i).getPressure());
                    wind.setText(dataReader.getPosition(i).getWind());
                }
                city_in_db = true;
            }
            //   test.setText(String.valueOf(dataReader.getPosition(0).getClouds()));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == Constants.CITYCHANGER_CODE) {
            if (data.getStringExtra(Constants.CITY_NAME) != null) {
                cityName.setText(data.getStringExtra(Constants.CITY_NAME));
            }
            if (data.getBooleanExtra(Constants.INFO, false)) {
                pressure.setVisibility(View.VISIBLE);
                pressure.setText(data.getStringExtra(Constants.PRESSURE));
                wind.setVisibility(View.VISIBLE);
                wind.setText(data.getStringExtra(Constants.WIND_SPEED));
            } else {
                pressure.setVisibility(View.GONE);
                wind.setVisibility(View.GONE);
            }
        }

        if (requestCode == Constants.SETTINGS_CODE) {
            //recreate();

            if (data.getBooleanExtra(Constants.UNTIT_OF_MEASURE, false)) {
                temperature_of_day.setText(R.string.forengheight);
            } else {
                temperature_of_day.setText(R.string.gradus_forengheit);
            }

            if (data.getBooleanExtra(Constants.FONTSIZE, false)) {

            } else {
            }
        }
    }


}
