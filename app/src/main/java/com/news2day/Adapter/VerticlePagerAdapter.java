package com.news2day.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.news2day.Activity.LoginActivity;
import com.news2day.OnStopTrackEventListener;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.model.DownloadPostPojos.DownloadPostResponsePojo;
import com.news2day.model.LikePostPojos.LikePostPojoOne;
import com.news2day.model.PostBookMarkPojos.PostBookMarkPojoOne;
import com.news2day.model.ReportPostPojo.ReportsPostPojoOne;
import com.news2day.model.RepostsPojo.ReportsPojoOne;
import com.news2day.model.SharePostsPojos.SharePostPojoOne;
import com.news2day.model.StringRefer;
import com.news2day.model.VerticalViewPojos.PostsPojoTwo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hanks.library.bang.SmallBangView;

/**
 * Created by rizvan on 12/13/16.
 */

public class VerticlePagerAdapter extends PagerAdapter implements StringRefer {
    int[] img = {R.drawable.news_bg, R.drawable.news_bg, R.drawable.news_bg};
    String currentPhotoPath = "";
    File img_file;
    Bitmap bitmap;
    FileOutputStream outputStream;
    private static final int REQUEST_EXTERNAL_STORAGe = 1;
    private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    Context mContext;
    Activity activity;
    LayoutInflater mLayoutInflater;
    ImageView ic_share, ic_bookmark, ic_download, ic_report, ic_like, ic_more;
    RelativeLayout bottom_lay_out, bottom_download, booked_rl;
    LinearLayout ll_data, ll_more, ic_bookmark_ll, ic_report_ll, dots_ll, main_ll;
    ImageView iv_image;
    ViewPager slide_view_pager;
    SmallBangView iv_like, iv_bookmark, iv_report;
    TextView tv_description, success_tx,
            dismiss_tx, title_tv;
    SpringDotsIndicator dots_indicator;
    OnStopTrackEventListener listener;
    UserDetails userDetails;
    View view;
    LinearLayout ll_dialog;
    CheckBox cb_other;
    TextView txt_title1,
            views, like_tv,
            download_tv,
            share_tv;
    List<PostsPojoTwo> data;
    RetrofitService service;
    String check_box_name = "";
    StringRefer stringRefer;
    public static TextView others_ed_tx;
    String reports_id, post_id, user_id;
    RadioButton radio_but;
    private static int checkedPosition = 0;
    int likes_count, shares_count,
            downloads_count;
    int currentItem;

    public VerticlePagerAdapter(Context context, OnStopTrackEventListener listener, List<PostsPojoTwo> data) {
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
        View itemView = mLayoutInflater.inflate(R.layout.item_news_slide, container, false);
        userDetails = new UserDetails(mContext);
        stringRefer = this;
        activity = new Activity();
        //        if (position % 2 == 0) {
//          //  label.setText(mResources[0]);
//            imageView.setImageResource(R.drawable.news_bg);
//        }
//        else{
//           // label.setText(mResources[1]);
//            imageView.setImageResource(R.drawable.ic_video1);
//        }
        service = RetrofitInstance.createService(RetrofitService.class);
        tv_description = itemView.findViewById(R.id.tv_description);
        title_tv = itemView.findViewById(R.id.title_tv);
        ic_more = itemView.findViewById(R.id.ic_more);
        ll_more = itemView.findViewById(R.id.ll_more);
        view = itemView.findViewById(R.id.view);
        iv_image = itemView.findViewById(R.id.iv_image);
        booked_rl = itemView.findViewById(R.id.booked_rl);
        success_tx = itemView.findViewById(R.id.success_tx);
        dismiss_tx = itemView.findViewById(R.id.dismiss_tx);
        slide_view_pager = itemView.findViewById(R.id.slide_view_pager);
        dots_indicator = itemView.findViewById(R.id.dots_indicator);
        txt_title1 = itemView.findViewById(R.id.txt_title1);
        views = itemView.findViewById(R.id.views);
        dots_ll = itemView.findViewById(R.id.dots_ll);
        main_ll = itemView.findViewById(R.id.main_ll);

     //   radio_but = itemView.findViewById(R.id.radio_but);

        like_tv = itemView.findViewById(R.id.like_tv);
        download_tv = itemView.findViewById(R.id.download_tv);
        share_tv = itemView.findViewById(R.id.share_tv);


        tv_description.setText(data.get(position).getDescription());
        title_tv.setText(data.get(position).getName());
        like_tv.setText(data.get(position).getLikes());
        likes_count = Integer.parseInt(data.get(position).getLikes());
        share_tv.setText(data.get(position).getShares());
        shares_count = Integer.parseInt(data.get(position).getShares());
        download_tv.setText(data.get(position).getDownloads());
        downloads_count = data.get(position).getDownloads();


        main_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDetails.getBrand().equalsIgnoreCase("1")) {
                    listener.onClick("0");
                    userDetails.setBrand("1");
                    ll_more.setVisibility(View.GONE);
                } else {
                    listener.onClick("1");
                    userDetails.setBrand("0");
                    ll_more.setVisibility(View.GONE);
                }
            }
        });
        userDetails.setPostId(data.get(position).get_id());
        user_id = userDetails.getUserId();
        post_id = data.get(position).get_id();

