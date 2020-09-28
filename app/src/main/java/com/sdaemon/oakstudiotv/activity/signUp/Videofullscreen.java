//package com.techindiana.oakstudiotv.activity.signUp;
//
//import android.app.Dialog;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.PopupMenu;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.ProgressBar;
//
//import com.google.android.exoplayer2.C;
//import com.google.android.exoplayer2.DefaultLoadControl;
//import com.google.android.exoplayer2.DefaultRenderersFactory;
//import com.google.android.exoplayer2.ExoPlaybackException;
//import com.google.android.exoplayer2.ExoPlayerFactory;
//import com.google.android.exoplayer2.LoadControl;
//import com.google.android.exoplayer2.PlaybackParameters;
//import com.google.android.exoplayer2.Player;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.Timeline;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.source.TrackGroupArray;
//import com.google.android.exoplayer2.source.hls.HlsMediaSource;
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
//import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
//import com.google.android.exoplayer2.trackselection.TrackSelection;
//import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
//import com.google.android.exoplayer2.trackselection.TrackSelector;
//import com.google.android.exoplayer2.ui.PlayerControlView;
//import com.google.android.exoplayer2.ui.PlayerView;
//import com.google.android.exoplayer2.upstream.BandwidthMeter;
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
//import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
//import com.google.android.exoplayer2.util.Util;
//import com.techindiana.oakstudiotv.R;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//public class Videofullscreen extends AppCompatActivity {
//    private final String STATE_RESUME_WINDOW = "resumeWindow";
//    private final String STATE_RESUME_POSITION = "resumePosition";
//    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
//
//    private PlayerView
//            mExoPlayerView;
//    private DataSource.Factory mediaDataSourceFactory;
//
//    SimpleExoPlayer player;
//    private MediaSource mVideoSource;
//    private boolean mExoPlayerFullscreen = false;
//    private FrameLayout mFullScreenButton;
//    private ImageView mFullScreenIcon;
//    private Dialog mFullScreenDialog;
//    ProgressBar load;
// String play="";
//
//    private int mResumeWindow;
//    private long mResumePosition;
//    PopupWindow mpopup;
////    public int customBitrateRate=169000;
//    public ImageView button;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_videofullscreen);
//        load = findViewById(R.id.load);
//        Bundle bundle=getIntent().getExtras();
//        if(bundle!=null)
//        { if(bundle.containsKey("URL"))
//            {
//                play=bundle.getString("URL");
//            }
//
//        }
//        if(getSupportActionBar()!=null) {
//            getSupportActionBar().hide();
//        }
//        if (savedInstanceState != null) {
//            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
//            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
//            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
//
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//
//        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
//        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
//        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
//
//        super.onSaveInstanceState(outState);
//    }
//
//
//    private void initFullscreenDialog() {
//        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
//            public void onBackPressed() {
//                if (mExoPlayerFullscreen)
//                    closeFullscreenDialog();
//                super.onBackPressed();
//            }
//        };
//    }
//
//
//    private void openFullscreenDialog() {
//        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
//        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(Videofullscreen.this, R.drawable.ic_fullscreen_skrink));
//        mExoPlayerFullscreen = true;
//        mFullScreenDialog.show();
//    }
//
//
//    private void closeFullscreenDialog() {
//        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
//        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
//        mExoPlayerFullscreen = false;
//        mFullScreenDialog.dismiss();
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(Videofullscreen.this, R.drawable.ic_fullscreen_expand));
//    }
//
//
//    private void initFullscreenButton() {
//
//        PlayerControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
//        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
//        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
//        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!mExoPlayerFullscreen)
//
//                    openFullscreenDialog();
//                else
//                    closeFullscreenDialog();
//            }
//        });
//    }
//
//
//    private void initExoPlayer() {
//        button = (ImageView) findViewById(R.id.button);
//        button.setVisibility(View.VISIBLE);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                //Creating the instance of PopupMenu
//
//                PopupMenu popup = new PopupMenu(Videofullscreen.this, button);
//                //Inflating the Popup using xml file
//                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
//                //registering popup with OnMenuItemClickListener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.one:
//                                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//
//                                if (item.isChecked())
//                                    item.setChecked(false);
//                                else item.setChecked(true);
//                                TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//                                DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//                                LoadControl loadControl = new DefaultLoadControl();
//
//                                trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoBitrate(988000).build());
//                                player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(Videofullscreen.this), trackSelector, loadControl);
//
//                                break;
//                            case R.id.two:
//                                if (item.isChecked()) item.setChecked(false);
//                                else item.setChecked(true);
//                                bandwidthMeter = new DefaultBandwidthMeter();
//
//                                videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//                                trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//                                loadControl = new DefaultLoadControl();
//
//                                trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoBitrate(509000).build());
//                                player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(Videofullscreen.this), trackSelector, loadControl);
//
//                                break;
//                            case R.id.three:
//                                if (item.isChecked()) item.setChecked(false);
//                                else item.setChecked(true);
//                                bandwidthMeter = new DefaultBandwidthMeter();
//
//                                videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//                                trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//                                loadControl = new DefaultLoadControl();
//
//                                trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoBitrate(169000).build());
//                                player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(Videofullscreen.this), trackSelector, loadControl);
//
//                                break;
//                            case R.id.four:
//                                if (item.isChecked()) item.setChecked(false);
//                                else item.setChecked(true);
//                                bandwidthMeter = new DefaultBandwidthMeter();
//                                videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//                                TrackSelector track = new DefaultTrackSelector(videoTrackSelectionFactory);
//                                loadControl = new DefaultLoadControl();
//                                // trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoBitrate(169000).build());
//                                player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(Videofullscreen.this), track, loadControl);
//
//                                break;
//                        }
//                        return true;
//                    }
//
//                });
//                popup.show();//showing popup menu
//            }
//        });//closing the setOnClickListener method
//
//     //   Log.i(String.valueOf(Videofullscreen.this),"============BITRATE:"+customBitrateRate);
//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//
//        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//        LoadControl loadControl = new DefaultLoadControl();
//
//        //trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoBitrate(customBitrateRate).build());
//        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);
//        mExoPlayerView.setPlayer(player);
//
//        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;
//
//        if (haveResumePosition) {
//            mExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
//        }
//       /* Uri srt = Uri.parse("http://www.storiesinflight.com/js_videosub/jellies.srt");
//
//        Format textFormat = Format.createTextSampleFormat(null, MimeTypes.APPLICATION_SUBRIP,
//                null, Format.NO_VALUE, Format.NO_VALUE, "en", null, Format.OFFSET_SAMPLE_RELATIVE);
//        MediaSource textMediaSource = new SingleSampleMediaSource.Factory(mediaDataSourceFactory)
//                .createMediaSource(srt, textFormat, C.TIME_UNSET);
//        mVideoSource = new MergingMediaSource(mVideoSource, textMediaSource);*/
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
//                        load.setVisibility(View.GONE);
//                        break;
//                    case Player.STATE_BUFFERING:
//                        load.setVisibility(View.VISIBLE);
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
//
//        player.prepare(mVideoSource);
//        mExoPlayerView.getPlayer().setPlayWhenReady(true);
//
//
//        String streamUrlNew = play;
//
//        new RetrieveFeedTask().execute(streamUrlNew);
//    }
//
//
//    @Override
//    protected void onResume() {
//
//        super.onResume();
//
//        if (mExoPlayerView == null) {
//
//            mExoPlayerView = (PlayerView) findViewById(R.id.exoplayer);
//            initFullscreenDialog();
//            initFullscreenButton();
//
//            String streamUrl = play;
//            String userAgent = Util.getUserAgent(Videofullscreen.this, getApplicationContext().getApplicationInfo().packageName);
//            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
//            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(Videofullscreen.this, null, httpDataSourceFactory);
//            Uri daUri = Uri.parse(streamUrl);
//
//            mVideoSource = new HlsMediaSource.Factory(
//                    dataSourceFactory).createMediaSource(daUri);
//
//            //trackSelector.getParameters().withMaxVideoBitrate(maxBitrate);
//            // player.prepare(mediaSource, !haveResumePosition, false);
//
//        }
//
//        initExoPlayer();
//
//        if (mExoPlayerFullscreen) {
//            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
//            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(Videofullscreen.this, R.drawable.ic_fullscreen_skrink));
//            mFullScreenDialog.show();
//        }
//    }
//
//
//    @Override
//    protected void onPause() {
//
//        super.onPause();
//
//        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
//            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
//            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());
//
//            mExoPlayerView.getPlayer().release();
//        }
//
//        if (mFullScreenDialog != null)
//            mFullScreenDialog.dismiss();
//    }
//
//    class RetrieveFeedTask extends AsyncTask<String, Void, String> {
//
//        private Exception exception;
//
//        protected String doInBackground(String... urls) {
//            try {
//
//                //   java.net.URL url = new java.net.URL(String.valueOf(urls));
//
//                String streamUrlNew  =play;
//                //  String streamUrlNew = "http://azuremediaservicedemo-inct.streaming.media.azure.net/01042abd-a7e7-4fa9-a5de-77ea51b2b9d0/AQUAMAN Trailer 2 (4K ULTRA HD) .ism/manifest(format=m3u8-aapl-v3,filter=Basic)";
//                URL yahoo = null;
//                try {
//                    yahoo = new URL(streamUrlNew);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//                BufferedReader in = null;
//                try {
//                    in = new BufferedReader(
//                            new InputStreamReader(
//                                    yahoo.openStream()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                String inputLine;
//                String out="";
//                String P_id = "";
//                String P_value = "";
//                int indexOfstart=0 ;
//                int indexOfend=0 ;
//                Map<String,String> myMap = new HashMap<>();
//                assert in != null;
//                while ((inputLine = in.readLine()) != null) {
//                    if (inputLine.contains("RESOLUTION")) {
//                        indexOfstart = inputLine.indexOf("x");
//                        indexOfend = inputLine.indexOf(",C");
//                        P_id = inputLine.substring(indexOfstart + 1, indexOfend) + "p";
//
//                    }
//                    if (inputLine.contains("video")) {
//                        indexOfstart = inputLine.indexOf("s(");
//                        indexOfend = inputLine.indexOf(")/");
//                        P_value = inputLine.substring(indexOfstart + 2, indexOfend);
//                        myMap.put(P_id,P_value);
//                    }
//                    out = out + inputLine;
//                }
//                System.out.println(inputLine);
//
//
//
//
//                Log.i(String.valueOf(Videofullscreen.this), "======== inputLine: " + out);
//
//
//
//                Iterator myVeryOwnIterator = myMap.keySet().iterator();
//                while(myVeryOwnIterator.hasNext()) {
//                    P_id=(String)myVeryOwnIterator.next();
//                    String value=(String)myMap.get(P_id);
//                    //   Toast.makeText(ctx, "Key: "+key+" Value: "+value, Toast.LENGTH_LONG).show();
//
//                    Log.i(String.valueOf(Videofullscreen.this),"############### Key: "+P_id);
//                    Log.i(String.valueOf(Videofullscreen.this),"############### Value: "+value);
//                }
//
//
//
//
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                return inputLine;
//            } catch (Exception e) {
//                this.exception = e;
//
//                return null;
//            } finally {
//                //  is.close();
//            }
//        }
//
//        protected void onPostExecute(String feed) {
//            // TODO: check this.exception
//            // TODO: do something with the feed
//            Log.i(String.valueOf(Videofullscreen.this), "======== inputLine: " + feed);
//        }
//
//    }
//
//}