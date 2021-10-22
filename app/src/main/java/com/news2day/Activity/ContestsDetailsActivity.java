package com.news2day.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityContestsDetailsBinding;
import com.news2day.model.SharePostsPojos.SharePostPojoOne;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContestsDetailsActivity extends AppCompatActivity {
    ActivityContestsDetailsBinding binding;
    UserDetails userDetails;
    String[] contest_img;
    String language,
            contest_heading,
            contest_description, id, url, title, category_id, img;
    RetrofitService service;
    List<String> wordList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_contests_details);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contests_details);
        userDetails = new UserDetails(this);
        service = RetrofitInstance.createService(RetrofitService.class);
        Intent intent = getIntent();
        contest_img = intent.getStringArrayExtra("contest_img");
        contest_heading = intent.getStringExtra("contest_heading");
        contest_description = intent.getStringExtra("contest_description");
        url = intent.getStringExtra("url");
        id = intent.getStringExtra("id");
        category_id = intent.getStringExtra("category_id");
        title = intent.getStringExtra("title");
        wordList = Arrays.asList(contest_img);
        if (wordList.size()>0 ) {
            for (int i = 0; i < contest_img.length; i++) {
                if (contest_img[i] == "") {
                    Glide.with(ContestsDetailsActivity.this).load("").error(R.mipmap.ic_launcher).into(binding.ivProPic);
                } else {
                    img = contest_img[i];
                    Glide.with(ContestsDetailsActivity.this).load("http://18.118.90.146:5001/" + img).error(R.mipmap.ic_launcher).into(binding.ivProPic);
                }
            }
        }else {
          //  Toast.makeText(this, "Image String Array is empty", Toast.LENGTH_SHORT).show();
        }


        binding.toolBar.titleTx.setText(title);
        binding.headingContest.setText(contest_heading);
        binding.descriptionContest.setText(contest_description);

        binding.toolBar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.participateNowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContestsDetailsActivity.this, WebPageActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("id", category_id);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
        binding.shareBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(ContestsDetailsActivity.this);
                pd.setMessage("Loading.....");
                pd.show();
                pd.setCancelable(true);
                service.share_posts(userDetails.getUserId(), id).enqueue(new Callback<SharePostPojoOne>() {
                    @Override
                    public void onResponse(Call<SharePostPojoOne> call, Response<SharePostPojoOne> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            SharePostPojoOne sharePostPojoOne = response.body();

                            if (sharePostPojoOne.getStatus() != null) {
                                if (!userDetails.isLoggedIn()) {
                                    Intent intent = new Intent(ContestsDetailsActivity.this, LoginActivity.class);
                                    ContestsDetailsActivity.this.startActivity(intent);
                                    Toast.makeText(ContestsDetailsActivity.this, R.string.you_must, Toast.LENGTH_SHORT).show();
                                } else {
                                    if (sharePostPojoOne.getStatus().equalsIgnoreCase("200")) {
                                        pd.dismiss();
                                       /* shares_count = shares_count + 1;
                                        share_tv.setText(String.valueOf(shares_count));*/
                                        Intent intent = new Intent(Intent.ACTION_SEND);
                                        intent.setType("text/plain");
                                        intent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                                        intent.putExtra(Intent.EXTRA_TEXT, "Hi,Friends This Post is Useful For all" + "\n" + sharePostPojoOne.getData().get_id() + "\n" + contest_heading + "\n" + contest_description);
                                        startActivity(Intent.createChooser(intent, "share via"));
                                        Toast.makeText(ContestsDetailsActivity.this, R.string.post_successfull, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ContestsDetailsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SharePostPojoOne> call, Throwable t) {

                    }
                });

            }
        });
    }
}