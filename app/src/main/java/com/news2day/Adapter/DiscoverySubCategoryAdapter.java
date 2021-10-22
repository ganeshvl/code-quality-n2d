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

import com.news2day.Activity.StatesActivity;
import com.news2day.R;
import com.news2day.UserDetails;

import java.util.ArrayList;

public class DiscoverySubCategoryAdapter extends RecyclerView.Adapter<DiscoverySubCategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> image = new ArrayList<>();
    UserDetails userDetails;

    public DiscoverySubCategoryAdapter(Context context, ArrayList<String> name, ArrayList<Integer> image) {
        this.context = context;
        this.name = name;
        this.image = image;
        userDetails = new UserDetails(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_language, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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

            iv_language = itemView.findViewById(R.id.iv_language);
            tv_langugae = itemView.findViewById(R.id.tv_langugae);

        }
    }
}
