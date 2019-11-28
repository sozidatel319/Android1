package com.example.helloworld;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class SettingsActivity extends BaseActivity {
    private Switch themecolor;
    private Switch font_size;
    private Switch unit_of_measure;
    private String tag = "SettingsActivity";
    boolean statusTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        init();
        restoreData(savedInstanceState);
    }

    private void init(){
        themecolor = findViewById(R.id.themecolor);
        font_size = findViewById(R.id.fontsize);
        unit_of_measure = findViewById(R.id.unitofmeasure);

        themecolor.setChecked(isDarkTheme());
        themecolor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                statusTheme = true;
                SettingsPresenter.getInstance().setThemecolor(statusTheme);
                setDarkTheme(isChecked);
                recreate();
            }
        });

        font_size.setChecked(SettingsPresenter.getInstance().getFontsize());
        unit_of_measure.setChecked(SettingsPresenter.getInstance().getUnitofmeasure());

        findViewById(R.id.savesettings_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Snackbar itsOk = Snackbar.make(v, R.string.savesettins, Snackbar.LENGTH_LONG);
                View snackBarView = itsOk.getView();
                snackBarView.setBackgroundColor(Color.RED);

                itsOk.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);

                        SettingsPresenter.getInstance().setFontsize(font_size.isChecked());
                        SettingsPresenter.getInstance().setUnitofmeasure(unit_of_measure.isChecked());

                        if (themecolor.isChecked()) {
                        }

                        intent.putExtra(Constants.FONTSIZE, font_size.isChecked());
                        intent.putExtra(Constants.UNTIT_OF_MEASURE, unit_of_measure.isChecked());

                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                itsOk.show();

                itsOk.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {

                            statusTheme = SettingsPresenter.getInstance().getThemecolor();
                            if (statusTheme) {
                                if (themecolor.isChecked()){
                                    themecolor.setChecked(false);
                                }else themecolor.setChecked(true);
                            }

                            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                            setResult(RESULT_CANCELED, intent);
                            finish();
                        }
                    }
                });


            }
        });
    }

    private void restoreData(Bundle savedInstanceState) {

        if (savedInstanceState == null) return;

        themecolor.setChecked(isDarkTheme());
        font_size.setChecked(SettingsPresenter.getInstance().getFontsize());
        unit_of_measure.setChecked(SettingsPresenter.getInstance().getUnitofmeasure());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        SettingsPresenter.getInstance().setThemecolor(isDarkTheme());
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
