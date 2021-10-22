package com.news2day.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.news2day.Adapter.DiscoveryNewsAdapter;
import com.news2day.Adapter.DiscoverySpecialVideosAdapter;
import com.news2day.Adapter.NewsAdapter;
import com.news2day.Adapter.SpecialVideosAdapter;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.databinding.ActivityNewsBinding;
import com.news2day.model.DiscoveryPojos.DataPojo;
import com.news2day.model.DiscoveryPojos.DiscoveryMyPojo;
import com.news2day.model.SubCategoryPojos.SubCatMyPojo;
import com.news2day.model.SubCategoryPojos.SubCatNamePojo;
import com.news2day.model.VerticalViewPojos.PostsPojoOne;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    ActivityNewsBinding binding;
    ArrayList<String> name_selected = new ArrayList<>();
    String category_id, title;
    RetrofitService service;
    UserDetails userDetails;
    String language;
    List<DataPojo> title_n;
    int page_count = 1, per_page_count = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_news);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news);

        service = RetrofitInstance.createService(RetrofitService.class);

        userDetails = new UserDetails(this);

        Intent intent = getIntent();
        category_id = intent.getStringExtra("category_id");
        title = intent.getStringExtra("title");

        binding.toolbar.titleTx.setText(title);

        binding.toolbar.backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        language = userDetails.getLanguage();


        if(title.equals("Special Videos")){
            service.Posts_special(page_count++, per_page_count,
                    category_id,
                    userDetails.getStateId(),
                    userDetails.getDistrictId(), userDetails.getLanguage_Id(),
                    userDetails.getUserId(), 1).enqueue(new Callback<PostsPojoOne>() {
                @Override
                public void onResponse(Call<PostsPojoOne> call, Response<PostsPojoOne> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        PostsPojoOne postsPojoOne = response.body();
                        if (postsPojoOne.getData() != null) {
                            if (postsPojoOne.getSuccess().equalsIgnoreCase("true")) {
                                SpecialVideosAdapter newsAdapter = new SpecialVideosAdapter(NewsActivity.this,postsPojoOne.getData());
                                LinearLayoutManager gridLayoutManager = new LinearLayoutManager(NewsActivity.this);
                                binding.lRv.setLayoutManager(gridLayoutManager);
                                binding.lRv.setAdapter(newsAdapter);

                            }else {

                                Toast.makeText(NewsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            }
                        }else {

                            Toast.makeText(NewsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<PostsPojoOne> call, Throwable t) {
                    Toast.makeText(NewsActivity.this, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();

                }
            });

        }else {
            api_call();
        }
    }



    public void api_call(){
        final ProgressDialog pd = new ProgressDialog(NewsActivity.this);
        pd.setMessage("Loading.....");
        pd.show();
        pd.setCancelable(true);

        service.get_sub_category(category_id).enqueue(new Callback<SubCatMyPojo>() {
            @Override
            public void onResponse(Call<SubCatMyPojo> call, Response<SubCatMyPojo> response) {
                if (response.body() != null && response.isSuccessful()) {
                    SubCatMyPojo subCatMyPojo = response.body();
                    if (subCatMyPojo.getSuccess().equals("true")) {
                        pd.dismiss();
                        if (subCatMyPojo.getData() != null) {
                            if (subCatMyPojo.getData().size() == 0) {
                                binding.newsNoDataTv.setVisibility(View.VISIBLE);
                                binding.lRv.setVisibility(View.GONE);
                            } else {
                                binding.newsNoDataTv.setVisibility(View.GONE);
                                binding.lRv.setVisibility(View.VISIBLE);
                                NewsAdapter newsAdapter = new NewsAdapter(NewsActivity.this, subCatMyPojo, language,title,category_id);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(NewsActivity.this, 3);
                                binding.lRv.setLayoutManager(gridLayoutManager);
                                binding.lRv.setAdapter(newsAdapter);
                            }

                        }

                    } else {
                        pd.dismiss();
                        Toast.makeText(NewsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<SubCatMyPojo> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(NewsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void put() {

        name_selected.add("Local");
        name_selected.add("National");
        name_selected.add("Global");

        name_selected.add("Local");
        name_selected.add("National");
        name_selected.add("Global");

        name_selected.add("Local");
        name_selected.add("National");
        name_selected.add("Global");
    }

}