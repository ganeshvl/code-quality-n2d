package com.news2day.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import com.news2day.Adapter.VerticalSliderAdapter;
import com.news2day.Adapter.VerticlePagerAdapter;
import com.news2day.OnStopTrackEventListener;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.VerticalViewPager;
import com.news2day.databinding.ActivityStartUpBinding;
import com.news2day.databinding.ActivityVideosBinding;
import com.news2day.model.VerticalViewPojos.PostsPojoOne;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartUpActivity extends AppCompatActivity implements OnStopTrackEventListener {

    ActivityStartUpBinding activityVideosBinding;

    UserDetails userDetails;
    RetrofitService service;
    int page_count = 1, per_page_count = 10;
    OnStopTrackEventListener listener;
    String category_id, sub_category_id, title,language,language_type;
    int currentItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityVideosBinding = DataBindingUtil.setContentView(this, R.layout.activity_start_up);
        userDetails = new UserDetails(this);


        service = RetrofitInstance.createService(RetrofitService.class);
        listener = this;

        activityVideosBinding.toolbar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        category_id = intent.getStringExtra("category_id");
        sub_category_id = intent.getStringExtra("sub_category_id");
        title = intent.getStringExtra("title");

        activityVideosBinding.toolbar.titleTx.setText(title);

        language=userDetails.getLanguage();
        if (language.equals("ENGLISH")) {
            language_type="en";
            activityVideosBinding.noDataTv.setText(R.string.data_not_available_en);

        } else if (language.equals("TELUGU")) {
            language_type="te";
            activityVideosBinding.noDataTv.setText(R.string.data_not_available_te);
        } else if (language.equals("HINDI")) {

            language_type="hi";
            activityVideosBinding.noDataTv.setText(R.string.data_not_available_hi);;
        }

        final ProgressDialog pd = new ProgressDialog(StartUpActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);
        service.Get_Posts_by_cat_id_sub_cat_id(page_count++, per_page_count,
                category_id, sub_category_id,
                userDetails.getStateId(),
                userDetails.getDistrictId(),language_type,
                userDetails.getUserId()).enqueue(new Callback<PostsPojoOne>() {
            @Override
            public void onResponse(Call<PostsPojoOne> call, Response<PostsPojoOne> response) {
                if (response.body() != null && response.isSuccessful()) {
                    PostsPojoOne postsPojoOne = response.body();
                    if (postsPojoOne.getSuccess().equalsIgnoreCase("true")) {
                        pd.dismiss();
                        if (postsPojoOne.getData().size() < 1) {

                            activityVideosBinding.noDataCard.setVisibility(View.VISIBLE);
                            activityVideosBinding.list.setVisibility(View.VISIBLE);
                            activityVideosBinding.vPager.setVisibility(View.GONE);
                        } else {

                            activityVideosBinding.noDataCard.setVisibility(View.GONE);
                            activityVideosBinding.list.setVisibility(View.VISIBLE);
                            activityVideosBinding.vPager.setVisibility(View.VISIBLE);
                            VerticalSliderAdapter verticlePagerAdapter = new VerticalSliderAdapter(StartUpActivity.this, listener, postsPojoOne.getData());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StartUpActivity.this);
                            activityVideosBinding.vPager.setLayoutManager(linearLayoutManager);
                            activityVideosBinding.vPager.setAdapter(verticlePagerAdapter);
                            activityVideosBinding.vPager.isNestedScrollingEnabled();

                            SnapHelper snapHelper = new PagerSnapHelper();
                            activityVideosBinding.vPager.setOnFlingListener(null);
                            snapHelper.attachToRecyclerView( activityVideosBinding.vPager);
                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<PostsPojoOne> call, Throwable t) {

            }
        });


    }

    private static RequestBody toRequestBody(String value) {

        return RequestBody.create(MultipartBody.FORM, value);
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    @Override
    public void onClick(String value) {
        if (userDetails.getBrand().equalsIgnoreCase("1")) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    activityVideosBinding.toolbar.list.setVisibility(View.VISIBLE);
                    userDetails.setBrand("0");

                }
            }, 1000);

        } else if (userDetails.getBrand().equalsIgnoreCase("0")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    activityVideosBinding.toolbar.list.setVisibility(View.VISIBLE);
                    userDetails.setBrand("1");

                }
            }, 1000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    activityVideosBinding.toolbar.list.setVisibility(View.VISIBLE);

                    userDetails.setBrand("1");

                }
            }, 1000);

        }
    }
}
