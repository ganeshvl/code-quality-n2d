package com.news2day.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityThemeBinding;

public class ThemeActivity extends AppCompatActivity {

    ActivityThemeBinding activityRegisterBinding;

    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_theme);
        activityRegisterBinding.toolBar.titleTx.setText("Theme");

    }
}
