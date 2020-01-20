package com.example.helloworld.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.helloworld.City_changerPresenter;
import com.example.helloworld.Constants;
import com.example.helloworld.R;
import com.google.android.material.textfield.TextInputEditText;

public class CityFragment extends Fragment {
    private TextView city1;
    private TextView city2;
    private TextView city3;
    private TextView city4;
    private TextInputEditText cityName;

    SharedPreferences sharedPreferences;

    public CityFragment() {
        // Required empty public constructor
    }

    private void saveSharedPReferences(SharedPreferences sharedPreferences) {
        String[] keys = {Constants.CITY_NAME};
        String[] values = {cityName.getText().toString()};
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //Log.d("Editor", String.valueOf(editor.hashCode()));
        sharedPreferences.edit().putString(keys[0], values[0]).commit();
        //editor.clear();
        //editor.commit();
        //editor.putString(keys[0], values[0]);
        //editor.commit();
    }

    private void getnowSharedPreferences(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            String[] keys = {Constants.CITY_NAME};
            String valueFirst = sharedPreferences.getString(keys[0], "Москва");
            cityName.setText(valueFirst);
            City_changerPresenter.getInstance().setCityName(valueFirst);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        saveSharedPReferences(sharedPreferences);
    }

    @Override
    public void onResume() {
        super.onResume();

        cityName = getActivity().findViewById(R.id.inputcity);
        city1 = getActivity().findViewById(R.id.city1);
        city2 = getActivity().findViewById(R.id.city2);
        city3 = getActivity().findViewById(R.id.city3);
        city4 = getActivity().findViewById(R.id.city4);
        city1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName.setText(city1.getText());
                City_changerPresenter.getInstance().setCityName(city1.getText().toString());
            }
        });
        city2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName.setText(city2.getText());
                City_changerPresenter.getInstance().setCityName(city2.getText().toString());

            }
        });
        city3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName.setText(city3.getText());
                City_changerPresenter.getInstance().setCityName(city3.getText().toString());
            }
        });
        city4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName.setText(city4.getText());
                City_changerPresenter.getInstance().setCityName(city4.getText().toString());
            }
        });
        sharedPreferences = getActivity().getSharedPreferences("now", Context.MODE_PRIVATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

