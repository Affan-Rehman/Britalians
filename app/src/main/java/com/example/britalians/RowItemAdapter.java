package com.example.britalians;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RowItemAdapter extends RecyclerView.Adapter<RowItemAdapter.ViewHolder> {
    List<RowItems> rowItems;
    Context context;

    public RowItemAdapter(List<RowItems> rowItems, Context context) {
        this.rowItems = rowItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RowItems rowItem = rowItems.get(position);
        holder.bind(rowItem);
    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        View focusIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            focusIndicator = itemView.findViewById(R.id.row_item_focus_indicator);
            itemView.setFocusable(true);
            itemView.setFocusableInTouchMode(true);
            image = itemView.findViewById(R.id.details_thumbnail);

            itemView.setOnFocusChangeListener((v, hasFocus) -> {
                focusIndicator.setVisibility(hasFocus ? View.VISIBLE : View.INVISIBLE);
            });
        }

        public void bind(RowItems rowItem) {
            Glide.with(context).load(rowItem.details_thumbnail).into(image);
            itemView.setTag(rowItem);
        }
    }
}
