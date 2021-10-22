package com.news2day.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.news2day.R;
import com.news2day.databinding.ActivityStoriesCompletedBinding;

public class StoriesCompletedActivity extends AppCompatActivity {
    ActivityStoriesCompletedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_stories_completed);
    }
}