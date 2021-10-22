package com.news2day.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.news2day.Adapter.AboutUsAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityContactUsBinding;
import com.news2day.model.AboutUsPojo.AboutUsMyPojo;
import com.news2day.model.ContactUsPojo.ContactUsMyPojo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {
    ActivityContactUsBinding binding;
    UserDetails userDetails;
    RetrofitService service;
    String phone_number,
            email_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_contact_us);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);

        binding.toolBar.titleTx.setText("Contact Us");

        userDetails = new UserDetails(this);
        service = RetrofitInstance.createService(RetrofitService.class);

        binding.llWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse(
                                        String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phone_number,"Hi how are you")
                                )
                        )
                );
//                Uri u = Uri.parse("tel:" + phone_number);
//
//                // Create the intent and set the data for the
//                // intent as the phone number.
//                Intent i = new Intent(Intent.ACTION_DIAL, u);
//
//                try
//                {
//                    // Launch the Phone app's dialer with a phone
//                    // number to dial a call.
//                    startActivity(i);
//                }
//                catch (SecurityException s)
//                {
//                    // show() method display the toast with
//                    // exception message.
//                    Toast.makeText(ContactUsActivity.this, "An error occurred", Toast.LENGTH_LONG)
//                            .show();
//                }
                /*Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.putExtra(Uri.parse(phone_number))
                startActivity(intent);*/
            }
        });
        final ProgressDialog pd = new ProgressDialog(ContactUsActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);
        service.get_contact_us().enqueue(new Callback<ContactUsMyPojo>() {
            @Override
            public void onResponse(Call<ContactUsMyPojo> call, Response<ContactUsMyPojo> response) {
                if (response.body() != null && response.isSuccessful()) {
                    ContactUsMyPojo contactUsMyPojo = response.body();
                    if (contactUsMyPojo.getData() != null) {
                        if (contactUsMyPojo.getSuccess().equalsIgnoreCase("true")) {
                            pd.dismiss();
                            binding.addressTv.setText(contactUsMyPojo.getData().getAddress());
                            phone_number = contactUsMyPojo.getData().getPhone();
                            email_id = contactUsMyPojo.getData().getEmail();
                        } else {

                            Toast.makeText(ContactUsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(ContactUsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ContactUsMyPojo> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(ContactUsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });

        binding.llEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, email_id);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        binding.toolBar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

}