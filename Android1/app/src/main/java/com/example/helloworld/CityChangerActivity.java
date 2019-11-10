package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class CityChangerActivity extends AppCompatActivity {
    private Switch info;
    private AutoCompleteTextView cityName;
    private static String INFO = "info";
    private String tag = "CityChangerActivity";
    private TextView city1;
    private TextView city2;
    private TextView city3;
    private TextView city4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_changer);


        cityName = findViewById(R.id.citychanger);
        cityName.setText(City_changerPresenter.getInstance().getCityName());
        info = findViewById(R.id.info);
        city1 = findViewById(R.id.city1);
        city2 = findViewById(R.id.city2);
        city3 = findViewById(R.id.city3);
        city4 = findViewById(R.id.city4);

        findViewById(R.id.savecity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        city1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName.setText(city1.getText());
            }
        });
        city2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName.setText(city2.getText());
            }
        });
        city3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName.setText(city3.getText());
            }
        });
        city4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName.setText(city4.getText());
            }
        });

        Log.d(tag, "onCreate");
        Toast.makeText(getApplicationContext(), "onCreate_" + tag, Toast.LENGTH_LONG).show();
        restoreData(savedInstanceState);
    }

    private void restoreData(Bundle savedInstanceState) {
        if (savedInstanceState == null) return;

        if (info != null) {
            info.setChecked(savedInstanceState.getBoolean(INFO));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (info != null) {
            outState.putBoolean(INFO, info.isChecked());
        }
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
