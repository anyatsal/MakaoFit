package com.makao.makaofit.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.makao.makaofit.R;
import com.makao.makaofit.service.GoogleFitService;

public class EditProfileActivity extends AppCompatActivity {

    private GoogleFitService googleFitService;
    private EditText heightView;
    private EditText weightView;

    private TextView heightError;
    private TextView weightError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        googleFitService = new GoogleFitService();
        heightView = findViewById(R.id.edit_height);
        weightView = findViewById(R.id.edit_weight);
        heightError = findViewById(R.id.height_error);
        weightError = findViewById(R.id.weight_error);
    }

    @SuppressLint("ShowToast")
    public void onSave(View view) {
        String height = heightView.getText().toString();
        String weight = weightView.getText().toString();

        if (isValid(height, true, heightError)) {
            googleFitService.updateHeight(this, height);
        }

        if (isValid(weight, false, weightError)) {
            googleFitService.updateWeight(this, weight);
        }

        if (heightError.getVisibility() == View.GONE && weightError.getVisibility() == View.GONE) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean isValid(String temp, Boolean isHeight, TextView error) {
        if (!isEmpty(temp)) {
            try {
                float var = Float.parseFloat(temp);
                if (isHeight) {
                    if (0.6 < var && var < 2.2) {
                        error.setVisibility(View.GONE);
                        return true;
                    } else {
                        error.setVisibility(View.VISIBLE);
                        return false;
                    }
                } else {
                    if (30 < var && var < 120) {
                        error.setVisibility(View.GONE);
                        return true;
                    } else {
                        error.setVisibility(View.VISIBLE);
                        return false;
                    }
                }
            } catch (Exception e) {
                error.setVisibility(View.VISIBLE);
                return false;
            }
        }
        return false;
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}