/*
        if (checkedPosition == 1) {
            radio_but.setChecked(false);
        } else {
            if (checkedPosition == position) {
                radio_but.setChecked(true);
            } else {
                radio_but.setChecked(false);
            }
        }
        radio_but.setOnClickListener(v -> {
            radio_but.setChecked(true);

            if (checkedPosition != position) {
                notifyDataSetChanged();
                checkedPosition = position;
            }
        });*/
        /*String[] img=data.get(position).getImage();
        System.out.println(Arrays.asList(img));*/

        verifystoragepermissions(mContext);
        SliderViewPagerAdapter sliderViewPagerAdapter = new SliderViewPagerAdapter(mContext, data.get(position).getImage(),data.get(position).getName());
        slide_view_pager.setAdapter(sliderViewPagerAdapter);
        dots_indicator.setViewPager(slide_view_pager);
        slide_view_pager.setVisibility(View.VISIBLE);
        dots_ll.setVisibility(View.VISIBLE);
        iv_image.setVisibility(View.GONE);
        /* Glide.with(mContext).load("https://news2day.s3.ap-south-1.amazonaws.com/1631077277334"+data.get(position).getImage()).error(R.mipmap.ic_launcher).into(iv_image);*/
        /*iv_image.setImageResource(Integer.parseInt(data.get(position).getImage()));*/

        ic_share = itemView.findViewById(R.id.ic_share);
        ic_bookmark = itemView.findViewById(R.id.ic_bookmark);
        ic_download = itemView.findViewById(R.id.ic_download);
        ic_report = itemView.findViewById(R.id.ic_report);
        ic_like = itemView.findViewById(R.id.ic_like);
        bottom_lay_out = itemView.findViewById(R.id.bottom_lay_out);
        bottom_download = itemView.findViewById(R.id.bottom_download);


        ll_data = itemView.findViewById(R.id.ll_data);
        iv_like = itemView.findViewById(R.id.iv_like);
        iv_bookmark = itemView.findViewById(R.id.iv_bookmark);
        iv_report = itemView.findViewById(R.id.iv_report);
        ic_more = itemView.findViewById(R.id.ic_more);
        ll_more = itemView.findViewById(R.id.ll_more);
        ic_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booked_rl.setVisibility(View.GONE);
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_dots);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog.getWindow();
//                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.setGravity(Gravity.CENTER | Gravity.CENTER);
                dialog.show();
                ImageView cancel_but = dialog.findViewById(R.id.cancel_but);
                ic_bookmark_ll = dialog.findViewById(R.id.ic_bookmark_ll);
                ic_report_ll = dialog.findViewById(R.id.ic_report_ll);


                ic_report_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        booked_rl.setVisibility(View.GONE);

                        final Dialog dialog2 = new Dialog(mContext);
                        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog2.setContentView(R.layout.dialog_report);
                        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Window window = dialog2.getWindow();
