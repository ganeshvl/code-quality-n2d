package com.news2day.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.news2day.Activity.LocationActivity;
import com.news2day.R;
import com.news2day.model.Districts.PojoThree;
import com.news2day.model.StringRefer;

import java.util.ArrayList;
import java.util.List;

import static com.news2day.Activity.LocationActivity.no_data_tx;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> implements Filterable {
    Context context;
    List<PojoThree> district_name;


    private static int checkedPosition = -1;
    StringRefer stringDistrict;
    public CityAdapter.OnClickListener listener;
    String language, name_str;
    ArrayList<String> name = new ArrayList<>();
    TextView noDataTx;

    public CityAdapter(Context context, List<PojoThree> name, StringRefer stringDistrict, String language) {
        this.context = context;
        this.district_name = name;
        this.stringDistrict = stringDistrict;
        this.language = language;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (language.equals("TELUGU")) {

            holder.tv_city.setText(district_name.get(position).getName().getTe());
            name_str = district_name.get(position).getName().getTe();
            name.add(name_str);
        } else if (language.equals("ENGLISH")) {

        /*    holder.tv_city.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);*/

            StringBuilder sb = new StringBuilder(district_name.get(position).getName().getEn());
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            holder.tv_city.setText(sb);

            name_str = district_name.get(position).getName().getEn();
            name.add(name_str);
        } else if (language.equals("HINDI")) {

            holder.tv_city.setText(district_name.get(position).getName().getHi());
            name_str = district_name.get(position).getName().getHi();
            name.add(name_str);
        }


        if (checkedPosition == -1) {
            holder.rd_city.setChecked(false);
        } else {
            if (checkedPosition == position) {
                holder.rd_city.setChecked(true);
                stringDistrict.district_name_str(String.valueOf(district_name.get(position).getName()), district_name.get(position).get_id());
            } else {
                holder.rd_city.setChecked(false);
            }
        }

        holder.rl_city.setOnClickListener(v -> {
            holder.rd_city.setChecked(true);
            stringDistrict.district_name_str(String.valueOf(district_name.get(position).getName()), district_name.get(position).get_id());

            if (checkedPosition != position) {
                notifyItemChanged(checkedPosition);
                checkedPosition = position;
            }
        });
        holder.rd_city.setOnClickListener(v -> {
            holder.rd_city.setChecked(true);
            stringDistrict.district_name_str(String.valueOf(district_name.get(position).getName()), district_name.get(position).get_id());

            if (checkedPosition != position) {
                notifyItemChanged(checkedPosition);
                checkedPosition = position;
            }
        });


    }


    @Override
    public int getItemCount() {
        return district_name.size();
    }

    public void SetOnClickListener(CityAdapter.OnClickListener onClickListener) {
        listener = onClickListener;
    }

    public interface OnClickListener {
        void Onclick(View v, int adapterPosition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RadioButton rd_city;
        TextView tv_city;
        LinearLayout rl_city;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rd_city = itemView.findViewById(R.id.rd_city);
            tv_city = itemView.findViewById(R.id.tv_city);
            rl_city = itemView.findViewById(R.id.rl_city);

            rd_city.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.Onclick(v, getAdapterPosition());
            }
        }
    }

    private Filter fRecords;

    @Override
    public Filter getFilter() {
       /* if (fRecords == null) {
            fRecords = new RecordFilter();
            notifyDataSetChanged();
        }*/
        return RecordFilter;
    }

    public Filter RecordFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {


            List<PojoThree> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(district_name);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (PojoThree item : district_name)
                    if (item.getName().getEn().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                        /*LocationActivity.no_data_tx.setVisibility(View.GONE);
                        LocationActivity.rv_city.setVisibility(View.VISIBLE);*/
                    } else if (item.getName().getHi().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                        /*LocationActivity.no_data_tx.setVisibility(View.GONE);
                        LocationActivity.rv_city.setVisibility(View.VISIBLE);*/
                    } else if (item.getName().getTe().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                     /*   LocationActivity.rv_city.setVisibility(View.VISIBLE);*/
                    }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

           /* if(district_name!=null){
                district_name=((List) results.values);
            }*/
            district_name = (ArrayList<PojoThree>) results.values;
            notifyDataSetChanged();
         //   district_name.clear();
        }
    };
   /* private class RecordFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            //Implement filter logic
            // if edittext is null return the actual list
            if (constraint == null || constraint.length() == 0) {
                //No need for filter
                results.values = district_name;
                results.count = district_name.size();
                notifyDataSetChanged();

            } else {
                //Need Filter
                // it matches the text  entered in the edittext and set the data in adapter list
                ArrayList<String> fRecords = new ArrayList<String>();

                for (String s : name) {
                    if (s.toUpperCase().trim().contains(constraint.toString().toUpperCase().trim())) {
                        fRecords.add(s);
                    }
                }
                results.values = fRecords;
                results.count = fRecords.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            //it set the data from filter to adapter list and refresh the recyclerview adapter
            name = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }*/
}
