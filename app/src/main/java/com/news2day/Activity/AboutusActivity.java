package com.news2day.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.news2day.R;
import com.news2day.databinding.ActivityAboutUsBinding;
import com.news2day.databinding.ActivityTermsAndconditionsBinding;

public class AboutusActivity extends AppCompatActivity {
    ActivityAboutUsBinding binding;
    String name,
            description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_terms_andconditions);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us);


        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        description = intent.getStringExtra("description");

        binding.headingTv.setText(name);
        binding.toolBar.titleTx.setText(name);
        binding.descriptionTv.setText(description);

        binding.toolBar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}