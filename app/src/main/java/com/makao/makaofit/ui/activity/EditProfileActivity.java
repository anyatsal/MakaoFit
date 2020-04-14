package com.makao.makaofit.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.makao.makaofit.R;
import com.makao.makaofit.service.GoogleFitService;

public class EditProfileActivity extends AppCompatActivity {

    private GoogleFitService googleFitService;
    private EditText heightView;
    private EditText weightView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        googleFitService = new GoogleFitService();
        heightView = findViewById(R.id.edit_height);
        weightView = findViewById(R.id.edit_weight);
    }

    @SuppressLint("ShowToast")
    public void onSave(View view) {
        String height = heightView.getText().toString();
        String weight = weightView.getText().toString();

        if (!isEmpty(height) || !isEmpty(weight)) {
            if (isValid(height)) {
                googleFitService.updateHeight(this, height);
            }
            if (isValid(weight)) {
                googleFitService.updateWeight(this, weight);
            }
            finish();
        } else {
            Toast.makeText(this, "Although one field must be filled!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean isValid(String temp) {
        if (!isEmpty(temp)) {
            try {
                float var = Float.parseFloat(temp);
                return true;
            } catch (Exception e) {
                heightView.setError("Not valid!");
                return false;
            }
        }
        return false;
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}