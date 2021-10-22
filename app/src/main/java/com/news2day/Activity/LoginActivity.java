package com.news2day.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityLoginBinding;
import com.news2day.model.LoginPojo;
import com.news2day.model.States.StatePojoOne;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("unchecked")
public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;
    Animation bottomAnim;
    String[] countryCode = {"+91", "+602", "+93", "+45", "+62"};
    String code, language;
    UserDetails userDetails;
    RetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_login);

        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        userDetails = new UserDetails(this);
        service = RetrofitInstance.createService(RetrofitService.class);

        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        activityLoginBinding.llLogin.setAnimation(bottomAnim);
        ArrayAdapter arrayAdapter = new ArrayAdapter(LoginActivity.this, android.R.layout.simple_dropdown_item_1line, countryCode);
        activityLoginBinding.spinner.setAdapter(arrayAdapter);

        activityLoginBinding.toolBar.backBut.setImageResource(R.drawable.back_arrow);

        language = userDetails.getLanguage();
        if (language.equals("ENGLISH")) {

            activityLoginBinding.termsAndConditionsTv.setHint(R.string.terms_amp_conditions_en);
            activityLoginBinding.verifyBut.setText(R.string.continue_en);
            activityLoginBinding.mobileEdText.setHint(R.string.mobile_number_en);
            activityLoginBinding.agreeTv.setText(R.string.by_continuing_you_agree_to_our_en);
            activityLoginBinding.enterMobileTv.setText(R.string.enter_your_mobile_number_en);


        } else if (language.equals("TELUGU")) {

            activityLoginBinding.mobileEdText.setHint(R.string.mobile_number_te);
            activityLoginBinding.termsAndConditionsTv.setText(R.string.terms_amp_conditions_te);
            activityLoginBinding.verifyBut.setText(R.string.continue_te);
            activityLoginBinding.agreeTv.setText(R.string.by_continuing_you_agree_to_our_te);
            activityLoginBinding.enterMobileTv.setText(R.string.enter_your_mobile_number_te);

        } else if (language.equals("HINDI")) {

            activityLoginBinding.mobileEdText.setHint(R.string.mobile_number_hi);
            activityLoginBinding.termsAndConditionsTv.setText(R.string.terms_amp_conditions_hi);
            activityLoginBinding.verifyBut.setText(R.string.continue_hi);
            activityLoginBinding.agreeTv.setText(R.string.by_continuing_you_agree_to_our_hi);
            activityLoginBinding.enterMobileTv.setText(R.string.enter_your_mobile_number_hi);
        }
        //activityLoginBinding.mobileEdText.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        activityLoginBinding.toolBar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activityLoginBinding.countryCodeTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityLoginBinding.spinner.performClick();
            }
        });
        activityLoginBinding.verifyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validations();
            }
        });

        activityLoginBinding.termsAndConditionsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, TermsAndconditionsActivity.class);
                startActivity(intent);
            }
        });
        activityLoginBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                code = countryCode[i];
                activityLoginBinding.countryCodeTx.setText(code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    void validations() {
        String validNumber = "^[6-9][0-9]{9}$";
        if (activityLoginBinding.mobileEdText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
        } else if (!activityLoginBinding.mobileEdText.getText().toString().matches(validNumber)) {
            Toast.makeText(this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
        } else {

            final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Loading.....");
            pd.show();
            pd.setCancelable(true);
            service.check_mobile(activityLoginBinding.mobileEdText.getText().toString().trim()).enqueue(new Callback<LoginPojo>() {
                @Override
                public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        LoginPojo loginPojo = response.body();
                        if (loginPojo != null) {
                            if (loginPojo.getSuccess().equalsIgnoreCase("true")) {
                                pd.dismiss();
                                userDetails.setMobile(activityLoginBinding.mobileEdText.getText().toString());
                                userDetails.setUserId(loginPojo.getUser_id());
                                Intent intent = new Intent(LoginActivity.this, OTPActivity.class);
                                intent.putExtra("OTP", loginPojo.getOtp());
                                intent.putExtra("MobileNumber", activityLoginBinding.mobileEdText.getText().toString());
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                pd.dismiss();
                                Toast.makeText(LoginActivity.this, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                }

                @Override
                public void onFailure(Call<LoginPojo> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

                }
            });


        }
    }
}