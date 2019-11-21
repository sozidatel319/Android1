package com.example.helloworld;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class CityFragment extends Fragment {
    private TextView city1;
    private TextView city2;
    private TextView city3;
    private TextView city4;
    private AutoCompleteTextView cityName;
    public CityFragment() {
        // Required empty public constructor
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
    }

    @Override
    public void onResume() {
        super.onResume();
        cityName = getActivity().findViewById(R.id.citychanger);
        city1 = getActivity().findViewById(R.id.city1);
        city2 = getActivity().findViewById(R.id.city2);
        city3 = getActivity().findViewById(R.id.city3);
        city4 = getActivity().findViewById(R.id.city4);
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
    }
}
