package com.example.helloworld;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.helloworld.model.WeatherModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class WeatherTodayFragment extends Fragment implements WeatherProviderListener {
    private TextView cityName;
    private TextView pressure;
    private TextView wind;
    private TextView temperature_of_day;
    private TextView clouds;

    public WeatherTodayFragment(){

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

        cityName.setText(City_changerPresenter.getInstance().getCityName());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        City_changerPresenter.getInstance().setCityName(cityName.getText().toString());
        City_changerPresenter.getInstance().setWind(wind.getText().toString());
        City_changerPresenter.getInstance().setPressure(pressure.getText().toString());
    }

    private void restoreData(Bundle savedInstanceState) {
        if (savedInstanceState == null) return;
        cityName.setText(City_changerPresenter.getInstance().getCityName());

        if (City_changerPresenter.getInstance().getInfoisChecked()) {
            pressure.setVisibility(View.VISIBLE);
            pressure.setText(City_changerPresenter.getInstance().getPressure());
            wind.setVisibility(View.VISIBLE);
            wind.setText(City_changerPresenter.getInstance().getWind());
        }
    }

    @Override
    public void updateWeather(WeatherModel model, ArrayList<String> ti) {
        if (model == null) {
           Intent intent = new Intent(getActivity(),ErrorActivity.class);
            startActivity(intent);
            cityName.setText("--");
            wind.setText("--");
            pressure.setText("--");
            temperature_of_day.setText("--");
            clouds.setText("--");
        } else {

            cityName.setText(City_changerPresenter.getInstance().getCityName());
            double windSpeed = new BigDecimal(Double.toString(model.getList().get(0).getWind().getSpeed())).setScale(0, RoundingMode.HALF_UP).doubleValue();
            wind.setText("Ветер " + (int) windSpeed + " м/с");
            pressure.setText("Давление " + model.getList().get(0).getMain().getPressure());
            temperature_of_day.setText(WeatherProvider.getInstance().tempInGradus(0) + " C");
            clouds.setText("Облачность " + Integer.valueOf(model.getList().get(0).getClouds().getAll()));

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        WeatherProvider.getInstance().removeListener(this);
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
