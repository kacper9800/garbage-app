package com.garbage.district;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.garbage.R;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DistrictListAdapter extends RecyclerView.Adapter<DistrictListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<String> items;
    private List<String> itemsFiltered;
    private ClickListener clickListener;

    public DistrictListAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
        this.itemsFiltered = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.district_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(itemsFiltered.get(position));
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
                List<String> filtered = items.stream().filter(s -> {
                    return Pattern.compile(Pattern.quote(constraint.toString()), Pattern.CASE_INSENSITIVE).matcher(s).find();
                }).collect(Collectors.toList());
                FilterResults results = new FilterResults();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemsFiltered = (List<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void sortList() {
        itemsFiltered = itemsFiltered.stream().sorted().collect(Collectors.toList());
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView card;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            card.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onItemClick(itemsFiltered.get(getAdapterPosition()));
                }
            });
            name = itemView.findViewById(R.id.name);
        }
    }

    public interface ClickListener {
        void onItemClick(String value);
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
