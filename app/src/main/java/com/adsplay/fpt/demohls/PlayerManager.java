package com.adsplay.fpt.demohls;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.io.IOException;

/**
 * Created by haint on 03/01/2018.
 */

public class PlayerManager {

    private Context context;
    private SimpleExoPlayer player;
    private SimpleExoPlayerView simpleExoPlayerView;
    private static final String UserAgent = System.getProperty("http.agent");
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static final Handler handler = new Handler();

    private ImaAdsLoader imaAdsLoader;
    private final DataSource.Factory manifestDataSourceFactory;
    private final DataSource.Factory mediaDataSourceFactory;

    public PlayerManager(Context context, SimpleExoPlayerView simpleExoPlayerView) {
        this.context = context;
        this.simpleExoPlayerView = simpleExoPlayerView;
        manifestDataSourceFactory =
                new DefaultDataSourceFactory(
                        context, UserAgent);
        mediaDataSourceFactory =
                new DefaultDataSourceFactory(
                        context,
                        UserAgent,
                        new DefaultBandwidthMeter());
    }

    public void initializePlayer(SimpleExoPlayerView exoPlayerView) {
        //Initializing ExoPlayer
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(context),
                new DefaultTrackSelector());
        //binding exoPlayerView to SimpleExoPlayer
        exoPlayerView.setPlayer(player);
        exoPlayerView.requestFocus();
    }
    //preparing player with media Source
    public void prepareVideo(String video, String adsTag){
        player.prepare(mediaSourceWithAds(createHlsMediaSource(video),adsTag, simpleExoPlayerView));
        //player.prepare(mediaSourceWithAds(createDashMediaSource(AdsConfig.VIDEO_URL_DASH),adsTag, simpleExoPlayerView));
    }
    //create source media
    private MediaSource createHlsMediaSource(String videoUrl)
    {
        //Creating Video Content Media Source With Ads
        //return media source with ads
        return new HlsMediaSource.Factory(mediaDataSourceFactory).createMediaSource(Uri.parse(videoUrl), handler, new MediaSourceEventListener() {
            @Override
            public void onLoadStarted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs) {

            }

            @Override
            public void onLoadCompleted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {

            }

            @Override
            public void onLoadCanceled(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {

            }

            @Override
            public void onLoadError(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded, IOException error, boolean wasCanceled) {

            }

            @Override
            public void onUpstreamDiscarded(int trackType, long mediaStartTimeMs, long mediaEndTimeMs) {

            }

            @Override
            public void onDownstreamFormatChanged(int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaTimeMs) {

            }
        });
    }

    private MediaSource createDashMediaSource(String videoUrl)
    {
        //Creating Video Content Media Source With Ads
        //return media source with ads
        return new DashMediaSource.Factory(new DefaultDashChunkSource.Factory(mediaDataSourceFactory),manifestDataSourceFactory)
                .createMediaSource(Uri.parse(videoUrl), handler, new MediaSourceEventListener() {
                    @Override
                    public void onLoadStarted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs) {

                    }

                    @Override
                    public void onLoadCompleted(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {

                    }

                    @Override
                    public void onLoadCanceled(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded) {

                    }

                    @Override
                    public void onLoadError(DataSpec dataSpec, int dataType, int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs, long bytesLoaded, IOException error, boolean wasCanceled) {

                    }

                    @Override
                    public void onUpstreamDiscarded(int trackType, long mediaStartTimeMs, long mediaEndTimeMs) {

                    }

                    @Override
                    public void onDownstreamFormatChanged(int trackType, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long mediaTimeMs) {

                    }
                });
    }
    //attach ads to media source
    private MediaSource mediaSourceWithAds(MediaSource hlsMediaSource, String AD_TAG_URL, SimpleExoPlayerView exoPlayerView){
        imaAdsLoader = new ImaAdsLoader(context, Uri.parse(AD_TAG_URL));
        return new AdsMediaSource(
                hlsMediaSource,
                new DefaultDataSourceFactory(context, UserAgent),
                imaAdsLoader,
                exoPlayerView.getOverlayFrameLayout(),
                null,
                null
                );
    }

    public void playVideo(){
        player.setPlayWhenReady(true);
    }
    public void pauseVideo(){
        player.setPlayWhenReady(false);
    }
    public void stopVideo(){
        player.release();
        if (imaAdsLoader != null){
            imaAdsLoader.release();
        }
    }
}
