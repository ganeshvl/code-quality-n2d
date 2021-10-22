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
import com.news2day.model.SubCategoryPojos.SubCatMyPojo;
import com.news2day.model.SubCategoryPojos.SubCatNamePojo;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    Context context;

    ArrayList<Integer> image = new ArrayList<>();
    private static int checkedPosition = -1;
    SubCatMyPojo name_selected;
    String language,s,category_id,tit;

    public NewsAdapter(Context context, SubCatMyPojo name_selected, String language, String s, String category_id) {
        this.context = context;
        this.name_selected = name_selected;
        this.language = language;
        this.s = s;
        this.category_id = category_id;

    }
  /*  Intent intent = new Intent(context, WebPageActivity.class);
                    intent.putExtra("category_id",subcat_data.get(position).getCategory_id());
                    intent.putExtra("url",subcat_data.get(position).getUrl());
                    intent.putExtra("title",tilte);
                    context.startActivity(intent);*/
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_recycler_adapter_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load("http://18.118.90.146:5001/" + name_selected.getData().get(position).getImage()).error(R.mipmap.ic_launcher).into(holder.news_iv);

        if (language.equals("ENGLISH")) {
            holder.select_tv.setText(name_selected.getData().get(position).getName().getEn());

        } else if (language.equals("TELUGU")) {
            holder.select_tv.setText(name_selected.getData().get(position).getName().getTe());

        } else if (language.equals("HINDI")) {

            holder.select_tv.setText(name_selected.getData().get(position).getName().getHi());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (language.equals("ENGLISH")) {
                    tit=name_selected.getData().get(position).getName().getEn();
                    holder.select_tv.setText(tit);
                    if(s.equalsIgnoreCase("Live Info")){
                        Intent intent = new Intent(context, WebPageActivity.class);
                        intent.putExtra("category_id",name_selected.getData().get(position).getCategory_id());
                        intent.putExtra("url","https://www.livemint.com/news/india/for-it-professionals-govt-employment-survey-brings-in-more-good-news-11632734139860.html");
                        intent.putExtra("title",tit);
                        context.startActivity(intent);
                    }else {
                        Intent intent = new Intent(context, StartUpActivity.class);
                        intent.putExtra("category_id",name_selected.getData().get(position).getCategory_id());
                        intent.putExtra("sub_category_id",name_selected.getData().get(position).get_id());
                        intent.putExtra("title",tit);
                        context.startActivity(intent);
                    }
                } else if (language.equals("TELUGU")) {
                    tit=name_selected.getData().get(position).getName().getTe();
                    holder.select_tv.setText(tit);
                    if(s.equals("ప్రత్యక్ష సమాచారం")){
                        Intent intent = new Intent(context, WebPageActivity.class);
                        intent.putExtra("category_id",name_selected.getData().get(position).getCategory_id());
                        intent.putExtra("url","https://www.livemint.com/news/india/for-it-professionals-govt-employment-survey-brings-in-more-good-news-11632734139860.html");
                        intent.putExtra("title",tit);
                        context.startActivity(intent);
                    }else {
                        Intent intent = new Intent(context, StartUpActivity.class);
                        intent.putExtra("category_id",name_selected.getData().get(position).getCategory_id());
                        intent.putExtra("sub_category_id",name_selected.getData().get(position).get_id());
                        intent.putExtra("title",tit);
                        context.startActivity(intent);
                    }

                } else if (language.equals("HINDI")) {

                    tit=name_selected.getData().get(position).getName().getHi();
                    holder.select_tv.setText(tit);
                    if(s.equals("लाइव जानकारी")){
                        Intent intent = new Intent(context, WebPageActivity.class);
                        intent.putExtra("category_id",name_selected.getData().get(position).getCategory_id());
                        intent.putExtra("url","https://www.livemint.com/news/india/for-it-professionals-govt-employment-survey-brings-in-more-good-news-11632734139860.html");
                        intent.putExtra("title",tit);
                        context.startActivity(intent);
                    }else {
                        Intent intent = new Intent(context, StartUpActivity.class);
                        intent.putExtra("category_id",name_selected.getData().get(position).getCategory_id());
                        intent.putExtra("sub_category_id",name_selected.getData().get(position).get_id());
                        intent.putExtra("title",tit);
                        context.startActivity(intent);
                    }
                }




            }
        });
    }


    @Override
    public int getItemCount() {
        return name_selected.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView select_tv;
        ImageView news_iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            select_tv = itemView.findViewById(R.id.select_tv);
            news_iv = itemView.findViewById(R.id.news_iv);
        }
    }


}
