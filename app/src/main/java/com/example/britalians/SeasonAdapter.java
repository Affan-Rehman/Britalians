package com.example.britalians;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder> {

    List<Season> seasonList;
    Context context;
    VideoAdapter.OnVideoSelectedListener onVideoSelectedListener;
    VideoAdapter.OnVideoFocusedListener onVideoFocusedListener;

    public SeasonAdapter(Context context, List<Season> seasonList, VideoAdapter.OnVideoSelectedListener onVideoSelectedListener, VideoAdapter.OnVideoFocusedListener onVideoFocusedListener) {
        this.seasonList = seasonList;
        this.context = context;
        this.onVideoSelectedListener = onVideoSelectedListener;
        this.onVideoFocusedListener = onVideoFocusedListener;
    }

    @Override
    public SeasonViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new SeasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder( SeasonViewHolder holder, int position) {
        Season season = seasonList.get(position);
        holder.text.setText(season.title);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(new VideoAdapter(season.videos, onVideoSelectedListener, onVideoFocusedListener));
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    static class SeasonViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView text;

        public SeasonViewHolder( View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.row_title);
            recyclerView = itemView.findViewById(R.id.row_recycler_view);
        }
    }
}
