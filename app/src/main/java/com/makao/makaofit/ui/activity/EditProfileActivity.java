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

    private final String HEIGHT_PATTERN = "^[01][.,][0-9]{1,3}$";
    private final String WEIGHT_PATTERN = "^[1-9][0-9]{1,2}[.,][0-9]{1,3}$";

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
            if (isValidHeight(height)) {
                googleFitService.updateHeight(this, height);
            }
            if (isValidWeight(weight)) {
                googleFitService.updateWeight(this, weight);
            }
        } else {
            Toast.makeText(this, "Although one field must be filled!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean isValidHeight(String height) {
        if (!isEmpty(height)) {
            boolean isValid = height.matches(HEIGHT_PATTERN);

            if (isValid)
                return true;
            else
                heightView.setError("Not valid!");
        }
        return false;
    }

    private boolean isValidWeight(String weight) {
        if (!isEmpty(weight)) {
            boolean isValid = weight.matches(WEIGHT_PATTERN);

            if (isValid)
                return true;
            else
                weightView.setError("Not valid!");
        }
        return false;
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
