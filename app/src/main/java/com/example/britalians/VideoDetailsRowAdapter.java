package com.example.britalians;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoDetailsRowAdapter extends RecyclerView.Adapter<VideoDetailsRowAdapter.ViewHolder> {

    List<VideoDetailsRow> videoDetailsRows;
    Context context;
    public VideoDetailsRowAdapter(List<VideoDetailsRow> videoDetailsRows, Context context) {
        this.videoDetailsRows = videoDetailsRows;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_details_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VideoDetailsRow videoDetailsRow = videoDetailsRows.get(position);
        List<RowItems> rowItems = videoDetailsRow.row_items;

        RowItemAdapter rowItemAdapter = new RowItemAdapter(rowItems,context);
        holder.rowItemsRecyclerView.setLayoutManager(new LinearLayoutManager(holder.rowItemsRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        holder.rowItemsRecyclerView.setAdapter(rowItemAdapter);
    }

    @Override
    public int getItemCount() {
        return videoDetailsRows.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rowItemsRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rowItemsRecyclerView = itemView.findViewById(R.id.row_itemsRecyclerViewok);
        }
    }
}

