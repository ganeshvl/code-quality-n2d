package com.news2day.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.news2day.Adapter.AboutUsAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityAboutListBinding;
import com.news2day.model.AboutUsPojo.AboutUsMyPojo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AboutActivity extends AppCompatActivity {
    ActivityAboutListBinding activityAboutListBinding;
    UserDetails userDetails;
    RetrofitService service;
    String language, language_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAboutListBinding = DataBindingUtil.setContentView(this, R.layout.activity_about_list);
        userDetails = new UserDetails(this);
        service = RetrofitInstance.createService(RetrofitService.class);
        activityAboutListBinding.toolbar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        language = userDetails.getLanguage();
        if (language.equals("TELUGU")) {

            language_type = "te";
            language = "TELUGU";
            activityAboutListBinding.toolbar.titleTx.setText(R.string.about_us_te);

        } else if (language.equals("ENGLISH")) {

            language_type = "en";
            language = "ENGLISH";
            activityAboutListBinding.toolbar.titleTx.setText(R.string.about_us_en);

        } else if (language.equals("HINDI")) {

            language_type = "hi";
            language = "HINDI";
            activityAboutListBinding.toolbar.titleTx.setText(R.string.about_us_hi);

        }

        final ProgressDialog pd = new ProgressDialog(AboutActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);
        service.get_about_us(language_type).enqueue(new Callback<AboutUsMyPojo>() {
            @Override
            public void onResponse(Call<AboutUsMyPojo> call, Response<AboutUsMyPojo> response) {
                if (response.body() != null && response.isSuccessful()) {
                    AboutUsMyPojo aboutUsMyPojo = response.body();
                    if (aboutUsMyPojo.getData() != null) {
                        if (aboutUsMyPojo.getSuccess().equalsIgnoreCase("true")) {
                            pd.dismiss();
                            if (aboutUsMyPojo.getData().size() != 0) {
                                AboutUsAdapter aboutUsAdapter = new AboutUsAdapter(AboutActivity.this, aboutUsMyPojo.getData(), language);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AboutActivity.this);
                                activityAboutListBinding.aboutUsRv.setLayoutManager(linearLayoutManager);
                                activityAboutListBinding.aboutUsRv.setAdapter(aboutUsAdapter);
                            } else {

                                Toast.makeText(AboutActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Toast.makeText(AboutActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(AboutActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<AboutUsMyPojo> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(AboutActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
