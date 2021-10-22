package com.news2day.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.news2day.R;

public class OnBoardingViewPagerAdapter extends PagerAdapter {

    Context context;
    ImageView onboading_img;
    TextView heading_tx,
            descripte_tx;
    int[] img = {R.drawable.onboard_one, R.drawable.onboard_two, R.drawable.onboard_three};
    int[] heading = {R.string.heading_one, R.string.heading_two, R.string.heading_three};

    public OnBoardingViewPagerAdapter(Context context, int[] img, int[] heading) {
        this.context = context;
        this.img = img;
        this.heading = heading;
    }

    @Override
    public int getCount() {
        return heading.length;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboarding_viewpager_adapter_lay_out, container, false);
        container.addView(view);
        onboading_img = view.findViewById(R.id.onboading_img);
        heading_tx = view.findViewById(R.id.heading_tx);

        onboading_img.setImageResource(img[position]);
        heading_tx.setText(heading[position]);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