//                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                        window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                        dialog2.show();
                        RecyclerView reports_rv = dialog2.findViewById(R.id.reports_rv);
                        LinearLayout out_side_lay_out = dialog2.findViewById(R.id.out_side_lay_out);
                        TextView submit_tx = (TextView) dialog2.findViewById(R.id.submit_tx);
                        out_side_lay_out.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog2.dismiss();
                            }
                        });

                        final ProgressDialog pd = new ProgressDialog(mContext);
                        pd.setMessage("Loading.....");
                        pd.show();
                        pd.setCancelable(true);
                        service.get_report().enqueue(new Callback<ReportsPojoOne>() {
                            @Override
                            public void onResponse(Call<ReportsPojoOne> call, Response<ReportsPojoOne> response) {
                                if (response.body() != null && response.isSuccessful()) {
                                    ReportsPojoOne reportsPojoOne = response.body();
                                    if (reportsPojoOne.getStatus() != null) {
                                        if (!userDetails.isLoggedIn()) {
                                            Intent intent = new Intent(mContext, LoginActivity.class);
                                            mContext.startActivity(intent);
                                            Toast.makeText(mContext, R.string.you_must, Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (reportsPojoOne.getStatus().equalsIgnoreCase("200")) {
                                                pd.dismiss();
                                                ReportMistakesAdapter reportMistakesAdapter = new ReportMistakesAdapter(mContext, reportsPojoOne.getData(), stringRefer);

                                                reports_rv.setAdapter(reportMistakesAdapter);
                                                GridLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 1);
                                                reports_rv.setLayoutManager(linearLayoutManager);

                                                reports_id = reportsPojoOne.getData().get(position).get_id();
                                            /*if (check_box_name.equals("others")){
                                                others_ed_tx.setVisibility(View.VISIBLE);
                                            }*/

                                            } else {
                                                pd.dismiss();
                                                Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }else{
                                        pd.dismiss();
                                        Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ReportsPojoOne> call, Throwable t) {
                                pd.dismiss();
                                Toast.makeText(mContext, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                            }
                        });


//                            cb_other = dialog2.findViewById(R.id.cb_other);
                        others_ed_tx = (TextView) dialog2.findViewById(R.id.others_ed_tx);

//                            cb_other.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    others_ed_tx.setVisibility(View.VISIBLE);
//                                }
//                            });

                        submit_tx.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final ProgressDialog pd = new ProgressDialog(mContext);
                                pd.setMessage("Loading.....");
                                pd.setContentView(R.layout.item_city);
                                pd.show();
                                service.report_post(reports_id, user_id, post_id).enqueue(new Callback<ReportsPostPojoOne>() {
                                    @Override
                                    public void onResponse(Call<ReportsPostPojoOne> call, Response<ReportsPostPojoOne> response) {
                                        if (response.body() != null && response.isSuccessful()) {
                                            ReportsPostPojoOne reportsPostPojoOne = response.body();

                                            if (reportsPostPojoOne.getStatus() != null) {
                                                if (!userDetails.isLoggedIn()) {
                                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                                    mContext.startActivity(intent);
                                                    Toast.makeText(mContext, R.string.you_must, Toast.LENGTH_SHORT).show();
                                                } else {
                                                    if (reportsPostPojoOne.getStatus().equalsIgnoreCase("200")) {
                                                        pd.dismiss();
                                                        if (check_box_name.equals("")) {
                                                            Toast.makeText(mContext, R.string.select_report, Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            if (check_box_name.equals("others")) {
                                                                if (others_ed_tx.getText().toString().isEmpty()) {
                                                                    Toast.makeText(mContext, R.string.select_other_report, Toast.LENGTH_SHORT).show();
                                                                } else {

                                                                    dialog2.dismiss();
                                                                    final Dialog dialog1 = new Dialog(mContext);
                                                                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                    dialog1.setContentView(R.layout.dialog_report_sucess);
                                                                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                                    Window window = dialog1.getWindow();
//                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                                                    window.setGravity(Gravity.CENTER | Gravity.CENTER);
                                                                    ll_dialog = dialog1.findViewById(R.id.ll_dialog);

                                                                    ll_dialog.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            dialog1.dismiss();
                                                                            dialog.dismiss();
                                                                            Toast.makeText(mContext, R.string.report_sucessfull, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                    dialog1.show();

                                                                }
                                                            } else {
                                                                dialog2.dismiss();
                                                                final Dialog dialog1 = new Dialog(mContext);
                                                                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                dialog1.setContentView(R.layout.dialog_report_sucess);
                                                                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                                Window window = dialog1.getWindow();
//                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                                                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                                                window.setGravity(Gravity.CENTER | Gravity.CENTER);
                                                                ll_dialog = dialog1.findViewById(R.id.ll_dialog);

                                                                ll_dialog.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        dialog1.dismiss();
                                                                        dialog.dismiss();
                                                                        Toast.makeText(mContext, "Reported Successfully", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                                dialog1.show();
                                                            }

                                                        }
                                                    } else {
                                                        pd.dismiss();
                                                        Toast.makeText(mContext, "Error Found Please Check  ", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure
                                            (Call<ReportsPostPojoOne> call, Throwable t) {

                                    }
                                });


                            }
                        });


                    }


                });


                ic_bookmark_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ProgressDialog pd = new ProgressDialog(mContext);
                        pd.setMessage("Loading.....");
                        pd.show();
                        pd.setCancelable(true);
                        service.bookmark_posts(user_id, post_id).enqueue(new Callback<PostBookMarkPojoOne>() {
                            @Override
                            public void onResponse(Call<PostBookMarkPojoOne> call, Response<PostBookMarkPojoOne> response) {
                                if (response.body() != null && response.isSuccessful()) {
                                    PostBookMarkPojoOne postBookMarkPojoOne = response.body();

                                    if (postBookMarkPojoOne.getStatus() != null) {
                                        if (!userDetails.isLoggedIn()) {
                                            Intent intent = new Intent(mContext, LoginActivity.class);
                                            mContext.startActivity(intent);
                                            Toast.makeText(mContext, "You Must Be LogIn Before BookMark Post", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (postBookMarkPojoOne.getStatus().equalsIgnoreCase("200")) {
                                                pd.dismiss();
                                                if (iv_bookmark.isSelected()) {
                                                    iv_bookmark.setSelected(false);
                                                    booked_rl.setVisibility(View.GONE);
                                                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    dialog.dismiss();
                                                    // if not selected only
                                                    // then show animation.
                                                    // iv_bookmark.setSelected(true);
                                                    //  iv_bookmark.likeAnimation();
                                                    booked_rl.setVisibility(View.VISIBLE);
                                                    success_tx.setText("You BookMarked this post Successful");
                                                    dismiss_tx.setText("Dismiss");
                                                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                pd.dismiss();
                                                Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }else {
                                        pd.dismiss();
                                        Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<PostBookMarkPojoOne> call, Throwable t) {
                                pd.dismiss();
                                Toast.makeText(mContext, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });

                cancel_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        for (int i=0;i<data.size();i++) {

            ic_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    booked_rl.setVisibility(View.GONE);
                    final ProgressDialog pd = new ProgressDialog(mContext);
                    pd.setMessage("Loading.....");
                    pd.show();
                    pd.setCancelable(true);
                    service.share_posts(user_id, post_id).enqueue(new Callback<SharePostPojoOne>() {
                        @Override
                        public void onResponse(Call<SharePostPojoOne> call, Response<SharePostPojoOne> response) {
                            if (response.body() != null && response.isSuccessful()) {
                                SharePostPojoOne sharePostPojoOne = response.body();

                                if (sharePostPojoOne.getStatus() != null) {
                                    if (!userDetails.isLoggedIn()) {
                                        Intent intent = new Intent(mContext, LoginActivity.class);
                                        mContext.startActivity(intent);
                                        Toast.makeText(mContext, "You Must Be LogIn Before Sharing Post", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (sharePostPojoOne.getStatus().equalsIgnoreCase("200")) {
                                            pd.dismiss();
                                            shares_count = shares_count + 1;
                                            share_tv.setText(String.valueOf(shares_count));
                                            Intent intent = new Intent(Intent.ACTION_SEND);
                                            intent.setType("text/plain");
                                            intent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                                            intent.putExtra(Intent.EXTRA_TEXT, "Hi,Friends This Post is Useful For all" + " " + sharePostPojoOne.getData().getPostId());
                                            mContext.startActivity(Intent.createChooser(intent, "share via"));
                                            Toast.makeText(mContext, "This Post Share Successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<SharePostPojoOne> call, Throwable t) {

                        }
                    });

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

    iv_like.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            booked_rl.setVisibility(View.GONE);
            final ProgressDialog pd = new ProgressDialog(mContext);
            pd.setMessage("Loading.....");
            pd.show();
            pd.setCancelable(true);
            service.like_post(userDetails.getUserId(), post_id).enqueue(new Callback<LikePostPojoOne>() {
                @Override
                public void onResponse(Call<LikePostPojoOne> call, Response<LikePostPojoOne> response) {
                    if (response.body() != null && response.isSuccessful()) {

                        LikePostPojoOne likePostPojoOne = response.body();
                        if (likePostPojoOne.getStatus() != null) {
                            if (!userDetails.isLoggedIn()) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                mContext.startActivity(intent);
                                Toast.makeText(mContext, "You Must Be LogIn Before Liking Post", Toast.LENGTH_SHORT).show();
                            } else {
                                if (likePostPojoOne.getStatus().equalsIgnoreCase("200")) {
                                    pd.dismiss();

                                    if (iv_like.isSelected()) {
                                        iv_like.setSelected(false);
                                        iv_like.likeAnimation();
                                        likes_count = likes_count - 1;
                                        like_tv.setText(String.valueOf(likes_count));
                                        ic_like.setColorFilter(mContext.getResources().getColor(R.color.black));
                                        Toast.makeText(mContext, "This Post UnLiked Successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // if not selected only
                                        // then show animation.

                                        iv_like.setSelected(true);
                                        ic_like.setColorFilter(mContext.getResources().getColor(R.color.app_color));
                                        iv_like.likeAnimation();
                                        likes_count = likes_count + 1;
                                        like_tv.setText(String.valueOf(likes_count));

                                                /*if (checkedPosition != position) {
                                                    notifyDataSetChanged();
                                                    checkedPosition = position;
                                                }*/
                                        Toast.makeText(mContext, "Post Liked Successfully", Toast.LENGTH_SHORT).show();
                                    }


                                } else {
                                    pd.dismiss();
                                    Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            mContext.startActivity(intent);
                        }
                    }

                }

                @Override
                public void onFailure(Call<LikePostPojoOne> call, Throwable t) {

                }
            });

            //  ic_like.setBackgroundResource(R.drawable.ic_like_click);
        }
    });




            ic_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // bottom_download.requestFocus();
                    main_ll.setVisibility(View.VISIBLE);
                    final ProgressDialog pd = new ProgressDialog(mContext);
                    pd.setMessage("Loading.....");
                    pd.show();
                    pd.setCancelable(true);
                    service.download_post(user_id, post_id).enqueue(new Callback<DownloadPostResponsePojo>() {
                        @Override
                        public void onResponse(Call<DownloadPostResponsePojo> call, Response<DownloadPostResponsePojo> response) {
                            if (response.body() != null && response.isSuccessful()) {

                                DownloadPostResponsePojo downloadPostResponsePojo = response.body();
                                if (downloadPostResponsePojo.getStatus() != null) {
                                    if (!userDetails.isLoggedIn()) {
                                        Intent intent = new Intent(mContext, LoginActivity.class);
                                        mContext.startActivity(intent);
                                    } else {
                                        if (downloadPostResponsePojo.getStatus().equalsIgnoreCase("200")) {
                                            pd.dismiss();


                                            takeScreenshot(main_ll.getRootView());
                                            downloads_count = downloads_count + 1;
                                            download_tv.setText(String.valueOf(downloads_count));
                                            bottom_download.setVisibility(View.VISIBLE);
                                            bottom_lay_out.setVisibility(View.GONE);
                                            view.setVisibility(View.GONE);

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    bottom_download.setVisibility(View.GONE);
                                                    bottom_lay_out.setVisibility(View.VISIBLE);
                                                    view.setVisibility(View.VISIBLE);
                                                    booked_rl.setVisibility(View.VISIBLE);
                                                    success_tx.setText("Download Successful");
                                                    dismiss_tx.setText("Dismiss");
                                                    //  Toast.makeText(mContext, "Download Sucessful", Toast.LENGTH_SHORT).show();
                                                }
                                            }, 2000);
                                        } else {
                                            pd.dismiss();
                                            Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<DownloadPostResponsePojo> call, Throwable t) {

                        }
                    });


                }
            });

        }
       dismiss_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booked_rl.setVisibility(View.GONE);
            }
        });


           /* iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }

            });*/


        container.addView(itemView);
        return itemView;
    }

    private void getSystemService(String downloadService) {

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    private void takeScreenshot(View v) {
        Date now = new Date();
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
      /*  BitmapDrawable bitmapDrawable = (BitmapDrawable) main_ll.getDrawingCache();
        Bitmap bitmap = bitmapDrawable.getBitmap();
         Bitmap bitmap = Bitmap.createScaledBitmap(bitmap, 1080, 1920, true);*/


        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsoluteFile() + "/Demo/");

        File file = new File(dir, System.currentTimeMillis() + ".jpg");
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        Toast.makeText(mContext, "Download Successfully" + "\"" + "Please Check ", Toast.LENGTH_SHORT).show();
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";

            // create bitmap screen capture
            //  View v1 = activity.getWindow().getDecorView().getRootView();
            v.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
            v.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //setting screenshot in imageview
            String filePath = imageFile.getPath();

            Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());


        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }*/
    }

    public static void verifystoragepermissions(Context activity) {

        int permissions = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // If storage permission is not given then request for External Storage Permission
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) activity, permissionstorage, REQUEST_EXTERNAL_STORAGe);
        }
    }

    @Override
    public void district_name_str(String d_n_s, String district_id) {
        check_box_name = d_n_s;
    }


}
