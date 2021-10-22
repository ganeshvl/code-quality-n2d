package com.news2day.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.news2day.Adapter.ContestsAdapter;
import com.news2day.Adapter.VerticlePagerAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.VerticalViewPager;
import com.news2day.databinding.ActivityContestsBinding;
import com.news2day.model.VerticalViewPojos.PostsPojoOne;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContestsActivity extends AppCompatActivity {
    ActivityContestsBinding binding;
    UserDetails userDetails;
    String language, category_id,title;
    RetrofitService service;
    int page_count = 1, per_page_count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_contests);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contests);

        userDetails = new UserDetails(this);

        binding.toolBar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        service = RetrofitInstance.createService(RetrofitService.class);

        Intent intent = getIntent();
        category_id = intent.getStringExtra("category_id");
        title = intent.getStringExtra("title");
        binding.toolBar.titleTx.setText(title);
        language = userDetails.getLanguage();

        if (language.equals("ENGLISH")) {
            binding.toolBar.titleTx.setText(title);
            binding.noDataTv.setText(R.string.data_not_available_en);

        } else if (language.equals("TELUGU")) {
            binding.toolBar.titleTx.setText(title);
            binding.noDataCard.setVisibility(View.VISIBLE);
            binding.noDataTv.setText(R.string.data_not_available_te);

        } else if (language.equals("HINDI")) {

            binding.toolBar.titleTx.setText(title);
            binding.noDataCard.setVisibility(View.VISIBLE);
            binding.noDataTv.setText(R.string.data_not_available_hi);
        }
        final ProgressDialog pd = new ProgressDialog(ContestsActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);
        service.Posts(page_count++, per_page_count,
                category_id,
                userDetails.getStateId(),
                userDetails.getDistrictId(), userDetails.getLanguage_Id(),
                userDetails.getUserId()).enqueue(new Callback<PostsPojoOne>() {
            @Override
            public void onResponse(Call<PostsPojoOne> call, Response<PostsPojoOne> response) {
                if (response.body() != null && response.isSuccessful()) {
                    PostsPojoOne postsPojoOne = response.body();
                    if (postsPojoOne.getData()!=null){
                        if (postsPojoOne.getSuccess().equalsIgnoreCase("true")) {
                            pd.dismiss();
                            if (postsPojoOne.getData().size() < 1) {

                                binding.noDataCard.setVisibility(View.VISIBLE);

                                binding.contestsRv.setVisibility(View.GONE);
                            }
                            else {

                                binding.noDataCard.setVisibility(View.GONE);

                                binding.contestsRv.setVisibility(View.VISIBLE);

                                ContestsAdapter contestsAdapter = new ContestsAdapter(ContestsActivity.this, postsPojoOne.getData(),title,category_id);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ContestsActivity.this);
                                binding.contestsRv.setLayoutManager(linearLayoutManager);
                                binding.contestsRv.setAdapter(contestsAdapter);
                            }

                        }else {
                            pd.dismiss();
                            Toast.makeText(ContestsActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        pd.dismiss();
                        Toast.makeText(ContestsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<PostsPojoOne> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(ContestsActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
            }
        });


    }
}