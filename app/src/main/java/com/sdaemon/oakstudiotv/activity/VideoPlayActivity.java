//package com.techindiana.oakstudiotv.activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.media.MediaFormat;
//import android.net.Uri;
//import android.os.Handler;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.PopupMenu;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageButton;
//import android.widget.ProgressBar;
//
//import com.google.android.exoplayer2.DefaultLoadControl;
//import com.google.android.exoplayer2.DefaultRenderersFactory;
//import com.google.android.exoplayer2.ExoPlaybackException;
//import com.google.android.exoplayer2.ExoPlayer;
//import com.google.android.exoplayer2.ExoPlayerFactory;
//import com.google.android.exoplayer2.PlaybackParameters;
//import com.google.android.exoplayer2.Player;
//
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.Timeline;
//import com.google.android.exoplayer2.extractor.mp4.Track;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.source.TrackGroupArray;
//import com.google.android.exoplayer2.source.hls.HlsMediaSource;
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
//import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
//import com.google.android.exoplayer2.trackselection.TrackSelection;
//import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
//import com.google.android.exoplayer2.trackselection.TrackSelector;
//import com.google.android.exoplayer2.ui.PlayerView;
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.util.MimeTypes;
//import com.google.android.exoplayer2.util.Util;
//import com.techindiana.oakstudiotv.R;
//
//
//
//public class VideoPlayActivity extends AppCompatActivity {
//    private boolean playWhenReady = true;
//    private int currentWindow = 0;
//    private long playbackPosition = 0;
//    PlayerView playerView;
//    ProgressBar loading;
//    Context mContext;
//
//    TrackSelector trackSelector;
//    ExoPlayer player;
//    String playerurl="";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video_play);
//        playerView = findViewById(R.id.video_view);
//        loading = findViewById(R.id.loading);
//        mContext=getApplicationContext();
//        Bundle bundle=getIntent().getExtras();
//
//        if(bundle!=null)
//        {
//            if(bundle.containsKey("URL"))
//            {
//                playerurl=bundle.getString("URL");
//                Log.i(String.valueOf(mContext),"=============="+playerurl);
//            }
//
//        }
//        if(getSupportActionBar()!=null) {
//            getSupportActionBar().hide();
//        }
////To show the action bar
//
//
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        //--------------------------------------
//        //Creating default track selector
//        //and init the player
//        TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
//        player = ExoPlayerFactory.newSimpleInstance(
//                new DefaultRenderersFactory(mContext),
//                new DefaultTrackSelector(adaptiveTrackSelection),
//                new DefaultLoadControl());
//        //init the player
//        playerView.setPlayer(player);
//
//        //-------------------------------------------------
//        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
//        // Produces DataSource instances through which media data is loaded.
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
//                Util.getUserAgent(mContext, "Exo2"), defaultBandwidthMeter);
//
//        //-----------------------------------------------
//        //Create media source
//      // String hls_url = "http://azuremediaservicedemo-inct.streaming.media.azure.net/01042abd-a7e7-4fa9-a5de-77ea51b2b9d0/AQUAMAN Trailer 2 (4K ULTRA HD) .ism/manifest(format=m3u8-aapl-v3,filter=Premium)";
//     //   String hls_url = "http://azuremediaservicedemo-inct.streaming.media.azure.net/01042abd-a7e7-4fa9-a5de-77ea51b2b9d0/AQUAMAN Trailer 2 (4K ULTRA HD) .ism/manifest(format=m3u8-aapl-v3)";
//       String hls_url=playerurl;
//        Uri uri = Uri.parse(hls_url);
//        Handler mainHandler = new Handler();
//        MediaSource mediaSource = new HlsMediaSource.Factory(
//                dataSourceFactory).createMediaSource(uri);
//        player.prepare(mediaSource);
//
//        player.addListener(new Player.EventListener() {
//            @Override
//            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
//
//            }
//
//            @Override
//            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//
//            }
//
//            @Override
//            public void onLoadingChanged(boolean isLoading) {
//
//            }
//
//            @Override
//            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                switch (playbackState) {
//                    case Player.STATE_READY:
//                        loading.setVisibility(View.GONE);
//                        break;
//                    case Player.STATE_BUFFERING:
//                        loading.setVisibility(View.VISIBLE);
//                        break;
//                }
//            }
//
//            @Override
//            public void onRepeatModeChanged(int repeatMode) {
//
//            }
//
//            @Override
//            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
//
//            }
//
//            @Override
//            public void onPlayerError(ExoPlaybackException error) {
//
//            }
//
//            @Override
//            public void onPositionDiscontinuity(int reason) {
//
//            }
//
//            @Override
//            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
//
//            }
//
//            @Override
//            public void onSeekProcessed() {
//
//            }
//
//        });
//        player.seekTo(currentWindow, playbackPosition);
//        player.prepare(mediaSource, true, false);
//    }
//    private void releasePlayer() {
//        if (player != null) {
//            playbackPosition = player.getCurrentPosition();
//            currentWindow = player.getCurrentWindowIndex();
//            playWhenReady = player.getPlayWhenReady();
//            player.release();
//            player = null;
//        }
//    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (Util.SDK_INT <= 23) {
//            releasePlayer();
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        if (Util.SDK_INT > 23) {
//            releasePlayer();
//        }
//    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
//
//
//}
