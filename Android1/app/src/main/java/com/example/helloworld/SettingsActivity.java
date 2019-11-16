package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private Switch themecolor;
    private Switch font_size;
    private Switch unit_of_measure;
    private String tag = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        themecolor = findViewById(R.id.themecolor);
        font_size = findViewById(R.id.fontsize);
        unit_of_measure = findViewById(R.id.unitofmeasure);

        themecolor.setChecked(SettingsPresenter.getInstance().getThemecolor());
        font_size.setChecked(SettingsPresenter.getInstance().getFontsize());
        unit_of_measure.setChecked(SettingsPresenter.getInstance().getUnitofmeasure());

        findViewById(R.id.savesettings_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                SettingsPresenter.getInstance().setThemecolor(themecolor.isChecked());
                SettingsPresenter.getInstance().setFontsize(font_size.isChecked());
                SettingsPresenter.getInstance().setUnitofmeasure(unit_of_measure.isChecked());

                intent.putExtra(Constants.THEME, themecolor.isChecked());
                intent.putExtra(Constants.FONTSIZE, font_size.isChecked());
                intent.putExtra(Constants.UNTIT_OF_MEASURE, unit_of_measure.isChecked());

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        restoreData(savedInstanceState);
    }

    private void restoreData(Bundle savedInstanceState) {

        if (savedInstanceState == null) return;

        themecolor.setChecked(SettingsPresenter.getInstance().getThemecolor());
        font_size.setChecked(SettingsPresenter.getInstance().getFontsize());
        unit_of_measure.setChecked(SettingsPresenter.getInstance().getUnitofmeasure());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        SettingsPresenter.getInstance().setThemecolor(themecolor.isChecked());
        SettingsPresenter.getInstance().setFontsize(font_size.isChecked());
        SettingsPresenter.getInstance().setUnitofmeasure(unit_of_measure.isChecked());

        Log.d(tag, "onSaveIntstanceState");
        Toast.makeText(getApplicationContext(), "onSaveIntstanceState_" + tag, Toast.LENGTH_LONG).show();
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
