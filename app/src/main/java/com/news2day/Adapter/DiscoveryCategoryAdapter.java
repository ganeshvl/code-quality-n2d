package com.news2day.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.news2day.Activity.ContestsActivity;
import com.news2day.Activity.NewsActivity;
import com.news2day.Activity.StatesActivity;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.model.DiscoveryPojos.DataPojo;
import com.news2day.model.VerticalViewPojos.PostsPojoOne;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoveryCategoryAdapter extends RecyclerView.Adapter<DiscoveryCategoryAdapter.ViewHolder> {
    Context context;
    UserDetails userDetails;
    List<DataPojo> data;
    String language,
            brief_data_str,
            servey_str, contests_str, title;
    RetrofitService service;
    int page_count = 1, per_page_count = 10;


    public DiscoveryCategoryAdapter(Context context, List<DataPojo> data, String language) {
        this.context = context;
        this.data = data;
        this.language = language;
        userDetails = new UserDetails(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.discovery_categories_adapter_lay_out, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        service = RetrofitInstance.createService(RetrofitService.class);

        if (language.equals("ENGLISH")) {
            holder.category_tv.setText(data.get(position).getName().getEn());
            holder.view_all_tv.setText(R.string.view_all_en);

            holder.top_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsActivity.class);
                    intent.putExtra("category_id", data.get(position).get_id());
                    intent.putExtra("title", data.get(position).getName().getEn());
                    context.startActivity(intent);
                }
            });
        } else if (language.equals("TELUGU")) {
            holder.category_tv.setText(data.get(position).getName().getTe());
            title = data.get(position).getName().getTe();
            holder.view_all_tv.setText(R.string.view_all_te);
            holder.top_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsActivity.class);
                    intent.putExtra("category_id", data.get(position).get_id());
                    intent.putExtra("title", data.get(position).getName().getTe());
                    context.startActivity(intent);
                }
            });
        } else if (language.equals("HINDI")) {
            holder.category_tv.setText(data.get(position).getName().getHi());
            holder.view_all_tv.setText(R.string.view_all_hi);
            title = data.get(position).getName().getHi();
            holder.top_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsActivity.class);
                    intent.putExtra("category_id", data.get(position).get_id());
                    intent.putExtra("title", data.get(position).getName().getHi());
                    context.startActivity(intent);
                }
            });
        }
        if (data.get(position).getSubcat_data().size() == 0) {
//            holder.no_data_tv.setVisibility(View.VISIBLE);
//            holder.category_rv.setVisibility(View.GONE);
//            holder.special_rv.setVisibility(View.GONE);

            holder.no_data_tv.setVisibility(View.GONE);
            holder.category_rv.setVisibility(View.GONE);
            holder.top_rl.setVisibility(View.GONE);
        } else {
            holder.no_data_tv.setVisibility(View.GONE);
            holder.category_rv.setVisibility(View.VISIBLE);
            holder.special_rv.setVisibility(View.GONE);
            DiscoveryLiveInfoAdapter discoveryLiveInfoAdapter = new DiscoveryLiveInfoAdapter(context, data.get(position).getSubcat_data(), language, data.get(position).getName());
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            holder.category_rv.setLayoutManager(linearLayoutManager1);
            holder.category_rv.setAdapter(discoveryLiveInfoAdapter);
        }


        if (data.get(position).getName().getEn().equals("Special Videos")) {
            holder.no_data_tv.setVisibility(View.GONE);
            holder.category_rv.setVisibility(View.GONE);
            holder.special_rv.setVisibility(View.VISIBLE);
            service.Posts_special(page_count++, per_page_count,
                    data.get(position).get_id(),
                    userDetails.getStateId(),
                    userDetails.getDistrictId(), userDetails.getLanguage_Id(),
                    userDetails.getUserId(), 1).enqueue(new Callback<PostsPojoOne>() {
                @Override
                public void onResponse(Call<PostsPojoOne> call, Response<PostsPojoOne> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        PostsPojoOne postsPojoOne = response.body();
                        if (postsPojoOne.getData() != null) {
                            if (postsPojoOne.getSuccess().equalsIgnoreCase("true")) {
                                DiscoverySpecialVideosAdapter discoveryLiveInfoAdapter = new DiscoverySpecialVideosAdapter(context, postsPojoOne.getData(), language);
                                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
                                holder.special_rv.setLayoutManager(linearLayoutManager1);
                                holder.special_rv.setAdapter(discoveryLiveInfoAdapter);
                                if (postsPojoOne.getData().size() > 0) {
                                    holder.top_rl.setVisibility(View.VISIBLE);
                                } else {

                                        holder.no_data_tv.setVisibility(View.GONE);
                                        holder.category_rv.setVisibility(View.GONE);
                                        holder.top_rl.setVisibility(View.GONE);

                                }

                            }
                        } else {
                            Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<PostsPojoOne> call, Throwable t) {
                    Toast.makeText(context, R.string.connectiontimeout, Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (position == 0) {
            holder.no_data_tv.setVisibility(View.GONE);
            holder.category_rv.setVisibility(View.GONE);
            holder.top_rl.setVisibility(View.GONE);
        } else if (position == 1) {
            holder.no_data_tv.setVisibility(View.GONE);
            holder.category_rv.setVisibility(View.GONE);
            holder.top_rl.setVisibility(View.GONE);
        } else if (position == 2) {
            holder.no_data_tv.setVisibility(View.GONE);
            holder.category_rv.setVisibility(View.GONE);
            holder.top_rl.setVisibility(View.GONE);
        }
        /*if (data.get(position).getName().getEn().equals("Contests")) {
            holder.contests_ll.setVisibility(View.VISIBLE);
            holder.no_data_tv.setVisibility(View.GONE);
            holder.category_rv.setVisibility(View.GONE);
            holder.top_rl.setVisibility(View.GONE);
            if (language.equals("ENGLISH")) {
                holder.contest_tv.setText(data.get(position).getName().getEn());
                contests_str = data.get(position).getName().getEn();
            } else if (language.equals("TELUGU")) {
                holder.contest_tv.setText(data.get(position).getName().getTe());
                contests_str = data.get(position).getName().getTe();
            } else if (language.equals("HINDI")) {
                holder.contest_tv.setText(data.get(position).getName().getHi());
                contests_str = data.get(position).getName().getHi();
            }

        }*/
/*
        if (data.get(position).getName().getEn().equals("Brief Data")) {
            holder.brief_data_rl.setVisibility(View.VISIBLE);
            holder.no_data_tv.setVisibility(View.GONE);
            holder.category_rv.setVisibility(View.GONE);
            holder.top_rl.setVisibility(View.GONE);

            if (language.equals("ENGLISH")) {
                holder.brief_data_tv.setText(data.get(position).getName().getEn());
                brief_data_str = data.get(position).getName().getEn();

            } else if (language.equals("TELUGU")) {
                holder.brief_data_tv.setText(data.get(position).getName().getTe());
                brief_data_str = data.get(position).getName().getTe();
            } else if (language.equals("HINDI")) {
                holder.brief_data_tv.setText(data.get(position).getName().getHi());
                brief_data_str = data.get(position).getName().getHi();
            }
        }
        if (data.get(position).getName().getEn().equals("Surveys")) {
            holder.servey_rl.setVisibility(View.VISIBLE);
            holder.no_data_tv.setVisibility(View.GONE);
            holder.category_rv.setVisibility(View.GONE);
            holder.top_rl.setVisibility(View.GONE);

            if (language.equals("ENGLISH")) {
                holder.surveys_tv.setText(data.get(position).getName().getEn());
                servey_str = data.get(position).getName().getEn();
            } else if (language.equals("TELUGU")) {
                holder.surveys_tv.setText(data.get(position).getName().getTe());
                servey_str = data.get(position).getName().getTe();
            } else if (language.equals("HINDI")) {
                holder.surveys_tv.setText(data.get(position).getName().getHi());
                servey_str = data.get(position).getName().getHi();
            }
        }*/


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView
                view_all_tv,
                category_tv, no_data_tv, contest_tv, surveys_tv,
                brief_data_tv;
        RecyclerView category_rv, special_rv;
        /* LinearLayout contests_ll, cont_sur_brief_ll, sur_brief_ll;*/
        ImageView contests_iv, surveys_iv,
                brief_data_iv;

        RelativeLayout servey_rl, brief_data_rl, top_rl, sur_brief_rl;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view_all_tv = itemView.findViewById(R.id.view_all_tv);
            category_tv = itemView.findViewById(R.id.category_tv);
            category_rv = itemView.findViewById(R.id.category_rv);
            special_rv = itemView.findViewById(R.id.special_rv);
            no_data_tv = itemView.findViewById(R.id.no_data_tv);
            top_rl = itemView.findViewById(R.id.top_rl);

          /*  contest_tv = itemView.findViewById(R.id.contest_tv);
            surveys_tv = itemView.findViewById(R.id.surveys_tv);
            brief_data_tv = itemView.findViewById(R.id.brief_data_tv);
            contests_ll = itemView.findViewById(R.id.contests_ll);
            servey_rl = itemView.findViewById(R.id.servey_rl);
            brief_data_rl = itemView.findViewById(R.id.brief_data_rl);
            sur_brief_rl = itemView.findViewById(R.id.sur_brief_rl);*/

        }
    }
}
