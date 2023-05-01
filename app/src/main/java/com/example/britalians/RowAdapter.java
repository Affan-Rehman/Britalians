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


public class RowAdapter extends RecyclerView.Adapter<RowAdapter.RowViewHolder> {

    List<Row> rowList;
    Context context;
    MainFragment fragment;
    VideoAdapter.OnVideoSelectedListener onVideoSelectedListener;
    VideoAdapter.OnVideoFocusedListener onVideoFocusedListener;
    SerieList serieList;

    public static final int TYPE_VIDEO_ROW = 0;
    public static final int TYPE_SERIE_LIST_ROW = 1;

    public RowAdapter(MainFragment fragment,List<Row> rowList, Context context,VideoAdapter.OnVideoSelectedListener onVideoSelectedListener, VideoAdapter.OnVideoFocusedListener onVideoFocusedListener, SerieList serieList) {
        this.rowList = rowList;
        this.context = context;
        this.fragment = fragment;
        this.onVideoSelectedListener = onVideoSelectedListener;
        this.onVideoFocusedListener = onVideoFocusedListener;
        this.serieList = serieList;
    }


    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_SERIE_LIST_ROW : TYPE_VIDEO_ROW;
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new RowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_SERIE_LIST_ROW) {
            holder.text.setText("Series");
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.setAdapter(new SerieAdapter(fragment, serieList.series, onVideoSelectedListener, onVideoFocusedListener));
        } else {
            Row row = rowList.get(position - 1); // Subtract 1 because the first position is the SerieList row
            holder.text.setText(row.title);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.setAdapter(new VideoAdapter(row.items, onVideoSelectedListener, onVideoFocusedListener));
        }
    }

    @Override
    public int getItemCount() {
        return rowList.size() + 1; // Add 1 for the SerieList row
    }
    static class RowViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView text;

        public RowViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.row_title);
            recyclerView = itemView.findViewById(R.id.row_recycler_view);
        }
    }
}
