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
import com.news2day.Activity.LocationActivity;
import com.news2day.R;
import com.news2day.model.States.StatePojoThree;

import java.util.ArrayList;
import java.util.List;

public class StatesAdapter extends RecyclerView.Adapter<StatesAdapter.ViewHolder> {
    Context context;
    List<StatePojoThree> statePojoFours;
    String state;
    ArrayList<Integer> image = new ArrayList<>();
    String language,lang,id;

    public StatesAdapter(Context context, List<StatePojoThree> statePojoFours, String language, ArrayList<Integer> image,String id) {
        this.context = context;
        this.statePojoFours = statePojoFours;
        this.image = image;
        this.language = language;
        this.id = id;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_state, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // holder.iv_language.setImageResource(image.get(position));
        if (language.equals("TELUGU")) {
            lang="TELUGU";
            holder.tv_langugae.setText(statePojoFours.get(position).getName().getTe());
        } else if (language.equals("ENGLISH")) {
            lang="ENGLISH";
            holder.tv_langugae.setText(statePojoFours.get(position).getName().getEn());
        } else if (language.equals("HINDI")) {
            lang="HINDI";
            holder.tv_langugae.setText(statePojoFours.get(position).getName().getHi());
        }

        Glide.with(context).load("http://18.118.90.146:5001/"+statePojoFours.get(position).getImage()).error(R.mipmap.ic_launcher).into(holder.iv_language);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, LocationActivity.class);
                intent.putExtra("state_id",statePojoFours.get(position).get_id());
                intent.putExtra("language",language);
                intent.putExtra("id",id);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return statePojoFours.size();
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
