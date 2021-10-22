package com.news2day.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.news2day.Adapter.OnBoardingViewPagerAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityOnBoardingBinding;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class OnBoardingActivity extends AppCompatActivity {
    ActivityOnBoardingBinding boardingBinding;
    OnBoardingViewPagerAdapter onBoardingViewPagerAdapter;
    int[] img = {R.drawable.onboard_one, R.drawable.onboard_two, R.drawable.onboard_three};
    int[] heading = {R.string.heading_one, R.string.heading_two, R.string.heading_three};
    private ImageView[] dots;
    private int dotscount;
    UserDetails userDetails;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        boardingBinding = DataBindingUtil.setContentView(this, R.layout.activity_on_boarding);


        onBoardingViewPagerAdapter = new OnBoardingViewPagerAdapter(OnBoardingActivity.this, img, heading);
        boardingBinding.viewPager.setAdapter(onBoardingViewPagerAdapter);
        boardingBinding.dotsIndicator.setViewPager(boardingBinding.viewPager);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                boardingBinding.viewPager.post(new Runnable() {

                    @Override
                    public void run() {
                        if (boardingBinding.viewPager.getCurrentItem() == 2) {
                            boardingBinding.viewPager.setCurrentItem((boardingBinding.viewPager.getCurrentItem() - 2));
                        } else {
                            boardingBinding.viewPager.setCurrentItem((boardingBinding.viewPager.getCurrentItem() + 1));
                        }

                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);
        boardingBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 3) {

                } else {

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        boardingBinding.tvStartup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OnBoardingActivity.this, LanguageActivity.class);
                startActivity(intent);

            }
        });




    }


}