package com.news2day.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.news2day.Adapter.AboutUsAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityTermsAndconditionsBinding;
import com.news2day.model.AboutUsPojo.AboutUsMyPojo;
import com.news2day.model.TermsAndConditionsPojo.TermsMyPojo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsAndconditionsActivity extends AppCompatActivity {
    ActivityTermsAndconditionsBinding binding;
    UserDetails userDetails;
    RetrofitService service;
    String language, language_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_terms_andconditions);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_andconditions);
        userDetails = new UserDetails(this);
        service = RetrofitInstance.createService(RetrofitService.class);

        language = userDetails.getLanguage();

        if (language.equals("TELUGU")) {

            language_type = "te";
            language = "TELUGU";
            binding.toolBar.titleTx.setText(R.string.terms_and_conditions_te);

        } else if (language.equals("ENGLISH")) {

            language_type = "en";
            language = "ENGLISH";
            binding.toolBar.titleTx.setText(R.string.terms_and_conditions_en);

        } else if (language.equals("HINDI")) {

            language_type = "hi";
            language = "HINDI";
            binding.toolBar.titleTx.setText(R.string.terms_and_conditions_hi);

        }
        userDetails = new UserDetails(this);
        service = RetrofitInstance.createService(RetrofitService.class);

        binding.toolBar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final ProgressDialog pd = new ProgressDialog(TermsAndconditionsActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);
        service.get_terms_and_conditions(language_type).enqueue(new Callback<TermsMyPojo>() {
            @Override
            public void onResponse(Call<TermsMyPojo> call, Response<TermsMyPojo> response) {
                if (response.body() != null && response.isSuccessful()) {
                    TermsMyPojo termsMyPojo = response.body();
                    if (termsMyPojo.getData() != null) {
                        if (termsMyPojo.getSuccess().equalsIgnoreCase("true")) {
                            pd.dismiss();
                            binding.termsAndConditionsTv.setText(termsMyPojo.getData().getDescription());

                        } else {

                            Toast.makeText(TermsAndconditionsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(TermsAndconditionsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<TermsMyPojo> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(TermsAndconditionsActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
            }
        });

    }
}