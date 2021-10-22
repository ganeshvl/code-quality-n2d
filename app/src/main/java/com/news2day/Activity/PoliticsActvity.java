package com.news2day.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.news2day.Adapter.VerticlePagerAdapter;
import com.news2day.OnStopTrackEventListener;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.VerticalViewPager;
import com.news2day.databinding.ActivityVideosBinding;
import com.news2day.model.VerticalViewPojos.PostsPojoOne;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoliticsActvity extends AppCompatActivity implements OnStopTrackEventListener {

    ActivityVideosBinding activityVideosBinding;

    UserDetails userDetails;
RetrofitService service;
    OnStopTrackEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityVideosBinding = DataBindingUtil.setContentView(this, R.layout.activity_videos);
        userDetails= new UserDetails(this);
        activityVideosBinding.toolbar.titleTx.setText("Politics");

        service= RetrofitInstance.createService(RetrofitService.class);
        listener=this;
        activityVideosBinding.toolbar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Map<String, RequestBody> map = new HashMap<>();
        map.put("", toRequestBody("/1"));
        map.put("", toRequestBody("/10"));
  /*      service.Posts(1,10,
                "614423e0c16897266e414896",
                userDetails.getStateId(),
                userDetails.getDistrictId(),userDetails.getLanguage_Id(),
                userDetails.getUserId()).enqueue(new Callback<PostsPojoOne>() {
            @Override
            public void onResponse(Call<PostsPojoOne> call, Response<PostsPojoOne> response) {
                if (response.body()!= null && response.isSuccessful()){
                    PostsPojoOne postsPojoOne=response.body();
                    VerticalViewPager verticalViewPager = (VerticalViewPager) activityVideosBinding.vpager;
                    verticalViewPager.setAdapter(new VerticlePagerAdapter(PoliticsActvity.this,listener , postsPojoOne.getData()));
                    activityVideosBinding.vpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        public void onPageScrollStateChanged(int state) {
                            activityVideosBinding.toolbar.list.setVisibility(View.GONE);
                        }

                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                        }

                        public void onPageSelected(int position) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<PostsPojoOne> call, Throwable t) {

            }
        });*/


    }
    private static RequestBody toRequestBody(String value) {

        return RequestBody.create(MultipartBody.FORM, value);
    }
    @Override
    public void onClick(String value) {
        if (userDetails.getBrand().equalsIgnoreCase("1")) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    activityVideosBinding.toolbar.list.setVisibility(View.GONE);
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
