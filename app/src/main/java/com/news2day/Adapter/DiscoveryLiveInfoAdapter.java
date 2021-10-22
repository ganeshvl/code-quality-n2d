package com.news2day.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.news2day.Activity.StartUpActivity;
import com.news2day.Activity.WebPageActivity;
import com.news2day.R;
import com.news2day.model.DiscoveryPojos.NamePojo;
import com.news2day.model.DiscoveryPojos.SubCategoryDetailsPojo;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryLiveInfoAdapter extends RecyclerView.Adapter<DiscoveryLiveInfoAdapter.ViewHolder> {
    Context context;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> image = new ArrayList<>();
    private static int checkedPosition = -1;
    List<SubCategoryDetailsPojo> subcat_data;
    String language,tilte;
    NamePojo s;

    public DiscoveryLiveInfoAdapter(Context context, List<SubCategoryDetailsPojo> subcat_data, String language, NamePojo s) {
        this.context = context;
        this.subcat_data = subcat_data;
        this.language = language;
        this.s = s;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.live_info_recycler_adapter_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load("http://18.118.90.146:5001/" + subcat_data.get(position).getImage()).error(R.mipmap.ic_launcher).into(holder.liv_img);

        if (language.equals("ENGLISH")) {
           tilte=subcat_data.get(position).getName().getEn();
                   holder.liv_title_tx.setText(tilte);

        } else if (language.equals("TELUGU")) {
            tilte= subcat_data.get(position).getName().getTe();
                    holder.liv_title_tx.setText(tilte);

        } else if (language.equals("HINDI")) {

            tilte= subcat_data.get(position).getName().getHi();
                    holder.liv_title_tx.setText(tilte);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s.getEn().equalsIgnoreCase("Live Info")){
                    Intent intent = new Intent(context, WebPageActivity.class);
                    intent.putExtra("category_id",subcat_data.get(position).getCategory_id());
                    intent.putExtra("url",subcat_data.get(position).getUrl());
                    intent.putExtra("title",tilte);
                    context.startActivity(intent);
                }
                else{
                    Intent intent = new Intent(context, StartUpActivity.class);
                    intent.putExtra("category_id",subcat_data.get(position).getCategory_id());
                    intent.putExtra("sub_category_id",subcat_data.get(position).get_id());
                    intent.putExtra("title",tilte);
                    context.startActivity(intent);

                }
                if (language.equals("ENGLISH")) {
                    tilte=subcat_data.get(position).getName().getEn();
                    holder.liv_title_tx.setText(tilte);
                    if(s.getEn().equals("Live Info")){
                        Intent intent = new Intent(context, WebPageActivity.class);
                        intent.putExtra("category_id",subcat_data.get(position).getCategory_id());
                        intent.putExtra("url",subcat_data.get(position).getUrl());
                        intent.putExtra("title",tilte);
                        context.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(context, StartUpActivity.class);
                        intent.putExtra("category_id",subcat_data.get(position).getCategory_id());
                        intent.putExtra("sub_category_id",subcat_data.get(position).get_id());
                        intent.putExtra("title",tilte);
                        context.startActivity(intent);

                    }

                } else if (language.equals("TELUGU")) {
                    tilte= subcat_data.get(position).getName().getTe();
                    holder.liv_title_tx.setText(tilte);
                    if(s.getTe().equals("ప్రత్యక్ష సమాచారం")){
                        Intent intent = new Intent(context, WebPageActivity.class);
                        intent.putExtra("category_id",subcat_data.get(position).getCategory_id());
                        intent.putExtra("url",subcat_data.get(position).getUrl());
                        intent.putExtra("title",tilte);
                        context.startActivity(intent);
                    }else{
                        Intent intent = new Intent(context, StartUpActivity.class);
                        intent.putExtra("category_id",subcat_data.get(position).getCategory_id());
                        intent.putExtra("sub_category_id",subcat_data.get(position).get_id());
                        intent.putExtra("title",tilte);
                        context.startActivity(intent);

                    }
                } else if (language.equals("HINDI")) {

                    tilte= subcat_data.get(position).getName().getHi();
                    holder.liv_title_tx.setText(tilte);
                    if(s.getHi().equals("लाइव जानकारी")){
                        Intent intent = new Intent(context, WebPageActivity.class);
                        intent.putExtra("category_id",subcat_data.get(position).getCategory_id());
                        intent.putExtra("url",subcat_data.get(position).getUrl());
                        intent.putExtra("title",tilte);
                        context.startActivity(intent);
                    }else{
                        Intent intent = new Intent(context, StartUpActivity.class);
                        intent.putExtra("category_id",subcat_data.get(position).getCategory_id());
                        intent.putExtra("sub_category_id",subcat_data.get(position).get_id());
                        intent.putExtra("title",tilte);
                        context.startActivity(intent);

                    }
                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return subcat_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView liv_img;
        TextView liv_title_tx;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            liv_title_tx = itemView.findViewById(R.id.liv_title_tx);
            liv_img = itemView.findViewById(R.id.liv_img);

        }
    }


}
