package com.makao.makaofit.service;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataUpdateRequest;

import java.util.Calendar;
import java.util.Date;
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

    public void updateHeight(Context context, String height) {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        cal.add(Calendar.MINUTE, 0);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.MINUTE, -50);
        long startTime = cal.getTimeInMillis();


        DataSet dataSet = createDataSetForRequest(DataType.TYPE_HEIGHT, DataSource.TYPE_RAW, Float.parseFloat(height), context, startTime, endTime);
        DataUpdateRequest request = new DataUpdateRequest.Builder()
                .setDataSet(dataSet)
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        processUpdateRequest(context, request);

    }

    public void updateWeight(Context context, String weight) {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        cal.add(Calendar.MINUTE, 0);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.MINUTE, -50);
        long startTime = cal.getTimeInMillis();


        DataSet dataSet = createDataSetForRequest(DataType.TYPE_WEIGHT, DataSource.TYPE_RAW, Float.parseFloat(weight), context, startTime, endTime);
        DataUpdateRequest request = new DataUpdateRequest.Builder()
                .setDataSet(dataSet)
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();

        processUpdateRequest(context, request);

    }

    private DataSet createDataSetForRequest(DataType dataType, int dataSourceType, Object value, Context context, long startTime, long endTime) {
        DataSource dataSource = new DataSource.Builder()
                .setAppPackageName(context)
                .setDataType(dataType)
                .setType(dataSourceType)
                .build();

        DataPoint.Builder builder =
                DataPoint.builder(dataSource)
                        .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
        builder = value instanceof Integer ? builder.setIntValues((Integer) value) : builder.setFloatValues((Float) value);
        DataPoint dataPoint = builder.build();

        return DataSet.builder(dataSource).add(dataPoint).build();
    }

    private void processUpdateRequest(Context context, DataUpdateRequest request) {
        Fitness.getHistoryClient(context, GoogleSignIn.getLastSignedInAccount(context)).updateData(request)
                .addOnSuccessListener(listener -> {
                    System.out.println("yyyy");
                })
                .addOnFailureListener(listener -> {
                    System.out.println("aaaaa");
                });
    }

    public FitnessOptions getFitnessOptions() {
        return fitnessOptions;
    }
}
