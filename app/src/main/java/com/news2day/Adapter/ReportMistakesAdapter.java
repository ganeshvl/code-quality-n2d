package com.news2day.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.news2day.R;
import com.news2day.model.RepostsPojo.ReportsPojoTwo;
import com.news2day.model.StringRefer;

import java.util.List;

public class ReportMistakesAdapter extends RecyclerView.Adapter<ReportMistakesAdapter.ViewHolder> {
    Context context;
    List<ReportsPojoTwo> name;
    private static int checkedPosition = -1;
    StringRefer stringRefer;
    public ReportMistakesAdapter.OnClickListener listener;

    public ReportMistakesAdapter(Context context, List<ReportsPojoTwo> name, StringRefer stringRefer) {
        this.context = context;
        this.name = name;
        this.stringRefer = stringRefer;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.reports_adapter_lay_out, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        StringBuilder sb = new StringBuilder(name.get(position).getName());
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        holder.cb_mistakes.setText(sb);


        if (checkedPosition == -1) {
            holder.cb_mistakes.setChecked(false);
        } else {
            if (checkedPosition == position) {
                holder.cb_mistakes.setChecked(true);
            } else {
                holder.cb_mistakes.setChecked(false);
            }
        }

        holder.cb_mistakes.setOnClickListener(v -> {
            holder.cb_mistakes.setChecked(true);
            stringRefer.district_name_str(name.get(position).getName(), name.get(position).get_id());
            if (name.get(position).getName().equalsIgnoreCase("others")) {
                VerticalSliderAdapter.others_ed_tx.setVisibility(View.VISIBLE);
            } else {
                VerticalSliderAdapter.others_ed_tx.setVisibility(View.GONE);
            }
            /* stringRefer.district_name_str(name.get(position).getName());*/
            if (checkedPosition != position) {
                notifyItemChanged(checkedPosition);
                checkedPosition = position;
            }
        });


    }


    @Override
    public int getItemCount() {
        return name.size();
    }

    public void SetOnClickListener(ReportMistakesAdapter.OnClickListener onClickListener) {
        listener = onClickListener;
    }

    public interface OnClickListener {
        void Onclick(View v, int adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckBox cb_mistakes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cb_mistakes = itemView.findViewById(R.id.cb_mistakes);
            cb_mistakes.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.Onclick(v, getAdapterPosition());
            }
        }
    }


}
