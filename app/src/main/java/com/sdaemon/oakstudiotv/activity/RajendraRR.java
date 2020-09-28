package com.sdaemon.oakstudiotv.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector.MappedTrackInfo;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SubtitleView;
import com.google.android.exoplayer2.ui.TrackSelectionView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dto.PlaybackDTO;
import com.sdaemon.oakstudiotv.model.VideoDTO;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.DatabaseHelperPlayBack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RajendraRR extends Activity implements View.OnClickListener {
    Context context;
    private String KEY_PLAY_WHEN_READY = "play_when_ready";
    private String KEY_WINDOW = "window";
    private String KEY_POSITION = "position";
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private SubtitleView tvSubTitleView;
    private Boolean shouldAutoPlay = true;
    public DefaultTrackSelector trackSelector;
    private TrackGroupArray lastSeenTrackGroupArray;
    private BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    //    private BandwidthMeter bandwidthMeter;
    private DataSource.Factory mediaDataSourceFactory;
    private DataSource.Factory dataSourceFactory;
    private Handler mainHandler;
    private Boolean playWhenReady = false;
    private int currentWindow = -1;
    //  private long playbackPosition = 87484;
    private long playbackPosition = 0;
    private ProgressBar progressBar;
    private ImageView ivHideControllerButton, ivSettings, ivBack, ivSubTitle, ivDownload, ivPlayDownload, downloadButton, delete;
    private Format textFormat;
    public MappedTrackInfo mappedTrackInfo;
    ArrayList<String> songsList = new ArrayList();
    private String streamingUrl = "";
    private String subTitleUrl = "";
    int checkedPosition = 0;
    public TextView subtitle;
    private Dialog dialog;
    Window window;
    private DownloadTracker downloadTracker;
    MediaSource mediaSource;
    public static final String ABR_ALGORITHM_DEFAULT = "default";
    public static final String ABR_ALGORITHM_RANDOM = "random";
    //    private MenuItem preferExtensionDecodersMenuItem;
//    private MenuItem randomAbrMenuItem;
    private boolean useExtensionRenderers;
    public static final String DRM_SCHEME_EXTRA = "drm_scheme";
    public static final String DRM_LICENSE_URL_EXTRA = "drm_license_url";
    public static final String DRM_KEY_REQUEST_PROPERTIES_EXTRA = "drm_key_request_properties";
    public static final String DRM_MULTI_SESSION_EXTRA = "drm_multi_session";
    public static final String PREFER_EXTENSION_DECODERS_EXTRA = "prefer_extension_decoders";
    public static final String ABR_ALGORITHM_EXTRA = "abr_algorithm";
    private AppSession appSession;
    private String type = "";
    ArrayList<VideoDTO> list;
    private int offLinePosition = -1;
    Bundle savedInstanceState;
    private DatabaseHelperPlayBack databaseHelperPlayBack;
    String strTemp = "0";
    private ArrayList<PlaybackDTO> playBackIdList;
    boolean isInitializePlayer = false;
    RelativeLayout rlOakstudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState = new Bundle();
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_video_player);
        context = this;
        appSession = AppSession.getInstance(getApplicationContext());
        playBackIdList = new ArrayList<>();
        databaseHelperPlayBack = new DatabaseHelperPlayBack(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            {
                if (bundle.containsKey("TYPE"))
                    type = bundle.getString("TYPE");
                if (bundle.containsKey("PLAYBACK_POSITION")) {
                    strTemp = bundle.getString("PLAYBACK_POSITION");
                    playbackPosition = Long.parseLong(strTemp);

                }
                if (bundle.containsKey("OFF_LINE_POSITION"))
                    offLinePosition = bundle.getInt("OFF_LINE_POSITION");

                if (bundle.containsKey("PN_JSON_OBJECT")) {
                    String jsonData = bundle.getString("PN_JSON_OBJECT");

                    if (!TextUtils.isEmpty(jsonData)) {
                        list = new ArrayList<VideoDTO>();
                        list = new Gson().fromJson(jsonData, new TypeToken<ArrayList<VideoDTO>>() {
                        }.getType());
                    }
                    Log.i(getClass().getName(), "===========VideoUrl SIZE: " + list.size());

                }
            }
        }
        dataSourceFactory = buildDataSourceFactory();
        rlOakstudio = (RelativeLayout) findViewById(R.id.ll_oakstudio);
        playerView = (PlayerView) findViewById(R.id.player_view);
        tvSubTitleView = (SubtitleView) findViewById(R.id.tv_sub_titlte);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        ivHideControllerButton = (ImageView) findViewById(R.id.exo_controller);
        ivSettings = (ImageView) findViewById(R.id.settings);
        ivSettings.setOnClickListener(this);
        ivSubTitle = (ImageView) findViewById(R.id.iv_sub_title);
        ivSubTitle.setOnClickListener(this);
        downloadButton = (ImageView) findViewById(R.id.download_button);
        downloadButton.setOnClickListener(this);
        ivPlayDownload = (ImageView) findViewById(R.id.iv_play_download);
        ivPlayDownload.setOnClickListener(this);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rlOakstudio.setVisibility(View.VISIBLE);
                playerView.setVisibility(View.VISIBLE);
            }
        }, 2000);


        // initializePlayer();

        if (playbackPosition > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogResumePlayBack(getResources().getString(R.string.would_you_like_to_resume_watching), playbackPosition);
                }
            }, 2000);


        } else {
            playbackPosition = 0;
            isInitializePlayer = true;
            // initializePlayer();

        }
        if (isInitializePlayer) {
            initializePlayer();
        }


        //  streamingUrl = "https://sdaemonazuremedia-inct.streaming.media.azure.net/988db5f4-0a7a-4ab2-983a-88e6e1c2cfe3/AQUAMAN.ism/manifest(format=m3u8-aapl).m3u8";
        //  streamingUrl =  "https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8";
        //  pre_streamingUrl = "https://sdaemonazuremedia-inct.streaming.media.azure.net/14edec6b-ed6c-497e-98f4-97146647ccb0/AQUAMAN Trailer 2 (4K ULTRA HD) .ism/manifest(format=m3u8-aapl).m3u8";

        //    subTitleUrl = "https://sdaemonazuremedia-inct.streaming.media.azure.net/463d929c-4198-4776-829f-e5068f95f1f3/AQUAMAN%20Trailer%202%20(4K%20ULTRA%20HD)%20NEW%202018_aud_SpReco.vtt";
        subTitleUrl = "https://nanomediaservices-inct.streaming.media.azure.net/f7f59c2b-3364-4d0a-a025-dfb10255a1d6/English.vtt";
        for (int i = 0; i < list.size(); i++) {
            songsList.add(list.get(i).getVideoUrl());
        }

        if (type.equalsIgnoreCase("download")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ivPlayDownload.setVisibility(View.VISIBLE);
                    streamingUrl = songsList.get(offLinePosition);
                    ivPlayDownload.performClick();


                }
            }, 2000);

        } else {

            ivPlayDownload.setVisibility(View.GONE);
        }

        //  songsList.add(streamingUrl);
        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
            currentWindow = savedInstanceState.getInt(KEY_WINDOW);
            //   playbackPosition = savedInstanceState.getLong(KEY_POSITION);

        }

