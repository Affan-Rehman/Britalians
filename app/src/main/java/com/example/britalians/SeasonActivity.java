package com.example.britalians;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class SeasonActivity extends FragmentActivity  implements VideoAdapter.OnVideoSelectedListener,VideoAdapter.OnVideoFocusedListener{
    ImageView selectedVideoThumbnail,logo,hd;
    TextView season_size,video_desc,content;
    RecyclerView mainRecyclerView;
    Serie serie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        if (savedInstanceState == null)
            serie = (Serie) getIntent().getSerializableExtra("serie");
        season_size = findViewById(R.id.season_size);
        selectedVideoThumbnail = findViewById(R.id.selected_video_thumbnail);
        video_desc = findViewById(R.id.video_desc);
        hd = findViewById(R.id.hd);
        content = findViewById(R.id.content);
        logo = findViewById(R.id.logo);
        mainRecyclerView = findViewById(R.id.main_recycler_view);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mainRecyclerView.setLayoutManager(verticalLayoutManager);

        SeasonAdapter rowAdapter = new SeasonAdapter(this, serie.seasons, this, this);
        mainRecyclerView.setAdapter(rowAdapter);
    }



    @Override
    public void onVideoSelected(Video video) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("video", video);
        startActivity(intent);
    }

    @Override
    public void onVideoFocused(Video video) {
        // Update ImageView and TextView based on the focused video
        Glide.with(this).load(serie.serie_page_image).into(logo);
        hd.setBackground(getResources().getDrawable(R.drawable.ic_hd));
        // For example, using Glide:
        Glide.with(this)
                .load(video.thumbnail169)
                .into(selectedVideoThumbnail);
        season_size.setText(video.releaseyear + "      " + video.rating + "      " + video.duration);
        video_desc.setText(video.description);
        content.setText(video.title);
    }
}