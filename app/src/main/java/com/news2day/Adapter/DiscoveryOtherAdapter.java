package com.news2day.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.news2day.Activity.StartUpActivity;
import com.news2day.R;
import com.news2day.model.DiscoveryPojos.DataPojo;
import com.news2day.model.DiscoveryPojos.NamePojo;
import com.news2day.model.DiscoveryPojos.SubCategoryDetailsPojo;

import java.util.ArrayList;
import java.util.List;

public class DiscoveryOtherAdapter extends RecyclerView.Adapter<DiscoveryOtherAdapter.ViewHolder> {
    Context context;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Integer> image = new ArrayList<>();
    private static int checkedPosition = -1;
    List<DataPojo> namePojo;
    String language;

    public DiscoveryOtherAdapter(Context context, List<DataPojo> namePojo, String language) {
        this.context = context;
        this.language = language;
        this.namePojo = namePojo;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.discovery_other_adapter_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (language.equals("ENGLISH")) {
            holder.brief_data_tv.setText(namePojo.get(1).getName().getEn());
            holder.brief_data_tv.setText(namePojo.get(2).getName().getEn());
        } else if (language.equals("TELUGU")) {
            holder.brief_data_tv.setText(namePojo.get(1).getName().getTe());
            holder.brief_data_tv.setText(namePojo.get(2).getName().getTe());
        } else if (language.equals("HINDI")) {
            holder.brief_data_tv.setText(namePojo.get(1).getName().getHi());
            holder.brief_data_tv.setText(namePojo.get(2).getName().getHi());
        }
     /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, StartUpActivity.class);
                context.startActivity(intent);

            }
        });*/
    }


    @Override
    public int getItemCount() {
        return namePojo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView brief_data_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brief_data_tv = itemView.findViewById(R.id.brief_data_tv);
        }
    }


}
