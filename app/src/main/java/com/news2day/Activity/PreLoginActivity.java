package com.news2day.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.news2day.Adapter.BookMarkAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityLoginPageBinding;
import com.news2day.model.BookMarkPojo.BookMarkMyPojo;
import com.news2day.model.SkipPojo.SkipMyPojo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreLoginActivity extends AppCompatActivity {
    Animation topAnim, bottomAnim;
    ActivityLoginPageBinding binding;
    UserDetails userDetails;
    String token, language;

    RetrofitService service;
    String android_id;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_page);
        userDetails = new UserDetails(this);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        binding.rlMobile.setAnimation(bottomAnim);
        userDetails = new UserDetails(this);
        service = RetrofitInstance.createService(RetrofitService.class);

  android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    token = task.getException().getMessage();
                    Log.w("FCM TOKEN Failed", task.getException());
                } else {
                    token = task.getResult().getToken();
                    userDetails.setVechile(token);
                    Log.i("FCM TOKEN", token);
                }
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        binding.etMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreLoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog pd = new ProgressDialog(PreLoginActivity.this);
                pd.setMessage("Loading.....");
                pd.show();
                pd.setCancelable(true);
                service.get_guest_login(android_id).enqueue(new Callback<SkipMyPojo>() {
                    @Override
                    public void onResponse(Call<SkipMyPojo> call, Response<SkipMyPojo> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            SkipMyPojo skipMyPojo = response.body();
                            if (skipMyPojo.getData() != null) {
                                if (skipMyPojo.getSuccess().equalsIgnoreCase("true")) {
                                    pd.dismiss();
                                    userDetails.setUserId(skipMyPojo.getData().get_id());
                                    Intent intent = new Intent(PreLoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    //Toast.makeText(PreLoginActivity.this, "Successfully taken device id", Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(PreLoginActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                }

                            } else {

                                Toast.makeText(PreLoginActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<SkipMyPojo> call, Throwable t) {
                        pd.dismiss();
                        Toast.makeText(PreLoginActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        language = userDetails.getLanguage();
        if (language.equals("ENGLISH")) {

            binding.tvSkip.setText(R.string.skip_en);
            binding.tvEnter.setText(R.string.enter_your_mobile_number_en);
            binding.etMobile.setHint(R.string.mobile_number_en);
            binding.letsGetPageTv.setText(R.string.let_s_get_started_en);

        } else if (language.equals("TELUGU")) {

            binding.tvSkip.setText(R.string.skip_te);
            binding.tvEnter.setText(R.string.enter_your_mobile_number_te);
            binding.etMobile.setHint(R.string.mobile_number_te);
            binding.letsGetPageTv.setText(R.string.let_s_get_started_te);

        } else if (language.equals("HINDI")) {

            binding.tvSkip.setText(R.string.skip_hi);
            binding.tvEnter.setText(R.string.enter_your_mobile_number_hi);
            binding.etMobile.setHint(R.string.mobile_number_hi);
            binding.letsGetPageTv.setText(R.string.let_s_get_started_hi);

        }

    }
}
