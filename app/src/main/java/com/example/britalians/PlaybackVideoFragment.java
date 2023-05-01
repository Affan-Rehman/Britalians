package com.example.britalians;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.leanback.app.VideoSupportFragment;
import androidx.leanback.app.VideoSupportFragmentGlueHost;
import androidx.leanback.media.MediaPlayerAdapter;
import androidx.leanback.media.PlaybackTransportControlGlue;
import androidx.leanback.widget.PlaybackControlsRow;

/**
 * Handles video playback with media controls.
 */
public class PlaybackVideoFragment extends VideoSupportFragment {

    private PlaybackTransportControlGlue<MediaPlayerAdapter> mTransportControlGlue;
    Video video = null;

    PlaybackVideoFragment(Video video){
        this.video = video;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        VideoSupportFragmentGlueHost glueHost =
                new VideoSupportFragmentGlueHost(PlaybackVideoFragment.this);

        MediaPlayerAdapter playerAdapter = new MediaPlayerAdapter(getActivity());
        playerAdapter.setRepeatAction(PlaybackControlsRow.RepeatAction.INDEX_NONE);

        if(video!=null) {

            mTransportControlGlue = new PlaybackTransportControlGlue<>(getActivity(), playerAdapter);
            mTransportControlGlue.setHost(glueHost);
            mTransportControlGlue.setTitle(video.getTitle());
            mTransportControlGlue.setSubtitle(video.getDescription());
            mTransportControlGlue.playWhenPrepared();
            playerAdapter.setDataSource(Uri.parse(video.link));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTransportControlGlue != null) {
            mTransportControlGlue.pause();
        }
    }
}