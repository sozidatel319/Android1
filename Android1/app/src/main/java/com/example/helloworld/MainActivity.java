package com.example.helloworld;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.model.WeatherModel;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements WeatherProviderListener {
    private String tag = "MainActivity";
    TextView tempnow;
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
    }

    private void init() {

        temperature = new ArrayList<>();
        tempnow = findViewById(R.id.now);
        tempAfter3h = findViewById(R.id.temp3h);
        tempAfter6h = findViewById(R.id.temp6h);
        tempAfter9h = findViewById(R.id.temp9h);
        tempAfter12h = findViewById(R.id.temp12h);
        tempAfter15h = findViewById(R.id.temp15h);
        tempAfter18h = findViewById(R.id.temp18);
        tempAfter21h = findViewById(R.id.temp21);
        tempAfter24h = findViewById(R.id.temp0);
        timenow = findViewById(R.id.today);

        temperature.add(tempnow);
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
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
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
        Intent service = new Intent(this,WeatherService.class);
        service.putExtra(Constants.CITY_NAME,City_changerPresenter.getInstance().getCityName());
        startService(service);

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_citychange:
                startActivityForResult(new Intent(MainActivity.this, CityChangerActivity.class), Constants.CITYCHANGER_CODE);
                break;
            case R.id.menu_settings:
                startActivityForResult(new Intent(MainActivity.this, SettingsActivity.class), Constants.SETTINGS_CODE);
                break;
            case R.id.menu_aboutcity:
                String url = "https://ru.wikipedia.org/wiki/" + City_changerPresenter.getInstance().getCityName();
                Uri uri = Uri.parse(url);
                Intent browser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(browser);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateWeather(WeatherModel model, ArrayList<String> ti) {
        if (model == null) {
            Intent intent = new Intent(this, ErrorActivity.class);
            startActivity(intent);
            for (int i = 0; i < temperature.size(); i++) {
                temperature.get(i).setText("--");
            }
            for (int i = 1; i < time.size(); i++) {
                time.get(i).setText("--");
            }
        } else {
            for (int i = 0; i < temperature.size(); i++) {
                temperature.get(i).setText(WeatherProvider.getInstance().tempInGradus(i));
            }
            if (ti != null) {
                for (int i = 1; i < time.size(); i++) {
                    time.get(i).setText(ti.get(i));
                }
            }

        }
    }

}