//      shouldAutoPlay = true;
//     mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<DataSource>) bandwidthMeter);

        shouldAutoPlay = true;
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener) bandwidthMeter);

        AppSubClass application = (AppSubClass) getApplication();
        useExtensionRenderers = application.useExtensionRenderers();
    }

    @Override
    public void onStart() {
        super.onStart();
        //   if (Util.SDK_INT > 23)
        if (isInitializePlayer) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //   if (Util.SDK_INT <= 23 || player == null)
        if (isInitializePlayer) {
            initializePlayer();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23)
            //  releasePlayer();
            //   String urlId = "Rajendra";
            playBackIdList = databaseHelperPlayBack.getAllTeachers();
        String urlId = "Rajendra";
        Log.i(String.valueOf(RajendraRR.this), "========= playBackIdList : " + playBackIdList.size());
        Log.i(String.valueOf(RajendraRR.this), "=========  player.getCurrentPosition() : " + player.getCurrentPosition());
        if (playBackIdList != null && playBackIdList.size() < 1) {
            databaseHelperPlayBack.addTeachersDetail(urlId, "" + player.getCurrentPosition());
        } else {
            for (int i = 0; i < playBackIdList.size(); i++) {
                Log.i(String.valueOf(RajendraRR.this), "=========URL : " + playBackIdList.get(i).getUrlId());
                Log.i(String.valueOf(RajendraRR.this), "=========URL ID : " + playBackIdList.get(i).getPlayBackPosition());
                if (urlId.equalsIgnoreCase(playBackIdList.get(i).getUrlId())) {
                    //  Toast.makeText(Rajendra.this, " if" + offlineIdList.get(i).getPlayBackPosition(), Toast.LENGTH_SHORT).show();
                    databaseHelperPlayBack.updateTeachers(urlId, "" + player.getCurrentPosition());
                } else {
                    //   Toast.makeText(Rajendra.this, "else" + offlineIdList.get(i).getPlayBackPosition(), Toast.LENGTH_SHORT).show();
                    //  String UrlId ="Rajendra",playBackPosition = ""+player.getCurrentPosition();
                    databaseHelperPlayBack.addTeachersDetail(urlId, "" + player.getCurrentPosition());
                    //  Toast.makeText(Rajendra.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        releasePlayer();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) releasePlayer();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        // ActivityCompat.finishAffinity(this);
        if (Util.SDK_INT <= 23)
            releasePlayer();
        finish();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (Util.SDK_INT <= 23)
                    releasePlayer();
                onBackPressed();
                break;
            case R.id.settings:
                dialogSetting(this, getResources().getString(R.string.do_you_want_to_close_the_app));
              /*  //  String UrlId ="Rajendra",playBackPosition = ""+player.getCurrentPosition();
                String UrlId ="Rajendra";
                databaseHelperTeacher.addTeachersDetail(UrlId,""+player.getCurrentPosition());
                Toast.makeText(Rajendra.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();
*/
                break;


            case R.id.iv_sub_title:
                //  alert();
                break;
            case R.id.download_button:
                AppSubClass application = (AppSubClass) getApplication();
                downloadTracker = application.getDownloadTracker();
                try {
                    DownloadService.start(this, DemoDownloadService.class);
                } catch (IllegalStateException e) {
                    DownloadService.startForeground(this, DemoDownloadService.class);
                }
                //   downloadTracker.toggleDownload(this, sample.name, uriSample.uri, uriSample.extension);
                downloadTracker.toggleDownload(this, "HLS", Uri.parse(streamingUrl), "");
                // downloadTracker.toggleDownload(this,"HLS", Uri.parse(streamingUrl), "m3u8");

                if (appSession != null) {
                      /*  ArrayList<VideoDTO> videoList =  new ArrayList<>();
                        //   if (videoList == null)
                        // videoList = new ArrayList<>();

                     VideoDTO videoDTO=new VideoDTO();
                     videoDTO.setVideoUrl(""+streamingUrl);
                     videoList.add(videoDTO);
                     //  videoList.add(new VideoDTO(""+streamingUrl));
                        appSession.setOfflineDownloadList(videoList);
                        */


                   /*   // previous
                      ArrayList<VideoDTO> videoList =  new ArrayList<>();
                        if (videoList == null)
                       videoList = new ArrayList<>();
                        videoList.addAll(appSession.getOfflineDownloadList());
                        VideoDTO videoDTO=new VideoDTO();
                        videoList.add(videoDTO);
                        videoDTO.setVideoUrl(""+streamingUrl);
                        //  videoList.add(new VideoDTO(""+streamingUrl));
                        appSession.setOfflineDownloadList(videoList);

                        */


                    // new
                    ArrayList<VideoDTO> videoList = new ArrayList<>();
                    if (videoList == null)
                        videoList = new ArrayList<>();
                    videoList.addAll(appSession.getOfflineDownloadList());
                    videoList.add(new VideoDTO("" + streamingUrl, "3/25/2019",0L,"","","",2019,"","",""));
                    appSession.setOfflineDownloadList(videoList);

                }
                break;

            case R.id.iv_play_download:

              /*  int l = list.size();
                MediaSource[] mediaSources = new MediaSource[l];
                for (int i = 0; i < l; i++) {
                    Log.e("uri is", list.get(i).getVideoUrl());
                    mediaSources[i] = new HlsMediaSource.Factory(dataSourceFactory).setPlaylistParserFactory(
                            new DefaultHlsPlaylistParserFactory(getOfflineStreamKeys(Uri.parse(list.get(offLinePosition).getVideoUrl())))).createMediaSource(Uri.parse(list.get(offLinePosition).getVideoUrl()));
                    Log.e("Media source is", mediaSources[i].toString());
                }
*/

                //Log.e("Media source is", mediaSources.toString());
/*
                if (appSession != null) {
                    offLinePosition = appSession.getOfflineDownloadList().size();
                }*/


                mediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                        .setPlaylistParserFactory(
                                // new DefaultHlsPlaylistParserFactory(getOfflineStreamKeys(Uri.parse(streamingUrl))))
                                new DefaultHlsPlaylistParserFactory(getOfflineStreamKeys(Uri.parse(list.get(offLinePosition).getVideoUrl()))))
                        //  .createMediaSource(Uri.parse(streamingUrl));
                        .createMediaSource(Uri.parse(list.get(offLinePosition).getVideoUrl()));
               /* mediaSource = mediaSources.length == 1 ? mediaSources[0]
                        : new ConcatenatingMediaSource(mediaSources);*/
                player.prepare(mediaSource);

                break;

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        updateStartPosition();
        outState.putBoolean(KEY_PLAY_WHEN_READY, playWhenReady);
        outState.putInt(KEY_WINDOW, currentWindow);
        //   outState.putLong(KEY_POSITION, playbackPosition);
        super.onSaveInstanceState(outState);
    }

    private void initializePlayer() {
        playerView.requestFocus();
        AdaptiveTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        lastSeenTrackGroupArray = null;
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        playerView.setPlayer(player);
        playerView.getSubtitleView().setVisibility(View.GONE);
        player.addListener(new PlayerEventListener());
        playWhenReady = shouldAutoPlay;

        // Use Hls, Dash or other smooth streaming media source if you want to test the track quality selection.
        /*val mediaSource: MediaSource = HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
                mediaDataSourceFactory, mainHandler, null)*/


        int l = songsList.size();
        MediaSource[] mediaSources = new MediaSource[l];
        for (int i = 0; i < l; i++) {
            Log.e("uri is", songsList.get(i));
            mediaSources[i] = buildMediaSource(Uri.parse(songsList.get(i)));

            Log.e("Media source is", mediaSources[i].toString());
        }

        //Log.e("Media source is", mediaSources.toString());

        mediaSource = mediaSources.length == 1 ? mediaSources[0]
                : new ConcatenatingMediaSource(mediaSources);
        player.prepare(mediaSource);

        //player.seekTo(0, C.TIME_UNSET);


        player.setPlayWhenReady(true);
        // player.setRepeatMode(Player.REPEAT_MODE_ALL);
        player.addTextOutput(new TextOutput() {
            @Override
            public void onCues(List<Cue> cues) {
                Log.e("cues are ", cues.toString());
                if (tvSubTitleView != null) {
                    tvSubTitleView.onCues(cues);
                }
            }
        });



    /*    val mediaSource: MediaSource = HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
               mediaDataSourceFactory, mainHandler, null)*/

    /*    val mediaSource = ExtractorMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
*/


        boolean haveStartPosition = currentWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            player.seekTo(currentWindow, playbackPosition);
            //  player.seekTo(currentWindow, 87484);


        }
        player.prepare(mediaSource, !haveStartPosition, false);
        updateButtonVisibilities();


        ivHideControllerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerView.hideController();
            }
        });

    }

    private MediaSource buildMediaSource(Uri uri) {


    /*    DefaultHlsExtractorFactory defaultHlsExtractorFactory =
                new DefaultHlsExtractorFactory(DefaultTsPayloadReaderFactory.FLAG_ALLOW_NON_IDR_KEYFRAMES);


        MediaSource mediaSource = new HlsMediaSource.Factory(
                mediaDataSourceFactory).setExtractorFactory(defaultHlsExtractorFactory).createMediaSource(uri);

        */


        MediaSource mediaSource = new HlsMediaSource.Factory(
                mediaDataSourceFactory).createMediaSource(uri);


        // For subtitles
        if ((subTitleUrl == null || subTitleUrl == "false" || subTitleUrl.isEmpty())) {
            return mediaSource;
        }

        textFormat = Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT,
                Format.NO_VALUE, "en-us");
        //Log.e("srt link is",srt_link);
        Uri uriSubtitle = Uri.parse(subTitleUrl);
        MediaSource contentMediaSource = new HlsMediaSource.Factory(
                mediaDataSourceFactory).createMediaSource(uri);
        MediaSource[] mediaSources = new MediaSource[2]; //The Size must change depending on the Uris
        mediaSources[0] = contentMediaSource;
        //MediaSource subtitleSource = new SingleSampleMediaSource(uriSubtitle, mediaDataSourceFactory, textFormat, C.TIME_UNSET);
        SingleSampleMediaSource subtitleSource = new SingleSampleMediaSource(Uri.parse(subTitleUrl), mediaDataSourceFactory,
                Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, Format.NO_VALUE, "en-us", null),
                C.TIME_UNSET);
        //Uri uri = Uri.parse("http://www.storiesinflight.com/js_videosub/jellies.srt");
        mediaSources[1] = subtitleSource;

        MergingMediaSource mergedSource = new MergingMediaSource(mediaSources);

