package com.example.britalians;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Objects;


public class MainFragment extends Fragment implements VideoAdapter.OnVideoSelectedListener,VideoAdapter.OnVideoFocusedListener {

    Context context;
    RowList rows;
    RecyclerView mainRecyclerView;
    ImageView selectedVideoThumbnail,logo,hd;
    TextView season_size,video_desc,content;

    public MainFragment() {
        // Required empty public constructor
    }

    public MainFragment(Context context, RowList rows) {
        this.context = context;
        this.rows = rows;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_browse, container, false);
        season_size = view.findViewById(R.id.season_size);
        selectedVideoThumbnail = view.findViewById(R.id.selected_video_thumbnail);
        video_desc = view.findViewById(R.id.video_desc);
        hd = view.findViewById(R.id.hd);
        content = view.findViewById(R.id.content);
        logo = view.findViewById(R.id.logo);
        mainRecyclerView = view.findViewById(R.id.main_recycler_view);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mainRecyclerView.setLayoutManager(verticalLayoutManager);

        RowAdapter rowAdapter = new RowAdapter(this,rows.rows, getContext(), this, this, rows.serieList,rows.counter);
        mainRecyclerView.setAdapter(rowAdapter);



        return view;
    }


    @Override
    public void onVideoSelected(Video video) {
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra("video", video);
        startActivity(intent);
    }

    public void launchSeason(Serie serie) {
        Intent intent = new Intent(context, SeasonActivity.class);
        intent.putExtra("serie", serie);
        startActivity(intent);
    }
    @Override
    public void onVideoFocused(Video video) {
        // Update ImageView and TextView based on the focused video
        Glide.with(this).load(video.serieslogo).into(logo);
        content.setTextColor(getResources().getColor(R.color.white));
        hd.setBackground(getResources().getDrawable(R.drawable.ic_hd));
        // For example, using Glide:
        Glide.with(this)
                .load(video.thumbnail169)
                .into(selectedVideoThumbnail);
        if(Objects.equals(video.rating, ""))
            video.rating = "16+";
        season_size.setText(video.releaseyear + "      " + video.rating + "      " + video.duration);
        video_desc.setText(video.description);
        content.setText(video.title);
    }
    public void updateSelectedSerie(Serie serie) {
        Glide.with(this).load(serie.serie_page_image).into(logo);
        hd.setBackground(getResources().getDrawable(R.drawable.ic_hd));
        Glide.with(this)
                .load(serie.thumbnail_image169)
                .into(selectedVideoThumbnail);
        int count = serie.seasons.size();
        content.setText(serie.series_category_to_show);
        content.setTextColor(getResources().getColor(R.color.neon_blue));
        if(count==1)
            season_size.setText(serie.seasons.size() + " Season");
        else
            season_size.setText(serie.seasons.size() + " Seasons");
    }
}
