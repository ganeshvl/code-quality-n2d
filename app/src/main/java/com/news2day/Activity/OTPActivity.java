package com.news2day.Activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityOtpactivityBinding;
import com.news2day.model.LoginPojo;
import com.news2day.model.OtpPojo;
import com.news2day.model.ResendOtpPojo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {
    ActivityOtpactivityBinding otpactivityBinding;
    ResendOtpPojo resendOtpPojo;
    OtpPojo OtpPojo;
    UserDetails userDetails;
    String otp, mobile_number, language;
    RetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_otpactivity);
        userDetails = new UserDetails(this);
        otpactivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_otpactivity);

        service = RetrofitInstance.createService(RetrofitService.class);

        Intent intent = getIntent();
        otp = intent.getStringExtra("OTP");
        mobile_number = intent.getStringExtra("MobileNumber");

        otpactivityBinding.pinText.setText(mobile_number);
        Toast.makeText(OTPActivity.this, otp, Toast.LENGTH_SHORT).show();


        language = userDetails.getLanguage();

        if (language.equals("ENGLISH")) {

            otpactivityBinding.enterOtpTv.setText(R.string.enter_otp_en);
            otpactivityBinding.verificationCodeTv.setText(R.string.verification_code_en);
            otpactivityBinding.tvResend.setText(R.string.resend_en);
            otpactivityBinding.otpSubmitBut.setText(R.string.log_in_en);


        } else if (language.equals("TELUGU")) {

            otpactivityBinding.enterOtpTv.setText(R.string.enter_otp_te);
            otpactivityBinding.verificationCodeTv.setText(R.string.verification_code_te);
            otpactivityBinding.tvResend.setText(R.string.resend_te);
            otpactivityBinding.otpSubmitBut.setText(R.string.log_in_te);

        } else if (language.equals("HINDI")) {

            otpactivityBinding.enterOtpTv.setText(R.string.enter_otp_hi);
            otpactivityBinding.verificationCodeTv.setText(R.string.verification_code_hi);
            otpactivityBinding.tvResend.setText(R.string.resend_hi);
            otpactivityBinding.otpSubmitBut.setText(R.string.log_in_hi);

        }


        otpactivityBinding.tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(OTPActivity.this);
                pd.setMessage("Loading.....");
                pd.show();
                pd.setCancelable(true);
                service.Resend_OTP(mobile_number, otpactivityBinding.txtPinEntry.getText().toString().trim()).enqueue(new Callback<ResendOtpPojo>() {
                    @Override
                    public void onResponse(Call<ResendOtpPojo> call, Response<ResendOtpPojo> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            resendOtpPojo = response.body();
                            otpactivityBinding.txtPinEntry.setText("");
                            if (resendOtpPojo != null) {
                                if (resendOtpPojo.getStatus().equalsIgnoreCase("200")) {
                                    pd.dismiss();
                                    Toast.makeText(OTPActivity.this, resendOtpPojo.getOtp() + "  " + "\\" + "  " + resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(OTPActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                Toast.makeText(OTPActivity.this, resendOtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResendOtpPojo> call, Throwable t) {
                        pd.dismiss();
                        Toast.makeText(OTPActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        otpactivityBinding.otpSubmitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otpactivityBinding.txtPinEntry.getText().toString().isEmpty()) {
                    Toast.makeText(OTPActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();

                } else if (otpactivityBinding.txtPinEntry.length() < 4) {
                    Toast.makeText(OTPActivity.this, "Please enter Valid OTP", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog pd = new ProgressDialog(OTPActivity.this);
                    pd.setMessage("Loading.....");
                    pd.show();
                    pd.setCancelable(true);
                    service.OTP(mobile_number, otpactivityBinding.txtPinEntry.getText().toString().trim()).enqueue(new Callback<OtpPojo>() {
                        @Override
                        public void onResponse(Call<OtpPojo> call, Response<OtpPojo> response) {
                            if (response.body() != null && response.isSuccessful()) {

                                OtpPojo = response.body();
                                if (OtpPojo != null) {
                                    if (OtpPojo.getStatus().equalsIgnoreCase("200")) {
                                        pd.dismiss();
                                        Intent intent = new Intent(OTPActivity.this, HomeActivity.class);
                                        userDetails.setLoggedIn();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(OTPActivity.this, OtpPojo.getMessage(), Toast.LENGTH_SHORT).show();

                                    } else {
                                        pd.dismiss();
                                        Toast.makeText(OTPActivity.this, R.string.invalid_otp, Toast.LENGTH_SHORT).show();

                                    }
                                } else {
                                    pd.dismiss();
                                    Toast.makeText(OTPActivity.this, R.string.error, Toast.LENGTH_SHORT).show();

                                }

                               /* if (otp==otpactivityBinding.txtPinEntry.getText().toString()){

                                }else{
                                    Toast.makeText(OTPActivity.this, "Please Check OTP", Toast.LENGTH_SHORT).show();
                                }*/
                            }
                        }

                        @Override
                        public void onFailure(Call<OtpPojo> call, Throwable t) {
                            pd.dismiss();
                            Toast.makeText(OTPActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        smsTask();
    }

    private void smsTask() {
        try {
            SmsRetrieverClient client = SmsRetriever.getClient(getApplicationContext());

            Task<Void> task = client.startSmsRetriever();

            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("TAG", "Successfully started retriever");
                }
            });
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("TAG", "Failed to start retriever");
                }

                ;
            });
            SmsRetriever.getClient(getApplicationContext()).startSmsUserConsent(null);//VM-WPNMSG //57575702
            IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
            registerReceiver(smsVerificationReceiver, intentFilter, SmsRetriever.SEND_PERMISSION, null);
            //registerReceiver(smsVerificationReceiver, SmsRetriever.SEND_PERMISSION, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.e(  "Enter OTP",otp ) ;
        //pinEntry.setText(otp);
        // Toast.makeText(OTPAtivity.this, "Enter OTP"+otp, Toast.LENGTH_LONG).show();
        // Task<Void> task = SmsRetriever.getClient(getApplicationContext()).startSmsUserConsent(  mobilenumber_str);

    }

    private static final int SMS_CONSENT_REQUEST = 2;  // Set to an unused request code
    private final BroadcastReceiver smsVerificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                Bundle extras = intent.getExtras();
                Status smsRetrieverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

                switch (smsRetrieverStatus.getStatusCode()) {
                    case CommonStatusCodes.SUCCESS:
                        // Get consent intent
                        Intent consentIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            String message = consentIntent.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                            System.out.println(message);
                            startActivityForResult(consentIntent, SMS_CONSENT_REQUEST);
                        } catch (ActivityNotFoundException e) {
                            // Handle the exception ...
                        }
                        break;
                    case CommonStatusCodes.TIMEOUT:
                        // Time out occurred, handle the error.
                        break;
                }
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case SMS_CONSENT_REQUEST:
                if (resultCode == RESULT_OK) {
                    // Get SMS message content
                    String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                    try {
                        String otp = message.replace("Your OTP Verification Code is ", "");
                        if (otp != null && otp.length() > 0) {
                            otpactivityBinding.txtPinEntry.setText(otp);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // Extract one-time code from the message and complete verification
                    // `sms` contains the entire text of the SMS message, so you will need
                    // to parse the string.
                    // define this function

                    // send one time code to the server
                } else {
                    // Consent canceled, handle the error ...
                }
                break;

        }
    }
}
