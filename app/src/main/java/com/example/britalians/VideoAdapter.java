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

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    List<Video> videoList;
     OnVideoSelectedListener onVideoSelectedListener;
    OnVideoFocusedListener onVideoFocusedListener;

    public VideoAdapter(List<Video> videoList, OnVideoSelectedListener onVideoSelectedListener, OnVideoFocusedListener onVideoFocusedListener) {
        this.videoList = videoList;
        this.onVideoSelectedListener = onVideoSelectedListener;
        this.onVideoFocusedListener = onVideoFocusedListener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videoList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(video.thumbnail169)
                .into(holder.thumbnail);

        holder.itemView.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                holder.itemView.setBackgroundResource(R.drawable.video_selection_border);
                onVideoFocusedListener.onVideoFocused(video);
            } else {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        holder.itemView.setOnClickListener(view -> onVideoSelectedListener.onVideoSelected(video));
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.video_thumbnail);
        }
    }

    public interface OnVideoSelectedListener {
        void onVideoSelected(Video video);
    }

    public interface OnVideoFocusedListener {

        void onVideoFocused(Video video);
    }
}