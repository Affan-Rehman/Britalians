package com.example.britalians;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MovieActivity extends FragmentActivity {
        Video video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        if (savedInstanceState == null) {
            video = (Video) getIntent().getSerializableExtra("video");
            MovieFragment fragment = new MovieFragment(video);

            // Use a FragmentTransaction to add the fragment to the activity
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.movie_frame, fragment);
            transaction.commit();
        }
    }
}