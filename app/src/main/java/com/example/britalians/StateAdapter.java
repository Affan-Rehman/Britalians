package com.example.britalians;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateHolder> {

    List<RowItems> rowItemsList;
    GlobalFragment fragment;
    public StateAdapter(List<RowItems> rowItemsList,GlobalFragment fragment) {
        this.rowItemsList = rowItemsList;
        this.fragment = fragment;
    }

    
    @Override
    public StateHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new StateHolder(view);
    }

    @Override
    public void onBindViewHolder( StateHolder holder, int position) {
        RowItems rowItem = rowItemsList.get(position);
        Glide.with(holder.itemView)
                .load(rowItem.details_thumbnail)
                .into(holder.imageView);

        holder.itemView.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                holder.itemView.setBackgroundResource(R.drawable.video_selection_border);
                fragment.updateFocusedRowItem(rowItem);
            } else {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), GlobalItemActivity.class);
            intent.putExtra("rowitem", rowItem);
            holder.itemView.getContext().startActivity(intent);


        });
    }

    @Override
    public int getItemCount() {
        return rowItemsList.size();
    }

    static class StateHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        View focusBorder;

        public StateHolder( View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            focusBorder = itemView.findViewById(R.id.focus_border);
        }
    }
}