// Prepare the player with the source.
        return mergedSource;


    }

    private void releasePlayer() {
        if (player != null) {
            updateStartPosition();
            shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            //  player = null;
            trackSelector = null;
        }
    }

    private void updateStartPosition() {
        playbackPosition = player.getCurrentPosition();
        currentWindow = player.getCurrentWindowIndex();
        playWhenReady = player.getPlayWhenReady();


    }

    private class PlayerEventListener implements EventListener {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            updateButtonVisibilities();
            // The video tracks are no supported in this device.
            if (type.equalsIgnoreCase("download")) {
                progressBar.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                currentWindow = offLinePosition;
            } else {
                progressBar.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                currentWindow = player.getCurrentWindowIndex();
            }
            streamingUrl = songsList.get(currentWindow);
            //  Toast.makeText(Rajendra.this, ""+currentWindow, Toast.LENGTH_SHORT).show();


            if (trackGroups != lastSeenTrackGroupArray) {
                MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                if (mappedTrackInfo != null) {
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO) == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        Toast.makeText(RajendraRR.this, "Error unsupported track", Toast.LENGTH_SHORT).show();
                    }
                }
                lastSeenTrackGroupArray = trackGroups;
            }

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case Player.STATE_IDLE:
                    progressBar.setVisibility(View.VISIBLE);
                    ivBack.setVisibility(View.VISIBLE);
                    break;
                case Player.STATE_BUFFERING:
                    progressBar.setVisibility(View.VISIBLE);
                    ivBack.setVisibility(View.VISIBLE);
                    break;
                case Player.STATE_READY:
                    progressBar.setVisibility(View.GONE);
                    ivBack.setVisibility(View.GONE);
                    break;
                case Player.STATE_ENDED:
                    progressBar.setVisibility(View.GONE);
                    ivBack.setVisibility(View.GONE);
                    break;

            }
            updateButtonVisibilities();

        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {

        }

        @Override
        public void onPositionDiscontinuity(int reason) {

        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

        }

        @Override
        public void onSeekProcessed() {

        }
    }


    private void updateButtonVisibilities() {
        ivSettings.setVisibility(View.VISIBLE);
        if (player == null) {
            return;
        }
        //    MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo() ?: return;
        MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();

        if (mappedTrackInfo != null) {
            for (int i = 0; i < mappedTrackInfo.getRendererCount(); i++) {
                TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
                if (trackGroups.length != 0) {
                    if (player.getRendererType(i) == C.TRACK_TYPE_VIDEO) {
                        ivSettings.setVisibility(View.VISIBLE);
                        ivSettings.setOnClickListener(this);
                        ivSettings.setTag(i);
                    }
                }
            }
        }
    }

    public void alert() {
        final String[] items = {"OFF", "ON"};
        final ArrayList itemsSelected = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Subtilte Setting:");
        builder.setSingleChoiceItems(items, checkedPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String selectedItem = Arrays.asList(items).get(i);
                String s = items[i];
                if (s.equalsIgnoreCase("OFF")) {
                    checkedPosition = 0;
                    playerView.getSubtitleView().setVisibility(View.GONE);

                } else if (s.equalsIgnoreCase("ON")) {
                    checkedPosition = 1;
                    playerView.getSubtitleView().setVisibility(View.VISIBLE);
                }

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public void dialogSetting(final Context context, String message) {
        final Dialog dialog = new Dialog(context);
        window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_box_settings);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setGravity(Gravity.TOP | Gravity.LEFT);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.action_settings));

        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        window.findViewById(R.id.tv_multi_audio).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                //    MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo() ?: return;
                MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();

                if (mappedTrackInfo != null) {
                    for (int i = 0; i < mappedTrackInfo.getRendererCount(); i++) {
                        TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);

                        if (trackGroups.length != 0) {
                            player.getRendererType(i);
                        }
                    }
                    String title = "Audio";
                    int rendererIndex = C.TRACK_TYPE_AUDIO;
                    int rendererType = mappedTrackInfo.getRendererType(rendererIndex);

                    boolean allowAdaptiveSelections =
                            rendererType == C.TRACK_TYPE_VIDEO
                                    || (rendererType == C.TRACK_TYPE_AUDIO
                                    && mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
                                    == MappedTrackInfo.RENDERER_SUPPORT_NO_TRACKS);
                    Pair<AlertDialog, TrackSelectionView> dialogPair = TrackSelectionView.getDialog(RajendraRR.this, title, trackSelector, rendererIndex);
                    dialogPair.second.setShowDisableOption(false);
                    // dialogPair.second.setAllowAdaptiveSelections(true)
                    dialogPair.second.setAllowAdaptiveSelections(allowAdaptiveSelections);
                    dialogPair.first.show();

                }
            }
        });

        window.findViewById(R.id.tv_quality).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();

                MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                if (mappedTrackInfo != null) {
                    String title = getString(R.string.video);
                    int rendererIndex = (int) ivSettings.getTag();
                    Pair<AlertDialog, TrackSelectionView> dialogPair = TrackSelectionView.getDialog(RajendraRR.this, title, trackSelector, rendererIndex);
                    dialogPair.second.setShowDisableOption(false);
                    // dialogPair.second.setAllowAdaptiveSelections(true)
                    dialogPair.second.setAllowAdaptiveSelections(false);
                    dialogPair.first.show();
                }
            }
        });

        if (type.equalsIgnoreCase("download")) {

            subtitle = (TextView) window.findViewById(R.id.tv_sub_title);
            subtitle.setVisibility(View.GONE);

        } else {
            subtitle = (TextView) window.findViewById(R.id.tv_sub_title);
            subtitle.setVisibility(View.VISIBLE);

        }
        subtitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dialog.dismiss();
                alert();
            }
        });

        dialog.show();
    }

    private List<StreamKey> getOfflineStreamKeys(Uri uri) {
        return ((AppSubClass) getApplication()).getDownloadTracker().getOfflineStreamKeys(uri);


    }

    /**
     * Returns a new DataSource factory.
     */
    private DataSource.Factory buildDataSourceFactory() {
        return ((AppSubClass) getApplication()).buildDataSourceFactory();
    }

    private static boolean isNonNullAndChecked(@Nullable MenuItem menuItem) {
        // Temporary workaround for layouts that do not inflate the options menu.
        return menuItem != null && menuItem.isChecked();
    }

    private abstract static class Sample {
        public final String name;
        public final DrmInfo drmInfo;

        public Sample(String name, DrmInfo drmInfo) {
            this.name = name;
            this.drmInfo = drmInfo;
        }

        public Intent buildIntent(
                Context context, boolean preferExtensionDecoders, String abrAlgorithm) {
            Intent intent = new Intent(context, RajendraRR.class);
            intent.putExtra(RajendraRR.PREFER_EXTENSION_DECODERS_EXTRA, preferExtensionDecoders);
            intent.putExtra(RajendraRR.ABR_ALGORITHM_EXTRA, abrAlgorithm);
            if (drmInfo != null) {
                drmInfo.updateIntent(intent);
            }
            return intent;
        }

  /*  public Intent buildIntent(
            Context context, String preferExtensionDecoders, String abrAlgorithm) {
        Intent intent = new Intent(context, Rajendra.class);
        intent.putExtra(Rajendra.PREFER_EXTENSION_DECODERS_EXTRA, preferExtensionDecoders);
        intent.putExtra(Rajendra.ABR_ALGORITHM_EXTRA, abrAlgorithm);
        if (drmInfo != null) {
            drmInfo.updateIntent(intent);
        }
        return intent;
    }
    */

    }

    private static final class DrmInfo {
        public final String drmScheme;
        public final String drmLicenseUrl;
        public final String[] drmKeyRequestProperties;
        public final boolean drmMultiSession;

        public DrmInfo(
                String drmScheme,
                String drmLicenseUrl,
                String[] drmKeyRequestProperties,
                boolean drmMultiSession) {
            this.drmScheme = drmScheme;
            this.drmLicenseUrl = drmLicenseUrl;
            this.drmKeyRequestProperties = drmKeyRequestProperties;
            this.drmMultiSession = drmMultiSession;
        }

        public void updateIntent(Intent intent) {
            Assertions.checkNotNull(intent);
            intent.putExtra(RajendraRR.DRM_SCHEME_EXTRA, drmScheme);
            intent.putExtra(RajendraRR.DRM_LICENSE_URL_EXTRA, drmLicenseUrl);
            intent.putExtra(RajendraRR.DRM_KEY_REQUEST_PROPERTIES_EXTRA, drmKeyRequestProperties);
            intent.putExtra(RajendraRR.DRM_MULTI_SESSION_EXTRA, drmMultiSession);
        }
    }

    private static final class UriSample extends Sample {
        public final Uri uri;
        public final String extension;
        public final String adTagUri;
        public final String sphericalStereoMode;

        public UriSample(
                String name,
                DrmInfo drmInfo,
                Uri uri,
                String extension,
                String adTagUri,
                String sphericalStereoMode) {
            super(name, drmInfo);
            this.uri = uri;
            this.extension = extension;
            this.adTagUri = adTagUri;
            this.sphericalStereoMode = sphericalStereoMode;
        }

        @Override
        public Intent buildIntent(
                Context context, boolean preferExtensionDecoders, String abrAlgorithm) {
            return super.buildIntent(context, preferExtensionDecoders, abrAlgorithm)
                    .setData(uri);
//                    .putExtra(PlayerActivity.EXTENSION_EXTRA, extension)
//                    .putExtra(PlayerActivity.AD_TAG_URI_EXTRA, adTagUri)
//                    .putExtra(PlayerActivity.SPHERICAL_STEREO_MODE_EXTRA, sphericalStereoMode)
//                    .setAction(PlayerActivity.ACTION_VIEW);
        }

    }

    private static final class PlaylistSample extends Sample {

        public final UriSample[] children;

        public PlaylistSample(
                String name,
                DrmInfo drmInfo,
                UriSample... children) {
            super(name, drmInfo);
            this.children = children;
        }

        @Override
        public Intent buildIntent(
                Context context, boolean preferExtensionDecoders, String abrAlgorithm) {
            String[] uris = new String[children.length];
            String[] extensions = new String[children.length];

            for (int i = 0; i < children.length; i++) {
                uris[i] = children[i].uri.toString();
                extensions[i] = children[i].extension;
            }
            return super.buildIntent(context, preferExtensionDecoders, abrAlgorithm);
//                    .putExtra(PlayerActivity.URI_LIST_EXTRA, uris)
//                    .putExtra(PlayerActivity.EXTENSION_LIST_EXTRA, extensions)
//                    .setAction(PlayerActivity.ACTION_VIEW_LIST);
        }

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.sample_chooser_menu, menu);
//        preferExtensionDecodersMenuItem = menu.findItem(R.id.prefer_extension_decoders);
//        preferExtensionDecodersMenuItem.setVisible(useExtensionRenderers);
//        randomAbrMenuItem = menu.findItem(R.id.random_abr);
//        return true;
//    }


    public void dialogResumePlayBack(String message, final long playbackPos) {
        final Dialog dialog = new Dialog(context);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_box_yes_no);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.resume_watching));
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        String hms = convertMilliSecondToHour(playbackPos);
        tv_message.setText(Html.fromHtml("" + message + " " + hms));
        tv_message.setMovementMethod(new ScrollingMovementMethod());
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playbackPosition = 0;
                isInitializePlayer = true;
                initializePlayer();
                dialog.dismiss();
            }
        });
        window.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playbackPosition = playbackPos;
                isInitializePlayer = true;
                initializePlayer();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public String convertMilliSecondToHour(long millis) {
        String hms = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            //  System.out.println(hms);

        }
        return hms;
    }

