package com.example.britalians;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.fragment.app.FragmentActivity;

import com.google.ads.interactivemedia.v3.api.AdDisplayContainer;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
import com.google.ads.interactivemedia.v3.api.AdsLoader;
import com.google.ads.interactivemedia.v3.api.AdsManager;
import com.google.ads.interactivemedia.v3.api.AdsRenderingSettings;
import com.google.ads.interactivemedia.v3.api.AdsRequest;
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory;
import com.google.ads.interactivemedia.v3.api.ImaSdkSettings;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;

import java.util.Arrays;

public class detailsActivity extends FragmentActivity {
    Video video;
    static final String LOGTAG = "IMABasicSample";


    /**
     * IMA sample tag for a single skippable inline video ad. See more IMA sample tags at
     * https://developers.google.com/interactive-media-ads/docs/sdks/html5/client-side/tags
     */
    static final String SAMPLE_VAST_TAG_URL =
            "https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_preroll_skippable&sz=640x480&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator=";

    ImaSdkFactory sdkFactory;
    AdsLoader adsLoader;
    AdsManager adsManager;
    int savedPosition = 0;
    VideoView videoPlayer;
    MediaController mediaController;
    VideoAdPlayerAdapter videoAdPlayerAdapter;
    Handler handler = new Handler();

    private void loadAdAfterDelay(int delay) {
        handler.postDelayed(() -> {

            if(delay==10000) {
                adsLoader = null;
            }
            if(delay == 0){

            }
            requestAds(SAMPLE_VAST_TAG_URL);
        }, delay);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            // Get the video object from intent
            video = (Video) getIntent().getSerializableExtra("video");

            // Initialize UI components
            videoPlayer = findViewById(R.id.videoView);
            mediaController = new MediaController(this);
            mediaController.setAnchorView(videoPlayer);
            videoPlayer.setMediaController(mediaController);

            // Create the video ad player adapter
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            videoAdPlayerAdapter = new VideoAdPlayerAdapter(videoPlayer, audioManager);

            // Create the IMA SDK factory and settings
            sdkFactory = ImaSdkFactory.getInstance();
            AdDisplayContainer adDisplayContainer = ImaSdkFactory.createAdDisplayContainer(
                    findViewById(R.id.videoPlayerContainer), videoAdPlayerAdapter);
            ImaSdkSettings settings = sdkFactory.createImaSdkSettings();
            adsLoader = sdkFactory.createAdsLoader(this, settings, adDisplayContainer);

            // Load the video
            videoPlayer.setVideoPath(video.link);
//            videoPlayer.start();

            // Load the ad after a delay
//            loadAdAfterDelay(30000);

            // Add listeners for when ads are loaded and for errors.
            adsLoader.addAdsLoadedListener(
                    adsManagerLoadedEvent -> {
                        adsManager = adsManagerLoadedEvent.getAdsManager();
                        adsManager.addAdEventListener(
                                adEvent -> {
                                    adEvent.getType();

                                    switch (adEvent.getType()) {
                                        case LOADED:
                                            // AdEventType.LOADED is fired when ads are ready to play.
                                            // LOADED event.
                                            adsManager.start();
                                            break;
                                        case CONTENT_PAUSE_REQUESTED:
                                            pauseContentForAds();
                                            break;
                                        case CONTENT_RESUME_REQUESTED:
                                            resumeContent();
                                            break;
                                        case ALL_ADS_COMPLETED:
                                            videoAdPlayerAdapter.release();
                                            adsManager.destroy();
                                            adsManager = null;
                                            break;
                                        case CLICKED:
                                            break;
                                        default:
                                            break;
                                    }
                                });
                        // Attach event and error event listeners.
                        adsManager.addAdErrorListener(
                                new AdErrorEvent.AdErrorListener() {
                                    /** An event raised when there is an error loading or playing ads. */
                                    @Override
                                    public void onAdError(AdErrorEvent adErrorEvent) {
                                        Log.e(LOGTAG, "Ad Error: " + adErrorEvent.getError().getMessage());
                                        String universalAdIds =
                                                Arrays.toString(adsManager.getCurrentAd().getUniversalAdIds());
                                        Log.i(
                                                LOGTAG,
                                                "Discarding the current ad break with universal "
                                                        + "ad Ids: "
                                                        + universalAdIds);
                                        adsManager.discardAdBreak();
                                    }
                                });
                        AdsRenderingSettings adsRenderingSettings =
                                ImaSdkFactory.getInstance().createAdsRenderingSettings();
                        adsManager.init(adsRenderingSettings);
                    });


            loadAdAfterDelay(0);
        }


    }
    private void pauseContentForAds() {
        Log.i(LOGTAG, "pauseContentForAds");
        savedPosition = videoPlayer.getCurrentPosition();
        videoPlayer.stopPlayback();
        // Hide the buttons and seek bar controlling the video view.
        videoPlayer.setMediaController(null);
    }

    private void resumeContent() {
            Log.i(LOGTAG, "resumeContent");

            // Show the buttons and seek bar controlling the video view.
            videoPlayer.setVideoPath(video.link);
            videoPlayer.setMediaController(mediaController);
            videoPlayer.setOnPreparedListener(
                    mediaPlayer -> {
                        if (savedPosition > 0) {
                            mediaPlayer.seekTo(savedPosition);
                        }
                        mediaPlayer.start();
                    });
            videoPlayer.setOnCompletionListener(
                    mediaPlayer -> videoAdPlayerAdapter.notifyImaOnContentCompleted());
        }


    private void requestAds(String adTagUrl) {
        // Create the ads request.
        Log.i(LOGTAG,"called 5");
        AdsRequest request = sdkFactory.createAdsRequest();
        request.setAdTagUrl(adTagUrl);
        request.setContentProgressProvider(
                () -> {
                    if (videoPlayer.getDuration() <= 0) {
                        return VideoProgressUpdate.VIDEO_TIME_NOT_READY;
                    }
                    return new VideoProgressUpdate(
                            videoPlayer.getCurrentPosition(), videoPlayer.getDuration());
                });

        // Request the ad. After the ad is loaded, onAdsManagerLoaded() will be called.
        adsLoader.requestAds(request);
    }

}