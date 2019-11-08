package com.example.helloworld;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toast toast;
    private String tag = "MainActivity";
    private AutoCompleteTextView cityName;
    private Switch info;
    private static String CITY_NAME = "city_name";
    private static String INFO = "info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_changer);
        cityName = findViewById(R.id.citychanger);
        info = findViewById(R.id.info);
        toast = Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_LONG);
        restoreData(savedInstanceState);
        toast.show();
        Log.d(tag, "onCreate");
    }

    private void restoreData(Bundle savedInstanceState){
        if (savedInstanceState == null) return;
        cityName.setText(savedInstanceState.getString(CITY_NAME,"Moscow"));
        info.setChecked(savedInstanceState.getBoolean(INFO));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CITY_NAME, cityName.getText().toString());
        outState.putBoolean(INFO, info.isChecked());
        toast = Toast.makeText(getApplicationContext(), "onSaveIntstanceState", Toast.LENGTH_LONG);
        toast.show();
        Log.d(tag, "onSaveIntstanceState");
    }

    @Override
    protected void onStart() {
        super.onStart();
        toast = Toast.makeText(getApplicationContext(), "onStart", Toast.LENGTH_LONG);
        toast.show();
        Log.d(tag, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        toast = Toast.makeText(getApplicationContext(), "onResume", Toast.LENGTH_LONG);
        toast.show();
        Log.d(tag, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        toast = Toast.makeText(getApplicationContext(), "onPause", Toast.LENGTH_LONG);
        toast.show();
        Log.d(tag, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        toast = Toast.makeText(getApplicationContext(), "onStop", Toast.LENGTH_LONG);
        toast.show();
        Log.d(tag, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag, "onDestroy");
        toast = Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_LONG);
        toast.show();
    }
}
