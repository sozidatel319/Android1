package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Switch;
import android.widget.Toast;

public class CityChangerActivity extends AppCompatActivity {
    private Switch info;
    private AutoCompleteTextView cityName;
    private String tag = "CityChangerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_changer);
        cityName = findViewById(R.id.citychanger);
        info = findViewById(R.id.info);
        cityName.setText(City_changerPresenter.getInstance().getCityName());
        info.setChecked(City_changerPresenter.getInstance().getInfoisChecked());


        findViewById(R.id.savecity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CityChangerActivity.this, MainActivity.class);
                if (cityName.getText().toString().matches("^\\S{3,}")) {
                    intent.putExtra(Constants.CITY_NAME, cityName.getText().toString());
                }
                    intent.putExtra(Constants.INFO, info.isChecked());
                    intent.putExtra(Constants.PRESSURE, "Давление в норме");
                    intent.putExtra(Constants.WIND_SPEED, "Ветер 5 м/с");

                City_changerPresenter.getInstance().setCityName(cityName.getText().toString());
                City_changerPresenter.getInstance().setInfoisChecked(info.isChecked());

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
        cityName.setText(City_changerPresenter.getInstance().getCityName());
        info.setChecked(City_changerPresenter.getInstance().getInfoisChecked());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        City_changerPresenter.getInstance().setCityName(cityName.getText().toString());
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
}