//  //  https://github.com/google/ExoPlayer/issues/4031
//    public final class CustomPlayerView
//            extends PlayerView implements PlayerControlView.VisibilityListener {
//
//        private static final float DRAG_THRESHOLD = 10;
//        private static final long LONG_PRESS_THRESHOLD_MS = 500;
//
//        private boolean controllerVisible;
//        private long tapStartTimeMs;
//        private float tapPositionX;
//        private float tapPositionY;
//
//        public CustomPlayerView(Context context) {
//            this(context, null);
//        }
//
//        public CustomPlayerView(Context context, AttributeSet attrs) {
//            this(context, attrs, 0);
//        }
//
//        public CustomPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
//            super(context, attrs, defStyleAttr);
//            setControllerVisibilityListener(this);
//        }
//
//        @Override
//        public boolean onTouchEvent(MotionEvent ev) {
//            switch (ev.getActionMasked()) {
//                case MotionEvent.ACTION_DOWN:
//                    tapStartTimeMs = SystemClock.elapsedRealtime();
//                    tapPositionX = ev.getX();
//                    tapPositionY = ev.getY();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    if (tapStartTimeMs != 0
//                            && (Math.abs(ev.getX() - tapPositionX) > DRAG_THRESHOLD
//                            || Math.abs(ev.getY() - tapPositionY) > DRAG_THRESHOLD)) {
//                        tapStartTimeMs = 0;
//                    }
//                    break;
//                case MotionEvent.ACTION_UP:
//                    if (tapStartTimeMs != 0) {
//                        if (SystemClock.elapsedRealtime() - tapStartTimeMs < LONG_PRESS_THRESHOLD_MS) {
//                            if (!controllerVisible) {
//                                showController();
//                            } else if (getControllerHideOnTouch()) {
//                                hideController();
//                            }
//                        }
//                        tapStartTimeMs = 0;
//                    }
//            }
//            return true;
//        }
//
//        @Override
//        public void onVisibilityChange(int visibility) {
//            controllerVisible = visibility == View.VISIBLE;
//
//        }
//    }


}






