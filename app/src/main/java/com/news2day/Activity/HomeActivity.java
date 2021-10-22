package com.news2day.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.news2day.Adapter.AdsSliderViewPagerAdapter;
import com.news2day.Adapter.MyAdapter;
import com.news2day.Adapter.SliderViewPagerAdapter;
import com.news2day.Adapter.VerticalSliderAdapter;
import com.news2day.Adapter.VerticalSliderBuzzAdapter;
import com.news2day.Adapter.VerticlePagerAdapter;

import com.news2day.EventData;
import com.news2day.Fragment.DiscoverFragment;
import com.news2day.Fragment.DiscoveryNewFragment;
import com.news2day.OnStopTrackEventListener;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.VerticalViewPager;
import com.news2day.databinding.ActivityHomeBinding;
import com.news2day.model.AdsPojo.AdsMyPojo;
import com.news2day.model.CategoryIDPojos.MyPojo;
import com.news2day.model.DiscoveryPojos.DiscoveryMyPojo;
import com.news2day.model.DiscoveryPojos.SubCategoryDetailsPojo;
import com.news2day.model.GetProfilePojos.GetProfileResponsePojo;
import com.news2day.model.VerticalViewPojos.PostsPojoOne;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements OnStopTrackEventListener {

    ActivityHomeBinding binding;
    UserDetails userDetails;
    Activity activity;
    boolean show = true;
    private long thisTime = 0;
    private long prevTime = 0;
    int page_count = 1, per_page_count = 10;
    private boolean firstTap = true;
    protected static final long DOUBLE_CLICK_MAX_DELAY = 1000L;
    OnStopTrackEventListener listener;
    RetrofitService service;

    int currentItem;

    String user_id,
            state_id,
            district_id,
            language_id,
            category_id = "613eb629ee95a332582b4609", language,
            News_category_id,
            Entertainment_category_id,
            Buzz_category_id,
            Career_category_id,
            Technology_category_id, language_type;
    LinearLayout ll_dialog;
    TextView no_data_tv;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"ClickableViewAccessibility", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        userDetails = new UserDetails(this);
        activity = this;
        service = RetrofitInstance.createService(RetrofitService.class);
        listener = this;
        binding.drawerLayOut.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        binding.toolbarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                discovery_frag();
                if (binding.drawerLayOut.isDrawerOpen(Gravity.LEFT)) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    binding.drawerLayOut.closeDrawer(Gravity.LEFT);

                } else {
                    binding.drawerLayOut.openDrawer(Gravity.LEFT);

                }
            }
        });

        binding.content.swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                apicall();
