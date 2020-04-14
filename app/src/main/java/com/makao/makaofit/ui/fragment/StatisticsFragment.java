package com.makao.makaofit.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.makao.makaofit.R;
import com.makao.makaofit.service.GoogleFitService;

public class StatisticsFragment extends Fragment {

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        GoogleFitService googleFitService = new GoogleFitService();
        TextView stepsCountView = view.findViewById(R.id.steps_count);
        TextView distance = view.findViewById(R.id.distance);
        googleFitService.setStepsCount(this.getContext(), stepsCountView, distance);

        return view;
    }
}