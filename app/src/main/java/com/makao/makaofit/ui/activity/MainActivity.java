package com.makao.makaofit.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.FitnessOptions;
import com.makao.makaofit.R;
import com.makao.makaofit.service.GoogleFitService;

public class MainActivity extends AppCompatActivity {

    final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = System.identityHashCode(this) & 0xFFFF;

    private FitnessOptions fitnessOptions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleFitService googleFitService = new GoogleFitService();
        fitnessOptions = googleFitService.getFitnessOptions();

        if (oAuthPermissionApproved()) {
            next();
        }
    }

    private void signIn() {
        if (!oAuthPermissionApproved()) {
            GoogleSignIn.requestPermissions(
                    this,
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    getGoogleAccount(),
                    fitnessOptions);
        } else {
            next();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            next();
        }
    }

    private boolean oAuthPermissionApproved() {
        return GoogleSignIn.hasPermissions(getGoogleAccount(), fitnessOptions);
    }

    private GoogleSignInAccount getGoogleAccount() {
        return GoogleSignIn.getAccountForExtension(this, fitnessOptions);
    }

    private void next() {
        Intent intent = new Intent(this, AppActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClick(View view) {
        signIn();
    }
}
