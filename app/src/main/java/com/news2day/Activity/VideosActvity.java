package com.news2day.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.news2day.Adapter.VerticlePagerAdapter;
import com.news2day.Adapter.VideoAdapter;
import com.news2day.OnStopTrackEventListener;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.VerticalViewPager;
import com.news2day.databinding.ActivityThemeBinding;
import com.news2day.databinding.ActivityVideosBinding;

public class VideosActvity extends AppCompatActivity implements OnStopTrackEventListener {

    ActivityVideosBinding activityVideosBinding;

    UserDetails userDetails;
String text;
 String[]   video_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityVideosBinding = DataBindingUtil.setContentView(this, R.layout.activity_videos);
        userDetails = new UserDetails(this);
        activityVideosBinding.toolbar.titleTx.setText("Special Videos");
        Intent intent1 = getIntent();

        text=intent1.getStringExtra("text");
        video_url=intent1.getStringArrayExtra("video_url");
        activityVideosBinding.tvClock.setText(text);
        MediaController mediaController = new MediaController(VideosActvity.this);

        mediaController.setAnchorView(activityVideosBinding.videoViewMain);

        //specify the location of media file
        // Uri uri = Uri.parse("https://www.youtube.com/watch?v=Q_EwuVHDb5U");
        // Uri uri = Uri.parse("https://www.istockphoto.com/video/harvesting-a-wheat-field-during-a-very-dry-summer-season-aerial-view-gm1002097560-270815174?utm_source=pixabay&utm_medium=affiliate&utm_campaign=SRP_video_sponsored&referrer_url=http%3A%2F%2Fpixabay.com%2Fvideos%2Fsearch%2Fscenery%2F&utm_term=scenery.mp4");

        for (int i = 0; i < video_url.length; i++) {

            Uri uri = Uri.parse("http://18.118.90.146:5001/" + video_url[i]);

            //Setting nViewCelMediaController and URI, then starting the videoView
            activityVideosBinding.videoViewMain.setMediaController(mediaController);
            activityVideosBinding.videoViewMain.setVideoURI(uri);
            activityVideosBinding.videoViewMain.requestFocus();
           activityVideosBinding.videoViewMain.start();
        }
        activityVideosBinding.toolbar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }


        });
      /*  VerticalViewPager verticalViewPager = (VerticalViewPager) activityVideosBinding.vpager;
        verticalViewPager.setAdapter(new VideoAdapter(this, this::onClick));
        activityVideosBinding.vpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
                activityVideosBinding.toolbar.list.setVisibility(View.GONE);
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            public void onPageSelected(int position) {

            }
        });
*/
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
