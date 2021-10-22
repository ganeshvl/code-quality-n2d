package com.news2day.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.news2day.BuildConfig;
import com.news2day.OnStopTrackEventListener;
import com.news2day.R;
import com.news2day.UserDetails;

import xyz.hanks.library.bang.SmallBangView;

/**
 * Created by rizvan on 12/13/16.
 */

public class VideoAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ImageView ic_share, ic_bookmark, ic_download, ic_report, ic_like;
    RelativeLayout bottom_lay_out, bottom_download;
    LinearLayout ll_data;
    ImageView iv_image;
    SmallBangView iv_like, iv_bookmark, iv_report;
    TextView tv_description;
    OnStopTrackEventListener listener;
    UserDetails userDetails;

    LinearLayout ll_video;

    public VideoAdapter(Context context, OnStopTrackEventListener listener) {
        this.mContext = context;
        this.listener = listener;

    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.item_vide_slide, container, false);
        userDetails = new UserDetails(mContext);
        ll_video = itemView.findViewById(R.id.ll_video);
        ll_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDetails.getBrand().equalsIgnoreCase("1")) {
                    listener.onClick("0");
                    userDetails.setBrand("1");
                } else {
                    listener.onClick("1");
                    userDetails.setBrand("0");
                }
            }
        });
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


}
