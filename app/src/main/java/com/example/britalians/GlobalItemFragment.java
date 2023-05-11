package com.example.britalians;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

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
        if (!row.items.isEmpty()) {
            videoRecyclerView.requestFocus();
            videoRecyclerView.scrollToPosition(0);
        }
        else {
            content.setText("No contents available");
            content.setTextSize(30);
        }
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
        Glide.with(this)
                .asBitmap()
                .load(video.serieslogo)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        int imageWidth = resource.getWidth();
                        int imageHeight = resource.getHeight();

                        // Get the ImageView LayoutParams
                        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) logo.getLayoutParams();

                        // Set a fixed height in dp
                        int fixedHeightDp = 60;
                        params.height = dpToPx(fixedHeightDp);

                        // Adjust the width to maintain the image's aspect ratio
                        params.width = (int) (params.height * ((float) imageWidth / imageHeight));

                        // Apply the updated LayoutParams to the ImageView
                        logo.setLayoutParams(params);

                        // Now that we've adjusted the size of the ImageView, load the image into it
                        Glide.with(content).load(video.serieslogo).into(logo);
                    }
                });
        // For example, using Glide:
        Glide.with(this)
                .load(video.thumbnail169)
                .into(selectedVideoThumbnail);
        video_desc.setText(video.description);
        content.setText(video.title);

    }
    int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}