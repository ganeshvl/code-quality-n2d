package com.news2day.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.news2day.Adapter.LanguageAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityLanguageBinding;

import java.util.ArrayList;
import java.util.Locale;


public class LanguageActivity extends AppCompatActivity {

    ActivityLanguageBinding binding;
    UserDetails userDetails;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> image = new ArrayList<>();
    Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDetails = new UserDetails(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language);
        binding.toolBar.titleTx.setText("Select Language");
        setLocale("te");
        binding.toolBar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        put();
        LanguageAdapter govtJobsSubRecyclerAdapter = new LanguageAdapter(this,name,image );
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        binding.rlLanguage.setLayoutManager(linearLayoutManager);
        binding.rlLanguage.setAdapter(govtJobsSubRecyclerAdapter);

    }

    void put() {


        name.add("ENGLISH");
        name.add("HINDI");
        name.add("TELUGU");

        image.add(R.drawable.ic_english);
        image.add(R.drawable.ic_hindhi);
        image.add(R.drawable.ic_telugu);


    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
//        Intent refresh = new Intent(this, LanguageActivity.class);
//        startActivity(refresh);
    }
}