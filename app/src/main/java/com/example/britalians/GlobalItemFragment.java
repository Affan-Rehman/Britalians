package com.example.britalians;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class GlobalItemFragment extends Fragment implements VideoAdapter.OnVideoSelectedListener, VideoAdapter.OnVideoFocusedListener  {
    Row row;
    ImageView selectedVideoThumbnail,logo;
    TextView video_desc,content;
    public GlobalItemFragment() {
        // Required empty public constructor
    }
    public GlobalItemFragment(Row row) {
        this.row = row;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_global_item, container, false);
        RecyclerView videoRecyclerView =root.findViewById(R.id.row_itemsRecyclerView);
        selectedVideoThumbnail= root.findViewById(R.id.top_right);
        logo = root.findViewById(R.id.logo1);
        content = root.findViewById(R.id.content);
        video_desc = root.findViewById(R.id.video_desc);
        videoRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        videoRecyclerView.setLayoutManager(layoutManager);

        VideoAdapter videoAdapter = new VideoAdapter(row.items, this, this);
        videoRecyclerView.setAdapter(videoAdapter);
        return root;
    }

    @Override
    public void onVideoSelected(Video video) {
        Intent intent = new Intent(getContext(), MovieActivity.class);
        intent.putExtra("video", video);
        startActivity(intent);
    }

    @Override
    public void onVideoFocused(Video video) {
        Glide.with(this).load(video.serieslogo).into(logo);
        // For example, using Glide:
        Glide.with(this)
                .load(video.thumbnail169)
                .into(selectedVideoThumbnail);
        video_desc.setText(video.description);
        content.setText(video.title);
    }
}