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


import com.news2day.Activity.LocationActivity;
import com.news2day.Activity.StartUpActivity;
import com.news2day.R;

import java.util.ArrayList;

public class StartupsAdapter extends RecyclerView.Adapter<StartupsAdapter.ViewHolder> {
    Context context;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> image = new ArrayList<>();

    public StartupsAdapter(Context context, ArrayList<String> name, ArrayList<Integer> image) {
        this.context = context;
        this.name = name;
        this.image = image;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_startup, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.iv_language.setImageResource(image.get(position));
        holder.tv_langugae.setText(name.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, StartUpActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_language;
        TextView tv_langugae;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_language = itemView.findViewById(R.id.iv_image);
            tv_langugae = itemView.findViewById(R.id.tv_startup);

        }
    }
}
