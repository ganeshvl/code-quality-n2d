package com.news2day.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.news2day.Activity.ContestsDetailsActivity;
import com.news2day.Activity.LocationActivity;
import com.news2day.Activity.LoginActivity;
import com.news2day.R;
import com.news2day.UserDetails;
import com.news2day.model.SharePostsPojos.SharePostPojoOne;
import com.news2day.model.VerticalViewPojos.PostsPojoTwo;
import com.news2day.retrofit.RetrofitInstance;
import com.news2day.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContestsAdapter extends RecyclerView.Adapter<ContestsAdapter.ViewHolder> {
    Context context;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> image = new ArrayList<>();
    private static int checkedPosition = -1;
    List<PostsPojoTwo> data;
    String img, contest_heading, id,
            contest_description;
    UserDetails userDetails;
    RetrofitService service;
    String title, category_id;
    String[] imgs;
    List<String> wordList;
    public ContestsAdapter(Context context, List<PostsPojoTwo> data, String title, String category_id) {
        this.context = context;
        this.data = data;
        this.title = title;
        this.category_id = category_id;


    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.contests_recycler_adapter_lay_out, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        userDetails = new UserDetails(context);
        service = RetrofitInstance.createService(RetrofitService.class);
        imgs = data.get(position).getImage();
       /* wordList = Arrays.asList(imgs);
        if (wordList.size()>0 ) {

        } else{
            Toast.makeText(context, "array is empty", Toast.LENGTH_SHORT).show();
            wordList=null;
        }*/
        for (int i = 0; i < imgs.length; i++) {
               /* if(imgs[i]==""){
                    imgs[i]=null;
                }else{

                }*/
            img =  imgs[i];

        }


        contest_heading = data.get(position).getName();
        id = data.get(position).get_id();
        contest_description = data.get(position).getDescription();

        Glide.with(context).load("http://18.118.90.146:5001/" +img).error(R.mipmap.ic_launcher).into(holder.iv_pro_pic);

        holder.heading_tx_contests.setText(contest_heading);
        holder.des_tx_contests.setText(contest_description);

        holder.know_more_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContestsDetailsActivity.class);
                intent.putExtra("contest_img", data.get(position).getImage());
                intent.putExtra("contest_heading", data.get(position).getName());
                intent.putExtra("contest_description", data.get(position).getDescription());
                intent.putExtra("id", data.get(position).get_id());
                intent.putExtra("url", data.get(position).getUrl());
                intent.putExtra("title", title);
                intent.putExtra("category_id", category_id);
                context.startActivity(intent);
            }
        });
        holder.share_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(context);
                pd.setMessage("Loading.....");
                pd.show();
                pd.setCancelable(true);
                service.share_posts(userDetails.getUserId(), id).enqueue(new Callback<SharePostPojoOne>() {
                    @Override
                    public void onResponse(Call<SharePostPojoOne> call, Response<SharePostPojoOne> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            SharePostPojoOne sharePostPojoOne = response.body();

                            if (sharePostPojoOne.getStatus() != null) {
                                if (!userDetails.isLoggedIn()) {
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    context.startActivity(intent);
                                    Toast.makeText(context, R.string.you_must, Toast.LENGTH_SHORT).show();
                                } else {
                                    if (sharePostPojoOne.getStatus().equalsIgnoreCase("200")) {
                                        pd.dismiss();
                                       /* shares_count = shares_count + 1;
                                        share_tv.setText(String.valueOf(shares_count));*/
                                        Intent intent = new Intent(Intent.ACTION_SEND);
                                        intent.setType("text/plain");
                                        intent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                                        intent.putExtra(Intent.EXTRA_TEXT, "Hi,Friends This Post is Useful For all" + "\n" + sharePostPojoOne.getData().get_id() + "\n" + data.get(position).getName() + "\n" + data.get(position).getDescription());
                                        context.startActivity(Intent.createChooser(intent, "share via"));
                                        Toast.makeText(context, R.string.post_successfull, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SharePostPojoOne> call, Throwable t) {

                    }
                });

            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView iv_pro_pic, share_but;
        TextView heading_tx_contests,
                des_tx_contests,
                know_more_tx;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_pro_pic = itemView.findViewById(R.id.iv_pro_pic);
            heading_tx_contests = itemView.findViewById(R.id.heading_tx_contests);
            des_tx_contests = itemView.findViewById(R.id.des_tx_contests);
            know_more_tx = itemView.findViewById(R.id.know_more_tx);
            share_but = itemView.findViewById(R.id.share_but);


        }
    }


}
