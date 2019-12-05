package com.example.helloworld;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.model.WeatherModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements WeatherProviderListener {

    private TextView cityName;
    private TextView pressure;
    private TextView wind;
    private TextView temperature_of_day;
    private TextView clouds;
    private String tag = "MainActivity";
    private static final int CITYCHANGER_CODE = 7;
    private static final int SETTINGS_CODE = 90;

    TextView now;
    TextView tempAfter3h;
    TextView tempAfter6h;
    TextView tempAfter9h;
    TextView tempAfter12h;
    TextView tempAfter15h;
    TextView tempAfter18h;
    TextView tempAfter21h;
    TextView tempAfter24h;

    TextView timenow;
    TextView time3;
    TextView time6;
    TextView time9;
    TextView time12;
    TextView time15;
    TextView time18;
    TextView time21;
    TextView time0;
    ArrayList<TextView> time;
    ArrayList<TextView> temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Log.d(tag, "onCreate");
        Toast.makeText(getApplicationContext(), "onCreate_" + tag, Toast.LENGTH_LONG).show();
        restoreData(savedInstanceState);
    }

    private void init() {

        cityName = findViewById(R.id.city);
        pressure = findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);
        temperature_of_day = findViewById(R.id.temperatureOfDay);
        temperature_of_day.setText(!SettingsPresenter.getInstance().getUnitofmeasure() ? R.string.gradus_forengheit : R.string.forengheight);
        clouds = findViewById(R.id.weather_type);

        temperature = new ArrayList<>();
        now = findViewById(R.id.now);
        tempAfter3h = findViewById(R.id.temp3h);
        tempAfter6h = findViewById(R.id.temp6h);
        tempAfter9h = findViewById(R.id.temp9h);
        tempAfter12h = findViewById(R.id.temp12h);
        tempAfter15h = findViewById(R.id.temp15h);
        tempAfter18h = findViewById(R.id.temp18);
        tempAfter21h = findViewById(R.id.temp21);
        tempAfter24h = findViewById(R.id.temp0);
        timenow = findViewById(R.id.today);

        temperature.add(now);
        temperature.add(tempAfter3h);
        temperature.add(tempAfter6h);
        temperature.add(tempAfter9h);
        temperature.add(tempAfter12h);
        temperature.add(tempAfter15h);
        temperature.add(tempAfter18h);
        temperature.add(tempAfter21h);
        temperature.add(tempAfter24h);

        time = new ArrayList<>();

        time3 = findViewById(R.id.time3now);
        time6 = findViewById(R.id.time6);
        time9 = findViewById(R.id.time9);
        time12 = findViewById(R.id.time12);
        time15 = findViewById(R.id.time15);
        time18 = findViewById(R.id.time18);
        time21 = findViewById(R.id.time21);
        time0 = findViewById(R.id.time0);

        time.add(timenow);
        time.add(time3);
        time.add(time6);
        time.add(time9);
        time.add(time12);
        time.add(time15);
        time.add(time18);
        time.add(time21);
        time.add(time0);

        cityName.setText(City_changerPresenter.getInstance().getCityName());
        findViewById(R.id.city_changer_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, CityChangerActivity.class), CITYCHANGER_CODE);
            }
        });
        findViewById(R.id.settings_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class), SETTINGS_CODE);
            }
        });

        findViewById(R.id.buttonUri).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ru.wikipedia.org/wiki/" + cityName.getText().toString();
                Uri uri = Uri.parse(url);
                Intent browser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browser);
            }
        });


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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(tag, "onSaveIntstanceState");
        Toast.makeText(getApplicationContext(), "onSaveIntstanceState_" + tag, Toast.LENGTH_LONG).show();
        City_changerPresenter.getInstance().setCityName(cityName.getText().toString());
        City_changerPresenter.getInstance().setWind(wind.getText().toString());
        City_changerPresenter.getInstance().setPressure(pressure.getText().toString());
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(tag, "onStart");
        Toast.makeText(getApplicationContext(), "onStart_" + tag, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();


        WeatherProvider.getInstance().addListener(this);
        if (City_changerPresenter.getInstance().getMistake() == 1) {
            Intent intent = new Intent(this, ErrorActivity.class);
            startActivity(intent);
        }
        Log.d(tag, "onResume");
        Toast.makeText(getApplicationContext(), "onResume_" + tag, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, "onPause");
        Toast.makeText(getApplicationContext(), "onPause_" + tag, Toast.LENGTH_LONG).show();
        WeatherProvider.getInstance().removeListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, "onStop");
        Toast.makeText(getApplicationContext(), "onStop_" + tag, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag, "onDestroy");
        Toast.makeText(getApplicationContext(), "onDestroy_" + tag, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == CITYCHANGER_CODE) {
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

        if (requestCode == SETTINGS_CODE) {
            recreate();

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

    public String tempInGradus(WeatherModel model, int numberofmodel) {
        double temp = (model.getList().get(numberofmodel).getMain().getTemp() - 273.15);
        double tempInCelvin = new BigDecimal(Double.toString(temp)).setScale(0, RoundingMode.HALF_UP).doubleValue();
        return ((int) tempInCelvin) + "°";
    }

    @Override
    public void updateWeather(WeatherModel model, ArrayList<String> ti) {
        if (model == null) {
            Intent intent = new Intent(this, ErrorActivity.class);
            startActivity(intent);
            cityName.setText("--");
            wind.setText("--");
            pressure.setText("--");
            temperature_of_day.setText("--");
            clouds.setText("--");
            for (int i = 0; i < temperature.size(); i++) {
                temperature.get(i).setText("--");
            }
            for (int i = 1; i < time.size(); i++) {
                time.get(i).setText("--");
            }
        } else {

            cityName.setText(City_changerPresenter.getInstance().getCityName());
            double windSpeed = new BigDecimal(Double.toString(model.getList().get(0).getWind().getSpeed())).setScale(0, RoundingMode.HALF_UP).doubleValue();
            wind.setText("Ветер " + (int) windSpeed + " м/с");
            pressure.setText("Давление " + model.getList().get(0).getMain().getPressure());
            temperature_of_day.setText(tempInGradus(model, 0) + " C");
            clouds.setText("Облачность " + Integer.valueOf(model.getList().get(0).getClouds().getAll()));

            for (int i = 0; i < temperature.size(); i++) {
                temperature.get(i).setText(tempInGradus(model, i));
            }
            if (ti != null) {
                for (int i = 1; i < time.size(); i++) {
                    time.get(i).setText(ti.get(i));
                }
            }

        }
    }
}