//                apicallbuzz();
                binding.content.swiperefreshlayout.setRefreshing(false);

            }
        });
        language = userDetails.getLanguage();
        user_id = userDetails.getUserId();
        state_id = userDetails.getStateId();
        district_id = userDetails.getDistrictId();
        language_id = userDetails.getLanguage_Id();
        if (language.equals("ENGLISH")) {
            language_type = "en";

        } else if (language.equals("TELUGU")) {
            language_type = "te";

        } else if (language.equals("HINDI")) {

            language_type = "hi";
        }

        apicall();


        binding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        binding.notificationBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });
        binding.left.toolbar.titleTx.setText("Discover");
        binding.left.toolbar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayOut.closeDrawer(Gravity.LEFT);
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        binding.left.toolbar.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, OptionActivity.class);
                startActivity(intent);
            }
        });

        binding.left.toolbar.bookmarkBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BookmarkActivity.class);
                startActivity(intent);
            }
        });


        binding.llEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                service.get_footer_category().enqueue(new Callback<MyPojo>() {
                    @Override
                    public void onResponse(Call<MyPojo> call, Response<MyPojo> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            MyPojo myPojo = response.body();
                            if (myPojo.getData() != null) {
                                if (myPojo.getData().size() != 0) {
                                    if (myPojo.getSuccess().equals("true")) {

                                        category_id = myPojo.getData().get(1).get_id();
                                        apicall();
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<MyPojo> call, Throwable t) {

                    }
                });

                binding.tvEntertainment.setTextColor(getResources().getColor(R.color.app_color));
                binding.tvBuzz.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvCareer.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvNews.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvTechnology.setTextColor(getResources().getColor(R.color.app_secondary));

                binding.ivCareer.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivBuzz.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivNews.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivEntertainment.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_color));
                binding.ivTechnology.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
            }
        });
        binding.llNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                service.get_footer_category().enqueue(new Callback<MyPojo>() {
                    @Override
                    public void onResponse(Call<MyPojo> call, Response<MyPojo> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            MyPojo myPojo = response.body();
                            if (myPojo.getData() != null) {
                                if (myPojo.getData().size() != 0) {
                                    if (myPojo.getSuccess().equals("true")) {

                                        category_id = myPojo.getData().get(0).get_id();
                                        apicall();
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<MyPojo> call, Throwable t) {

                    }
                });

                binding.tvEntertainment.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvBuzz.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvCareer.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvNews.setTextColor(getResources().getColor(R.color.app_color));
                binding.tvTechnology.setTextColor(getResources().getColor(R.color.app_secondary));

                binding.ivCareer.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivBuzz.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivNews.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_color));
                binding.ivEntertainment.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivTechnology.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
            }
        });
        binding.llBuzz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                service.get_footer_category().enqueue(new Callback<MyPojo>() {
                    @Override
                    public void onResponse(Call<MyPojo> call, Response<MyPojo> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            MyPojo myPojo = response.body();
                            if (myPojo.getData() != null) {
                                if (myPojo.getData().size() != 0) {
                                    if (myPojo.getSuccess().equals("true")) {
                                        category_id = myPojo.getData().get(2).get_id();
                                        apicallbuzz();
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<MyPojo> call, Throwable t) {

                    }
                });

                binding.tvEntertainment.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvBuzz.setTextColor(getResources().getColor(R.color.app_color));
                binding.tvCareer.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvNews.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvTechnology.setTextColor(getResources().getColor(R.color.app_secondary));

                binding.ivCareer.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivBuzz.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_color));
                binding.ivNews.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivEntertainment.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivTechnology.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
            }
        });
        binding.llCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                service.get_footer_category().enqueue(new Callback<MyPojo>() {
                    @Override
                    public void onResponse(Call<MyPojo> call, Response<MyPojo> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            MyPojo myPojo = response.body();
                            if (myPojo.getData() != null) {
                                if (myPojo.getData().size() != 0) {
                                    if (myPojo.getSuccess().equals("true")) {

                                        category_id = myPojo.getData().get(3).get_id();
                                        apicall();
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<MyPojo> call, Throwable t) {

                    }
                });

                binding.tvEntertainment.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvBuzz.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvCareer.setTextColor(getResources().getColor(R.color.app_color));
                binding.tvNews.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvTechnology.setTextColor(getResources().getColor(R.color.app_secondary));

                binding.ivCareer.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_color));
                binding.ivBuzz.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivNews.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivEntertainment.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivTechnology.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
            }
        });
        binding.llTechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                service.get_footer_category().enqueue(new Callback<MyPojo>() {
                    @Override
                    public void onResponse(Call<MyPojo> call, Response<MyPojo> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            MyPojo myPojo = response.body();
                            if (myPojo.getData() != null) {
                                if (myPojo.getData().size() != 0) {
                                    if (myPojo.getSuccess().equals("true")) {

                                        category_id = myPojo.getData().get(4).get_id();
                                        apicall();
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<MyPojo> call, Throwable t) {

                    }
                });

                binding.tvEntertainment.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvBuzz.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvCareer.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvNews.setTextColor(getResources().getColor(R.color.app_secondary));
                binding.tvTechnology.setTextColor(getResources().getColor(R.color.app_color));

                binding.ivCareer.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivBuzz.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivNews.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivEntertainment.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_secondary));
                binding.ivTechnology.setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.app_color));
            }
        });

        language = userDetails.getLanguage();
        service.get_footer_category().enqueue(new Callback<MyPojo>() {
            @Override
            public void onResponse(Call<MyPojo> call, Response<MyPojo> response) {
                if (response.body() != null && response.isSuccessful()) {
                    MyPojo myPojo = response.body();
                    if (myPojo.getData() != null) {
                        if (myPojo.getData().size() != 0) {
                            if (myPojo.getSuccess().equals("true")) {
                                News_category_id = myPojo.getData().get(0).get_id();
                                Entertainment_category_id = myPojo.getData().get(1).get_id();
                                Buzz_category_id = myPojo.getData().get(2).get_id();
                                Career_category_id = myPojo.getData().get(3).get_id();
                                Technology_category_id = myPojo.getData().get(4).get_id();
                                if (language.equals("ENGLISH")) {
                                    binding.noDataTv.setText(R.string.nodata_english);
                                    binding.tvNews.setText(myPojo.getData().get(0).getName().getEn());
                                    binding.tvEntertainment.setText(myPojo.getData().get(1).getName().getEn());
                                    binding.tvBuzz.setText(myPojo.getData().get(2).getName().getEn());
                                    binding.tvCareer.setText(myPojo.getData().get(3).getName().getEn());
                                    binding.tvTechnology.setText(myPojo.getData().get(4).getName().getEn());
                                } else if (language.equals("TELUGU")) {
                                    binding.noDataTv.setText(R.string.nodata_telugu);
                                    binding.tvNews.setText(myPojo.getData().get(0).getName().getTe());
                                    binding.tvEntertainment.setText(myPojo.getData().get(1).getName().getTe());
                                    binding.tvBuzz.setText(myPojo.getData().get(2).getName().getTe());
                                    binding.tvCareer.setText(myPojo.getData().get(3).getName().getTe());
                                    binding.tvTechnology.setText(myPojo.getData().get(4).getName().getTe());
                                } else if (language.equals("HINDI")) {
                                    binding.noDataTv.setText(R.string.nodata_hindi);
                                    binding.tvNews.setText(myPojo.getData().get(0).getName().getHi());
                                    binding.tvEntertainment.setText(myPojo.getData().get(1).getName().getHi());
                                    binding.tvBuzz.setText(myPojo.getData().get(2).getName().getHi());
                                    binding.tvCareer.setText(myPojo.getData().get(3).getName().getHi());
                                    binding.tvTechnology.setText(myPojo.getData().get(4).getName().getHi());
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyPojo> call, Throwable t) {

            }
        });

/*
        final ProgressDialog pd = new ProgressDialog(HomeActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);*/

        service.get_profile(userDetails.getUserId()).enqueue(new Callback<GetProfileResponsePojo>() {
            @Override
            public void onResponse(Call<GetProfileResponsePojo> call, Response<GetProfileResponsePojo> response) {
                if (response.body() != null && response.isSuccessful()) {
                    GetProfileResponsePojo getProfileResponsePojo = response.body();
                    //pd.dismiss();
                    if (getProfileResponsePojo.getData() != null) {
                        if (getProfileResponsePojo.getData().size() != 0) {
                            if (getProfileResponsePojo.getStatus().equalsIgnoreCase("200")) {
                                binding.homePicTv.setText(getProfileResponsePojo.getData().get(0).getName());
                            } else {
                                Toast.makeText(HomeActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetProfileResponsePojo> call, Throwable t) {

            }
        });


       /* if (savedInstanceState == null) {
            Fragment fm = new DiscoverFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.framelayout, fm, DiscoverFragment.class.getSimpleName())
                    .addToBackStack(DiscoverFragment.class.getSimpleName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }*/


    }


    @Override
    public void onClick(String value) {
        if (userDetails.getBrand().equalsIgnoreCase("1")) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.bottomNavigation.setVisibility(View.GONE);
                    binding.list.setVisibility(View.GONE);
                    userDetails.setBrand("0");

                }
            }, 500);

        } else if (userDetails.getBrand().equalsIgnoreCase("0")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    binding.bottomNavigation.setVisibility(View.VISIBLE);
                    binding.list.setVisibility(View.VISIBLE);
                    userDetails.setBrand("1");

                }
            }, 500);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    binding.bottomNavigation.setVisibility(View.VISIBLE);
                    binding.list.setVisibility(View.VISIBLE);
                    userDetails.setBrand("1");

                }
            }, 500);

        }
    }

    @Override
    public void onBackPressed() {
        if (userDetails.getUserId().equalsIgnoreCase("")) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            if (binding.drawerLayOut.isDrawerOpen(Gravity.LEFT)) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                binding.drawerLayOut.closeDrawer(Gravity.LEFT);

            } else {
                finish();

            }
        }


    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    void apicall() {
        final ProgressDialog pd = new ProgressDialog(HomeActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);

        service.Posts(page_count, per_page_count, category_id,
                state_id,
                district_id, language_type,
                user_id).enqueue(new Callback<PostsPojoOne>() {
            @Override
            public void onResponse(Call<PostsPojoOne> call, Response<PostsPojoOne> response) {

                if (response.body() != null && response.isSuccessful()) {
                    PostsPojoOne postsPojoOne = response.body();
                    pd.dismiss();
                    if (postsPojoOne.getSuccess().equals("true")) {
                        if (postsPojoOne.getData() != null) {

                            if (postsPojoOne.getData().size() != 0) {
                                if (postsPojoOne.getData().size() < 1) {
                                    binding.bottomNavigation.setVisibility(View.VISIBLE);
                                    binding.noDataCard.setVisibility(View.VISIBLE);
                                    binding.list.setVisibility(View.VISIBLE);
                                    binding.content.vPager.setVisibility(View.GONE);
                                } else {
                                    binding.bottomNavigation.setVisibility(View.GONE);
                                    binding.noDataCard.setVisibility(View.GONE);
                                    binding.list.setVisibility(View.GONE);
                                    binding.content.vPager.setVisibility(View.VISIBLE);


                /*                     VerticalViewPager verticalViewPager = (VerticalViewPager) binding.content.vPager;
                                    verticalViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),HomeActivity.this, postsPojoOne.getData()));*/
                                    VerticalSliderAdapter verticlePagerAdapter = new VerticalSliderAdapter(HomeActivity.this, listener, postsPojoOne.getData());
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
                                    binding.content.vPager.setLayoutManager(linearLayoutManager);
                                    binding.content.vPager.setAdapter(verticlePagerAdapter);
                                    binding.content.vPager.isNestedScrollingEnabled();

                                    SnapHelper snapHelper = new PagerSnapHelper();
                                    binding.content.vPager.setOnFlingListener(null);
                                    snapHelper.attachToRecyclerView(binding.content.vPager);

                                    //   currentItem= binding.content.vPager.getCurrentItem();
/*
                            binding.content.vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                public void onPageScrollStateChanged(int state) {
                                    binding.bottomNavigation.setVisibility(View.GONE);
                                    binding.list.setVisibility(View.GONE);
                                }

                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                                }

                                public void onPageSelected(int position) {
                                    binding.bottomNavigation.setVisibility(View.GONE);
                                    binding.list.setVisibility(View.GONE);
                                    binding.content.vPager.setCurrentItem(position++);
                                }
                            });
*/
                                }
                            } else {
                                binding.bottomNavigation.setVisibility(View.VISIBLE);
                                binding.noDataCard.setVisibility(View.VISIBLE);
                                binding.list.setVisibility(View.VISIBLE);
                                binding.content.vPager.setVisibility(View.GONE);
                                //   binding.content.adsSlideViewPager.setVisibility(View.GONE);
                                Toast.makeText(HomeActivity.this, "No Posts Data Available", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(HomeActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, postsPojoOne.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<PostsPojoOne> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(HomeActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

            }

        });
    }

    void apicallbuzz() {
        final ProgressDialog pd = new ProgressDialog(HomeActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);

        service.Posts(page_count, per_page_count, category_id,
                state_id,
                district_id, language_type,
                user_id).enqueue(new Callback<PostsPojoOne>() {
            @Override
            public void onResponse(Call<PostsPojoOne> call, Response<PostsPojoOne> response) {

                if (response.body() != null && response.isSuccessful()) {
                    PostsPojoOne postsPojoOne = response.body();
                    pd.dismiss();
                    if (postsPojoOne.getSuccess().equals("true")) {
                        if (postsPojoOne.getData() != null) {

                            if (postsPojoOne.getData().size() != 0) {
                                if (postsPojoOne.getData().size() < 1) {
                                    binding.bottomNavigation.setVisibility(View.VISIBLE);
                                    binding.noDataCard.setVisibility(View.VISIBLE);
                                    binding.list.setVisibility(View.VISIBLE);
                                    binding.content.vPager.setVisibility(View.GONE);
                                } else {
                                    binding.bottomNavigation.setVisibility(View.GONE);
                                    binding.noDataCard.setVisibility(View.GONE);
                                    binding.list.setVisibility(View.GONE);
                                    binding.content.vPager.setVisibility(View.VISIBLE);


                /*                     VerticalViewPager verticalViewPager = (VerticalViewPager) binding.content.vPager;
                                    verticalViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(),HomeActivity.this, postsPojoOne.getData()));*/
                                    VerticalSliderBuzzAdapter verticlePagerAdapter = new VerticalSliderBuzzAdapter(HomeActivity.this, listener, postsPojoOne.getData());
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
                                    binding.content.vPager.setLayoutManager(linearLayoutManager);
                                    binding.content.vPager.setAdapter(verticlePagerAdapter);
                                    binding.content.vPager.isNestedScrollingEnabled();

                                    SnapHelper snapHelper = new PagerSnapHelper();
                                    binding.content.vPager.setOnFlingListener(null);
                                    snapHelper.attachToRecyclerView(binding.content.vPager);

                                    //   currentItem= binding.content.vPager.getCurrentItem();
/*
                            binding.content.vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                public void onPageScrollStateChanged(int state) {
                                    binding.bottomNavigation.setVisibility(View.GONE);
                                    binding.list.setVisibility(View.GONE);
                                }

                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                                }

                                public void onPageSelected(int position) {
                                    binding.bottomNavigation.setVisibility(View.GONE);
                                    binding.list.setVisibility(View.GONE);
                                    binding.content.vPager.setCurrentItem(position++);
                                }
                            });
*/
                                }
                            } else {
                                binding.bottomNavigation.setVisibility(View.VISIBLE);
                                binding.noDataCard.setVisibility(View.VISIBLE);
                                binding.list.setVisibility(View.VISIBLE);
                                binding.content.vPager.setVisibility(View.GONE);
                                //   binding.content.adsSlideViewPager.setVisibility(View.GONE);
                                Toast.makeText(HomeActivity.this, "No Posts Data Available", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(HomeActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, postsPojoOne.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<PostsPojoOne> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(HomeActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

            }

        });
    }


    void discovery_frag() {
        DiscoveryNewFragment fragment = new DiscoveryNewFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.commit();
    }


}
