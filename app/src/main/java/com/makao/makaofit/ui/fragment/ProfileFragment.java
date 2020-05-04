package com.makao.makaofit.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.makao.makaofit.R;
import com.makao.makaofit.service.GoogleFitService;
import com.makao.makaofit.ui.training.TutorialActivity;
import com.makao.makaofit.ui.activity.EditProfileActivity;
import com.squareup.picasso.Picasso;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {

    private SharedPreferences preferences;
    private GoogleFitService googleFitService;
    private GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        preferences = getContext().getSharedPreferences("com.makao.makaofit", MODE_PRIVATE);
        googleFitService = new GoogleFitService();

        initView(view);

        return view;
    }

    private void logout() {
        getContext().getSharedPreferences("com.makao.makaofit", Context.MODE_PRIVATE).edit().putBoolean("AUTHENTICATED", false).commit();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this.getActivity(), gso);
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this.getActivity(), task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(this.getActivity(), TutorialActivity.class);
                        startActivity(intent);
                        getActivity().onBackPressed();
                    }
                });
    }

    private void initView(View view) {
        TextView nameView = view.findViewById(R.id.user_name);
        TextView emailView = view.findViewById(R.id.user_email);
        TextView heightView = view.findViewById(R.id.user_height);
        TextView weightView = view.findViewById(R.id.user_weight);
        ImageView photoView = view.findViewById(R.id.img_user_photo);
        SeekBar goalView = view.findViewById(R.id.goal);
        TextView goalTextView = view.findViewById(R.id.tv_goal);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            goalView.setMin(5000);
        }
        goalView.setMax(20000);
        goalTextView.setText("" + preferences.getInt("goal", 10000));
        goalView.setProgress(preferences.getInt("goal", 10000));

        goalView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                goalTextView.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                preferences.edit().putInt("goal", seekBar.getProgress()).commit();
                goalView.setProgress(seekBar.getProgress());
            }
        });

        nameView.setText(preferences.getString("username", ""));
        emailView.setText(preferences.getString("email", ""));


        String photoUrl = preferences.getString("photoUrl", "");
        Picasso.with(getContext()).load(photoUrl).into(photoView);

        googleFitService.setHeight(getContext(), heightView);
        googleFitService.setWeight(getContext(), weightView);

        Button button = view.findViewById(R.id.btn_logout);
        button.setOnClickListener(listener -> logout());

        Button editButton = view.findViewById(R.id.edit_profile_button);
        editButton.setOnClickListener(listener -> {
            Intent intent = new Intent(this.getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });
    }
}
