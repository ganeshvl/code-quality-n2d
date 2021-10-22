package com.news2day.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import com.news2day.Adapter.BookMarkAdapter;
import com.news2day.Adapter.BookmarkViewpager;
import com.news2day.Adapter.VerticalSliderAdapter;
import com.news2day.Adapter.VerticlePagerAdapter;
import com.news2day.OnStopTrackEventListener;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.VerticalViewPager;
import com.news2day.databinding.ActivityBookmarkBinding;
import com.news2day.model.BookMarkPojo.BookMarkMyPojo;
import com.news2day.model.ContactUsPojo.ContactUsMyPojo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkActivity extends AppCompatActivity implements OnStopTrackEventListener {

    ActivityBookmarkBinding activityBookmarkBinding;

    UserDetails userDetails;
    RetrofitService service;
    OnStopTrackEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBookmarkBinding = DataBindingUtil.setContentView(this, R.layout.activity_bookmark);

        userDetails = new UserDetails(this);
        service = RetrofitInstance.createService(RetrofitService.class);
        listener = this;

        activityBookmarkBinding.toolbar.titleTx.setText("Bookmarks");

        activityBookmarkBinding.toolbar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        final ProgressDialog pd = new ProgressDialog(BookmarkActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);
        service.get_bookmark(userDetails.getUserId()).enqueue(new Callback<BookMarkMyPojo>() {
            @Override
            public void onResponse(Call<BookMarkMyPojo> call, Response<BookMarkMyPojo> response) {
                if (response.body() != null && response.isSuccessful()) {
                    BookMarkMyPojo bookMarkMyPojo = response.body();
                    if (bookMarkMyPojo.getData() != null) {
                        if (bookMarkMyPojo.getSuccess().equalsIgnoreCase("true")) {
                            pd.dismiss();
                            BookMarkAdapter bookMarkAdapter = new BookMarkAdapter(BookmarkActivity.this, bookMarkMyPojo.getData());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookmarkActivity.this);
                            activityBookmarkBinding.vPager.setLayoutManager(linearLayoutManager);
                            activityBookmarkBinding.vPager.setAdapter(bookMarkAdapter);
                            activityBookmarkBinding.vPager.isNestedScrollingEnabled();

                            SnapHelper snapHelper = new PagerSnapHelper();
                            activityBookmarkBinding.vPager.setOnFlingListener(null);
                            snapHelper.attachToRecyclerView(  activityBookmarkBinding.vPager);

                        } else {

                            Toast.makeText(BookmarkActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(BookmarkActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<BookMarkMyPojo> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(BookmarkActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(String value) {
        if (userDetails.getBrand().equalsIgnoreCase("1")) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    activityBookmarkBinding.toolbar.list.setVisibility(View.GONE);
                    userDetails.setBrand("0");

                }
            }, 1000);

        } else if (userDetails.getBrand().equalsIgnoreCase("0")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    activityBookmarkBinding.toolbar.list.setVisibility(View.VISIBLE);
                    userDetails.setBrand("1");

                }
            }, 1000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    activityBookmarkBinding.toolbar.list.setVisibility(View.VISIBLE);

                    userDetails.setBrand("1");

                }
            }, 1000);

        }
    }
}
