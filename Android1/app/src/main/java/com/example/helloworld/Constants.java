package com.example.helloworld;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Constants extends AppCompatActivity {
    static final String CITY_NAME = "cityname";
    static final String INFO = "info";
    static final String PRESSURE = "pressure";
    static final String WIND_SPEED = "windspeed";
    static final String THEME = "theme";
    static final String FONTSIZE = "fontsize";
    static final String UNTIT_OF_MEASURE = "unitofmeasure";
    static final int CITYCHANGER_CODE = 7;
    static final int SETTINGS_CODE = 90;

    public static void logAndToast(String text, String tag){
        Activity activity = new Activity();
        Log.d(tag, text);
        Toast.makeText(activity.getApplicationContext(),text + "_" + tag, Toast.LENGTH_LONG).show();
    }
}



