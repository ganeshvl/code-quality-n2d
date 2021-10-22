package com.news2day.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivitySplashBinding;


public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDetails = new UserDetails(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        if (userDetails.isLoggedIn()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                }
            }, 2000);

        } else {
            if (userDetails.getUserId().equalsIgnoreCase("")) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, OnBoardingActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }, 2000);
            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }, 2000);

            }

        }
    }
}