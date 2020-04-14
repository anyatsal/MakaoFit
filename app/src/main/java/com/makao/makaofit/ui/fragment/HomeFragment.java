package com.makao.makaofit.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.makao.makaofit.R;
import com.makao.makaofit.service.GoogleFitService;

public class HomeFragment extends Fragment {

    private GoogleFitService googleFitService;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        googleFitService = new GoogleFitService();

        initView(view);

        return view;
    }

    private void initView(View view) {
        Button stepsButton = view.findViewById(R.id.steps);
        TextView distance = view.findViewById(R.id.distance);
        googleFitService.setStepsCount(getContext(), stepsButton, distance);
        stepsButton.setOnClickListener(listener -> googleFitService.setStepsCount(getContext(), stepsButton, distance));

    }
}
