package com.news2day.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityNewSettingsBinding;
import com.news2day.databinding.ActivityOptionsBinding;

public class OptionActivity extends AppCompatActivity {
    ActivityOptionsBinding activitySettingsBinding;
    UserDetails userDetails;
String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySettingsBinding = DataBindingUtil.setContentView(this, R.layout.activity_options);
        userDetails = new UserDetails(this);
        Intent intent = getIntent();
        language = intent.getStringExtra("language");

        language = userDetails.getLanguage();

        if (language.equals("ENGLISH")) {
            activitySettingsBinding.toolbar.titleTx.setText(R.string.options_en);
            activitySettingsBinding.settingsTv.setText(R.string.settings_en);
            activitySettingsBinding.aboutUsTv.setText(R.string.about_us_en);
            activitySettingsBinding.faqTv.setText(R.string.faq_s_amp_tutorials_en);
            activitySettingsBinding.contactUsTv.setText(R.string.contact_us_en);
            activitySettingsBinding.feedbackTv.setText(R.string.feedback_en);
            activitySettingsBinding.referTheAppTv.setText(R.string.refer_the_app_en);
            activitySettingsBinding.rateUsTv.setText(R.string.rate_us_en);



        } else if (language.equals("TELUGU")) {
            activitySettingsBinding.toolbar.titleTx.setText(R.string.options_te);
            activitySettingsBinding.settingsTv.setText(R.string.settings_te);
            activitySettingsBinding.aboutUsTv.setText(R.string.about_us_te);
            activitySettingsBinding.faqTv.setText(R.string.faq_s_amp_tutorials_te);
            activitySettingsBinding.contactUsTv.setText(R.string.contact_us_te);
            activitySettingsBinding.feedbackTv.setText(R.string.feedback_te);
            activitySettingsBinding.referTheAppTv.setText(R.string.refer_the_app_te);
            activitySettingsBinding.rateUsTv.setText(R.string.rate_us_te);



        } else if (language.equals("HINDI")) {
            activitySettingsBinding.toolbar.titleTx.setText(R.string.options_hi);
            activitySettingsBinding.settingsTv.setText(R.string.settings_hi);
            activitySettingsBinding.aboutUsTv.setText(R.string.about_us_hi);
            activitySettingsBinding.faqTv.setText(R.string.faq_s_amp_tutorials_hi);
            activitySettingsBinding.contactUsTv.setText(R.string.contact_us_hi);
            activitySettingsBinding.feedbackTv.setText(R.string.feedback_hi);
            activitySettingsBinding.referTheAppTv.setText(R.string.refer_the_app_hi);
            activitySettingsBinding.rateUsTv.setText(R.string.rate_us_hi);


        }


        activitySettingsBinding.toolbar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, HomeActivity.class);
                intent.putExtra("opt","change");
                startActivity(intent);
            }
        });


        activitySettingsBinding.llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        activitySettingsBinding.llAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        activitySettingsBinding.llFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

        activitySettingsBinding.llContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, ContactUsActivity.class);
                startActivity(intent);
            }
        });

        activitySettingsBinding.llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                intent.putExtra(Intent.EXTRA_TEXT, "Share via");
                startActivity(Intent.createChooser(intent, "share via"));
            }
        });
        activitySettingsBinding.llRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store?utm_source=apac_med&utm_medium=hasem&utm_content=Jul0121&utm_campaign=Evergreen&pcampaignid=MKT-EDR-apac-in-1003227-med-hasem-py-Evergreen-Jul0121-Text_Search_BKWS-BKWS%7CONSEM_kwid_43700065205026415_creativeid_535350509927_device_c&gclid=Cj0KCQjwu7OIBhCsARIsALxCUaNnhGgTcfDU_ovOV_kL9lxk_snMOB3Z4eeizWY4r4dZ8MJoheUMg_waAm7UEALw_wcB&gclsrc=aw.ds"));
                startActivity(intent);
            }
        });


    }
}
