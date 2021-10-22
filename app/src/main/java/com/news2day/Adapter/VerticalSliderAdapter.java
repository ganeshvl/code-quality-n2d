package com.news2day.Adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chaos.view.PinView;
import com.news2day.Activity.ContestsActivity;
import com.news2day.Activity.HomeActivity;

import com.news2day.Activity.OTPActivity;
import com.news2day.Activity.TermsAndconditionsActivity;
import com.news2day.Activity.TrendingActivity;
import com.news2day.EventData;
import com.news2day.FlipAnimation;
import com.news2day.OnStopTrackEventListener;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.model.AdsPojo.AdsMyPojo;
import com.news2day.model.DownloadPostPojos.DownloadPostResponsePojo;
import com.news2day.model.LikePostPojos.LikePostPojoOne;
import com.news2day.model.LoginPojo;
import com.news2day.model.OtpPojo;
import com.news2day.model.PostBookMarkPojos.PostBookMarkPojoOne;
import com.news2day.model.ReportPostPojo.ReportsPostPojoOne;
import com.news2day.model.RepostsPojo.ReportsPojoOne;
import com.news2day.model.ResendOtpPojo;
import com.news2day.model.SharePostsPojos.SharePostPojoOne;
import com.news2day.model.StringRefer;
import com.news2day.model.VerticalViewPojos.PostsPojoTwo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hanks.library.bang.SmallBangView;

import static com.news2day.Adapter.VerticlePagerAdapter.verifystoragepermissions;

public class VerticalSliderAdapter extends RecyclerView.Adapter<VerticalSliderAdapter.ViewHolder> implements StringRefer {
    Context context;
    FileOutputStream outputStream;
    private static final int REQUEST_EXTERNAL_STORAGe = 1;
    private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private final static int FADE_DURATION = 1000;
    Activity activity;
    Bitmap bitmap;
    LayoutInflater mLayoutInflater;
    static final int BUFF_SIZE = 2048;
    static final String DEFAULT_ENCODING = "utf-8";
    public static boolean isFront = false;
    LinearLayout[] data_ll_arr, data_ll_arr_two;

    private static final float Min_Scale = 0.8f;
    public static SpringDotsIndicator dots_indicator;
    OnStopTrackEventListener listener;
    UserDetails userDetails;
    View view;
    LinearLayout ll_dialog;
    CheckBox cb_other;
    Uri uri;
    String path;
    List<PostsPojoTwo> data;
    RetrofitService service;
    String check_box_name = "";
    StringRefer stringRefer;
    public static TextView others_ed_tx;

    String reports_id, post_id, user_id;
    RadioButton radio_but;
    private static int checkedPosition = 0;
    int likes_count, shares_count;
    int currentItem, downloads_count;


    public VerticalSliderAdapter(Context context, OnStopTrackEventListener listener, List<PostsPojoTwo> data) {
        this.context = context;
        this.listener = listener;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(context).inflate(R.layout.item_news_slide, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        verifystoragepermissions(context);

        userDetails = new UserDetails(context);
        service = RetrofitInstance.createService(RetrofitService.class);
        return viewHolder;

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //  transformPage(holder.main_ll, position);
        SliderViewPagerAdapter sliderViewPagerAdapter = new SliderViewPagerAdapter(context, data.get(position).getImage(), data.get(position).getName());
        holder.slide_view_pager.setAdapter(sliderViewPagerAdapter);
        dots_indicator.setViewPager(holder.slide_view_pager);
        holder.slide_view_pager.setVisibility(View.VISIBLE);
        stringRefer = this;
        String[] imageurl = data.get(position).getImage();

        if (imageurl.length < 2) {
            holder.dots_ll.setVisibility(View.GONE);
            dots_indicator.setVisibility(View.GONE);

        } else {
            holder.dots_ll.setVisibility(View.VISIBLE);
            dots_indicator.setVisibility(View.VISIBLE);
        }
        if (imageurl != null) {
            for (int i = 0; i < imageurl.length; i++) {
                if (imageurl[i] != null) {
                    uri = Uri.parse("http://18.118.90.146:5001/" + imageurl[i]);
                    path = "http://18.118.90.146:5001/" + imageurl[i];
                } else {
                    path = "";
                    uri = null;
                    holder.trending_tv.setTextColor(context.getResources().getColor(R.color.app_color));
                    holder.trending_tv.setBackgroundColor(context.getResources().getColor(R.color.white));
                }
            }
        }


        holder.iv_image.setVisibility(View.GONE);
        userDetails.setPostId(data.get(position).get_id());
        user_id = userDetails.getUserId();
        /* post_id = data.get(position).get_id();*/

        if (data.get(position).getDescription() == null) {

        } else {
            holder.tv_description.setText(Html.fromHtml(decodeString(data.get(position).getDescription())));

        }


        holder.title_tv.setText(data.get(position).getName());
        holder.like_tv.setText(data.get(position).getLikes());

        holder.share_tv.setText(data.get(position).getShares());

        holder.download_tv.setText(data.get(position).getDownloads() + "");
//        downloads_count = data.get(position).getDownloads();

        holder.main_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDetails.getBrand().equalsIgnoreCase("1")) {
                    listener.onClick("0");
                    userDetails.setBrand("1");
                    holder.ll_more.setVisibility(View.GONE);
                } else {
                    listener.onClick("1");
                    userDetails.setBrand("0");
                    holder.ll_more.setVisibility(View.GONE);
                }
            }
        });

        holder.ic_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.booked_rl.setVisibility(View.GONE);
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_dots);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Window window = dialog.getWindow();
                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.setGravity(Gravity.CENTER | Gravity.CENTER);
                dialog.show();
                ImageView cancel_but = dialog.findViewById(R.id.cancel_but);
                holder.ic_bookmark_ll = dialog.findViewById(R.id.ic_bookmark_ll);
                holder.ic_report_ll = dialog.findViewById(R.id.ic_report_ll);
                holder.ic_report_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        holder.booked_rl.setVisibility(View.GONE);
                        final Dialog dialog2 = new Dialog(context);
                        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog2.setContentView(R.layout.dialog_report);
                        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        Window window = dialog2.getWindow();
                        window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
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
                        final ProgressDialog pd = new ProgressDialog(context);
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

                                            final Dialog dialog2 = new Dialog(context);
                                            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog2.setContentView(R.layout.activity_login);
                                            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            Window window = dialog2.getWindow();
                                            window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                            window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                            dialog2.show();
                                            TextView enterMobileTv, agreeTv, termsAndConditionsTv, verifyBut;
                                            EditText mobileEdText;
                                            enterMobileTv = dialog2.findViewById(R.id.enter_mobile_tv);
                                            agreeTv = dialog2.findViewById(R.id.agree_tv);
                                            termsAndConditionsTv = dialog2.findViewById(R.id.terms_and_conditions_tv);
                                            verifyBut = dialog2.findViewById(R.id.verify_but);
                                            mobileEdText = dialog2.findViewById(R.id.mobile_ed_text);
                                            //  backBut.setImageResource(R.drawable.back_arrow);

                                            String language = userDetails.getLanguage();
                                            if (language.equals("ENGLISH")) {

                                                termsAndConditionsTv.setHint(R.string.terms_amp_conditions_en);
                                                verifyBut.setText(R.string.continue_en);
                                                mobileEdText.setHint(R.string.mobile_number_en);
                                                agreeTv.setText(R.string.by_continuing_you_agree_to_our_en);
                                                enterMobileTv.setText(R.string.enter_your_mobile_number_en);


                                            } else if (language.equals("TELUGU")) {

                                                mobileEdText.setHint(R.string.mobile_number_te);
                                                termsAndConditionsTv.setText(R.string.terms_amp_conditions_te);
                                                verifyBut.setText(R.string.continue_te);
                                                agreeTv.setText(R.string.by_continuing_you_agree_to_our_te);
                                                enterMobileTv.setText(R.string.enter_your_mobile_number_te);

                                            } else if (language.equals("HINDI")) {

                                                mobileEdText.setHint(R.string.mobile_number_hi);
                                                termsAndConditionsTv.setText(R.string.terms_amp_conditions_hi);
                                                verifyBut.setText(R.string.continue_hi);
                                                agreeTv.setText(R.string.by_continuing_you_agree_to_our_hi);
                                                enterMobileTv.setText(R.string.enter_your_mobile_number_hi);
                                            }
                                            //activityLoginBinding.mobileEdText.requestFocus();


                                            verifyBut.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    String validNumber = "^[6-9][0-9]{9}$";
                                                    if (mobileEdText.getText().toString().isEmpty()) {
                                                        Toast.makeText(context, R.string.enter_mobile_number, Toast.LENGTH_SHORT).show();
                                                    } else if (!mobileEdText.getText().toString().matches(validNumber)) {
                                                        Toast.makeText(context, R.string.enter_valid_mobile, Toast.LENGTH_SHORT).show();
                                                    } else {

                                                        final ProgressDialog pd = new ProgressDialog(context);
                                                        pd.setMessage("Loading.....");
                                                        pd.show();
                                                        pd.setCancelable(true);
                                                        service.check_mobile(mobileEdText.getText().toString().trim()).enqueue(new Callback<LoginPojo>() {
                                                            @Override
                                                            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                                                                if (response.body() != null && response.isSuccessful()) {
                                                                    LoginPojo loginPojo = response.body();
                                                                    if (loginPojo != null) {
                                                                        if (loginPojo.getSuccess().equalsIgnoreCase("true")) {
                                                                            pd.dismiss();
                                                                            userDetails.setMobile(mobileEdText.getText().toString());
                                                                            userDetails.setUserId(loginPojo.getUser_id());
//                                                                            Intent intent = new Intent(context, OTPActivity.class);
//                                                                            intent.putExtra("OTP", loginPojo.getOtp());
//                                                                            intent.putExtra("MobileNumber", mobileEdText.getText().toString());
//                                                                            context.startActivity(intent);
                                                                            dialog2.dismiss();
                                                                            Toast.makeText(context, R.string.otp_sent + loginPojo.getOtp(), Toast.LENGTH_SHORT).show();

                                                                            final Dialog dialog3 = new Dialog(context);
                                                                            dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                            dialog3.setContentView(R.layout.activity_otpactivity);
                                                                            dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                                            Window window = dialog3.getWindow();
                                                                            window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                                                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                                                            window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                                                            dialog3.show();
                                                                            TextView enterMobileTv, verificationCodeTv, pin_text, enterOtpTv, tvResend, otpSubmitBut;
                                                                            PinView txtPinEntry;

                                                                            verificationCodeTv = dialog3.findViewById(R.id.verification_code_tv);
                                                                            pin_text = dialog3.findViewById(R.id.pin_text);
                                                                            enterOtpTv = dialog3.findViewById(R.id.enter_otp_tv);
                                                                            txtPinEntry = dialog3.findViewById(R.id.txt_pin_entry);
                                                                            tvResend = dialog3.findViewById(R.id.tv_resend);
                                                                            pin_text.setText(mobileEdText.getText().toString());
                                                                            otpSubmitBut = dialog3.findViewById(R.id.otp_submit_but);


                                                                            String language = userDetails.getLanguage();

                                                                            if (language.equals("ENGLISH")) {

                                                                                enterOtpTv.setText(R.string.enter_otp_en);
                                                                                verificationCodeTv.setText(R.string.verification_code_en);
                                                                                tvResend.setText(R.string.resend_en);
                                                                                otpSubmitBut.setText(R.string.log_in_en);


                                                                            } else if (language.equals("TELUGU")) {

                                                                                enterOtpTv.setText(R.string.enter_otp_te);
                                                                                verificationCodeTv.setText(R.string.verification_code_te);
                                                                                tvResend.setText(R.string.resend_te);
                                                                                otpSubmitBut.setText(R.string.log_in_te);

                                                                            } else if (language.equals("HINDI")) {

                                                                                enterOtpTv.setText(R.string.enter_otp_hi);
                                                                                verificationCodeTv.setText(R.string.verification_code_hi);
                                                                                tvResend.setText(R.string.resend_hi);
                                                                                otpSubmitBut.setText(R.string.log_in_hi);

                                                                            }


                                                                            tvResend.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    final ProgressDialog pd = new ProgressDialog(context);
                                                                                    pd.setMessage("Loading.....");
                                                                                    pd.show();
                                                                                    pd.setCancelable(true);
                                                                                    service.Resend_OTP(mobileEdText.getText().toString(), txtPinEntry.getText().toString().trim()).enqueue(new Callback<ResendOtpPojo>() {
                                                                                        @Override
                                                                                        public void onResponse(Call<ResendOtpPojo> call, Response<ResendOtpPojo> response) {
                                                                                            if (response.body() != null && response.isSuccessful()) {
                                                                                                ResendOtpPojo resendOtpPojo = response.body();
                                                                                                txtPinEntry.setText("");
                                                                                                if (resendOtpPojo != null) {
                                                                                                    if (resendOtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                        pd.dismiss();
                                                                                                        Toast.makeText(context, resendOtpPojo.getOtp() + "  " + "\\" + "  " + resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                    } else {
                                                                                                        Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                                    }

                                                                                                } else {
                                                                                                    Toast.makeText(context, resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                                }

                                                                                            }
                                                                                        }

                                                                                        @Override
                                                                                        public void onFailure(Call<ResendOtpPojo> call, Throwable t) {
                                                                                            pd.dismiss();
                                                                                            Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    });
                                                                                }
                                                                            });
                                                                            otpSubmitBut.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View view) {
                                                                                    if (txtPinEntry.getText().toString().isEmpty()) {
                                                                                        Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show();

                                                                                    } else if (txtPinEntry.length() < 4) {
                                                                                        Toast.makeText(context, "Please enter Valid OTP", Toast.LENGTH_SHORT).show();
                                                                                    } else {
                                                                                        final ProgressDialog pd = new ProgressDialog(context);
                                                                                        pd.setMessage("Loading.....");
                                                                                        pd.show();
                                                                                        pd.setCancelable(true);
                                                                                        service.OTP(mobileEdText.getText().toString(), txtPinEntry.getText().toString().trim()).enqueue(new Callback<OtpPojo>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<OtpPojo> call, Response<OtpPojo> response) {
                                                                                                if (response.body() != null && response.isSuccessful()) {

                                                                                                    OtpPojo OtpPojo = response.body();
                                                                                                    if (OtpPojo != null) {
                                                                                                        if (OtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                            pd.dismiss();
                                                                                                            dialog3.dismiss();
                                                                                                            userDetails.setLoggedIn();

                                                                                                            Toast.makeText(context, OtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                                        } else {
                                                                                                            pd.dismiss();
                                                                                                            Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                                        }
                                                                                                    } else {
                                                                                                        pd.dismiss();
                                                                                                        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();

                                                                                                    }

                               /* if (otp==otpactivityBinding.txtPinEntry.getText().toString()){

                                }else{
                                    Toast.makeText(context, "Please Check OTP", Toast.LENGTH_SHORT).show();
                                }*/
                                                                                                }
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<OtpPojo> call, Throwable t) {
                                                                                                pd.dismiss();
                                                                                                Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });

                                                                                    }

                                                                                }
                                                                            });
                                                                        } else {
                                                                            pd.dismiss();
                                                                            Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    } else {
                                                                        pd.dismiss();
                                                                        Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }


                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<LoginPojo> call, Throwable t) {
                                                                pd.dismiss();
                                                                Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

                                                            }
                                                        });


                                                    }
                                                }
                                            });

                                            termsAndConditionsTv.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(context, TermsAndconditionsActivity.class);
                                                    context.startActivity(intent);
                                                }
                                            });

