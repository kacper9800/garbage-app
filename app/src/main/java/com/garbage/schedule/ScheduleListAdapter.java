package com.garbage.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.garbage.R;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Schedule> items;
    private List<Schedule> itemsFiltered;

    public ScheduleListAdapter(Context context, List<Schedule> items) {
        this.context = context;
        this.items = items;
        this.itemsFiltered = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.schedule_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule item = itemsFiltered.get(position);
        holder.icon.setImageResource(item.getGarbageType().getResId());
        holder.date.setText(item.getDate());
        holder.type.setText(item.getGarbageType().getName());
        holder.street.setText(item.getStreet());
    }

    @Override
    public int getItemCount() {
        return itemsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Schedule> filtered = items.stream().filter(s -> {
                    return Pattern.compile(Pattern.quote(constraint.toString()), Pattern.CASE_INSENSITIVE).matcher(s.getStreet()).find();
                }).collect(Collectors.toList());
                FilterResults results = new FilterResults();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemsFiltered = (List<Schedule>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView date;
        TextView type;
        TextView street;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            date = itemView.findViewById(R.id.date_text);
            type = itemView.findViewById(R.id.type_text);
            street = itemView.findViewById(R.id.street_text);
        }
    }
}
