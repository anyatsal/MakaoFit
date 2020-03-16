package com.makao.makaofit.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.material.snackbar.Snackbar;
import com.makao.makaofit.BuildConfig;
import com.makao.makaofit.R;
import com.makao.makaofit.service.GoogleFitService;


public class MainActivity extends AppCompatActivity {

    final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = System.identityHashCode(this) & 0xFFFF;

    private boolean runningQOrLater =
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;
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

    private void checkPermission() {
        if (permissionApproved()) {
            signIn();
        } else {
            requestPermission();
        }
    }

    private boolean permissionApproved() {
        if (runningQOrLater) {
            return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION);
        } else return true;
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            signIn();
        } else {
            Snackbar.make(findViewById(R.id.main_activity_view), R.string.permission_denied_explanation, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.settings, listener -> {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",
                                BuildConfig.APPLICATION_ID, null);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            next();
        } else {
            Toast.makeText(this,"Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String [] { Manifest.permission.ACTIVITY_RECOGNITION }, GOOGLE_FIT_PERMISSIONS_REQUEST_CODE);
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
    }

    public void onClick(View view) {
        checkPermission();
    }
}
