package com.makao.makaofit.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.tasks.Task;
import com.makao.makaofit.R;
import com.makao.makaofit.service.GoogleFitService;

public class MainActivity extends AppCompatActivity {

    final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = System.identityHashCode(this) & 0xFFFF;

    private SharedPreferences preferences;
    private FitnessOptions fitnessOptions;
    private GoogleFitService googleFitService;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          Scope bodyScopeRead = new Scope("https://www.googleapis.com/auth/fitness.body.read");
//        Scope bodyScopeWrite = new Scope("https://www.googleapis.com/auth/fitness.body.write");
//        Scope activityScope = new Scope("https://www.googleapis.com/auth/fitness.activity.read");

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
               .requestScopes(bodyScopeRead)
                .requestIdToken("210497297826-q2dhqv3t6fhmm0q8e7es1mcf4h7uoj75.apps.googleusercontent.com")
                .build();

        googleFitService = new GoogleFitService();
        fitnessOptions = googleFitService.getFitnessOptions();
        preferences = getSharedPreferences("com.makao.makaofit", MODE_PRIVATE);

        if (oAuthPermissionApproved()) {
            next();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            getProfileInformation(account);
            next();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private boolean oAuthPermissionApproved() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    private void next() {
        this.getSharedPreferences("com.makao.makaofit", Context.MODE_PRIVATE).edit().putBoolean("AUTHENTICATED", true).commit();

        Intent intent = new Intent(this, AppActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClick(View view) {
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_FIT_PERMISSIONS_REQUEST_CODE);
    }

    private void getProfileInformation(GoogleSignInAccount account) {
        if (account != null) {
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("username", account.getDisplayName());
            editor.putString("email", account.getEmail());

            Uri photoUrl = account.getPhotoUrl();
            if (photoUrl != null) {
                editor.putString("photoUrl", photoUrl.toString());
            }
            editor.apply();
        }
    }
}
