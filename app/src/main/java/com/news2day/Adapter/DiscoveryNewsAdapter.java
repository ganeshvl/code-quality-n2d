package com.news2day.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.news2day.Activity.StartUpActivity;
import com.news2day.R;
import com.news2day.model.DiscoveryPojos.SubCategoryDetailsPojo;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryNewsAdapter extends RecyclerView.Adapter<DiscoveryNewsAdapter.ViewHolder> {
    Context context;
    List<SubCategoryDetailsPojo> name_selected;
    ArrayList<Integer> image = new ArrayList<>();
    private static int checkedPosition = -1;

    String language, tilte;
    TextView textView;

    public DiscoveryNewsAdapter(Context context, List<SubCategoryDetailsPojo> name_selected, String language) {
        this.context = context;
        this.name_selected = name_selected;
        this.language = language;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_recycler_adapter_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load("http://18.118.90.146:5001/" + name_selected.get(position).getImage()).error(R.mipmap.ic_launcher).into(holder.news_iv);


        if (language.equals("ENGLISH")) {
            tilte = name_selected.get(position).getName().getEn();
            holder.select_tv.setText(tilte);

        } else if (language.equals("TELUGU")) {
            tilte = name_selected.get(position).getName().getTe();
            holder.select_tv.setText(tilte);

        } else if (language.equals("HINDI")) {

            tilte = name_selected.get(position).getName().getHi();
            holder.select_tv.setText(tilte);
        }

        holder.sub_cat_main_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, StartUpActivity.class);
                intent.putExtra("category_id", name_selected.get(position).getCategory_id());
                intent.putExtra("sub_category_id", name_selected.get(position).get_id());

                if (language.equals("ENGLISH")) {
                    intent.putExtra("title", name_selected.get(position).getName().getEn());

                } else if (language.equals("TELUGU")) {
                    intent.putExtra("title", name_selected.get(position).getName().getTe());

                } else if (language.equals("HINDI")) {
                    intent.putExtra("title", name_selected.get(position).getName().getHi());
                }

                context.startActivity(intent);


            }
        });
    }


    @Override
    public int getItemCount() {
        return name_selected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView select_tv;
        ImageView news_iv;
        LinearLayout sub_cat_main_ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            select_tv = itemView.findViewById(R.id.select_tv);
            news_iv = itemView.findViewById(R.id.news_iv);
            sub_cat_main_ll = itemView.findViewById(R.id.sub_cat_main_ll);
        }
    }


}
