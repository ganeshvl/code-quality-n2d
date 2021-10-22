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
import androidx.viewpager.widget.ViewPager;

import com.news2day.BuildConfig;
import com.news2day.OnStopTrackEventListener;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.model.BookMarkPojo.BookMarkDataPojo;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.List;

import xyz.hanks.library.bang.SmallBangView;

/**
 * Created by rizvan on 12/13/16.
 */

public class BookmarkViewpager extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ImageView ic_share, ic_bookmark, ic_download, ic_report, ic_like;
    RelativeLayout bottom_lay_out, bottom_download, rl_bookmark;
    LinearLayout ll_data, ll_bookmark,main_ll;
    ImageView iv_image;
    SmallBangView iv_like, iv_bookmark, iv_report;
    TextView tv_description,title_tv;
    OnStopTrackEventListener listener;
    UserDetails userDetails;
    List<BookMarkDataPojo> data;
    ViewPager slide_view_pager;
    SpringDotsIndicator dots_indicator;

    public BookmarkViewpager(Context context, List<BookMarkDataPojo> data, OnStopTrackEventListener listener) {
        this.mContext = context;
        this.listener = listener;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.item_news_bookmark, container, false);
        userDetails = new UserDetails(mContext);
        ll_bookmark = itemView.findViewById(R.id.ll_bookmark);

        ic_share = itemView.findViewById(R.id.ic_share);
        ic_bookmark = itemView.findViewById(R.id.ic_bookmark);
        ic_download = itemView.findViewById(R.id.ic_download);
        ic_report = itemView.findViewById(R.id.ic_report);
        ic_like = itemView.findViewById(R.id.ic_like);
        bottom_lay_out = itemView.findViewById(R.id.bottom_lay_out);
        bottom_download = itemView.findViewById(R.id.bottom_download);
        iv_image = itemView.findViewById(R.id.iv_image);
        ll_data = itemView.findViewById(R.id.ll_data);
        iv_like = itemView.findViewById(R.id.iv_like);
        iv_bookmark = itemView.findViewById(R.id.iv_bookmark);
        iv_report = itemView.findViewById(R.id.iv_report);
        tv_description = itemView.findViewById(R.id.tv_description);
        title_tv = itemView.findViewById(R.id.title_tv);
        main_ll = itemView.findViewById(R.id.main_ll);


        tv_description.setText(data.get(position).getPost().getDescription());
        title_tv.setText(data.get(position).getPost().getName());

        /*SliderViewPagerAdapter sliderViewPagerAdapter = new SliderViewPagerAdapter(mContext, data.get(position).getPostdata().get(position).getImage());
        slide_view_pager.setAdapter(sliderViewPagerAdapter);
        dots_indicator.setViewPager(slide_view_pager);*/

        main_ll.setOnClickListener(new View.OnClickListener() {
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




            ic_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    sendIntent.setType("text/plain");
                    mContext.startActivity(sendIntent);

//                Bitmap b = itemView.getDrawingCache();
//                try {
//                    File cachePath = new File(mContext.getCacheDir(), "images");
//                    cachePath.mkdirs();
//                    FileOutputStream stream = new FileOutputStream(cachePath + "/image.png");
//                    b.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    stream.close();
//                    Uri contentUri = FileProvider.getUriForFile(mContext, "com.news2day", new File(new File(mContext.getCacheDir(), "images"), "image.png"));
//                    if (contentUri != null) {
//                        Intent shareIntent = new Intent();
//                        shareIntent.setAction("android.intent.action.SEND");
//                        shareIntent.setDataAndType(contentUri, mContext.getContentResolver().getType(contentUri));
//                        shareIntent.putExtra("android.intent.extra.STREAM", contentUri);
//                        mContext.startActivity(Intent.createChooser(shareIntent, mContext.getString(R.string.share_using)));
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                }
            });

            iv_bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (iv_bookmark.isSelected()) {
                        iv_bookmark.setSelected(false);
                    } else {
                        // if not selected only
                        // then show animation.
                        iv_bookmark.setSelected(true);
                        iv_bookmark.likeAnimation();
                    }
                    Toast.makeText(mContext, R.string.add_booked, Toast.LENGTH_SHORT).show();
                }
            });


           /* iv_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (iv_report.isSelected()) {
                        iv_report.setSelected(false);
                    } else {
                        // if not selected only
                        // then show animation.
                        iv_report.setSelected(true);
                        iv_report.likeAnimation();
                    }
                    final Dialog dialog = new Dialog(mContext);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_report);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    Window window = dialog.getWindow();
//                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    window.setGravity(Gravity.CENTER | Gravity.CENTER);
                    CheckBox edit_tx = dialog.findViewById(R.id.cb_mistakes);
                    LinearLayout out_side_lay_out = dialog.findViewById(R.id.out_side_lay_out);
                    TextView submit_tx = (TextView) dialog.findViewById(R.id.submit_tx);
                    out_side_lay_out.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    submit_tx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            final Dialog dialog = new Dialog(mContext);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_report_sucess);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            Window window = dialog.getWindow();
//                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            window.setGravity(Gravity.CENTER | Gravity.CENTER);
                            LinearLayout ll_dialog = dialog.findViewById(R.id.ll_dialog);

                            ll_dialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();

                        }
                    });

                    dialog.show();

                }
            });
            ic_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // bottom_download.requestFocus();
                    bottom_download.setVisibility(View.VISIBLE);
                    bottom_lay_out.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bottom_download.setVisibility(View.GONE);
                            bottom_lay_out.setVisibility(View.VISIBLE);
                            Toast.makeText(mContext, "Download Sucessfull", Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);

                }
            });*/

            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(mContext);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_image);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    Window window = dialog.getWindow();
//                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    window.setGravity(Gravity.CENTER);
                    LinearLayout ll_dialog = dialog.findViewById(R.id.ll_dialog);
                    ImageView ic_share = dialog.findViewById(R.id.ic_share);
                    ImageView ic_download = dialog.findViewById(R.id.ic_download);
                    ic_share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT,
                                    "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                            sendIntent.setType("text/plain");
                            mContext.startActivity(sendIntent);
                        }
                    });

                    ic_download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "Download Sucessful", Toast.LENGTH_SHORT).show();
                        }
                    });

                    ll_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

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
