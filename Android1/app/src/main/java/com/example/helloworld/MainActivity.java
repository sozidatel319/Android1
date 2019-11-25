package com.example.helloworld;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    private TextView cityName;
    private TextView pressure;
    private TextView wind;
    private TextView temperature_of_day;
    private String tag = "MainActivity";
    private static final int CITYCHANGER_CODE = 7;
    private static final int SETTINGS_CODE = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.city);
        pressure = findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);
        temperature_of_day = findViewById(R.id.temperatureOfDay);
        temperature_of_day.setText(!SettingsPresenter.getInstance().getUnitofmeasure() ? R.string.gradus_forengheit : R.string.forengheight);

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

        Log.d(tag, "onCreate");
        Toast.makeText(getApplicationContext(), "onCreate_" + tag, Toast.LENGTH_LONG).show();
        restoreData(savedInstanceState);
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
        Log.d(tag, "onResume");
        Toast.makeText(getApplicationContext(), "onResume_" + tag, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, "onPause");
        Toast.makeText(getApplicationContext(), "onPause_" + tag, Toast.LENGTH_LONG).show();
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
}



