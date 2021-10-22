package com.news2day.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.news2day.Adapter.StatesAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityLanguageBinding;
import com.news2day.model.States.StatePojoOne;
import com.news2day.model.StringRefer;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StatesActivity extends AppCompatActivity implements StringRefer {

    ActivityLanguageBinding binding;
    UserDetails userDetails;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> image = new ArrayList<>();
    RetrofitService service;
    String language, id, language_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDetails = new UserDetails(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language);

        service = RetrofitInstance.createService(RetrofitService.class);


        Intent intent = getIntent();
        language = intent.getStringExtra("language");
        id = intent.getStringExtra("id");


        if (language.equals("ENGLISH")) {
            binding.toolBar.titleTx.setText(R.string.update_location_en);

        } else if (language.equals("TELUGU")) {
            binding.toolBar.titleTx.setText(R.string.update_location_te);

        } else if (language.equals("HINDI")) {
            binding.toolBar.titleTx.setText(R.string.update_location_hi);
        }


        binding.toolBar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        put();
        final ProgressDialog pd = new ProgressDialog(StatesActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);
        service.states().enqueue(new Callback<StatePojoOne>() {
            @Override
            public void onResponse(Call<StatePojoOne> call, Response<StatePojoOne> response) {
                if (response.body() != null && response.isSuccessful()) {

                    StatePojoOne statePojo = response.body();
                    pd.dismiss();
                    if (statePojo.getData() != null) {
                        if (statePojo.getStatus().equalsIgnoreCase("200")) {
                            if (statePojo.getData().getDetails().size() != 0) {
                                StatesAdapter govtJobsSubRecyclerAdapter = new StatesAdapter(StatesActivity.this, statePojo.getData().getDetails(), language, image, id);
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(StatesActivity.this, 2);
                                binding.rlLanguage.setLayoutManager(linearLayoutManager);
                                binding.rlLanguage.setAdapter(govtJobsSubRecyclerAdapter);
                            }else {
                                Toast.makeText(StatesActivity.this, R.string.states_data, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(StatesActivity.this, statePojo.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(StatesActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<StatePojoOne> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(StatesActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

            }
        });


    }

    void put() {


    /*    name.add("ANDHRA PRADESH");
        name.add("KARNATAKA");
        name.add("TELANGANA");
        name.add("TAMIL NADU");
        name.add("MAHARASTRA");
        name.add("KERALA");*/

        image.add(R.drawable.ic_andhra);
        image.add(R.drawable.ic_karnataka);
        image.add(R.drawable.ic_telugu);
        image.add(R.drawable.ic_tamil);
        image.add(R.drawable.ic_maharastra);
        image.add(R.drawable.ic_kerela);


    }

    @Override
    public void district_name_str(String d_n_s, String district_id) {

    }
}