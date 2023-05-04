package com.example.britalians;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Objects;


public class MovieFragment extends Fragment {
    Button play;
    ImageView top_right;
    Video video;
    ImageView logo,hd;
    TextView season_size,video_desc,content;
    public MovieFragment() {
        // Required empty public constructor
    }

    public MovieFragment(Video video) {
        this.video = video;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup myContainer, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_movie, myContainer, false);
        content = root.findViewById(R.id.content);
        season_size = root.findViewById(R.id.season_size);
        logo = root.findViewById(R.id.logo1);
        play = root.findViewById(R.id.play_button);
        hd = root.findViewById(R.id.hd);
        video_desc = root.findViewById(R.id.video_desc);
        top_right = root.findViewById(R.id.top_right);
        RecyclerView recyclerView =root. findViewById(R.id.row_itemsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        VideoDetailsRowAdapter videoDetailsRowAdapter = new VideoDetailsRowAdapter(video.getVideoDetailsRows(),getContext());
        recyclerView.setAdapter(videoDetailsRowAdapter);

        if(Objects.equals(video.rating, ""))
            video.rating = "16+";
        season_size.setText(video.releaseyear + "      " + video.rating + "      " + video.duration);
        Glide.with(this).load(video.serieslogo).into(logo);
        hd.setVisibility(View.VISIBLE);
        Glide.with(this).load(video.thumbnail169).into(top_right);
        video_desc.setText(video.description);
        content.setText(video.title);
        root.getViewTreeObserver().addOnGlobalFocusChangeListener((oldFocus, newFocus) -> {
            if (newFocus != null && newFocus.getTag() instanceof RowItems) {
                RowItems focusedRowItem = (RowItems) newFocus.getTag();
                video_desc.setText(null);
                season_size.setText(null);
                hd.setVisibility(View.INVISIBLE);
                Glide.with(this).load(focusedRowItem.details_logo).into(logo);
                content.setText(focusedRowItem.details_name);
                Glide.with(this).load(focusedRowItem.details_thumbnail169).into(top_right);
            }
            else if (newFocus != null && newFocus.getId() == R.id.play_button) {
                if(Objects.equals(video.rating, ""))
                    video.rating = "16+";
                season_size.setText(video.releaseyear + "      " + video.rating + "      " + video.duration);
                Glide.with(this).load(video.serieslogo).into(logo);
                hd.setVisibility(View.VISIBLE);
                Glide.with(this).load(video.thumbnail169).into(top_right);
                video_desc.setText(video.description);
                content.setText(video.title);
            }
        });
        play.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                recyclerView.requestFocus();
                return true;
            }
            else if (event.getAction() == KeyEvent.ACTION_UP && play.hasFocus() && keyCode == KeyEvent.KEYCODE_ENTER) {
                Intent intent = new Intent(getContext(), detailsActivity.class);
                intent.putExtra("video", video);
                startActivity(intent);
                return true;
            }
            return false;
        });
        play.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), detailsActivity.class);
            intent.putExtra("video", video);
            startActivity(intent);
        });


        recyclerView.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                play.requestFocus();
                return true;
            }
            return false;
        });


        return root;
    }
}