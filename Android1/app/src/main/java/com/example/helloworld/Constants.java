package com.example.helloworld;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Constants extends AppCompatActivity {
    public static final String CITY_NAME = "cityname";
    public static final String INFO = "info";
    public static final String PRESSURE = "pressure";
    public static final String WIND_SPEED = "windspeed";
    public static final String THEME = "theme";
    public static final String FONTSIZE = "fontsize";
    public static final String UNTIT_OF_MEASURE = "unitofmeasure";
    public static final int CITYCHANGER_CODE = 7;
    public static final int SETTINGS_CODE = 90;

    public static void logAndToast(String text, String tag){
        Activity activity = new Activity();
        Log.d(tag, text);
        Toast.makeText(activity.getApplicationContext(),text + "_" + tag, Toast.LENGTH_LONG).show();
    }
}



