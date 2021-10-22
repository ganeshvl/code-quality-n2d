package com.news2day.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.news2day.Adapter.SpecialVideosAdapter;
import com.news2day.R;
import com.news2day.databinding.ActivityAboutUsBinding;
import com.news2day.databinding.ActivityVideoListBinding;

import java.util.ArrayList;

public class SpecialVideosListActivity extends AppCompatActivity {
    ActivityVideoListBinding binding;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> special = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_terms_andconditions);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_list);

        binding.toolbar.titleTx.setText("Special Videos");

        binding.toolbar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        special.add(R.drawable.special1);
        special.add(R.drawable.special2);
        special.add(R.drawable.special2);

       /* SpecialVideosAdapter specialVideosAdapter = new SpecialVideosAdapter(this,  special);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        binding.rvVideo.setLayoutManager(gridLayoutManager);
        binding.rvVideo.setAdapter(specialVideosAdapter);*/
    }
}