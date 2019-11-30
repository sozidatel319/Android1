package com.example.helloworld;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.model.WeatherModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.regex.Pattern;

public class CityChangerActivity extends BaseActivity implements WeatherProviderListener {
    private Switch info;
    private TextInputEditText inputCityName;
    private String tag = "CityChangerActivity";
    private boolean isFirstOpened = City_changerPresenter.getInstance().getOpened();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_changer);
        final Pattern patternCityName = Pattern.compile("^\\p{Lu}\\p{Ll}+(((-|\\s)\\p{Ll}+)?(-|\\s)\\p{Lu}\\p{Ll}+)?");
        inputCityName = findViewById(R.id.inputcity);
        inputCityName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) return;
                if (patternCityName.matcher(((TextView) v).getText().toString()).matches()) {
                    ((TextView) v).setError(null);
                } else {
                    ((TextView) v).setError(getResources().getText(R.string.cityinputerror));
                }
            }
        });
        info = findViewById(R.id.info);
        inputCityName.setText(City_changerPresenter.getInstance().getCityName());
        info.setChecked(City_changerPresenter.getInstance().getInfoisChecked());


        findViewById(R.id.savecity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CityChangerActivity.this, MainActivity.class);
                if (Objects.requireNonNull(inputCityName.getText()).toString().matches("^\\p{Lu}\\p{Ll}+(((-|\\s)\\p{Ll}+)?(-|\\s)\\p{Lu}\\p{Ll}+)?")) {
                    intent.putExtra(Constants.CITY_NAME, inputCityName.getText().toString());
                }
                intent.putExtra(Constants.INFO, info.isChecked());
                intent.putExtra(Constants.PRESSURE, "Давление в норме");
                intent.putExtra(Constants.WIND_SPEED, "Ветер 5 м/с");

               // City_changerPresenter.getInstance().setCityName(inputCityName.getText().toString());
                City_changerPresenter.getInstance().setInfoisChecked(info.isChecked());
                if (isFirstOpened) {
                   // City_changerPresenter.getInstance().setMistake(0);
                    City_changerPresenter.getInstance().setOpened(false);

                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Log.d(tag, "onCreate");
        Toast.makeText(getApplicationContext(), "onCreate_" + tag, Toast.LENGTH_LONG).show();
        restoreData(savedInstanceState);
    }

    private void restoreData(Bundle savedInstanceState) {
        if (savedInstanceState == null) return;
        inputCityName.setText(City_changerPresenter.getInstance().getCityName());
        info.setChecked(City_changerPresenter.getInstance().getInfoisChecked());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        City_changerPresenter.getInstance().setCityName(Objects.requireNonNull(inputCityName.getText()).toString());
        City_changerPresenter.getInstance().setInfoisChecked(info.isChecked());
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
        Log.d(tag, "onResume");
        Toast.makeText(getApplicationContext(), "onResume_" + tag, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        WeatherProvider.getInstance().removeListener(this);
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
    public void updateWeather(WeatherModel model) {
        City_changerPresenter.getInstance().setCityName(inputCityName.getText().toString());
    }
}
