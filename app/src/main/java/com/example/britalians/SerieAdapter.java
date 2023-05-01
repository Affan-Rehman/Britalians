package com.example.britalians;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.SerieViewHolder> {

    List<Serie> serieList;
    VideoAdapter.OnVideoSelectedListener onVideoSelectedListener;
    VideoAdapter.OnVideoFocusedListener onVideoFocusedListener;
    MainFragment fragment;
    public SerieAdapter(MainFragment fragment,List<Serie> serieList, VideoAdapter.OnVideoSelectedListener onVideoSelectedListener, VideoAdapter.OnVideoFocusedListener onVideoFocusedListener) {
        this.serieList = serieList;
        this.fragment = fragment;
        this.onVideoSelectedListener = onVideoSelectedListener;
        this.onVideoFocusedListener = onVideoFocusedListener;
    }
    @NonNull
    @Override
    public SerieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new SerieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerieViewHolder holder, int position) {
        Serie serie = serieList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(serie.thumbnail_image)
                .into(holder.thumbnail);

        holder.itemView.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                holder.itemView.setBackgroundResource(R.drawable.video_selection_border);
                fragment.updateSelectedSerie(serie);
            } else {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        holder.itemView.setOnClickListener(view -> fragment.launchSeason(serie));
    }

    @Override
    public int getItemCount() {
        return serieList.size();
    }

    static class SerieViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;

        public SerieViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.video_thumbnail);
        }
    }
}
