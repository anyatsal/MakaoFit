package com.makao.makaofit.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.makao.makaofit.R;
import com.makao.makaofit.ui.fragment.HomeFragment;
import com.makao.makaofit.ui.fragment.ProfileFragment;
import com.makao.makaofit.ui.fragment.StatisticsFragment;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (item -> {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigate_home:
                            selectedFragment = HomeFragment.newInstance();
                            break;
                        case R.id.navigate_statistics:
                            selectedFragment = StatisticsFragment.newInstance();
                            break;
                        case R.id.navigate_profile:
                            selectedFragment = ProfileFragment.newInstance();
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, selectedFragment);
                    transaction.commit();
                    return true;
                });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, HomeFragment.newInstance());
        transaction.commit();
    }
}