//                                            Intent intent = new Intent(context, LoginActivity.class);
//                                            context.startActivity(intent);
                                            Toast.makeText(context, "You Must Be LogIn Before Reporting Post", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (reportsPojoOne.getStatus().equalsIgnoreCase("200")) {
                                                pd.dismiss();
                                                ReportMistakesAdapter reportMistakesAdapter = new ReportMistakesAdapter(context, reportsPojoOne.getData(), stringRefer);
                                                reports_rv.setAdapter(reportMistakesAdapter);
                                                GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 1);
                                                reports_rv.setLayoutManager(linearLayoutManager);
                                                reports_id = reportsPojoOne.getData().get(position).get_id();
                                     /*if (check_box_name.equals("others")){
                                         others_ed_tx.setVisibility(View.VISIBLE);
                                     }*/
                                            } else {
                                                pd.dismiss();
                                                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        pd.dismiss();
                                        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ReportsPojoOne> call, Throwable t) {
                                pd.dismiss();
                                Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                            }
                        });
                        //  cb_other = dialog2.findViewById(R.id.cb_other);
                        others_ed_tx = (TextView) dialog2.findViewById(R.id.others_ed_tx);
                       /* cb_other.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                others_ed_tx.setVisibility(View.VISIBLE);
                            }
                        });*/
                        submit_tx.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final ProgressDialog pd = new ProgressDialog(context);
                                pd.setMessage("Loading.....");
                                pd.setContentView(R.layout.item_city);
                                pd.show();
                                service.report_post(reports_id, user_id, data.get(position).get_id()).enqueue(new Callback<ReportsPostPojoOne>() {
                                    @Override
                                    public void onResponse(Call<ReportsPostPojoOne> call, Response<ReportsPostPojoOne> response) {
                                        if (response.body() != null && response.isSuccessful()) {
                                            ReportsPostPojoOne reportsPostPojoOne = response.body();
                                            if (reportsPostPojoOne != null) {
                                                if (!userDetails.isLoggedIn()) {
                                                    final Dialog dialog2 = new Dialog(context);
                                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    dialog2.setContentView(R.layout.activity_login);
                                                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                    Window window = dialog2.getWindow();
                                                    window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                                    window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                                    dialog2.show();
                                                    TextView enterMobileTv, agreeTv, termsAndConditionsTv, verifyBut;
                                                    EditText mobileEdText;
                                                    enterMobileTv = dialog2.findViewById(R.id.enter_mobile_tv);
                                                    agreeTv = dialog2.findViewById(R.id.agree_tv);
                                                    termsAndConditionsTv = dialog2.findViewById(R.id.terms_and_conditions_tv);
                                                    verifyBut = dialog2.findViewById(R.id.verify_but);
                                                    mobileEdText = dialog2.findViewById(R.id.mobile_ed_text);
                                                    //  backBut.setImageResource(R.drawable.back_arrow);

                                                    String language = userDetails.getLanguage();
                                                    if (language.equals("ENGLISH")) {

                                                        termsAndConditionsTv.setHint(R.string.terms_amp_conditions_en);
                                                        verifyBut.setText(R.string.continue_en);
                                                        mobileEdText.setHint(R.string.mobile_number_en);
                                                        agreeTv.setText(R.string.by_continuing_you_agree_to_our_en);
                                                        enterMobileTv.setText(R.string.enter_your_mobile_number_en);


                                                    } else if (language.equals("TELUGU")) {

                                                        mobileEdText.setHint(R.string.mobile_number_te);
                                                        termsAndConditionsTv.setText(R.string.terms_amp_conditions_te);
                                                        verifyBut.setText(R.string.continue_te);
                                                        agreeTv.setText(R.string.by_continuing_you_agree_to_our_te);
                                                        enterMobileTv.setText(R.string.enter_your_mobile_number_te);

                                                    } else if (language.equals("HINDI")) {

                                                        mobileEdText.setHint(R.string.mobile_number_hi);
                                                        termsAndConditionsTv.setText(R.string.terms_amp_conditions_hi);
                                                        verifyBut.setText(R.string.continue_hi);
                                                        agreeTv.setText(R.string.by_continuing_you_agree_to_our_hi);
                                                        enterMobileTv.setText(R.string.enter_your_mobile_number_hi);
                                                    }
                                                    //activityLoginBinding.mobileEdText.requestFocus();


                                                    verifyBut.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            String validNumber = "^[6-9][0-9]{9}$";
                                                            if (mobileEdText.getText().toString().isEmpty()) {
                                                                Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                                                            } else if (!mobileEdText.getText().toString().matches(validNumber)) {
                                                                Toast.makeText(context, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                                                            } else {

                                                                final ProgressDialog pd = new ProgressDialog(context);
                                                                pd.setMessage("Loading.....");
                                                                pd.show();
                                                                pd.setCancelable(true);
                                                                service.check_mobile(mobileEdText.getText().toString().trim()).enqueue(new Callback<LoginPojo>() {
                                                                    @Override
                                                                    public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                                                                        if (response.body() != null && response.isSuccessful()) {
                                                                            LoginPojo loginPojo = response.body();
                                                                            if (loginPojo != null) {
                                                                                if (loginPojo.getSuccess().equalsIgnoreCase("true")) {
                                                                                    pd.dismiss();
                                                                                    userDetails.setMobile(mobileEdText.getText().toString());
                                                                                    userDetails.setUserId(loginPojo.getUser_id());
//                                                                            Intent intent = new Intent(context, OTPActivity.class);
//                                                                            intent.putExtra("OTP", loginPojo.getOtp());
//                                                                            intent.putExtra("MobileNumber", mobileEdText.getText().toString());
//                                                                            context.startActivity(intent);
                                                                                    dialog2.dismiss();
                                                                                    Toast.makeText(context, "OTP Sent Successfully :" + loginPojo.getOtp(), Toast.LENGTH_SHORT).show();

                                                                                    final Dialog dialog3 = new Dialog(context);
                                                                                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                                    dialog3.setContentView(R.layout.activity_otpactivity);
                                                                                    dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                                                    Window window = dialog3.getWindow();
                                                                                    window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                                                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                                                                    window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                                                                    dialog3.show();
                                                                                    TextView enterMobileTv, verificationCodeTv, pin_text, enterOtpTv, tvResend, otpSubmitBut;
                                                                                    PinView txtPinEntry;

                                                                                    verificationCodeTv = dialog3.findViewById(R.id.verification_code_tv);
                                                                                    pin_text = dialog3.findViewById(R.id.pin_text);
                                                                                    enterOtpTv = dialog3.findViewById(R.id.enter_otp_tv);
                                                                                    txtPinEntry = dialog3.findViewById(R.id.txt_pin_entry);
                                                                                    tvResend = dialog3.findViewById(R.id.tv_resend);
                                                                                    pin_text.setText(mobileEdText.getText().toString());
                                                                                    otpSubmitBut = dialog3.findViewById(R.id.otp_submit_but);


                                                                                    String language = userDetails.getLanguage();

                                                                                    if (language.equals("ENGLISH")) {

                                                                                        enterOtpTv.setText(R.string.enter_otp_en);
                                                                                        verificationCodeTv.setText(R.string.verification_code_en);
                                                                                        tvResend.setText(R.string.resend_en);
                                                                                        otpSubmitBut.setText(R.string.log_in_en);


                                                                                    } else if (language.equals("TELUGU")) {

                                                                                        enterOtpTv.setText(R.string.enter_otp_te);
                                                                                        verificationCodeTv.setText(R.string.verification_code_te);
                                                                                        tvResend.setText(R.string.resend_te);
                                                                                        otpSubmitBut.setText(R.string.log_in_te);

                                                                                    } else if (language.equals("HINDI")) {

                                                                                        enterOtpTv.setText(R.string.enter_otp_hi);
                                                                                        verificationCodeTv.setText(R.string.verification_code_hi);
                                                                                        tvResend.setText(R.string.resend_hi);
                                                                                        otpSubmitBut.setText(R.string.log_in_hi);

                                                                                    }


                                                                                    tvResend.setOnClickListener(new View.OnClickListener() {
                                                                                        @Override
                                                                                        public void onClick(View v) {
                                                                                            final ProgressDialog pd = new ProgressDialog(context);
                                                                                            pd.setMessage("Loading.....");
                                                                                            pd.show();
                                                                                            pd.setCancelable(true);
                                                                                            service.Resend_OTP(mobileEdText.getText().toString(), txtPinEntry.getText().toString().trim()).enqueue(new Callback<ResendOtpPojo>() {
                                                                                                @Override
                                                                                                public void onResponse(Call<ResendOtpPojo> call, Response<ResendOtpPojo> response) {
                                                                                                    if (response.body() != null && response.isSuccessful()) {
                                                                                                        ResendOtpPojo resendOtpPojo = response.body();
                                                                                                        txtPinEntry.setText("");
                                                                                                        if (resendOtpPojo != null) {
                                                                                                            if (resendOtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                                pd.dismiss();
                                                                                                                Toast.makeText(context, resendOtpPojo.getOtp() + "  " + "\\" + "  " + resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                            } else {
                                                                                                                Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                                            }

                                                                                                        } else {
                                                                                                            Toast.makeText(context, resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                                        }

                                                                                                    }
                                                                                                }

                                                                                                @Override
                                                                                                public void onFailure(Call<ResendOtpPojo> call, Throwable t) {
                                                                                                    pd.dismiss();
                                                                                                    Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            });
                                                                                        }
                                                                                    });
                                                                                    otpSubmitBut.setOnClickListener(new View.OnClickListener() {
                                                                                        @Override
                                                                                        public void onClick(View view) {
                                                                                            if (txtPinEntry.getText().toString().isEmpty()) {
                                                                                                Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show();

                                                                                            } else if (txtPinEntry.length() < 4) {
                                                                                                Toast.makeText(context, "Please enter Valid OTP", Toast.LENGTH_SHORT).show();
                                                                                            } else {
                                                                                                final ProgressDialog pd = new ProgressDialog(context);
                                                                                                pd.setMessage("Loading.....");
                                                                                                pd.show();
                                                                                                pd.setCancelable(true);
                                                                                                service.OTP(mobileEdText.getText().toString(), txtPinEntry.getText().toString().trim()).enqueue(new Callback<OtpPojo>() {
                                                                                                    @Override
                                                                                                    public void onResponse(Call<OtpPojo> call, Response<OtpPojo> response) {
                                                                                                        if (response.body() != null && response.isSuccessful()) {

                                                                                                            OtpPojo OtpPojo = response.body();
                                                                                                            if (OtpPojo != null) {
                                                                                                                if (OtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                                    pd.dismiss();
                                                                                                                    dialog3.dismiss();
                                                                                                                    userDetails.setLoggedIn();

                                                                                                                    Toast.makeText(context, OtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                                                } else {
                                                                                                                    pd.dismiss();
                                                                                                                    Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                                                }
                                                                                                            } else {
                                                                                                                pd.dismiss();
                                                                                                                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();

                                                                                                            }

                               /* if (otp==otpactivityBinding.txtPinEntry.getText().toString()){

                                }else{
                                    Toast.makeText(context, "Please Check OTP", Toast.LENGTH_SHORT).show();
                                }*/
                                                                                                        }
                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onFailure(Call<OtpPojo> call, Throwable t) {
                                                                                                        pd.dismiss();
                                                                                                        Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                });

                                                                                            }

                                                                                        }
                                                                                    });
                                                                                } else {
                                                                                    pd.dismiss();
                                                                                    Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            } else {
                                                                                pd.dismiss();
                                                                                Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                            }


                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<LoginPojo> call, Throwable t) {
                                                                        pd.dismiss();
                                                                        Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

                                                                    }
                                                                });


                                                            }
                                                        }
                                                    });

                                                    termsAndConditionsTv.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent intent = new Intent(context, TermsAndconditionsActivity.class);
                                                            context.startActivity(intent);
                                                        }
                                                    });

//                                            Intent intent = new Intent(context, LoginActivity.class);
//                                            context.startActivity(intent);
                                                    Toast.makeText(context, "You Must Be LogIn Before Reporting Post", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    if (reportsPostPojoOne.getStatus().equalsIgnoreCase("200")) {
                                                        pd.dismiss();
                                                        if (check_box_name.equals("")) {
                                                            Toast.makeText(context, "Please Select Any Report", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            if (check_box_name.equals("others")) {
                                                                if (others_ed_tx.getText().toString().isEmpty()) {
                                                                    Toast.makeText(context, "Please Enter Other Report In Box", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    dialog2.dismiss();
                                                                    final Dialog dialog1 = new Dialog(context);
                                                                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                    dialog1.setContentView(R.layout.dialog_report_sucess);
                                                                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                                    Window window = dialog1.getWindow();
                                                                    window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                                                    window.setGravity(Gravity.CENTER | Gravity.CENTER);
                                                                    ll_dialog = dialog1.findViewById(R.id.ll_dialog);
                                                                    ll_dialog.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            dialog1.dismiss();
                                                                            dialog.dismiss();
                                                                            Toast.makeText(context, "Reported Successfully", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                                    dialog1.show();
                                                                }
                                                            } else {
                                                                dialog2.dismiss();
                                                                final Dialog dialog1 = new Dialog(context);
                                                                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                dialog1.setContentView(R.layout.dialog_report_sucess);
                                                                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                                Window window = dialog1.getWindow();
                                                                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                                                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                                                window.setGravity(Gravity.CENTER | Gravity.CENTER);
                                                                ll_dialog = dialog1.findViewById(R.id.ll_dialog);
                                                                ll_dialog.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        dialog1.dismiss();
                                                                        dialog.dismiss();
                                                                        Toast.makeText(context, "Reported Successfully", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                                dialog1.show();
                                                            }
                                                        }
                                                    } else {
                                                        pd.dismiss();
                                                        Toast.makeText(context, "Error Found Please Check  ", Toast.LENGTH_SHORT).show();
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
                holder.ic_bookmark_ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ProgressDialog pd = new ProgressDialog(context);
                        pd.setMessage("Loading.....");
                        pd.show();
                        pd.setCancelable(true);
                        service.bookmark_posts(user_id, data.get(position).get_id()).enqueue(new Callback<PostBookMarkPojoOne>() {
                            @Override
                            public void onResponse(Call<PostBookMarkPojoOne> call, Response<PostBookMarkPojoOne> response) {
                                if (response.body() != null && response.isSuccessful()) {
                                    PostBookMarkPojoOne postBookMarkPojoOne = response.body();
                                    if (postBookMarkPojoOne.getStatus() != null) {
                                        if (!userDetails.isLoggedIn()) {
                                            final Dialog dialog2 = new Dialog(context);
                                            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog2.setContentView(R.layout.activity_login);
                                            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            Window window = dialog2.getWindow();
                                            window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                            window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                            dialog2.show();
                                            TextView enterMobileTv, agreeTv, termsAndConditionsTv, verifyBut;
                                            EditText mobileEdText;
                                            enterMobileTv = dialog2.findViewById(R.id.enter_mobile_tv);
                                            agreeTv = dialog2.findViewById(R.id.agree_tv);
                                            termsAndConditionsTv = dialog2.findViewById(R.id.terms_and_conditions_tv);
                                            verifyBut = dialog2.findViewById(R.id.verify_but);
                                            mobileEdText = dialog2.findViewById(R.id.mobile_ed_text);
                                            //  backBut.setImageResource(R.drawable.back_arrow);

                                            String language = userDetails.getLanguage();
                                            if (language.equals("ENGLISH")) {

                                                termsAndConditionsTv.setHint(R.string.terms_amp_conditions_en);
                                                verifyBut.setText(R.string.continue_en);
                                                mobileEdText.setHint(R.string.mobile_number_en);
                                                agreeTv.setText(R.string.by_continuing_you_agree_to_our_en);
                                                enterMobileTv.setText(R.string.enter_your_mobile_number_en);


                                            } else if (language.equals("TELUGU")) {

                                                mobileEdText.setHint(R.string.mobile_number_te);
                                                termsAndConditionsTv.setText(R.string.terms_amp_conditions_te);
                                                verifyBut.setText(R.string.continue_te);
                                                agreeTv.setText(R.string.by_continuing_you_agree_to_our_te);
                                                enterMobileTv.setText(R.string.enter_your_mobile_number_te);

                                            } else if (language.equals("HINDI")) {

                                                mobileEdText.setHint(R.string.mobile_number_hi);
                                                termsAndConditionsTv.setText(R.string.terms_amp_conditions_hi);
                                                verifyBut.setText(R.string.continue_hi);
                                                agreeTv.setText(R.string.by_continuing_you_agree_to_our_hi);
                                                enterMobileTv.setText(R.string.enter_your_mobile_number_hi);
                                            }
                                            //activityLoginBinding.mobileEdText.requestFocus();


                                            verifyBut.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    String validNumber = "^[6-9][0-9]{9}$";
                                                    if (mobileEdText.getText().toString().isEmpty()) {
                                                        Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                                                    } else if (!mobileEdText.getText().toString().matches(validNumber)) {
                                                        Toast.makeText(context, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                                                    } else {

                                                        final ProgressDialog pd = new ProgressDialog(context);
                                                        pd.setMessage("Loading.....");
                                                        pd.show();
                                                        pd.setCancelable(true);
                                                        service.check_mobile(mobileEdText.getText().toString().trim()).enqueue(new Callback<LoginPojo>() {
                                                            @Override
                                                            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                                                                if (response.body() != null && response.isSuccessful()) {
                                                                    LoginPojo loginPojo = response.body();
                                                                    if (loginPojo != null) {
                                                                        if (loginPojo.getSuccess().equalsIgnoreCase("true")) {
                                                                            pd.dismiss();
                                                                            userDetails.setMobile(mobileEdText.getText().toString());
                                                                            userDetails.setUserId(loginPojo.getUser_id());
//                                                                            Intent intent = new Intent(context, OTPActivity.class);
//                                                                            intent.putExtra("OTP", loginPojo.getOtp());
//                                                                            intent.putExtra("MobileNumber", mobileEdText.getText().toString());
//                                                                            context.startActivity(intent);
                                                                            dialog2.dismiss();
                                                                            Toast.makeText(context, "OTP Sent Successfully :" + loginPojo.getOtp(), Toast.LENGTH_SHORT).show();

                                                                            final Dialog dialog3 = new Dialog(context);
                                                                            dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                            dialog3.setContentView(R.layout.activity_otpactivity);
                                                                            dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                                            Window window = dialog3.getWindow();
                                                                            window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                                                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                                                            window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                                                            dialog3.show();
                                                                            TextView enterMobileTv, verificationCodeTv, pin_text, enterOtpTv, tvResend, otpSubmitBut;
                                                                            PinView txtPinEntry;

                                                                            verificationCodeTv = dialog3.findViewById(R.id.verification_code_tv);
                                                                            pin_text = dialog3.findViewById(R.id.pin_text);
                                                                            enterOtpTv = dialog3.findViewById(R.id.enter_otp_tv);
                                                                            txtPinEntry = dialog3.findViewById(R.id.txt_pin_entry);
                                                                            tvResend = dialog3.findViewById(R.id.tv_resend);
                                                                            pin_text.setText(mobileEdText.getText().toString());
                                                                            otpSubmitBut = dialog3.findViewById(R.id.otp_submit_but);


                                                                            String language = userDetails.getLanguage();

                                                                            if (language.equals("ENGLISH")) {

                                                                                enterOtpTv.setText(R.string.enter_otp_en);
                                                                                verificationCodeTv.setText(R.string.verification_code_en);
                                                                                tvResend.setText(R.string.resend_en);
                                                                                otpSubmitBut.setText(R.string.log_in_en);


                                                                            } else if (language.equals("TELUGU")) {

                                                                                enterOtpTv.setText(R.string.enter_otp_te);
                                                                                verificationCodeTv.setText(R.string.verification_code_te);
                                                                                tvResend.setText(R.string.resend_te);
                                                                                otpSubmitBut.setText(R.string.log_in_te);

                                                                            } else if (language.equals("HINDI")) {

                                                                                enterOtpTv.setText(R.string.enter_otp_hi);
                                                                                verificationCodeTv.setText(R.string.verification_code_hi);
                                                                                tvResend.setText(R.string.resend_hi);
                                                                                otpSubmitBut.setText(R.string.log_in_hi);

                                                                            }


                                                                            tvResend.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    final ProgressDialog pd = new ProgressDialog(context);
                                                                                    pd.setMessage("Loading.....");
                                                                                    pd.show();
                                                                                    pd.setCancelable(true);
                                                                                    service.Resend_OTP(mobileEdText.getText().toString(), txtPinEntry.getText().toString().trim()).enqueue(new Callback<ResendOtpPojo>() {
                                                                                        @Override
                                                                                        public void onResponse(Call<ResendOtpPojo> call, Response<ResendOtpPojo> response) {
                                                                                            if (response.body() != null && response.isSuccessful()) {
                                                                                                ResendOtpPojo resendOtpPojo = response.body();
                                                                                                txtPinEntry.setText("");
                                                                                                if (resendOtpPojo != null) {
                                                                                                    if (resendOtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                        pd.dismiss();
                                                                                                        Toast.makeText(context, resendOtpPojo.getOtp() + "  " + "\\" + "  " + resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                    } else {
                                                                                                        Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                                    }

                                                                                                } else {
                                                                                                    Toast.makeText(context, resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                                }

                                                                                            }
                                                                                        }

                                                                                        @Override
                                                                                        public void onFailure(Call<ResendOtpPojo> call, Throwable t) {
                                                                                            pd.dismiss();
                                                                                            Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    });
                                                                                }
                                                                            });
                                                                            otpSubmitBut.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View view) {
                                                                                    if (txtPinEntry.getText().toString().isEmpty()) {
                                                                                        Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show();

                                                                                    } else if (txtPinEntry.length() < 4) {
                                                                                        Toast.makeText(context, "Please enter Valid OTP", Toast.LENGTH_SHORT).show();
                                                                                    } else {
                                                                                        final ProgressDialog pd = new ProgressDialog(context);
                                                                                        pd.setMessage("Loading.....");
                                                                                        pd.show();
                                                                                        pd.setCancelable(true);
                                                                                        service.OTP(mobileEdText.getText().toString(), txtPinEntry.getText().toString().trim()).enqueue(new Callback<OtpPojo>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<OtpPojo> call, Response<OtpPojo> response) {
                                                                                                if (response.body() != null && response.isSuccessful()) {

                                                                                                    OtpPojo OtpPojo = response.body();
                                                                                                    if (OtpPojo != null) {
                                                                                                        if (OtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                            pd.dismiss();
                                                                                                            dialog3.dismiss();
                                                                                                            userDetails.setLoggedIn();

                                                                                                            Toast.makeText(context, OtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                                        } else {
                                                                                                            pd.dismiss();
                                                                                                            Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                                        }
                                                                                                    } else {
                                                                                                        pd.dismiss();
                                                                                                        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();

                                                                                                    }

                               /* if (otp==otpactivityBinding.txtPinEntry.getText().toString()){

                                }else{
                                    Toast.makeText(context, "Please Check OTP", Toast.LENGTH_SHORT).show();
                                }*/
                                                                                                }
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<OtpPojo> call, Throwable t) {
                                                                                                pd.dismiss();
                                                                                                Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });

                                                                                    }

                                                                                }
                                                                            });
                                                                        } else {
                                                                            pd.dismiss();
                                                                            Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    } else {
                                                                        pd.dismiss();
                                                                        Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }


                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<LoginPojo> call, Throwable t) {
                                                                pd.dismiss();
                                                                Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

                                                            }
                                                        });


                                                    }
                                                }
                                            });

                                            termsAndConditionsTv.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(context, TermsAndconditionsActivity.class);
                                                    context.startActivity(intent);
                                                }
                                            });

//                                            Intent intent = new Intent(context, LoginActivity.class);
//                                            context.startActivity(intent);
                                            Toast.makeText(context, "You Must Be LogIn Before Reporting Post", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (postBookMarkPojoOne.getStatus().equalsIgnoreCase("200")) {
                                                pd.dismiss();
                                                if (holder.iv_bookmark.isSelected()) {
                                                    holder.iv_bookmark.setSelected(false);
                                                    holder.booked_rl.setVisibility(View.GONE);
                                                    // Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    dialog.dismiss();
                                                    // if not selected only
                                                    // then show animation.
                                                    // iv_bookmark.setSelected(true);
                                                    //  iv_bookmark.likeAnimation();
                                                    holder.booked_rl.setVisibility(View.VISIBLE);

                                                    if (userDetails.getLanguage().equals("ENGLISH")) {
                                                        holder.success_tx.setText("You BookMarked this post Successful.");
                                                        holder.dismiss_tx.setText("Dismiss");

                                                    } else if (userDetails.getLanguage().equals("TELUGU")) {
                                                        holder.success_tx.setText("మీరు ఈ పోస్ట్\u200Cని విజయవంతంగా బుక్ చేసారు");
                                                        holder.dismiss_tx.setText("రద్దుచేసే");

                                                    } else if (userDetails.getLanguage().equals("HINDI")) {
                                                        holder.success_tx.setText("आपने इस पोस्ट को बुकमार्क किया सफल");
                                                        holder.dismiss_tx.setText("खारिज");

                                                    }
                                                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                pd.dismiss();
                                                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        pd.dismiss();
                                        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PostBookMarkPojoOne> call, Throwable t) {
                                pd.dismiss();
                                Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
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

        holder.ic_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.booked_rl.setVisibility(View.GONE);
                final ProgressDialog pd = new ProgressDialog(context);
                pd.setMessage("Loading.....");
                pd.show();
                pd.setCancelable(true);
                service.share_posts(user_id, data.get(position).get_id()).enqueue(new Callback<SharePostPojoOne>() {
                    @Override
                    public void onResponse(Call<SharePostPojoOne> call, Response<SharePostPojoOne> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            SharePostPojoOne sharePostPojoOne = response.body();
                            if (sharePostPojoOne.getStatus() != null) {
                                if (!userDetails.isLoggedIn()) {
                                    final Dialog dialog2 = new Dialog(context);
                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog2.setContentView(R.layout.activity_login);
                                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    Window window = dialog2.getWindow();
                                    window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                    window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                    dialog2.show();
                                    TextView enterMobileTv, agreeTv, termsAndConditionsTv, verifyBut;
                                    EditText mobileEdText;
                                    enterMobileTv = dialog2.findViewById(R.id.enter_mobile_tv);
                                    agreeTv = dialog2.findViewById(R.id.agree_tv);
                                    termsAndConditionsTv = dialog2.findViewById(R.id.terms_and_conditions_tv);
                                    verifyBut = dialog2.findViewById(R.id.verify_but);
                                    mobileEdText = dialog2.findViewById(R.id.mobile_ed_text);
                                    //  backBut.setImageResource(R.drawable.back_arrow);

                                    String language = userDetails.getLanguage();
                                    if (language.equals("ENGLISH")) {

                                        termsAndConditionsTv.setHint(R.string.terms_amp_conditions_en);
                                        verifyBut.setText(R.string.continue_en);
                                        mobileEdText.setHint(R.string.mobile_number_en);
                                        agreeTv.setText(R.string.by_continuing_you_agree_to_our_en);
                                        enterMobileTv.setText(R.string.enter_your_mobile_number_en);


                                    } else if (language.equals("TELUGU")) {

                                        mobileEdText.setHint(R.string.mobile_number_te);
                                        termsAndConditionsTv.setText(R.string.terms_amp_conditions_te);
                                        verifyBut.setText(R.string.continue_te);
                                        agreeTv.setText(R.string.by_continuing_you_agree_to_our_te);
                                        enterMobileTv.setText(R.string.enter_your_mobile_number_te);

                                    } else if (language.equals("HINDI")) {

                                        mobileEdText.setHint(R.string.mobile_number_hi);
                                        termsAndConditionsTv.setText(R.string.terms_amp_conditions_hi);
                                        verifyBut.setText(R.string.continue_hi);
                                        agreeTv.setText(R.string.by_continuing_you_agree_to_our_hi);
                                        enterMobileTv.setText(R.string.enter_your_mobile_number_hi);
                                    }
                                    //activityLoginBinding.mobileEdText.requestFocus();


                                    verifyBut.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String validNumber = "^[6-9][0-9]{9}$";
                                            if (mobileEdText.getText().toString().isEmpty()) {
                                                Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                                            } else if (!mobileEdText.getText().toString().matches(validNumber)) {
                                                Toast.makeText(context, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                                            } else {

                                                final ProgressDialog pd = new ProgressDialog(context);
                                                pd.setMessage("Loading.....");
                                                pd.show();
                                                pd.setCancelable(true);
                                                service.check_mobile(mobileEdText.getText().toString().trim()).enqueue(new Callback<LoginPojo>() {
                                                    @Override
                                                    public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                                                        if (response.body() != null && response.isSuccessful()) {
                                                            LoginPojo loginPojo = response.body();
                                                            if (loginPojo != null) {
                                                                if (loginPojo.getSuccess().equalsIgnoreCase("true")) {
                                                                    pd.dismiss();
                                                                    userDetails.setMobile(mobileEdText.getText().toString());
                                                                    userDetails.setUserId(loginPojo.getUser_id());
//                                                                            Intent intent = new Intent(context, OTPActivity.class);
//                                                                            intent.putExtra("OTP", loginPojo.getOtp());
//                                                                            intent.putExtra("MobileNumber", mobileEdText.getText().toString());
//                                                                            context.startActivity(intent);
                                                                    dialog2.dismiss();
                                                                    Toast.makeText(context, "OTP Sent Successfully :" + loginPojo.getOtp(), Toast.LENGTH_SHORT).show();

                                                                    final Dialog dialog3 = new Dialog(context);
                                                                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                    dialog3.setContentView(R.layout.activity_otpactivity);
                                                                    dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                                    Window window = dialog3.getWindow();
                                                                    window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                                                    window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                                                    dialog3.show();
                                                                    TextView enterMobileTv, verificationCodeTv, pin_text, enterOtpTv, tvResend, otpSubmitBut;
                                                                    PinView txtPinEntry;

                                                                    verificationCodeTv = dialog3.findViewById(R.id.verification_code_tv);
                                                                    pin_text = dialog3.findViewById(R.id.pin_text);
                                                                    enterOtpTv = dialog3.findViewById(R.id.enter_otp_tv);
                                                                    txtPinEntry = dialog3.findViewById(R.id.txt_pin_entry);
                                                                    tvResend = dialog3.findViewById(R.id.tv_resend);
                                                                    pin_text.setText(userDetails.getMobile());
                                                                    otpSubmitBut = dialog3.findViewById(R.id.otp_submit_but);


                                                                    String language = userDetails.getLanguage();

                                                                    if (language.equals("ENGLISH")) {

                                                                        enterOtpTv.setText(R.string.enter_otp_en);
                                                                        verificationCodeTv.setText(R.string.verification_code_en);
                                                                        tvResend.setText(R.string.resend_en);
                                                                        otpSubmitBut.setText(R.string.log_in_en);


                                                                    } else if (language.equals("TELUGU")) {

                                                                        enterOtpTv.setText(R.string.enter_otp_te);
                                                                        verificationCodeTv.setText(R.string.verification_code_te);
                                                                        tvResend.setText(R.string.resend_te);
                                                                        otpSubmitBut.setText(R.string.log_in_te);

                                                                    } else if (language.equals("HINDI")) {

                                                                        enterOtpTv.setText(R.string.enter_otp_hi);
                                                                        verificationCodeTv.setText(R.string.verification_code_hi);
                                                                        tvResend.setText(R.string.resend_hi);
                                                                        otpSubmitBut.setText(R.string.log_in_hi);

                                                                    }


                                                                    tvResend.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            final ProgressDialog pd = new ProgressDialog(context);
                                                                            pd.setMessage("Loading.....");
                                                                            pd.show();
                                                                            pd.setCancelable(true);
                                                                            service.Resend_OTP(mobileEdText.getText().toString(), txtPinEntry.getText().toString().trim()).enqueue(new Callback<ResendOtpPojo>() {
                                                                                @Override
                                                                                public void onResponse(Call<ResendOtpPojo> call, Response<ResendOtpPojo> response) {
                                                                                    if (response.body() != null && response.isSuccessful()) {
                                                                                        ResendOtpPojo resendOtpPojo = response.body();
                                                                                        txtPinEntry.setText("");
                                                                                        if (resendOtpPojo != null) {
                                                                                            if (resendOtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                pd.dismiss();
                                                                                                Toast.makeText(context, resendOtpPojo.getOtp() + "  " + "\\" + "  " + resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                            } else {
                                                                                                Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                            }

                                                                                        } else {
                                                                                            Toast.makeText(context, resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                        }

                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void onFailure(Call<ResendOtpPojo> call, Throwable t) {
                                                                                    pd.dismiss();
                                                                                    Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                        }
                                                                    });
                                                                    otpSubmitBut.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {
                                                                            if (txtPinEntry.getText().toString().isEmpty()) {
                                                                                Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show();

                                                                            } else if (txtPinEntry.length() < 4) {
                                                                                Toast.makeText(context, "Please enter Valid OTP", Toast.LENGTH_SHORT).show();
                                                                            } else {
                                                                                final ProgressDialog pd = new ProgressDialog(context);
                                                                                pd.setMessage("Loading.....");
                                                                                pd.show();
                                                                                pd.setCancelable(true);
                                                                                service.OTP(mobileEdText.getText().toString(), txtPinEntry.getText().toString().trim()).enqueue(new Callback<OtpPojo>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<OtpPojo> call, Response<OtpPojo> response) {
                                                                                        if (response.body() != null && response.isSuccessful()) {

                                                                                            OtpPojo OtpPojo = response.body();
                                                                                            if (OtpPojo != null) {
                                                                                                if (OtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                    pd.dismiss();
                                                                                                    dialog3.dismiss();
                                                                                                    userDetails.setLoggedIn();

                                                                                                    Toast.makeText(context, OtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                                } else {
                                                                                                    pd.dismiss();
                                                                                                    Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                                }
                                                                                            } else {
                                                                                                pd.dismiss();
                                                                                                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();

                                                                                            }

                               /* if (otp==otpactivityBinding.txtPinEntry.getText().toString()){

                                }else{
                                    Toast.makeText(context, "Please Check OTP", Toast.LENGTH_SHORT).show();
                                }*/
                                                                                        }
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<OtpPojo> call, Throwable t) {
                                                                                        pd.dismiss();
                                                                                        Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                });

                                                                            }

                                                                        }
                                                                    });
                                                                } else {
                                                                    pd.dismiss();
                                                                    Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                pd.dismiss();
                                                                Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }


                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<LoginPojo> call, Throwable t) {
                                                        pd.dismiss();
                                                        Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

                                                    }
                                                });


                                            }
                                        }
                                    });

                                    termsAndConditionsTv.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(context, TermsAndconditionsActivity.class);
                                            context.startActivity(intent);
                                        }
                                    });

//                                            Intent intent = new Intent(context, LoginActivity.class);
//                                            context.startActivity(intent);
                                    Toast.makeText(context, "You Must Be LogIn Before Reporting Post", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (sharePostPojoOne.getStatus().equalsIgnoreCase("200")) {
                                        pd.dismiss();
                                        shares_count = Integer.parseInt(data.get(position).getShares());
                                        shares_count = shares_count + 1;
                                        holder.share_tv.setText(String.valueOf(shares_count));
                                        share_option(holder.main_ll.getRootView());

                                        /*Drawable mDrawable = holder.iv_image.getDrawable();
                                        Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
                                        Intent share = new Intent(Intent.ACTION_SEND);
                                        share.setType("image/jpeg"+"text/plain");
                                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                                        try {
                                            File f = new File("path");
                                            f.createNewFile();
                                            FileOutputStream fo = new FileOutputStream(f);
                                            fo.write(bytes.toByteArray());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        share.putExtra(Intent.EXTRA_STREAM, uri);
                                        share.putExtra(Intent.EXTRA_TEXT, uri+"  "+"Hi,Friends This Post is Useful For all" + " " + sharePostPojoOne.getData().getPostId());
                                        context.startActivity(Intent.createChooser(share, "Share Image"));*/
                                       /* Intent intent = new Intent(Intent.ACTION_SEND);
                                        intent.setType("text/plain");
                                        intent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                                        intent.putExtra(Intent.EXTRA_TEXT, uri+"  "+"Hi,Friends This Post is Useful For all" + " " + sharePostPojoOne.getData().getPostId());
                                        context.startActivity(Intent.createChooser(intent, "share via"));*/

                                    } else {
                                        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SharePostPojoOne> call, Throwable t) {
                    }
                });

            }
        });

        holder.ic_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.booked_rl.setVisibility(View.GONE);

                service.like_post(userDetails.getUserId(), data.get(position).get_id()).enqueue(new Callback<LikePostPojoOne>() {
                    @Override
                    public void onResponse(Call<LikePostPojoOne> call, Response<LikePostPojoOne> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            LikePostPojoOne likePostPojoOne = response.body();
                            if (likePostPojoOne.getStatus() != null) {
                                if (!userDetails.isLoggedIn()) {
                                    final Dialog dialog2 = new Dialog(context);
                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog2.setContentView(R.layout.activity_login);
                                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    Window window = dialog2.getWindow();
                                    window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                    window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                    dialog2.show();
                                    TextView enterMobileTv, agreeTv, termsAndConditionsTv, verifyBut;
                                    EditText mobileEdText;
                                    enterMobileTv = dialog2.findViewById(R.id.enter_mobile_tv);
                                    agreeTv = dialog2.findViewById(R.id.agree_tv);
                                    termsAndConditionsTv = dialog2.findViewById(R.id.terms_and_conditions_tv);
                                    verifyBut = dialog2.findViewById(R.id.verify_but);
                                    mobileEdText = dialog2.findViewById(R.id.mobile_ed_text);
                                    //  backBut.setImageResource(R.drawable.back_arrow);

                                    String language = userDetails.getLanguage();
                                    if (language.equals("ENGLISH")) {

                                        termsAndConditionsTv.setHint(R.string.terms_amp_conditions_en);
                                        verifyBut.setText(R.string.continue_en);
                                        mobileEdText.setHint(R.string.mobile_number_en);
                                        agreeTv.setText(R.string.by_continuing_you_agree_to_our_en);
                                        enterMobileTv.setText(R.string.enter_your_mobile_number_en);


                                    } else if (language.equals("TELUGU")) {

                                        mobileEdText.setHint(R.string.mobile_number_te);
                                        termsAndConditionsTv.setText(R.string.terms_amp_conditions_te);
                                        verifyBut.setText(R.string.continue_te);
                                        agreeTv.setText(R.string.by_continuing_you_agree_to_our_te);
                                        enterMobileTv.setText(R.string.enter_your_mobile_number_te);

                                    } else if (language.equals("HINDI")) {

                                        mobileEdText.setHint(R.string.mobile_number_hi);
                                        termsAndConditionsTv.setText(R.string.terms_amp_conditions_hi);
                                        verifyBut.setText(R.string.continue_hi);
                                        agreeTv.setText(R.string.by_continuing_you_agree_to_our_hi);
                                        enterMobileTv.setText(R.string.enter_your_mobile_number_hi);
                                    }
                                    //activityLoginBinding.mobileEdText.requestFocus();


                                    verifyBut.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String validNumber = "^[6-9][0-9]{9}$";
                                            if (mobileEdText.getText().toString().isEmpty()) {
                                                Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                                            } else if (!mobileEdText.getText().toString().matches(validNumber)) {
                                                Toast.makeText(context, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                                            } else {

                                                final ProgressDialog pd = new ProgressDialog(context);
                                                pd.setMessage("Loading.....");
                                                pd.show();
                                                pd.setCancelable(true);
                                                service.check_mobile(mobileEdText.getText().toString().trim()).enqueue(new Callback<LoginPojo>() {
                                                    @Override
                                                    public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                                                        if (response.body() != null && response.isSuccessful()) {
                                                            LoginPojo loginPojo = response.body();
                                                            if (loginPojo != null) {
                                                                if (loginPojo.getSuccess().equalsIgnoreCase("true")) {
                                                                    pd.dismiss();
                                                                    String mobile = mobileEdText.getText().toString().trim();
                                                                    userDetails.setMobile(mobileEdText.getText().toString());
                                                                    userDetails.setUserId(loginPojo.getUser_id());
//                                                                            Intent intent = new Intent(context, OTPActivity.class);
//                                                                            intent.putExtra("OTP", loginPojo.getOtp());
//                                                                            intent.putExtra("MobileNumber", mobileEdText.getText().toString());
//                                                                            context.startActivity(intent);
                                                                    dialog2.dismiss();
                                                                    Toast.makeText(context, "OTP Sent Successfully :" + loginPojo.getOtp(), Toast.LENGTH_SHORT).show();

                                                                    final Dialog dialog3 = new Dialog(context);
                                                                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                    dialog3.setContentView(R.layout.activity_otpactivity);
                                                                    dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                                    Window window = dialog3.getWindow();
                                                                    window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                                                    window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                                                    dialog3.show();
                                                                    TextView enterMobileTv, verificationCodeTv, pin_text, enterOtpTv, tvResend, otpSubmitBut;
                                                                    PinView txtPinEntry;

                                                                    verificationCodeTv = dialog3.findViewById(R.id.verification_code_tv);
                                                                    pin_text = dialog3.findViewById(R.id.pin_text);
                                                                    enterOtpTv = dialog3.findViewById(R.id.enter_otp_tv);
                                                                    txtPinEntry = dialog3.findViewById(R.id.txt_pin_entry);
                                                                    tvResend = dialog3.findViewById(R.id.tv_resend);
                                                                    pin_text.setText(mobile);
                                                                    otpSubmitBut = dialog3.findViewById(R.id.otp_submit_but);


                                                                    String language = userDetails.getLanguage();

                                                                    if (language.equals("ENGLISH")) {

                                                                        enterOtpTv.setText(R.string.enter_otp_en);
                                                                        verificationCodeTv.setText(R.string.verification_code_en);
                                                                        tvResend.setText(R.string.resend_en);
                                                                        otpSubmitBut.setText(R.string.log_in_en);


                                                                    } else if (language.equals("TELUGU")) {

                                                                        enterOtpTv.setText(R.string.enter_otp_te);
                                                                        verificationCodeTv.setText(R.string.verification_code_te);
                                                                        tvResend.setText(R.string.resend_te);
                                                                        otpSubmitBut.setText(R.string.log_in_te);

                                                                    } else if (language.equals("HINDI")) {

                                                                        enterOtpTv.setText(R.string.enter_otp_hi);
                                                                        verificationCodeTv.setText(R.string.verification_code_hi);
                                                                        tvResend.setText(R.string.resend_hi);
                                                                        otpSubmitBut.setText(R.string.log_in_hi);

                                                                    }


                                                                    tvResend.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            final ProgressDialog pd = new ProgressDialog(context);
                                                                            pd.setMessage("Loading.....");
                                                                            pd.show();
                                                                            pd.setCancelable(true);
                                                                            service.Resend_OTP(mobile, txtPinEntry.getText().toString().trim()).enqueue(new Callback<ResendOtpPojo>() {
                                                                                @Override
                                                                                public void onResponse(Call<ResendOtpPojo> call, Response<ResendOtpPojo> response) {
                                                                                    if (response.body() != null && response.isSuccessful()) {
                                                                                        ResendOtpPojo resendOtpPojo = response.body();
                                                                                        txtPinEntry.setText("");
                                                                                        if (resendOtpPojo != null) {
                                                                                            if (resendOtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                pd.dismiss();
                                                                                                Toast.makeText(context, resendOtpPojo.getOtp() + "  " + "\\" + "  " + resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                            } else {
                                                                                                Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                            }

                                                                                        } else {
                                                                                            Toast.makeText(context, resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                        }

                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void onFailure(Call<ResendOtpPojo> call, Throwable t) {
                                                                                    pd.dismiss();
                                                                                    Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                        }
                                                                    });
                                                                    otpSubmitBut.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {
                                                                            if (txtPinEntry.getText().toString().isEmpty()) {
                                                                                Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show();

                                                                            } else if (txtPinEntry.length() < 4) {
                                                                                Toast.makeText(context, "Please enter Valid OTP", Toast.LENGTH_SHORT).show();
                                                                            } else {
                                                                                final ProgressDialog pd = new ProgressDialog(context);
                                                                                pd.setMessage("Loading.....");
                                                                                pd.show();
                                                                                pd.setCancelable(true);
                                                                                service.OTP(mobile, txtPinEntry.getText().toString().trim()).enqueue(new Callback<OtpPojo>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<OtpPojo> call, Response<OtpPojo> response) {
                                                                                        if (response.body() != null && response.isSuccessful()) {

                                                                                            OtpPojo OtpPojo = response.body();
                                                                                            if (OtpPojo != null) {
                                                                                                if (OtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                    pd.dismiss();
                                                                                                    dialog3.dismiss();
                                                                                                    userDetails.setLoggedIn();

                                                                                                    Toast.makeText(context, OtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                                } else {
                                                                                                    pd.dismiss();
                                                                                                    Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                                }
                                                                                            } else {
                                                                                                pd.dismiss();
                                                                                                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();

                                                                                            }

                               /* if (otp==otpactivityBinding.txtPinEntry.getText().toString()){

                                }else{
                                    Toast.makeText(context, "Please Check OTP", Toast.LENGTH_SHORT).show();
                                }*/
                                                                                        }
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<OtpPojo> call, Throwable t) {
                                                                                        pd.dismiss();
                                                                                        Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                });

                                                                            }

                                                                        }
                                                                    });
                                                                } else {
                                                                    pd.dismiss();
                                                                    Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                pd.dismiss();
                                                                Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }


                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<LoginPojo> call, Throwable t) {
                                                        pd.dismiss();
                                                        Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

                                                    }
                                                });


                                            }
                                        }
                                    });

                                    termsAndConditionsTv.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(context, TermsAndconditionsActivity.class);
                                            context.startActivity(intent);
                                        }
                                    });

//                                            Intent intent = new Intent(context, LoginActivity.class);
//                                            context.startActivity(intent);
                                    Toast.makeText(context, "You Must Be LogIn Before Reporting Post", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (likePostPojoOne.getStatus().equalsIgnoreCase("200")) {
                                        //  pd.dismiss();
                                        if (holder.iv_like.isSelected()) {
                                            holder.iv_like.setSelected(false);
                                            holder.iv_like.likeAnimation();
                                            likes_count = likes_count - 1;
                                            holder.like_tv.setText(String.valueOf(likes_count));

                                            holder.ic_like.setColorFilter(context.getResources().getColor(R.color.black));
                                            Toast.makeText(context, "This Post UnLiked Successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            // if not selected only
                                            // then show animation.
                                            holder.iv_like.setSelected(true);
                                            holder.ic_like.setColorFilter(context.getResources().getColor(R.color.app_color));
                                            holder.iv_like.likeAnimation();

                                         /*   int count = data.get(position).getDownloads();
                                            count++;
                                            data.get(position).setDownloads(count);*/

                                            likes_count = Integer.parseInt(data.get(position).getLikes());
                                            likes_count = likes_count + 1;
                                            holder.like_tv.setText(String.valueOf(likes_count));
                                            //holder.like_tv.setText(likes_count + 1 + "");
                                         /*if (checkedPosition != position) {
                                             notifyDataSetChanged();
                                             checkedPosition = position;
                                         }*/
                                            Toast.makeText(context, "Post Liked Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        //   pd.dismiss();
                                        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                final Dialog dialog2 = new Dialog(context);
                                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog2.setContentView(R.layout.activity_login);
                                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                Window window = dialog2.getWindow();
                                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                dialog2.show();
                                TextView enterMobileTv, agreeTv, termsAndConditionsTv, verifyBut;
                                EditText mobileEdText;
                                enterMobileTv = dialog2.findViewById(R.id.enter_mobile_tv);
                                agreeTv = dialog2.findViewById(R.id.agree_tv);
                                termsAndConditionsTv = dialog2.findViewById(R.id.terms_and_conditions_tv);
                                verifyBut = dialog2.findViewById(R.id.verify_but);
                                mobileEdText = dialog2.findViewById(R.id.mobile_ed_text);
                                //  backBut.setImageResource(R.drawable.back_arrow);

                                String language = userDetails.getLanguage();
                                if (language.equals("ENGLISH")) {

                                    termsAndConditionsTv.setHint(R.string.terms_amp_conditions_en);
                                    verifyBut.setText(R.string.continue_en);
                                    mobileEdText.setHint(R.string.mobile_number_en);
                                    agreeTv.setText(R.string.by_continuing_you_agree_to_our_en);
                                    enterMobileTv.setText(R.string.enter_your_mobile_number_en);


                                } else if (language.equals("TELUGU")) {

                                    mobileEdText.setHint(R.string.mobile_number_te);
                                    termsAndConditionsTv.setText(R.string.terms_amp_conditions_te);
                                    verifyBut.setText(R.string.continue_te);
                                    agreeTv.setText(R.string.by_continuing_you_agree_to_our_te);
                                    enterMobileTv.setText(R.string.enter_your_mobile_number_te);

                                } else if (language.equals("HINDI")) {

                                    mobileEdText.setHint(R.string.mobile_number_hi);
                                    termsAndConditionsTv.setText(R.string.terms_amp_conditions_hi);
                                    verifyBut.setText(R.string.continue_hi);
                                    agreeTv.setText(R.string.by_continuing_you_agree_to_our_hi);
                                    enterMobileTv.setText(R.string.enter_your_mobile_number_hi);
                                }
                                //activityLoginBinding.mobileEdText.requestFocus();


                                verifyBut.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String validNumber = "^[6-9][0-9]{9}$";
                                        if (mobileEdText.getText().toString().isEmpty()) {
                                            Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                                        } else if (!mobileEdText.getText().toString().matches(validNumber)) {
                                            Toast.makeText(context, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                                        } else {

                                            final ProgressDialog pd = new ProgressDialog(context);
                                            pd.setMessage("Loading.....");
                                            pd.show();
                                            pd.setCancelable(true);
                                            service.check_mobile(mobileEdText.getText().toString().trim()).enqueue(new Callback<LoginPojo>() {
                                                @Override
                                                public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                                                    if (response.body() != null && response.isSuccessful()) {
                                                        LoginPojo loginPojo = response.body();
                                                        if (loginPojo != null) {
                                                            if (loginPojo.getSuccess().equalsIgnoreCase("true")) {
                                                                pd.dismiss();
                                                                userDetails.setMobile(mobileEdText.getText().toString());
                                                                userDetails.setUserId(loginPojo.getUser_id());
//                                                                            Intent intent = new Intent(context, OTPActivity.class);
//                                                                            intent.putExtra("OTP", loginPojo.getOtp());
//                                                                            intent.putExtra("MobileNumber", mobileEdText.getText().toString());
//                                                                            context.startActivity(intent);
                                                                dialog2.dismiss();
                                                                Toast.makeText(context, "OTP Sent Successfully :" + loginPojo.getOtp(), Toast.LENGTH_SHORT).show();

                                                                final Dialog dialog3 = new Dialog(context);
                                                                dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                dialog3.setContentView(R.layout.activity_otpactivity);
                                                                dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                                Window window = dialog3.getWindow();
                                                                window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                                                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                                                window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                                                dialog3.show();
                                                                TextView enterMobileTv, verificationCodeTv, pin_text, enterOtpTv, tvResend, otpSubmitBut;
                                                                PinView txtPinEntry;

                                                                verificationCodeTv = dialog3.findViewById(R.id.verification_code_tv);
                                                                pin_text = dialog3.findViewById(R.id.pin_text);
                                                                enterOtpTv = dialog3.findViewById(R.id.enter_otp_tv);
                                                                txtPinEntry = dialog3.findViewById(R.id.txt_pin_entry);
                                                                tvResend = dialog3.findViewById(R.id.tv_resend);
                                                                pin_text.setText(mobileEdText.getText().toString());
                                                                otpSubmitBut = dialog3.findViewById(R.id.otp_submit_but);


                                                                String language = userDetails.getLanguage();

                                                                if (language.equals("ENGLISH")) {

                                                                    enterOtpTv.setText(R.string.enter_otp_en);
                                                                    verificationCodeTv.setText(R.string.verification_code_en);
                                                                    tvResend.setText(R.string.resend_en);
                                                                    otpSubmitBut.setText(R.string.log_in_en);


                                                                } else if (language.equals("TELUGU")) {

                                                                    enterOtpTv.setText(R.string.enter_otp_te);
                                                                    verificationCodeTv.setText(R.string.verification_code_te);
                                                                    tvResend.setText(R.string.resend_te);
                                                                    otpSubmitBut.setText(R.string.log_in_te);

                                                                } else if (language.equals("HINDI")) {

                                                                    enterOtpTv.setText(R.string.enter_otp_hi);
                                                                    verificationCodeTv.setText(R.string.verification_code_hi);
                                                                    tvResend.setText(R.string.resend_hi);
                                                                    otpSubmitBut.setText(R.string.log_in_hi);

                                                                }


                                                                tvResend.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        final ProgressDialog pd = new ProgressDialog(context);
                                                                        pd.setMessage("Loading.....");
                                                                        pd.show();
                                                                        pd.setCancelable(true);
                                                                        service.Resend_OTP(mobileEdText.getText().toString(), txtPinEntry.getText().toString().trim()).enqueue(new Callback<ResendOtpPojo>() {
                                                                            @Override
                                                                            public void onResponse(Call<ResendOtpPojo> call, Response<ResendOtpPojo> response) {
                                                                                if (response.body() != null && response.isSuccessful()) {
                                                                                    ResendOtpPojo resendOtpPojo = response.body();
                                                                                    txtPinEntry.setText("");
                                                                                    if (resendOtpPojo != null) {
                                                                                        if (resendOtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                            pd.dismiss();
                                                                                            Toast.makeText(context, resendOtpPojo.getOtp() + "  " + "\\" + "  " + resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                        } else {
                                                                                            Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                        }

                                                                                    } else {
                                                                                        Toast.makeText(context, resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                    }

                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<ResendOtpPojo> call, Throwable t) {
                                                                                pd.dismiss();
                                                                                Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                                otpSubmitBut.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        if (txtPinEntry.getText().toString().isEmpty()) {
                                                                            Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show();

                                                                        } else if (txtPinEntry.length() < 4) {
                                                                            Toast.makeText(context, "Please enter Valid OTP", Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            final ProgressDialog pd = new ProgressDialog(context);
                                                                            pd.setMessage("Loading.....");
                                                                            pd.show();
                                                                            pd.setCancelable(true);
                                                                            service.OTP(mobileEdText.getText().toString(), txtPinEntry.getText().toString().trim()).enqueue(new Callback<OtpPojo>() {
                                                                                @Override
                                                                                public void onResponse(Call<OtpPojo> call, Response<OtpPojo> response) {
                                                                                    if (response.body() != null && response.isSuccessful()) {

                                                                                        OtpPojo OtpPojo = response.body();
                                                                                        if (OtpPojo != null) {
                                                                                            if (OtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                pd.dismiss();
                                                                                                dialog3.dismiss();
                                                                                                userDetails.setLoggedIn();

                                                                                                Toast.makeText(context, OtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                            } else {
                                                                                                pd.dismiss();
                                                                                                Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                            }
                                                                                        } else {
                                                                                            pd.dismiss();
                                                                                            Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();

                                                                                        }

                               /* if (otp==otpactivityBinding.txtPinEntry.getText().toString()){

                                }else{
                                    Toast.makeText(context, "Please Check OTP", Toast.LENGTH_SHORT).show();
                                }*/
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void onFailure(Call<OtpPojo> call, Throwable t) {
                                                                                    pd.dismiss();
                                                                                    Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });

                                                                        }

                                                                    }
                                                                });
                                                            } else {
                                                                pd.dismiss();
                                                                Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            pd.dismiss();
                                                            Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }


                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<LoginPojo> call, Throwable t) {
                                                    pd.dismiss();
                                                    Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

                                                }
                                            });


                                        }
                                    }
                                });

                                termsAndConditionsTv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, TermsAndconditionsActivity.class);
                                        context.startActivity(intent);
                                    }
                                });

//                                            Intent intent = new Intent(context, LoginActivity.class);
//                                            context.startActivity(intent);
                                // Toast.makeText(context, "You Must Be LogIn Before Reporting Post", Toast.LENGTH_SHORT).show();
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

        holder.ic_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // bottom_download.requestFocus();
                holder.main_ll.setVisibility(View.VISIBLE);
                final ProgressDialog pd = new ProgressDialog(context);
                pd.setMessage("Loading.....");
                pd.show();
                pd.setCancelable(true);
                service.download_post(user_id, data.get(position).get_id()).enqueue(new Callback<DownloadPostResponsePojo>() {
                    @Override
                    public void onResponse(Call<DownloadPostResponsePojo> call, Response<DownloadPostResponsePojo> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            DownloadPostResponsePojo downloadPostResponsePojo = response.body();
                            if (downloadPostResponsePojo.getStatus() != null) {
                                if (!userDetails.isLoggedIn()) {
                                    final Dialog dialog2 = new Dialog(context);
                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog2.setContentView(R.layout.activity_login);
                                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    Window window = dialog2.getWindow();
                                    window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                    window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                    dialog2.show();
                                    TextView enterMobileTv, agreeTv, termsAndConditionsTv, verifyBut;
                                    EditText mobileEdText;
                                    enterMobileTv = dialog2.findViewById(R.id.enter_mobile_tv);
                                    agreeTv = dialog2.findViewById(R.id.agree_tv);
                                    termsAndConditionsTv = dialog2.findViewById(R.id.terms_and_conditions_tv);
                                    verifyBut = dialog2.findViewById(R.id.verify_but);
                                    mobileEdText = dialog2.findViewById(R.id.mobile_ed_text);
                                    //  backBut.setImageResource(R.drawable.back_arrow);

                                    String language = userDetails.getLanguage();
                                    if (language.equals("ENGLISH")) {

                                        termsAndConditionsTv.setHint(R.string.terms_amp_conditions_en);
                                        verifyBut.setText(R.string.continue_en);
                                        mobileEdText.setHint(R.string.mobile_number_en);
                                        agreeTv.setText(R.string.by_continuing_you_agree_to_our_en);
                                        enterMobileTv.setText(R.string.enter_your_mobile_number_en);


                                    } else if (language.equals("TELUGU")) {

                                        mobileEdText.setHint(R.string.mobile_number_te);
                                        termsAndConditionsTv.setText(R.string.terms_amp_conditions_te);
                                        verifyBut.setText(R.string.continue_te);
                                        agreeTv.setText(R.string.by_continuing_you_agree_to_our_te);
                                        enterMobileTv.setText(R.string.enter_your_mobile_number_te);

                                    } else if (language.equals("HINDI")) {

                                        mobileEdText.setHint(R.string.mobile_number_hi);
                                        termsAndConditionsTv.setText(R.string.terms_amp_conditions_hi);
                                        verifyBut.setText(R.string.continue_hi);
                                        agreeTv.setText(R.string.by_continuing_you_agree_to_our_hi);
                                        enterMobileTv.setText(R.string.enter_your_mobile_number_hi);
                                    }
                                    //activityLoginBinding.mobileEdText.requestFocus();


                                    verifyBut.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String validNumber = "^[6-9][0-9]{9}$";
                                            if (mobileEdText.getText().toString().isEmpty()) {
                                                Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                                            } else if (!mobileEdText.getText().toString().matches(validNumber)) {
                                                Toast.makeText(context, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                                            } else {

                                                final ProgressDialog pd = new ProgressDialog(context);
                                                pd.setMessage("Loading.....");
                                                pd.show();
                                                pd.setCancelable(true);
                                                service.check_mobile(mobileEdText.getText().toString().trim()).enqueue(new Callback<LoginPojo>() {
                                                    @Override
                                                    public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                                                        if (response.body() != null && response.isSuccessful()) {
                                                            LoginPojo loginPojo = response.body();
                                                            if (loginPojo != null) {
                                                                if (loginPojo.getSuccess().equalsIgnoreCase("true")) {
                                                                    pd.dismiss();
                                                                    userDetails.setMobile(mobileEdText.getText().toString());
                                                                    userDetails.setUserId(loginPojo.getUser_id());
//                                                                            Intent intent = new Intent(context, OTPActivity.class);
//                                                                            intent.putExtra("OTP", loginPojo.getOtp());
//                                                                            intent.putExtra("MobileNumber", mobileEdText.getText().toString());
//                                                                            context.startActivity(intent);
                                                                    dialog2.dismiss();
                                                                    Toast.makeText(context, "OTP Sent Successfully :" + loginPojo.getOtp(), Toast.LENGTH_SHORT).show();

                                                                    final Dialog dialog3 = new Dialog(context);
                                                                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                    dialog3.setContentView(R.layout.activity_otpactivity);
                                                                    dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                                    Window window = dialog3.getWindow();
                                                                    window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
                                                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                                                    window.setGravity(Gravity.BOTTOM | Gravity.BOTTOM);
                                                                    dialog3.show();
                                                                    TextView enterMobileTv, verificationCodeTv, pin_text, enterOtpTv, tvResend, otpSubmitBut;
                                                                    PinView txtPinEntry;

                                                                    verificationCodeTv = dialog3.findViewById(R.id.verification_code_tv);
                                                                    pin_text = dialog3.findViewById(R.id.pin_text);
                                                                    enterOtpTv = dialog3.findViewById(R.id.enter_otp_tv);
                                                                    txtPinEntry = dialog3.findViewById(R.id.txt_pin_entry);
                                                                    tvResend = dialog3.findViewById(R.id.tv_resend);
                                                                    pin_text.setText(userDetails.getMobile());
                                                                    otpSubmitBut = dialog3.findViewById(R.id.otp_submit_but);


                                                                    String language = userDetails.getLanguage();

                                                                    if (language.equals("ENGLISH")) {

                                                                        enterOtpTv.setText(R.string.enter_otp_en);
                                                                        verificationCodeTv.setText(R.string.verification_code_en);
                                                                        tvResend.setText(R.string.resend_en);
                                                                        otpSubmitBut.setText(R.string.log_in_en);


                                                                    } else if (language.equals("TELUGU")) {

                                                                        enterOtpTv.setText(R.string.enter_otp_te);
                                                                        verificationCodeTv.setText(R.string.verification_code_te);
                                                                        tvResend.setText(R.string.resend_te);
                                                                        otpSubmitBut.setText(R.string.log_in_te);

                                                                    } else if (language.equals("HINDI")) {

                                                                        enterOtpTv.setText(R.string.enter_otp_hi);
                                                                        verificationCodeTv.setText(R.string.verification_code_hi);
                                                                        tvResend.setText(R.string.resend_hi);
                                                                        otpSubmitBut.setText(R.string.log_in_hi);

                                                                    }


                                                                    tvResend.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            final ProgressDialog pd = new ProgressDialog(context);
                                                                            pd.setMessage("Loading.....");
                                                                            pd.show();
                                                                            pd.setCancelable(true);
                                                                            service.Resend_OTP(mobileEdText.getText().toString(), txtPinEntry.getText().toString().trim()).enqueue(new Callback<ResendOtpPojo>() {
                                                                                @Override
                                                                                public void onResponse(Call<ResendOtpPojo> call, Response<ResendOtpPojo> response) {
                                                                                    if (response.body() != null && response.isSuccessful()) {
                                                                                        ResendOtpPojo resendOtpPojo = response.body();
                                                                                        txtPinEntry.setText("");
                                                                                        if (resendOtpPojo != null) {
                                                                                            if (resendOtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                pd.dismiss();
                                                                                                Toast.makeText(context, resendOtpPojo.getOtp() + "  " + "\\" + "  " + resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                            } else {
                                                                                                Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                            }

                                                                                        } else {
                                                                                            Toast.makeText(context, resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                        }

                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void onFailure(Call<ResendOtpPojo> call, Throwable t) {
                                                                                    pd.dismiss();
                                                                                    Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                        }
                                                                    });
                                                                    otpSubmitBut.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {
                                                                            if (txtPinEntry.getText().toString().isEmpty()) {
                                                                                Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show();

                                                                            } else if (txtPinEntry.length() < 4) {
                                                                                Toast.makeText(context, "Please enter Valid OTP", Toast.LENGTH_SHORT).show();
                                                                            } else {
                                                                                final ProgressDialog pd = new ProgressDialog(context);
                                                                                pd.setMessage("Loading.....");
                                                                                pd.show();
                                                                                pd.setCancelable(true);
                                                                                service.OTP(mobileEdText.getText().toString(), txtPinEntry.getText().toString().trim()).enqueue(new Callback<OtpPojo>() {
                                                                                    @Override
                                                                                    public void onResponse(Call<OtpPojo> call, Response<OtpPojo> response) {
                                                                                        if (response.body() != null && response.isSuccessful()) {

                                                                                            OtpPojo OtpPojo = response.body();
                                                                                            if (OtpPojo != null) {
                                                                                                if (OtpPojo.getStatus().equalsIgnoreCase("200")) {
                                                                                                    pd.dismiss();
                                                                                                    dialog3.dismiss();
                                                                                                    userDetails.setLoggedIn();

                                                                                                    Toast.makeText(context, OtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                                                                                } else {
                                                                                                    pd.dismiss();
                                                                                                    Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                                                                                }
                                                                                            } else {
                                                                                                pd.dismiss();
                                                                                                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();

                                                                                            }

                               /* if (otp==otpactivityBinding.txtPinEntry.getText().toString()){

                                }else{
                                    Toast.makeText(context, "Please Check OTP", Toast.LENGTH_SHORT).show();
                                }*/
                                                                                        }
                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(Call<OtpPojo> call, Throwable t) {
                                                                                        pd.dismiss();
                                                                                        Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                });

                                                                            }

                                                                        }
                                                                    });
                                                                } else {
                                                                    pd.dismiss();
                                                                    Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                pd.dismiss();
                                                                Toast.makeText(context, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }


                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<LoginPojo> call, Throwable t) {
                                                        pd.dismiss();
                                                        Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

                                                    }
                                                });


                                            }
                                        }
                                    });

                                    termsAndConditionsTv.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(context, TermsAndconditionsActivity.class);
                                            context.startActivity(intent);
                                        }
                                    });

//                                            Intent intent = new Intent(context, LoginActivity.class);
//                                            context.startActivity(intent);
                                    //       Toast.makeText(context, "You Must Be LogIn Before Reporting Post", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (downloadPostResponsePojo.getStatus().equalsIgnoreCase("200")) {
                                        pd.dismiss();
                                        takeScreenshot(holder.main_ll.getRootView());
                                        holder.main_ll.setVisibility(View.VISIBLE);
                                        holder.bottom_download.setVisibility(View.VISIBLE);
                                        holder.bottom_lay_out.setVisibility(View.GONE);
                                        view.setVisibility(View.GONE);

                                        int count = data.get(position).getDownloads();
                                        count++;
                                        data.get(position).setDownloads(count);

                                        holder.download_tv.setText(count++ + "");

                                        Log.d("count", downloads_count++ + "");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                holder.bottom_download.setVisibility(View.GONE);
                                                holder.bottom_lay_out.setVisibility(View.VISIBLE);
                                                view.setVisibility(View.VISIBLE);
                                                holder.booked_rl.setVisibility(View.VISIBLE);
                                                holder.success_tx.setText("Download Successful");
                                                holder.dismiss_tx.setText("Dismiss");

                                            }
                                        }, 2000);
                                    } else {
                                        pd.dismiss();
                                        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
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

        holder.dismiss_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.booked_rl.setVisibility(View.GONE);
            }
        });

        if (position == 2) {
            holder.ll_data.setVisibility(View.GONE);
            holder.ads_rl.setVisibility(View.VISIBLE);
            final ProgressDialog pd = new ProgressDialog(context);
            pd.setMessage("Loading.....");
            pd.show();
            pd.setCancelable(true);

            service.get_ads(userDetails.getStateId(),
                    userDetails.getDistrictId(), userDetails.getLanguage_Id(),
                    user_id).enqueue(new Callback<AdsMyPojo>() {
                @Override
                public void onResponse(Call<AdsMyPojo> call, Response<AdsMyPojo> response) {

                    if (response.body() != null && response.isSuccessful()) {
                        AdsMyPojo adsMyPojo = response.body();
                        pd.dismiss();
                        if (adsMyPojo.getStatus().equals("200")) {
                            if (adsMyPojo.getData() != null) {

                                if (adsMyPojo.getData().size() != 0) {
                                    if (adsMyPojo.getData().size() < 1) {
                                        Toast.makeText(context, "No ads are available", Toast.LENGTH_SHORT).show();
                                    } else {
                                        for (int i = 0; i < adsMyPojo.getData().size(); i++) {

                                            if (adsMyPojo.getData().get(i).getImage().length == 0) {
                                                holder.ll_data.setVisibility(View.VISIBLE);
                                                holder.ads_rl.setVisibility(View.GONE);
                                                Toast.makeText(context, "No Ads Data Available", Toast.LENGTH_SHORT).show();
                                            } else {
                                                holder.total_num_count_tv.setText(adsMyPojo.getData().get(position).getImage().length + "");

                                                AdsSliderViewPagerAdapter adsSliderViewPagerAdapter = new AdsSliderViewPagerAdapter(context, adsMyPojo.getData());
                                                holder.ads_slide_view_pager.setAdapter(adsSliderViewPagerAdapter);

                                                holder.ads_slide_view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                                    @SuppressLint("SetTextI18n")
                                                    @Override
                                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                                        holder.num_count_tv.setText(holder.ads_slide_view_pager.getCurrentItem() + 1 + "");
                                                    }

                                                    @Override
                                                    public void onPageSelected(int position) {

                                                    }

                                                    @Override
                                                    public void onPageScrollStateChanged(int state) {

                                                    }
                                                });

                                            }


                                        }
                                    }
                                } else {
                                    Toast.makeText(context, "No Ads Data Available", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<AdsMyPojo> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

                }

            });

        }

        holder.ll_data.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swapXY(event);
                return false;
            }
        });
    }

    private MotionEvent swapXY(MotionEvent event) {
        float x = (float) view.getWidth();
        float y = (float) view.getHeight();
        event.setLocation((event.getY() / y) * y, (event.getX() / x) * x);
        return event;
    }
/*
    void api_call() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);

        service.get_ads(userDetails.getStateId(),
                userDetails.getDistrictId(), userDetails.getLanguage_Id(),
                user_id).enqueue(new Callback<AdsMyPojo>() {
            @Override
            public void onResponse(Call<AdsMyPojo> call, Response<AdsMyPojo> response) {

                if (response.body() != null && response.isSuccessful()) {
                    AdsMyPojo adsMyPojo = response.body();
                    pd.dismiss();
                    if (adsMyPojo.getSuccess().equals("true")) {
                        if (adsMyPojo.getData() != null) {

                            if (adsMyPojo.getData().size() != 0) {
                                if (adsMyPojo.getData().size() < 1) {
                                    Toast.makeText(context, "No ads are available", Toast.LENGTH_SHORT).show();
                                } else {
                                    for (int i = 0; i < adsMyPojo.getData().size(); i++) {

                                        if (adsMyPojo.getData().get(i).getImage().length == 0) {
                                            binding.content.vPager.setVisibility(View.VISIBLE);
                                            binding.content.adsSlideViewPager.setVisibility(View.GONE);
                                            Toast.makeText(context, "No Ads Data Available", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            binding.content.vPager.setVisibility(View.GONE);
                                            binding.content.adsSlideViewPager.setVisibility(View.VISIBLE);
                                            binding.content.totalNumCountTv.setText(adsMyPojo.getData().get(0).getImage().length + "");

                                            AdsSliderViewPagerAdapter adsSliderViewPagerAdapter = new AdsSliderViewPagerAdapter(context, adsMyPojo.getData());
                                            binding.content.adsSlideViewPager.setAdapter(adsSliderViewPagerAdapter);

                                            binding.content.adsSlideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                                @SuppressLint("SetTextI18n")
                                                @Override
                                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                                    binding.content.numCountTv.setText(binding.content.adsSlideViewPager.getCurrentItem() + 1 + "");
                                                }

                                                @Override
                                                public void onPageSelected(int position) {

                                                }

                                                @Override
                                                public void onPageScrollStateChanged(int state) {

                                                }
                                            });

                                        }


                                    }
                                }
                            } else {
                                Toast.makeText(context, "No Ads Data Available", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "No Ads Data Available", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<AdsMyPojo> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

            }

        });

    }
*/


    public void transformPage(View page, float position) {
        if (position < -1.0f) {
            page.setAlpha(0.0f);
        } else if (position <= 0.0f) {
            page.setAlpha(1.0f);
            page.setTranslationX(((float) page.getWidth()) * (-position));
            page.setTranslationY(((float) page.getHeight()) * position);
            page.setScaleX(1.0f);
            page.setScaleY(1.0f);
        } else if (position <= 1.0f) {
            page.setAlpha(1.0f - position);
            page.setTranslationX(((float) page.getWidth()) * (-position));
            page.setTranslationY(0.0f);
            float scaleFactor = ((1.0f - Math.abs(position)) * 0.19999999f) + Min_Scale;
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else if (position > 1.0f) {
            page.setAlpha(0.0f);
        }
    }

    private void share_option(View v) {
        Date now = new Date();
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        v.setDrawingCacheEnabled(true);

        bitmap = Bitmap.createBitmap(v.getDrawingCache());
      /*  BitmapDrawable bitmapDrawable = (BitmapDrawable) main_ll.getDrawingCache();
        Bitmap bitmap = bitmapDrawable.getBitmap();
         Bitmap bitmap = Bitmap.createScaledBitmap(bitmap, 1080, 1920, true);*/


        File filepath = Environment.getExternalStorageDirectory();

        File dir = new File(filepath.getAbsoluteFile() + "/Download/");

        File file = new File(dir, System.currentTimeMillis() + ".jpg");
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            String img = String.valueOf(outputStream);

    /*        Intent shareIntent = new Intent();
            shareIntent.setType("text/plain");
            shareIntent.setType("image/*");
            shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "I have found this great " + "News" + "\n\n" + uri + "\n\n");
//            shareIntent.putExtra(Intent.EXTRA_TEXT, u_name+"\n"+urlLink+""    +id);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            *//* shareIntent.setPackage("com.whatsapp");*//*

            // Launch sharing dialog for image
            context.startActivity(Intent.createChooser(shareIntent, "Share Images"));
            Toast.makeText(context, "This Post Share Successfully", Toast.LENGTH_SHORT).show();
*/


            Uri U = FileProvider.getUriForFile(context.getApplicationContext(), "com.news2day.fileprovider", file);
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("image/png");
            i.putExtra(Intent.EXTRA_STREAM, U);
            i.putExtra(Intent.EXTRA_TEXT, "Hi,Friends This Post is Useful For all, download app " + "https://play.google.com/store/apps/details?id=com.news2day&hl=en");
            ((Activity) context).startActivityForResult(Intent.createChooser(i, "Email:"), 1);

//            Intent sendIntent = new Intent();
//            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, Uri.fromFile(file) + "   " + "Hi,Friends This Post is Useful For all");
//
//            sendIntent.setType("text/plain");
//
//            context.startActivity(Intent.createChooser(sendIntent, "share post"));
           /* Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_STREAM, file);
            share.putExtra(Intent.EXTRA_TEXT, file + "  " + "Hi,Friends This Post is Useful For all" + " ");
            share.setType("image/*"+"text/*");
            *//*share.setType("text/*");*//*
            context.startActivity(Intent.createChooser(share,"share Post"));*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void takeScreenshot(View v) {
        Date now = new Date();
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        v.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(v.getDrawingCache());
      /*  BitmapDrawable bitmapDrawable = (BitmapDrawable) main_ll.getDrawingCache();
        Bitmap bitmap = bitmapDrawable.getBitmap();
         Bitmap bitmap = Bitmap.createScaledBitmap(bitmap, 1080, 1920, true);*/


        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsoluteFile() + "/Download/");

        File file = new File(dir, System.currentTimeMillis() + ".jpg");
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            Toast.makeText(context, "Download Successfully" + "\"" + "Please Check ", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            outputStream.flush();
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

    @Subscribe
    public void onEvent(EventData data) {
        FlipAnimation.flipView(context, data_ll_arr[data.getPosition()], data_ll_arr[data.getPosition()], data.isFront(), data.getPosition());
    }

    private void FlipListener() {
        EventData data = new EventData();
        if (isFront) {
            isFront = false;
            data.setFront(false);
        } else {
            isFront = true;
            data.setFront(true);
        }
        data.setPosition(0);
        onEvent(data);
        EventBus.getDefault().post(data);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ic_share, ic_bookmark, ic_download, ic_report, ic_like, ic_more;
        RelativeLayout bottom_lay_out, bottom_download, booked_rl;
        LinearLayout ll_data, ll_more, ic_bookmark_ll, ic_report_ll, dots_ll, main_ll;
        ImageView iv_image;
        ViewPager slide_view_pager;
        SmallBangView iv_like, iv_bookmark, iv_report;
        TextView tv_description, success_tx,
                dismiss_tx, title_tv;
        TextView txt_title1,
                views, like_tv,
                download_tv,
                share_tv;

        RelativeLayout ads_rl;
        ViewPager ads_slide_view_pager;
        TextView num_count_tv,
                total_num_count_tv;

        @SuppressLint("StaticFieldLeak")
        public TextView trending_tv;
        EasyFlipView flipView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_description = itemView.findViewById(R.id.tv_description);
            title_tv = itemView.findViewById(R.id.title_tv);
            ic_more = itemView.findViewById(R.id.ic_more);
            ll_more = itemView.findViewById(R.id.ll_more);
            // view = itemView.findViewById(R.id.view);
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
            trending_tv = itemView.findViewById(R.id.trending_tv);


            //   radio_but = itemView.findViewById(R.id.radio_but);

            like_tv = itemView.findViewById(R.id.like_tv);
            download_tv = itemView.findViewById(R.id.download_tv);
            share_tv = itemView.findViewById(R.id.share_tv);
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

            ads_rl = itemView.findViewById(R.id.ads_rl);
            ads_slide_view_pager = itemView.findViewById(R.id.ads_slide_view_pager);
            num_count_tv = itemView.findViewById(R.id.num_count_tv);
            total_num_count_tv = itemView.findViewById(R.id.total_num_count_tv);


            //   flipView = itemView.findViewById(R.id.flipView);

        }
    }

    private String decodeString(String encoded) {
        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String decodedString = "";
        try {

            decodedString = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } finally {

            return decodedString;
        }
    }

}
