package com.news2day.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.news2day.Adapter.CityAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityLocationBinding;
import com.news2day.model.Districts.PojoOne;
import com.news2day.model.Districts.PojoThree;
import com.news2day.model.Districts.PojoTwo;
import com.news2day.model.StringRefer;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocationActivity extends AppCompatActivity implements StringRefer {

    ActivityLocationBinding binding;
    UserDetails userDetails;
    /*ArrayList<String> name = new ArrayList<>();*/
    ArrayList<Integer> image = new ArrayList<>();
    String state_id, dis_id, district_name = "", language, lang, id;
    RetrofitService service;
    CityAdapter cityAdapter;
    StringRefer stringDistrict;
    String d = "";
    public static TextView no_data_tx;
    ArrayList<PojoThree> districts = new ArrayList<>();
    String name;
    public static  RecyclerView rv_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        userDetails = new UserDetails(this);
        no_data_tx = findViewById(R.id.no_data_tx);
        rv_city = findViewById(R.id.rv_city);
        stringDistrict = this;
        binding.toolBar.backBut.setVisibility(View.VISIBLE);
        binding.toolBar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        state_id = intent.getStringExtra("state_id");
        language = intent.getStringExtra("language");
        id = intent.getStringExtra("id");
        if(userDetails.isLoggedIn()) {
            if (language.equals("ENGLISH")) {
                lang = "en";
                binding.toolBar.titleTx.setText(R.string.update_location_en);
                binding.searchEd.setHint(R.string.search_district_en);
                binding.verifyBut.setText(R.string.continue_en);
            } else if (language.equals("TELUGU")) {
                lang = "te";
                binding.toolBar.titleTx.setText(R.string.update_location_te);
                binding.searchEd.setHint(R.string.search_district_te);
                binding.verifyBut.setText(R.string.continue_te);
            } else if (language.equals("HINDI")) {
                lang = "hi";
                binding.toolBar.titleTx.setText(R.string.update_location_hi);
                binding.searchEd.setHint(R.string.search_district_hi);
                binding.verifyBut.setText(R.string.continue_hi);
            }
        }
        else {
            if (language.equals("ENGLISH")) {
                lang = "en";
                binding.toolBar.titleTx.setText(R.string.change_location_en);
                binding.searchEd.setHint(R.string.search_district_en);
                binding.verifyBut.setText(R.string.continue_en);
            } else if (language.equals("TELUGU")) {
                lang = "te";
                binding.toolBar.titleTx.setText(R.string.change_location_te);
                binding.searchEd.setHint(R.string.search_district_te);
                binding.verifyBut.setText(R.string.continue_te);
            } else if (language.equals("HINDI")) {
                lang = "hi";
                binding.toolBar.titleTx.setText(R.string.change_location_hi);
                binding.searchEd.setHint(R.string.search_district_hi);
                binding.verifyBut.setText(R.string.continue_hi);
            }
        }
        userDetails.setStateId(state_id);
        userDetails.setLanguage_Id(lang);
        service = RetrofitInstance.createService(RetrofitService.class);

        /*      district_name = String.valueOf(districts.getData().getDetails().get(0).getName());*/
        binding.verifyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validations();
            }
        });

        binding.searchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 0) {
                    api_call();
                } else {
                    if (d.equals("nodistricts")) {
                        district_name = "No districts are available";
                        Toast.makeText(LocationActivity.this, R.string.no_records, Toast.LENGTH_SHORT).show();
                    } else {
                        cityAdapter.getFilter().filter(s.toString());
                    }
                    // cityAdapter.getFilter().filter(s.toString());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    api_call();
                } else {
                    if (d.equals("nodistricts")) {
                        district_name = "No districts are available";
                        Toast.makeText(LocationActivity.this, R.string.no_records, Toast.LENGTH_SHORT).show();
                    } else {
                       /* if (districts != null) {
                            cityAdapter.getFilter().filter(s.toString());
                        } else {
                            Toast.makeText(LocationActivity.this, R.string.error, Toast.LENGTH_SHORT).show();

                        }*/
                        cityAdapter.getFilter().filter(s.toString());
                    }
                    //cityAdapter.getFilter().filter(s.toString());

                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    api_call();
                } else {
                    /*if (districts.size() <= 1) {
                        Toast.makeText(LocationActivity.this, "No Districts are available", Toast.LENGTH_SHORT).show();
                    } else {
                        cityAdapter.getFilter().filter(s.toString());
                    }*/
                    if (d.equals("nodistricts")) {
                        district_name = "No districts are available";
                        Toast.makeText(LocationActivity.this, R.string.no_records, Toast.LENGTH_SHORT).show();
                    } else {
                        cityAdapter.getFilter().filter(s.toString());
                    }
                    //cityAdapter.getFilter().filter(s.toString());
                }
            }
        });

        final ProgressDialog pd = new ProgressDialog(LocationActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);
        service.districts(state_id).enqueue(new Callback<PojoOne>() {
            @Override
            public void onResponse(Call<PojoOne> call, Response<PojoOne> response) {
                if (response.body() != null && response.isSuccessful()) {
                    PojoOne districts = response.body();
                    pd.dismiss();
                    if (districts.getData() != null) {
                        if (districts.getStatus().equalsIgnoreCase("200")) {
                            if (districts.getData().getDetails().size() < 1) {
                                binding.noDataTx.setVisibility(View.VISIBLE);
                                binding.rvCity.setVisibility(View.GONE);
                                d = "nodistricts";
                                district_name = "No districts are available";

                            } else {

                                cityAdapter = new CityAdapter(LocationActivity.this, districts.getData().getDetails(), stringDistrict, language);
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(LocationActivity.this, 1);
                                binding.rvCity.setLayoutManager(linearLayoutManager);
                                binding.rvCity.setAdapter(cityAdapter);

                            }
                        } else {
                            pd.dismiss();
                            Toast.makeText(LocationActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pd.dismiss();
                        Toast.makeText(LocationActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PojoOne> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(LocationActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

            }
        });

    }

    void validations() {
        if (district_name.equals("No districts are available")) {

            Toast.makeText(this, R.string.no_records, Toast.LENGTH_SHORT).show();

        } else if (district_name.equals("")) {

            Toast.makeText(this, R.string.please_select_district, Toast.LENGTH_SHORT).show();

        } else {
            if (id != null) {
                if (id.equals("id")) {
                    Intent intent = new Intent(LocationActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LocationActivity.this, PreLoginActivity.class);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(LocationActivity.this, PreLoginActivity.class);
                startActivity(intent);
            }

        }


    }

    @Override
    public void district_name_str(String d_n_s, String district_id) {
        district_name = d_n_s;
        dis_id = district_id;
        userDetails.setDistrictId(dis_id);
    }

    void api_call() {

        service.districts(state_id).enqueue(new Callback<PojoOne>() {
            @Override
            public void onResponse(Call<PojoOne> call, Response<PojoOne> response) {
                if (response.body() != null && response.isSuccessful()) {
                    PojoOne districts = response.body();
                    if (districts.getData() != null) {
                        if (districts.getStatus().equalsIgnoreCase("200")) {
                            if (districts.getData().getDetails().size() < 1) {
                                binding.noDataTx.setVisibility(View.VISIBLE);
                                binding.rvCity.setVisibility(View.GONE);
                            }
                            else {
                                binding.noDataTx.setVisibility(View.GONE);
                                binding.rvCity.setVisibility(View.VISIBLE);
                                cityAdapter = new CityAdapter(LocationActivity.this, districts.getData().getDetails(), stringDistrict, language);
                                GridLayoutManager linearLayoutManager = new GridLayoutManager(LocationActivity.this, 1);
                                binding.rvCity.setLayoutManager(linearLayoutManager);
                                binding.rvCity.setAdapter(cityAdapter);
                                //   Toast.makeText(LocationActivity.this, districts.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(LocationActivity.this, districts.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(LocationActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PojoOne> call, Throwable t) {

                Toast.makeText(LocationActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
            }
        });

    }
}