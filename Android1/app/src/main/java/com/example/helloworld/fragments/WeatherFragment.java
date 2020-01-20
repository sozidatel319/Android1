package com.example.helloworld.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.helloworld.DaysOfWeekAdapter;
import com.example.helloworld.R;
import com.example.helloworld.WeatherProvider;
import com.example.helloworld.WeatherProviderListener;
import com.example.helloworld.model.WeatherModel;

import java.util.ArrayList;
import java.util.Objects;


public class WeatherFragment extends Fragment implements WeatherProviderListener {
    private RecyclerView recyclerView;
    private String[] daysofweek;
    private String[] mintemptoweek;
    private String[] maxtempofweek;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        WeatherProvider.getInstance().addListener(this);
        initRecyclerView();
    }

    @Override
    public void onPause() {
        super.onPause();
        WeatherProvider.getInstance().removeListener(this);
    }

    private void initRecyclerView() {
        recyclerView = Objects.requireNonNull(getActivity()).findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        daysofweek = getResources().getStringArray(R.array.daysofweek);
        mintemptoweek = new String[7];
        mintemptoweek = new String[]{"--", "--", "--", "--", "--", "--", "--"};
        maxtempofweek = mintemptoweek;
        recyclerView.setAdapter(new DaysOfWeekAdapter(daysofweek, mintemptoweek, maxtempofweek));
    }

    @Override
    public void updateWeather(WeatherModel model, ArrayList<String> time) {
        if (model != null) {
            daysofweek = getResources().getStringArray(R.array.daysofweek);
            mintemptoweek = WeatherProvider.getInstance().tempMinToWeek();
            maxtempofweek = WeatherProvider.getInstance().tempMaxToWeek();
            recyclerView.setAdapter(new DaysOfWeekAdapter(daysofweek, mintemptoweek, maxtempofweek));
        }
    }
}
