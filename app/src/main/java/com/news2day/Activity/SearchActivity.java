package com.news2day.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.news2day.Adapter.SearchAdapter;
import com.news2day.Adapter.TrendingAdapter;
import com.news2day.R;
import com.news2day.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding activitySearchBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        activitySearchBinding.toolbar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        activitySearchBinding.toolbar.titleTx.setText("Search");
        SearchAdapter govtJobsSubRecyclerAdapter = new SearchAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activitySearchBinding.rvSearch.setLayoutManager(linearLayoutManager);
        activitySearchBinding.rvSearch.setAdapter(govtJobsSubRecyclerAdapter);
    }
}
