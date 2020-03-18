package com.makao.makaofit.service;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class GoogleFitService {

    private final String TAG = "GoogleFitService";
    private final FitnessOptions fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_WEIGHT, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_HEIGHT, FitnessOptions.ACCESS_READ)
            .build();

    public void setStepsCount(Context context, TextView view) {
        Fitness.getHistoryClient(context, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(dataReadResponse -> {
                    if (!dataReadResponse.isEmpty()) {
                        view.setText(String.format("%s Steps\nWooooooow!", dataReadResponse.getDataPoints().get(0).getValue(Field.FIELD_STEPS).toString()));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "There was a problem getting the step count.", e);
                });
    }

    public void setWeight(Context context, TextView view) {
        Calendar calendar = Calendar.getInstance();

        DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_WEIGHT)
                .setTimeRange(1, calendar.getTimeInMillis(), TimeUnit.MILLISECONDS)
                .setLimit(1)
                .build();

        Fitness.getHistoryClient(context, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
                .readData(dataReadRequest)
                .addOnSuccessListener(dataReadResponse -> {
                    if (dataReadResponse.getStatus().isSuccess()) {
                        String weight = dataReadResponse.getDataSet(DataType.TYPE_WEIGHT).getDataPoints().get(0).getValue(Field.FIELD_WEIGHT).toString();
                        view.setText(weight);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "There was a problem getting the weight.", e);
                });
    }

    public void setHeight(Context context, TextView view) {
        Calendar calendar = Calendar.getInstance();

        DataReadRequest dataReadRequest = new DataReadRequest.Builder()
                .read(DataType.TYPE_HEIGHT)
                .setTimeRange(1, calendar.getTimeInMillis(), TimeUnit.MILLISECONDS)
                .setLimit(1)
                .build();

        Fitness.getHistoryClient(context, GoogleSignIn.getAccountForExtension(context, fitnessOptions))
                .readData(dataReadRequest)
                .addOnSuccessListener(dataReadResponse -> {
                    if (dataReadResponse.getStatus().isSuccess()) {
                        String height = dataReadResponse.getDataSet(DataType.TYPE_HEIGHT).getDataPoints().get(0).getValue(Field.FIELD_HEIGHT).toString();
                        view.setText(height);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "There was a problem getting the height.", e);
                });
    }

    public FitnessOptions getFitnessOptions() {
        return fitnessOptions;
    }
}
