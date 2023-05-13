package com.example.britalians;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;

import androidx.fragment.app.FragmentActivity;
import androidx.multidex.MultiDex;

import com.google.ads.interactivemedia.v3.api.ImaSdkFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.util.Util;

public class detailsActivity extends FragmentActivity {
    Video video;
    private StyledPlayerView playerView;
    private ExoPlayer player;
    private ImaAdsLoader adsLoader;

    /**
     * IMA sample tag for a single skippable inline video ad. See more IMA sample tags at
     * https://developers.google.com/interactive-media-ads/docs/sdks/html5/client-side/tags
     */
    static final String ad_tag =
            "https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_preroll_skippable&sz=640x480&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator=";

    ImaSdkFactory sdkFactory;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            // Get the video object from intent
            video = (Video) getIntent().getSerializableExtra("video");

            mediaController = new MediaController(this);
            MultiDex.install(this);

            playerView = findViewById(R.id.player_view);

            // Create an AdsLoader.
            adsLoader = new ImaAdsLoader.Builder(/* context= */ this)
                    .build();

            // Create the IMA SDK factory and settings
            sdkFactory = ImaSdkFactory.getInstance();

        }
    }


    private void releasePlayer() {
        adsLoader.setPlayer(null);
        playerView.setPlayer(null);
        player.release();
        player = null;
    }

    private void initializePlayer() {
        // Set up the factory for media sources, passing the ads loader and ad view providers.
        DataSource.Factory dataSourceFactory = new DefaultDataSource.Factory(this);

        MediaSourceFactory mediaSourceFactory =
                new DefaultMediaSourceFactory(dataSourceFactory)
                        .setAdsLoaderProvider(unusedAdTagUri -> adsLoader)
                        .setAdViewProvider(playerView);

        // Create an ExoPlayer and set it as the player for content and ads.
        player = new ExoPlayer.Builder(this)
                .setMediaSourceFactory(mediaSourceFactory)
                .build();

        player.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if(!isPlaying && player.getPlayWhenReady()){
                    player.setPlayWhenReady(true);
                }
            }
        });

        playerView.setPlayer(player);
        adsLoader.setPlayer(player);

        // Create the MediaItem to play, specifying the content URI and ad tag URI.
        Uri contentUri = Uri.parse(video.link);
        Uri adTagUri = Uri.parse(ad_tag);
        MediaItem mediaItem =
                new MediaItem.Builder()
                        .setAdsConfiguration(new MediaItem.AdsConfiguration.Builder(adTagUri).build())
                        .setUri(contentUri)
                        .build();

        // Prepare the content and ad to be played with the SimpleExoPlayer.
        player.setMediaItem(mediaItem);
        player.prepare();

        // Set PlayWhenReady. If true, content and ads will autoplay.
        player.setPlayWhenReady(true);
    }
    @Override
    public void onStart() {
        super.onStart();
        //
        if (Util.SDK_INT > 23) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adsLoader.release();
    }
}