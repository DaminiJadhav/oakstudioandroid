package com.sdaemon.oakstudiotv.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaDrm;
import android.media.NotProvisionedException;
import android.media.ResourceBusyException;
import android.media.UnsupportedSchemeException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.StrictMode;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rubensousa.previewseekbar.PreviewView;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.OfflineLicenseHelper;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DashUtil;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.hls.DefaultHlsExtractorFactory;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
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
import com.google.android.exoplayer2.upstream.DataSourceInputStream;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.PreviewSeekBar.ExoPlayerManager;

import com.sdaemon.oakstudiotv.audioorsubtitletrack.TrackS;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.ContentViewTimeDTO;
import com.sdaemon.oakstudiotv.dto.PlaybackDTO;
import com.sdaemon.oakstudiotv.model.VideoDTO;

import com.sdaemon.oakstudiotv.trackselectionclass.NewTrackSelection1;
import com.sdaemon.oakstudiotv.trackselectionclass.TrackSelectionDialog;
import com.sdaemon.oakstudiotv.utils.AppConstant;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.DatabaseHelperPlayBack;
import com.sdaemon.oakstudiotv.utils.Utilities;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.MediaDrm.KEY_TYPE_OFFLINE;
import static android.media.MediaDrm.KEY_TYPE_RELEASE;
import static android.os.Build.VERSION.SDK_INT;
import static com.google.android.exoplayer2.C.WIDEVINE_UUID;

//import static androidx.constraintlayout.Constraints.TAG;

public class Rajendra extends AppCompatActivity implements View.OnClickListener, PreviewView.OnPreviewChangeListener, NewTrackSelection1.GetReso {

    Runnable runnable1;
//    Context context = getContext();
    Context context;
    private int mResumeWindow;
    String getToken;

    int bitrate1,selectedPosition,selectedBitrate;
    String resolution="quality";
    private long mResumePosition;
    NewTrackSelection1.GetReso getReso;
    private boolean isShowingTrackSelectionDialog;
    FragmentActivity fragmentActivity;
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
    DefaultBandwidthMeter meter=new DefaultBandwidthMeter();
    //    private BandwidthMeter bandwidthMeter;
    private DataSource.Factory mediaDataSourceFactory;
    private DataSource.Factory dataSourceFactory;
    private Handler mainHandler;
    private Boolean playWhenReady = false;
    private int currentWindow = -1;
    //  private long playbackPosition = 87484;
    private long playbackPosition = 0;
    private ProgressBar progressBar;
    private ImageView ivHideControllerButton, ivSettings, ivBack, ivSubTitle, ivDownload, ivPlayDownload, downloadButton, delete,ivbackward,ivforward;
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
    long milliseconds=0;

    //drm
    public static final String PREFER_EXTENSION_DECODERS = "prefer_extension_decoders";
    public static final String DRM_SCHEME_UUID_EXTRA = "drm_scheme_uuid";
    public static final String DRM_LICENSE_URL = "https://oakstudio.keydelivery.southcentralus.media.azure.net/Widevine/?kid=e0c90a42-1e00-47a7-9071-9726e9dbc448";
    public static final String DRM_KEY_REQUEST_PROPERTIES = "drm_key_request_properties";
    public static final String EXTRA_IS_OFFLINE = "extra_is_offline";
    private EventLogger eventLogger;
    OfflineLicenseHelper offlineLicenseHelper;
    byte [] offlineLicenseKeySetId2;
    MediaDrm mediaDrm;
    DrmSession drmSession;

    public static final String DRM_SCHEME_EXTRA = "drm_scheme";
    public static final String DRM_LICENSE_URL_EXTRA = "drm_license_url";
    public static final String DRM_KEY_REQUEST_PROPERTIES_EXTRA = "drm_key_request_properties";
    public static final String DRM_MULTI_SESSION_EXTRA = "drm_multi_session";
    public static final String PREFER_EXTENSION_DECODERS_EXTRA = "prefer_extension_decoders";
    public static final String ABR_ALGORITHM_EXTRA = "abr_algorithm";

    HttpMediaDrmCallback drmCallback;
    DefaultDrmSessionManager drmSessionManager;
    DefaultDrmSessionManager  drmSessionManager1;
    DefaultDrmSessionManager<FrameworkMediaCrypto> drmManager;
    Handler handler;
    RenderersFactory renderersFactory;
    RenderersFactory renderersFactory1;
    FrameworkMediaDrm frameworkMediaDrm;
    String drmLicenseUrl;
    String[] keyRequestPropertiesArray;
    //
    DefaultHttpDataSourceFactory httpDataSourceFactory;
    SingleSampleMediaSource subtitleSource;


    private AppSession appSession;
    int userid,contentid;
    private String type = "";
    public String TAG="play";
    ArrayList<VideoDTO> list;
    private int offLinePosition = -1;
    int bitrate=0;
    ArrayList<Integer> bitRateList = new ArrayList<>();
    Bundle savedInstanceState;
    private DatabaseHelperPlayBack databaseHelperPlayBack;
    String strTemp = "0";
    String strTemp1 = "0";

    private ArrayList<PlaybackDTO> playBackIdList;
    boolean isInitializePlayer = false;
    RelativeLayout rlOakstudio;
    Utilities utilities;
    MediaSource[] mediaSources;
    MediaCodecInfo codecInfo;
    long time=0;




    private PreviewTimeBar previewTimeBar;
    public ImageView imageView1;
    ExoPlayerManager exoPlayerManager;

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG,"onRecive download");
            if(intent.getAction().equals("hellp")){
                if (SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    HashMap<Uri, Integer> hashMap = (HashMap<Uri, Integer>)intent.getSerializableExtra("map");
                    //int progress1= intent.getIntExtra("progress", 0);
                    //  Log.i(TAG,"notification "+progress1);
                    Set keys = hashMap.keySet();
                    for (Iterator i = keys.iterator(); i.hasNext(); ) {
                        Uri key = (Uri) i.next();
                        Integer value =  hashMap.get(key);
                        if(key.toString().equals(streamingUrl.toString()))
                        {
                            Log.i("StreamingURL(braocast)",""+streamingUrl);
                            Log.i("yes","notifi_p "+key.toString() + " = " + value);
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState = new Bundle();
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_video_player);
        context = this;
        utilities = Utilities.getInstance(context);
        try {
            appSession = AppSession.getInstance(getApplicationContext());
            if (appSession!=null){
                userid=appSession.getUserDTO().getResult().getCustomerId();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        playBackIdList = new ArrayList<>();
        databaseHelperPlayBack = new DatabaseHelperPlayBack(this);

        Log.i("Playbackposition 1",""+playbackPosition);
        Log.i("Playbackposition 2",""+milliseconds);



        // drmCallback = new HttpMediaDrmCallback("http://myAudience",
        drmCallback = new HttpMediaDrmCallback("https://oakstudio.keydelivery.southcentralus.media.azure.net/Widevine/?kid=4361ec0e-87c2-45ff-8e6e-ea77248d75ef",
                new DefaultHttpDataSourceFactory("user-agent"));
       /* Map<String, String> keyRequestProperties;
            keyRequestProperties = new HashMap<>();
            keyRequestProperties.put("Authorization","Bearer=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiIxY2YxNWQ4MS02MmQ3LTQ1YTYtOWRkNC0xMGRkN2NjMGNjMzYiLCJpc3MiOiJodHRwczovL2JyZWFrZG93bi5tZSIsImF1ZCI6IkJyZWFrZG93blVzZXIiLCJleHAiOjE1NzMwMDQ4NTAsIm5iZiI6MTU3Mjk2MTU5MH0.UX6B2EOgTbZRFkLtSKK8cKIJFCyS3TktPRO0ADxNfHE");
*/
        drmCallback.setKeyRequestProperty("Authorization","Bearer=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiI0MzYxZWMwZS04N2MyLTQ1ZmYtOGU2ZS1lYTc3MjQ4ZDc1ZWYiLCJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6bWF4dXNlcyI6IjgwIiwibmJmIjoxNTc3MTY4MDMyLCJleHAiOjE1NzcxOTIzMzIsImlzcyI6Im15SXNzdWVyIiwiYXVkIjoibXlBdWRpZW5jZSJ9.2YZL0M-yp7CHD6Kt7mmt8O3GEb8BW2gzqI0GcKd-4KU");
//        drmCallback.setKeyRequestProperty("Authorization","Bearer=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiIwNWIzMWExZS0zMDFjLTRiYzgtYWQxYS01NDlhNDYzZTVhNWMiLCJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6bWF4dXNlcyI6IjEwMCIsIm5iZiI6MTU3OTc1ODU4MSwiZXhwIjoxNTc5ODE4ODgxLCJpc3MiOiJteUlzc3VlciIsImF1ZCI6Im15QXVkaWVuY2UifQ.6XkPa7wwL72Bk050l_EKW9OgBGrE_aQBe19rQdJpVb0");

        try {
            drmSessionManager = new DefaultDrmSessionManager(WIDEVINE_UUID,
                    FrameworkMediaDrm.newInstance(WIDEVINE_UUID), drmCallback, null, handler, null,true,3);
        } catch (UnsupportedDrmException e) {
            e.printStackTrace();
        }






        Bundle bundle = getIntent().getExtras();
        IntentFilter filter = new IntentFilter("hellp");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
        if (bundle != null) {
            {
                if (bundle.containsKey("TYPE"))
                    type = bundle.getString("TYPE");
                if (bundle.containsKey("PLAYBACK_POSITION")) {
                    strTemp = bundle.getString("PLAYBACK_POSITION");
                    playbackPosition = Long.parseLong(strTemp);
                    Log.i(getClass().getName(), "===========playbackPosition : " + playbackPosition);
                }


                if (bundle.containsKey("tokenvalue"))
                    getToken = bundle.getString("tokenvalue");
//                Log.i("toj",getToken);


                if (bundle.containsKey("OFF_LINE_POSITION"))
                    offLinePosition = bundle.getInt("OFF_LINE_POSITION");

                if (bundle.containsKey("PN_JSON_OBJECT")) {
                    String jsonData = bundle.getString("PN_JSON_OBJECT");
                    Log.i("Offline Json",""+jsonData);

                    if (!TextUtils.isEmpty(jsonData)) {
                        list = new ArrayList<VideoDTO>();

                        list = new Gson().fromJson(jsonData, new TypeToken<ArrayList<VideoDTO>>() {
                        }.getType());
                    }
                    Log.i(getClass().getName(), "===========VideoUrl SIZE: " + list.size());

                }

                if (bundle.containsKey("KEY_WatchContentID")){
                    contentid=bundle.getInt("KEY_WatchContentID");
                    Log.i("WatchContentID",""+contentid);
                }

                if (bundle.containsKey("KEY_MOVIE_TIME")){
                    strTemp1 = bundle.getString("KEY_MOVIE_TIME");
                    time = Long.parseLong(strTemp1);
//                    time=bundle.getInt("KEY_MOVIE_TIME");
                    Log.i("Movie Time 1",""+time);
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


        previewTimeBar = playerView.findViewById(R.id.exo_progress);
        imageView1 = (ImageView) playerView.findViewById(R.id.imageView2);


        previewTimeBar = playerView.findViewById(R.id.exo_progress);
        imageView1 = (ImageView) findViewById(R.id.imageView2);

        ivbackward=(ImageView)findViewById(R.id.progress_reverse);
        ivbackward.setOnClickListener(this);


        ivforward=(ImageView)findViewById(R.id.progress_forward);
        ivforward.setOnClickListener(this);


        previewTimeBar.addOnPreviewChangeListener(this);
        exoPlayerManager = new ExoPlayerManager(playerView, previewTimeBar,
                imageView1, "https://bitdash-a.akamaihd.net/content/MI201109210084_1/thumbnails/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.jpg", Rajendra.this, mediaSource);
//        exoPlayerManager.play(Uri.parse("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"));

        previewTimeBar.setPreviewLoader(exoPlayerManager);



        playerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean touch=playerView.isControllerVisible();
                if(touch)
                {
                    ivbackward.setVisibility(View.VISIBLE);
                    ivforward.setVisibility(View.VISIBLE);
                }
                else{
                    ivbackward.setVisibility(View.GONE);
                    ivforward.setVisibility(View.GONE);
                }



                return false;
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rlOakstudio.setVisibility(View.VISIBLE);
                playerView.setVisibility(View.VISIBLE);
            }
        }, 2000);


        // initializePlayer();

//-------------------------------------------------------------------
        if (playbackPosition > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

//                    progressBar.setVisibility(View.VISIBLE);

                    dialogResumePlayBack(getResources().getString(R.string.would_you_like_to_resume_watching), playbackPosition);
                }
            }, 2000);


        } else {
            playbackPosition = 0;
            isInitializePlayer = true;
            // initializePlayer();

        }
//--------------------------------------------------------

//        if (time > 0) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    dialogResumePlayBack(getResources().getString(R.string.would_you_like_to_resume_watching), time);
//                }
//            }, 2000);
//
//
//        } else {
//            time = 0;
//            isInitializePlayer = true;
//            // initializePlayer();
//
//        }


//---------------------------------------------------------------





        if (isInitializePlayer) {
            initializePlayer();
            Log.i("Initialize Player","damy");

        }
        //  streamingUrl = "https://sdaemonazuremedia-inct.streaming.media.azure.net/988db5f4-0a7a-4ab2-983a-88e6e1c2cfe3/AQUAMAN.ism/manifest(format=m3u8-aapl).m3u8";
        //  streamingUrl =  "https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_4x3/bipbop_4x3_variant.m3u8";
        //  pre_streamingUrl = "https://sdaemonazuremedia-inct.streaming.media.azure.net/14edec6b-ed6c-497e-98f4-97146647ccb0/AQUAMAN Trailer 2 (4K ULTRA HD) .ism/manifest(format=m3u8-aapl).m3u8";

        //    subTitleUrl = "https://sdaemonazuremedia-inct.streaming.media.azure.net/463d929c-4198-4776-829f-e5068f95f1f3/AQUAMAN%20Trailer%202%20(4K%20ULTRA%20HD)%20NEW%202018_aud_SpReco.vtt";
        subTitleUrl = "https://amssamples.streaming.mediaservices.windows.net/bc57e088-27ec-44e0-ac20-a85ccbcd50da/TOS-en.vtt";
        for (int i = 0; i < list.size(); i++) {
            songsList.add(list.get(i).getVideoUrl());
        }

        if (type.equalsIgnoreCase("download")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    ivPlayDownload.setVisibility(View.VISIBLE);
                    ivPlayDownload.setVisibility(View.GONE);

                    streamingUrl = songsList.get(offLinePosition);
                    Log.i("StreamingURL(oncreate)",""+streamingUrl);
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
            //  playbackPosition = savedInstanceState.getLong(KEY_POSITION);

        }

//      shouldAutoPlay = true;
//     mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<DataSource>) bandwidthMeter);

        shouldAutoPlay = true;
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener) bandwidthMeter);

        AppSubClass application = (AppSubClass) getApplication();
        useExtensionRenderers = application.useExtensionRenderers();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onStart() {
        super.onStart();
        exoPlayerManager.onStart();
        //  if (Util.SDK_INT > 23)
        if (isInitializePlayer) {
            initializePlayer();
            Log.i("Initialize Player","start");

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        exoPlayerManager.onResume();
        //  if (Util.SDK_INT <= 23 || player == null)
        if (isInitializePlayer) {
          // initializePlayer();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        progressBar.setVisibility(View.INVISIBLE);
        exoPlayerManager.onPause();


        //  if (Util.SDK_INT <= 23)
        //  releasePlayer();

        if (milliseconds==0) {
            if (player!=null) {
                milliseconds = player.getCurrentPosition();
// long minutes = (milliseconds / 1000) / 60;
                long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
// long seconds = (milliseconds / 1000);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
                Log.d(TAG, "minutes" + minutes);
                Log.i(TAG, "seconds" + seconds);

                if (type.equalsIgnoreCase("download")) {
                    Log.i("Download ", "download");
                } else {
                    postContentTime(milliseconds);
                    Log.i("Download ", "not download");

                }

            }
        }

        playBackIdList = databaseHelperPlayBack.getAllTeachers();
        String urlId = "Rajendra";
        Log.i(String.valueOf(Rajendra.this), "========= playBackIdList : " + playBackIdList.size());
        if (playBackIdList != null && playBackIdList.size() < 1) {
            databaseHelperPlayBack.addTeachersDetail(urlId, "" + player.getCurrentPosition());

        } else {
            for (int i = 0; i < playBackIdList.size(); i++) {
                Log.i(String.valueOf(Rajendra.this), "=========URL : " + playBackIdList.get(i).getUrlId());
                Log.i(String.valueOf(Rajendra.this), "=========URL ID : " + playBackIdList.get(i).getPlayBackPosition());
                if (player!=null) {
                    Log.i(String.valueOf(Rajendra.this), "========= player.getCurrentPosition() : " + player.getCurrentPosition());
                }
                if (urlId.equalsIgnoreCase(playBackIdList.get(i).getUrlId())) {
                    //  Toast.makeText(Rajendra.this, " if" + offlineIdList.get(i).getPlayBackPosition(), Toast.LENGTH_SHORT).show();
                    if (player!=null) {
                        databaseHelperPlayBack.updateTeachers(urlId, "" + player.getCurrentPosition());
                    }
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
        exoPlayerManager.onStop();
        //   if (Util.SDK_INT > 23)
        if (Util.SDK_INT <= 23)
            releasePlayer();
    }

    @Override
    public void onBackPressed() {
        if (Util.SDK_INT <= 23)
            releasePlayer();
        // super.onBackPressed();
        // ActivityCompat.finishAffinity(this);
        finish();

    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (Util.SDK_INT <= 23)

                    releasePlayer();
                onBackPressed();
                break;

            case R.id.settings:
                progressBar.setVisibility(View.INVISIBLE);
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


                case R.id.progress_reverse:
                    player.seekTo( player.getCurrentPosition()- 5000);
                //  alert();
                break;



                case R.id.progress_forward:
                    player.seekTo(player.getCurrentPosition() + 5000);
                //  alert();
                break;


            case R.id.download_button:
                AppSubClass application = (AppSubClass) getApplication();
                downloadTracker = application.getDownloadTracker();
               /*try {
                   DownloadService.start(this, DemoDownloadService.class);
               } catch (IllegalStateException e) {
                   DownloadService.startForeground(this, DemoDownloadService.class);
               }*/
                //   downloadTracker.toggleDownload(this, sample.name, uriSample.uri, uriSample.extension);
                Bundle bundle = new Bundle();
                bundle.putInt("NotificationId", 1);
                bundle.putInt("empID", 2);
                int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
                bundle.putInt("visitor", uniqueInt);
      //          bundle.putString("token",Base64.encodeToString(offlineLicenseKeySetId2,Base64.DEFAULT));
              bundle.putString("token","dad");
                int days=2;
                String currentDateTimeString =
                        //  DateFormat.getDateTimeInstance().format(new Date());
                        DateFormat.getDateInstance().format(new Date());
                Calendar startDate = Calendar.getInstance();
                int mYear = startDate.get(Calendar.YEAR);
                int mMonth = startDate.get(Calendar.MONTH)+1;
                int mDay = startDate.get(Calendar.DAY_OF_MONTH) ;
                startDate.set(mYear, mMonth, mDay);
                long startDateMillis = startDate.getTimeInMillis();




                Calendar endDate = Calendar.getInstance();
                int mYear_2days = endDate.get(Calendar.YEAR);
                int mMonth_2days = endDate.get(Calendar.MONTH)+1;
                int mDay_2days = endDate.get(Calendar.DAY_OF_MONTH)+days ;
                endDate.set(mYear_2days,mMonth_2days,mDay_2days);
                long endDateMillis = endDate.getTimeInMillis();
                long differenceMillis = endDateMillis - startDateMillis;
                int daysDifference = (int) (differenceMillis / (1000 * 60 * 60 * 24));
                bundle.putString("date", currentDateTimeString);
                Log.i(" Download current date",""+currentDateTimeString);
                bundle.putLong("end_date_time",endDateMillis);
                Log.i(" Download end date",""+endDateMillis);

                Intent yesReceive2 = new Intent(context,DownloadTracker.NotificationReceiver.class);
                yesReceive2.putExtras(bundle);
                yesReceive2.setAction(AppConstant.YES_ACTION);
                PendingIntent pendingIntentYes2 = PendingIntent.getBroadcast(context, uniqueInt, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (1 * 1000), pendingIntentYes2);
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




                   /* ArrayList<VideoDTO> videoList = new ArrayList<>();
                    if (videoList == null)
                        videoList = new ArrayList<>();
                    videoList.addAll(appSession.getOfflineDownloadList());
                    videoList.add(new VideoDTO("" + streamingUrl, "3/25/2019"));

                    appSession.setOfflineDownloadList(videoList);
*/
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


              /*  mediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                        .setPlaylistParserFactory(
                                // new DefaultHlsPlaylistParserFactory(getOfflineStreamKeys(Uri.parse(streamingUrl))))
                                new DefaultHlsPlaylistParserFactory(getOfflineStreamKeys(Uri.parse(list.get(offLinePosition).getVideoUrl()))))
                        //  .createMediaSource(Uri.parse(streamingUrl));
                        .createMediaSource(Uri.parse(list.get(offLinePosition).getVideoUrl()));
               *//* mediaSource = mediaSources.length == 1 ? mediaSources[0]
                        : new ConcatenatingMediaSource(mediaSources);*//*
                player.prepare(mediaSource);*/


                mediaSource = new DashMediaSource.Factory(dataSourceFactory)
                        .setManifestParser(
                                new FilteringManifestParser<>(new DashManifestParser(), getOfflineStreamKeys(Uri.parse(list.get(offLinePosition).getVideoUrl()))))
                        .createMediaSource(Uri.parse(streamingUrl));
                Log.i("StreamingURL ivclick",""+streamingUrl);

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
        //  outState.putLong(KEY_POSITION, playbackPosition);
        super.onSaveInstanceState(outState);
    }



    protected final DefaultDrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManager(
            final String userAgent, UUID uuid, String licenseUrl, String[] keyRequestPropertiesArray, boolean multiSession) {
        HttpDataSource.Factory licenseDataSourceFactory =
                ((AppSubClass) getApplication()).buildHttpDataSourceFactory();
        HttpMediaDrmCallback drmCallback =
                new HttpMediaDrmCallback(licenseUrl, licenseDataSourceFactory);
        if (keyRequestPropertiesArray != null) {
            for (int i = 0; i < keyRequestPropertiesArray.length - 1; i += 2) {
                drmCallback.setKeyRequestProperty(keyRequestPropertiesArray[i],
                        keyRequestPropertiesArray[i + 1]);
            }
        }
        releaseMediaDrm();
        //  drmSessionManager.setMode(DefaultDrmSessionManager.MODE_PLAYBACK, appSession.getOfflinekey().getBytes());
           /* try {
                MediaDrmCallback drmCallback = new HttpMediaDrmCallback("https://oakstudio.keydelivery.southcentralus.media.azure.net/Widevine/?KID=91d4591e-3629-4ddc-b2c5-cac8cb9ab9ec",
                        new DefaultHttpDataSourceFactory(userAgent));
                drmSessionManager = DefaultDrmSessionManager.newWidevineInstance(drmCallback, null,
                        null, null);
            } catch (UnsupportedDrmException e) {
                throw new IllegalStateException(e);
            }*/
        return drmSessionManager;
    }
    private void releaseMediaDrm() {
        if (mediaDrm != null) {
            mediaDrm.release();
            mediaDrm = null;
        }
    }



    private void initializePlayer() {
        playerView.requestFocus();
        AdaptiveTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
       trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        lastSeenTrackGroupArray = null;
//        player.useDummySurfaceWorkaround(true);





        if (type.equalsIgnoreCase("download")) {

        } else {
            if (SDK_INT > 8) {
                ivSettings.setVisibility(View.GONE);

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory("userAgent");
                httpDataSourceFactory.getDefaultRequestProperties().set("Authorization", "Bearer=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiIwOTZlNDczYi0zZGUzLTQzYWUtYTI4ZC0zYjg2ZmJmNzBkYjkiLCJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6bWF4dXNlcyI6IjEwMCIsIm5iZiI6MTU4MDI5Mjg3MywiZXhwIjoxNTgwODkzMTczLCJpc3MiOiJteUlzc3VlciIsImF1ZCI6Im15QXVkaWVuY2UifQ.XrV25wI65xN6n2hQuFxhN_Uj-p1dpTavfNl7ALJLsZ4");
//                httpDataSourceFactory.getDefaultRequestProperties().set("Authorization", "Bearer=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiIwNWIzMWExZS0zMDFjLTRiYzgtYWQxYS01NDlhNDYzZTVhNWMiLCJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6bWF4dXNlcyI6IjEwMCIsIm5iZiI6MTU3OTc1ODU4MSwiZXhwIjoxNTc5ODE4ODgxLCJpc3MiOiJteUlzc3VlciIsImF1ZCI6Im15QXVkaWVuY2UifQ.6XkPa7wwL72Bk050l_EKW9OgBGrE_aQBe19rQdJpVb0");

                DataSource dataSource = httpDataSourceFactory.createDataSource();
                DashManifest dashManifest = null; //movie url
                try {
                    dashManifest = DashUtil.loadManifest(dataSource,
                            Uri.parse("https://oakstudio-usso.streaming.media.azure.net/00dc2eda-0330-47f2-b2a8-fa8447e1f0de/THE-LION-KING-Trailer-(4K-ULTRA-.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DrmInitData drmInitData = null;
                try {
                    if (dashManifest!=null) {
                        drmInitData = DashUtil.loadDrmInitData(dataSource, dashManifest.getPeriod(0));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    offlineLicenseHelper = OfflineLicenseHelper.newWidevineInstance("https://oakstudio.keydelivery.southcentralus.media.azure.net/Widevine/?kid=05b31a1e-301c-4bc8-ad1a-549a463e5a5c", httpDataSourceFactory);
                } catch (UnsupportedDrmException e) {
                    e.printStackTrace();
                }
                try {
                    if (drmInitData!=null) {
                        offlineLicenseKeySetId2 = offlineLicenseHelper.downloadLicense(drmInitData);
                        Log.d(TAG, "offlineLicenseKey: " + offlineLicenseKeySetId2.toString());
                        byte[] offlineKeySetId = Base64.decode(offlineLicenseKeySetId2, Base64.DEFAULT);
                        Pair<Long, Long> p = offlineLicenseHelper.getLicenseDurationRemainingSec(offlineLicenseKeySetId2);
                        Log.e(TAG, "download done : " + p.toString());
                        if (type.equalsIgnoreCase("download")) {
                        } else {
                            AppSession.getInstance(context).setOffline(Base64.encodeToString(offlineLicenseKeySetId2, Base64.DEFAULT));
                        }
                        Log.d(TAG, "offlineLicenseKeySetId: " + offlineKeySetId);
                    }
                } catch (DrmSession.DrmSessionException e) {
                    e.printStackTrace();
                }
            }
        }

        Intent intent = getIntent();
        String drmSchemeExtra = intent.hasExtra(DRM_SCHEME_EXTRA) ? DRM_SCHEME_EXTRA
                : DRM_SCHEME_UUID_EXTRA;
        boolean preferExtensionDecoders = intent.getBooleanExtra(PREFER_EXTENSION_DECODERS, false);
        UUID drmSchemeUuid = intent.hasExtra(DRM_SCHEME_UUID_EXTRA)
                ? UUID.fromString(intent.getStringExtra(DRM_SCHEME_UUID_EXTRA)) : null;
        DrmSessionManager<FrameworkMediaCrypto> drmSessionManager1 = null;
        if (drmSchemeUuid != null) {
            drmLicenseUrl = intent.getStringExtra(DRM_LICENSE_URL);
            keyRequestPropertiesArray = intent.getStringArrayExtra(DRM_KEY_REQUEST_PROPERTIES);
            Map<String, String> keyRequestProperties;
            if (keyRequestPropertiesArray == null || keyRequestPropertiesArray.length < 2) {
                keyRequestProperties = null;
            } else {
                keyRequestProperties = new HashMap<>();
                for (int i = 0; i < keyRequestPropertiesArray.length - 1; i += 2) {
                    keyRequestProperties.put(keyRequestPropertiesArray[i],
                            keyRequestPropertiesArray[i + 1]);
                }
            }
        }
        drmManager = buildDrmSessionManager("Hello Agent", C.WIDEVINE_UUID,drmLicenseUrl,keyRequestPropertiesArray,true);



        if (type.equalsIgnoreCase("download")) {
            //  byte[] offlineKeySetId = Base64.decode(appSession.getOfflinekey(), Base64.DEFAULT);
//            if (offlineKeySetId!=null){

            byte[] offlineKeySetId = Base64.decode(getToken, Base64.DEFAULT);

            // byte [] off={107,115,105,100,49,69,51,51,56,52,68,70};
            drmManager.setMode(DefaultDrmSessionManager.MODE_PLAYBACK,offlineKeySetId);
            try {
                MediaDrm drm= new MediaDrm(WIDEVINE_UUID);
                try {
                    byte a[]=drm.openSession();
                    int a1=KEY_TYPE_OFFLINE;
                    int b1=KEY_TYPE_RELEASE;
                    //  drm.removeKeys(a);
                } catch (NotProvisionedException e) {
                    e.printStackTrace();
                } catch (ResourceBusyException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedSchemeException e) {
                e.printStackTrace();
            }
//            }
        }
        else {
        }





















        //  byte[] offlineKeySetId = Base64.decode("Kd9O5Uw15o38r5rLYMeOtxWxF9UVmqT8+SbUBf6Dbyq/n/xsMN31dg==", Base64.DEFAULT);
        //   drmManager.setMode(DefaultDrmSessionManager.MODE_PLAYBACK,offlineKeySetId);
        renderersFactory = new DefaultRenderersFactory(this,drmSessionManager);
        renderersFactory1=new DefaultRenderersFactory(this,drmManager);
        if (type.equalsIgnoreCase("download")) {
            Log.i("fffsdf","1");
            player = ExoPlayerFactory.newSimpleInstance(renderersFactory1, trackSelector);
        } else {
            Log.i("fffsdf","2");
            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);
        }




        //    player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            playerView.setPlayer(player);
            playerView.getSubtitleView().setVisibility(View.VISIBLE);
            player.addListener(new PlayerEventListener());
            playWhenReady = shouldAutoPlay;



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
//        player.prepare(hlsMediaSource);
        //player.seekTo(0, C.TIME_UNSET);


        player.setPlayWhenReady(true);
        // player.setRepeatMode(Player.REPEAT_MODE_ALL);
        player.addTextOutput(new TextOutput() {
            @Override
            public void onCues(List<Cue> cues) {
                progressBar.setVisibility(View.INVISIBLE);
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
//            player.seekTo(currentWindow, time);
            Log.i("PlaybackTime",""+playbackPosition);

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

//
//        DataSource.Factory datasource=new DefaultHttpDataSourceFactory("ua",meter);
//        DashChunkSource.Factory data=new DefaultDashChunkSource.Factory(datasource);
        DefaultHlsExtractorFactory defaultHlsExtractorFactory =
                new DefaultHlsExtractorFactory(DefaultTsPayloadReaderFactory.FLAG_ALLOW_NON_IDR_KEYFRAMES);



      /*  MediaSource mediaSource = new HlsMediaSource.Factory(
                mediaDataSourceFactory).setExtractorFactory(defaultHlsExtractorFactory).createMediaSource(uri);
        */

        final String cred = "username" + ":" + "password";
        final String auth = "Basic "+ Base64.encodeToString(cred.getBytes(),Base64.URL_SAFE|Base64.NO_WRAP);
        DefaultHttpDataSource dataSource = new DefaultHttpDataSource("com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory@c5b2c3b", null);
        dataSource.setRequestProperty("Authorization","Bearer=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiJjMzBhYzRhNy1hOTAyLTQzZDQtOWNiYi0wY2EwODM2NDc1MDMiLCJpc3MiOiJodHRwczovL2JyZWFrZG93bi5tZSIsImF1ZCI6IkJyZWFrZG93blVzZXIiLCJleHAiOjE1NzE1MDk4MTMsIm5iZiI6MTU3MTQ2NjU1M30.FouVa7tE_VLPokl8jSL2uenlzBomHcY-S_sTv17IggI");
//        dataSource.setRequestProperty("Authorization","Bearer=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiIwNWIzMWExZS0zMDFjLTRiYzgtYWQxYS01NDlhNDYzZTVhNWMiLCJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6bWF4dXNlcyI6IjEwMCIsIm5iZiI6MTU3OTc1ODU4MSwiZXhwIjoxNTc5ODE4ODgxLCJpc3MiOiJteUlzc3VlciIsImF1ZCI6Im15QXVkaWVuY2UifQ.6XkPa7wwL72Bk050l_EKW9OgBGrE_aQBe19rQdJpVb0");

        dataSource.setRequestProperty("Accept", "...");

        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(getApplicationContext(), "yourApplicationName"), null);

        // or maps

        httpDataSourceFactory.getDefaultRequestProperties().set("Authorization", "Bearer=urn%3amicrosoft%3aazure%3amediaservices%3acontentkeyidentifier=5f5076de-4322-42f7-a533-6265f686d5b9&Audience=urn%3atest&ExpiresOn=4581880130&Issuer=http%3a%2f%2ftestacs.com%2f&HMACSHA256=%2bx77Qo0CeBzP4aCntfe2sVkbKeKguOUNAebHQb53sLc%3d");


        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), null, httpDataSourceFactory);


      /*  MediaSource mediaSource = new DashMediaSource.Factory(
                mediaDataSourceFactory).createMediaSource(uri);*/
//-------------------------------

//        DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory(getActivity(),
//                bandwidthMeter,
//                httpDataSourceFactory);
//        OkHttpDataSourceFactory okHttpDataSourceFactory = new OkHttpDataSourceFactory(okHttpClient,
//                userAgent,
//                bandwidthMeter);

//        int type=Util.inferContentType(uri);
//        switch (type){
//            case C.TYPE_SS:
//                return new SsMediaSource.Factory(new DefaultSsChunkSource.Factory(defaultDataSourceFactory), okHttpDataSourceFactory)
//                        .createMediaSource(uri);
//            case C.TYPE_DASH:
//                return new DashMediaSource.Factory(new DefaultDashChunkSource.Factory(defaultDataSourceFactory), okHttpDataSourceFactory)
//                        .createMediaSource(uri);
//            case C.TYPE_HLS:
//                return new HlsMediaSource.Factory(okHttpDataSourceFactory)
//                        .createMediaSource(uri);
//            case C.TYPE_OTHER:
//                return new ExtractorMediaSource.Factory(okHttpDataSourceFactory)
//                        .createMediaSource(uri);
//            default:
//                Toast.makeText(this, "Unsupported video type", Toast.LENGTH_LONG).show();
//                return null;
//        }
//-------------------------------------------
      /*  MediaSource mediaSource = new SsMediaSource.Factory(
                mediaDataSourceFactory).createMediaSource(uri);*/



      /*  MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(streamUrl);*/
        /*MediaSource mediaSource = new HlsMediaSource.Factory(
                dataSourceFactory).createMediaSource(uri);
*/


         DashMediaSource mediaSource =
               new DashMediaSource.Factory(
                       () -> {
                           HttpDataSource dataSource3 = new DefaultHttpDataSource("com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory@c5b2c3b",null);
                           // Set a custom authentication request header.
                           dataSource3.setRequestProperty("Authorization", "Bearer=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiIxY2YxNWQ4MS02MmQ3LTQ1YTYtOWRkNC0xMGRkN2NjMGNjMzYiLCJpc3MiOiJodHRwczovL2JyZWFrZG93bi5tZSIsImF1ZCI6IkJyZWFrZG93blVzZXIiLCJleHAiOjE1NzI5MDcwNjUsIm5iZiI6MTU3Mjg2MzgwNX0.oYg160Rv2OmmkEApyM4DdRHguv2lCYflmkoxBFfcn6k");
//                           dataSource3.setRequestProperty("Authorization", "Bearer=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiIwNWIzMWExZS0zMDFjLTRiYzgtYWQxYS01NDlhNDYzZTVhNWMiLCJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6bWF4dXNlcyI6IjEwMCIsIm5iZiI6MTU3OTc1ODU4MSwiZXhwIjoxNTc5ODE4ODgxLCJpc3MiOiJteUlzc3VlciIsImF1ZCI6Im15QXVkaWVuY2UifQ.6XkPa7wwL72Bk050l_EKW9OgBGrE_aQBe19rQdJpVb0");

                           dataSource3.setRequestProperty("Accept","....");
                           return dataSource3;
                       })
                       .createMediaSource(uri);




       /* HlsMediaSource mediaSource = new HlsMediaSource.Factory(
                dataType -> {
                    HttpDataSource dataSource1 =
                            new DefaultHttpDataSource("com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory@c5b2c3b",null);
                    if (dataType == C.DATA_TYPE_MEDIA) {
                        // The data source will be used for fetching media segments. We
                        // set a custom authentication request header.

                        dataSource.setRequestProperty("Authorization", "Bearer=urn%3amicrosoft%3aazure%3amediaservices%3acontentkeyidentifier=5f5076de-4322-42f7-a533-6265f686d5b9&Audience=urn%3atest&ExpiresOn=4581880130&Issuer=http%3a%2f%2ftestacs.com%2f&HMACSHA256=%2bx77Qo0CeBzP4aCntfe2sVkbKeKguOUNAebHQb53sLc%3d");
                        dataSource.setRequestProperty("Accept", "...");
                    }
                    return dataSource;
                }).setExtractorFactory(defaultHlsExtractorFactory).createMediaSource(uri);
*/



        // For subtitles
        if ((subTitleUrl == null || subTitleUrl == "false" || subTitleUrl.isEmpty())) {
            return mediaSource;
        }

        textFormat = Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT,
                Format.NO_VALUE, "en-us");
        //Log.e("srt link is",srt_link);
        Uri uriSubtitle = Uri.parse(subTitleUrl);
        /*MediaSource contentMediaSource = new HlsMediaSource.Factory(
                mediaDataSourceFactory).createMediaSource(uri);*/


        MediaSource contentMediaSource = new HlsMediaSource.Factory(
                dataType -> {
                    HttpDataSource dataSource1 =
                            new DefaultHttpDataSource("com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory@c5b2c3b", null);
                    if (dataType == C.DATA_TYPE_MEDIA) {
                        // The data source will be used for fetching media segments. We
                        // set a custom authentication request header.

                        dataSource.setRequestProperty("Authorization", "Bearer=urn%3amicrosoft%3aazure%3amediaservices%3acontentkeyidentifier=5f5076de-4322-42f7-a533-6265f686d5b9&Audience=urn%3atest&ExpiresOn=4581880130&Issuer=http%3a%2f%2ftestacs.com%2f&HMACSHA256=%2bx77Qo0CeBzP4aCntfe2sVkbKeKguOUNAebHQb53sLc%3d");
                        dataSource.setRequestProperty("Accept", "...");
                    }
                    return dataSource;
                }).createMediaSource(uri);





        String [] url={"https://amssamples.streaming.mediaservices.windows.net/bc57e088-27ec-44e0-ac20-a85ccbcd50da/TOS-en.vtt",
                "https://ams-samplescdn.streaming.mediaservices.windows.net/11196e3d-2f40-4835-9a4d-fc52751b0323/TOS-fr.vtt"};
        mediaSources = new MediaSource[url.length+1];
        for (int i = 0; i < url.length; i++) {
            //The Size must change depending on the Uris
            mediaSources[0] = mediaSource;
            if(i==0)
            {
                subtitleSource = new SingleSampleMediaSource(Uri.parse(url[i]), mediaDataSourceFactory,
                        // SingleSampleMediaSource subtitleSource = new SingleSampleMediaSource(Uri.parse(subTitleUrl), dataSourceFactory,
                        Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, Format.NO_VALUE, "en-us", null),
                        C.TIME_UNSET);
            }
            else if(i==1)
            {
                subtitleSource = new SingleSampleMediaSource(Uri.parse(url[i]), mediaDataSourceFactory,
                        // SingleSampleMediaSource subtitleSource = new SingleSampleMediaSource(Uri.parse(subTitleUrl), dataSourceFactory,
                        Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, Format.NO_VALUE, "fr", null),
                        C.TIME_UNSET);
                //Uri uri = Uri.parse("http://www.storiesinflight.com/js_videosub/jellies.srt");
            }
            //   mediaSources[0] = contentMediaSource;
            //MediaSource subtitleSource = new SingleSampleMediaSource(uriSubtitle, mediaDataSourceFactory, textFormat, C.TIME_UNSET);
            mediaSources[i+1] = subtitleSource;
        }
        /*MediaSource[] mediaSources = new MediaSource[2]; //The Size must change depending on the Uris
        mediaSources[0] = mediaSource;
     //   mediaSources[0] = contentMediaSource;
        //MediaSource subtitleSource = new SingleSampleMediaSource(uriSubtitle, mediaDataSourceFactory, textFormat, C.TIME_UNSET);
        SingleSampleMediaSource subtitleSource = new SingleSampleMediaSource(Uri.parse(subTitleUrl), mediaDataSourceFactory,
     // SingleSampleMediaSource subtitleSource = new SingleSampleMediaSource(Uri.parse(subTitleUrl), dataSourceFactory,
                Format.createTextSampleFormat(null, MimeTypes.APPLICATION_SUBRIP, Format.NO_VALUE, "en-us", null),
                C.TIME_UNSET);
        //Uri uri = Uri.parse("http://www.storiesinflight.com/js_videosub/jellies.srt");
        mediaSources[1] = subtitleSource;*/
/*
 SingleSampleMediaSource subtitleSource2 = new SingleSampleMediaSource(Uri.parse("https://api.videoindexer.ai/southcentralus/Accounts/58afb946-2897-4b4b-a3eb-9bb84ba02073/Videos/d25012fa22/Captions?format=Vtt&language=hi-IN&accessToken=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJBY2NvdW50SWQiOiI1OGFmYjk0Ni0yODk3LTRiNGItYTNlYi05YmI4NGJhMDIwNzMiLCJBbGxvd0VkaXQiOiJUcnVlIiwiRXh0ZXJuYWxVc2VySWQiOiIxOmxpdmUuY29tOjAwMDM3RkZFMUQxM0MwMUYiLCJVc2VyVHlwZSI6Ik1pY3Jvc29mdENvcnBBYWQiLCJpc3MiOiJodHRwczovL3NjdXMudmlkZW9pbmRleGVyLmFpLyIsImF1ZCI6Imh0dHBzOi8vc2N1cy52aWRlb2luZGV4ZXIuYWkvIiwiZXhwIjoxNTcxODI5MDEwLCJuYmYiOjE1NzE4MjUxMTB9.Juz0YlYrkUghI47A5I0iNZxFn6oiaJjdL-QIyIQtWtQ"), mediaDataSourceFactory,
     // SingleSampleMediaSource subtitleSource = new SingleSampleMediaSource(Uri.parse(subTitleUrl), dataSourceFactory,
                Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, Format.NO_VALUE, "hi-IN", null),
                C.TIME_UNSET);
        //Uri uri = Uri.parse("http://www.storiesinflight.com/js_videosub/jellies.srt");
        mediaSources[2] = subtitleSource2;*/
        MergingMediaSource mergedSource = new MergingMediaSource(mediaSources);

// Prepare the player with the source.


        return mergedSource;
      //  return mediaSource;


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

        if (player!=null) {
            playbackPosition = player.getCurrentPosition();
            Log.i("Playbackposition",""+playbackPosition);

//            time = player.getCurrentPosition();
//            Log.i("Playbackposition",""+time);

        currentWindow = player.getCurrentWindowIndex();
        playWhenReady = player.getPlayWhenReady();

        }

    }


    @Override
    public void onStartPreview(PreviewView previewView, int progress) {


    }

    @Override
    public void onStopPreview(PreviewView previewView, int progress) {

    }

    @Override
    public void onPreview(PreviewView previewView, int progress, boolean fromUser) {

    }

    @Override
    public String getResoText(String resoText) {
        Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
        return resoText;
    }


    private class PlayerEventListener implements EventListener {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            updateButtonVisibilities();
            int Quality, QualityIndex;
            // The video tracks are no supported in this device.


            if (type.equalsIgnoreCase("download")) {
                progressBar.setVisibility(View.INVISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                currentWindow = offLinePosition;
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                ivBack.setVisibility(View.VISIBLE);
                currentWindow = player.getCurrentWindowIndex();
            }
            streamingUrl = songsList.get(currentWindow);
            Log.i("StreamingURL(ontrack)",""+streamingUrl);

            //  Toast.makeText(Rajendra.this, ""+currentWindow, Toast.LENGTH_SHORT).show();


            if (trackGroups != lastSeenTrackGroupArray) {
                if (trackSelector!=null) {
                    MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                    if (mappedTrackInfo != null) {
                        if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO) == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                            Toast.makeText(Rajendra.this, "Error unsupported track", Toast.LENGTH_SHORT).show();
                        }
                    }
                    lastSeenTrackGroupArray = trackGroups;
                }
            }
//--------------------------------------

//            List<Format> videoFormats = HlsRendererBuilder.VideoFormats;

//            if (trackGroups != null) {
//                ArrayList<String> contentItems = new ArrayList<String>();
//
//                if (trackGroups.length > 0) {
//                    contentItems.add("Data Saver");
//                    contentItems.add("Auto");
//                }
//
//                if (bitRateList != null)
//                    bitRateList.clear();
//                for (int i = 0; i < trackGroups.length; i++) {
//                    bitrate = 1;
////                Toast.makeText(Rajendra.this,""+trackGroups.get(i),Toast.LENGTH_LONG).show();
//                    Log.i("Track format", "" + trackGroups.get(i));
//                    TrackGroup group = trackGroups.get(i);
//                    for (int j = 0; j < group.length; j++) {
////                    Toast.makeText(context, ""+group.getFormat(j).bitrate, Toast.LENGTH_SHORT).show();
//                        bitrate = group.getFormat(j).bitrate;
//                        bitrate1 = bitrate / 1024;
//                        bitRateList.add(bitrate1);
//                        Log.i("Bitrate", "" + group.getFormat(j).bitrate);
//
//                    if (bitrate <= 500) {
//                        Log.i(TAG,"LOW");
//                        if (!contentItems.contains("Low"))
//                            contentItems.add("Low");
//
//                    } else if (bitrate > 500 && bitrate <= 1500) {
//                        Log.i(TAG,"MEDIUM");
//
//                        if (!contentItems.contains("Medium"))
//                            contentItems.add("Medium");
//
//                    } else if (bitrate > 3000) {
//                        Log.i(TAG,"HIGH");
//                        if (!contentItems.contains("High"))
//                            contentItems.add("High");
//
//
//                    }
//                }
//                showDialog(contentItems);
//            }
//        }


//---------------------------------------

//            for (int i = 0; i < trackGroups.length; i++) {
//                int currentQuality= trackGroups.get(0).getFormat(i).height;
//                Log.i("Current Quality","" +currentQuality);
//
//                Quality = currentQuality;
//                QualityIndex = i;
//
//                if (Quality == 720) {
//
//                    break;
//                }
//                Log.i("Quality","" +Quality);
//            }

//---------------------------------------------------------------------
//
//            for (int i=0;i<trackGroups.length;i++){
//                bitrate=1;
////                Toast.makeText(Rajendra.this,""+trackGroups.get(i),Toast.LENGTH_LONG).show();
//                Log.i("Track format",""+trackGroups.get(i));
//                resolution = resolution.toLowerCase();
//                TrackGroup group=trackGroups.get(i);
//                for (int j=0;j<group.length;j++){
////                    Toast.makeText(context, ""+group.getFormat(j).bitrate, Toast.LENGTH_SHORT).show();
//                   bitrate=group.getFormat(j).bitrate;
//                    bitrate1=bitrate/1024;
//                   bitRateList.add(bitrate1);
//                    Log.i("Bitrate",""+group.getFormat(j).bitrate);
//                }
//                if (resolution.equals("auto")) {
//                    resolution = "medium";
////                    this.selectedBitrate = 0;
//                }
//                int dataSaverBitrate = 0;
//                try {
//                    if (bitRateList != null && bitRateList.size() > 0)
//                        dataSaverBitrate = Collections.min(bitRateList);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//
//
//                Log.d(TAG, "setSelectedBitrate - DataSaver Bitrate:" + dataSaverBitrate);
//                int minBitRate = resolution.equals("data saver") ? 0 : resolution.equals("low") ? dataSaverBitrate
//                        : resolution.equals("medium") ? 500 : resolution.equals("high") ? 3000 : 3136;
//
//        int maxBitRate = resolution.equals("low") ? 500 : resolution.equals("medium") ? 1500 :
//                resolution.equals("high") ? 3136 : 5928;
//                for (int i1 = 0; i1 < bitRateList.size(); i1++) {
//                    int bitrate = bitRateList.get(i1);
//                    if (bitrate > minBitRate) {
//                        selectedPosition = i1 + 1;
//                        selectedBitrate = bitrate;
//                        break;
//                    }
//            if (bitrate <= maxBitRate) {
//                selectedPosition = i + 1;
//                Log.d(TAG, "Selected Track Number: Selecting position: " + selectedPosition);
//                selectedBitrate = bitrate;
//            } else
//                break;
//                }
////        Log.d(TAG, "Selected Track Number " + selectedPosition);
//
//                // notify the Observable that selectedBitrate just changed
//
////                playerView.setSelected(DemoUtils.MIME_TYPE_VIDEO_MP4, selectedPosition);
//                playerView.setSelected(true);
//
//                Log.d(TAG, "setSelectedBitrate - Bitrate:" + selectedBitrate + ", Available Bitrates:" +
//                        bitRateList.toString());
//
////        Log.d(TAG, "Selected Track " + player.getSelectedTrack(DemoPlayer.TYPE_VIDEO) + ",
////                TrackCount: " + player.getTrackCount(DemoPlayer.TYPE_VIDEO));
//            }

//----------------------------------------

       }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {

                case Player.STATE_IDLE:

                    if((player.getCurrentPosition() >= player.getDuration()) || (player.getCurrentPosition()==player.getDuration())) {
                        progressBar.setVisibility(View.GONE);
                    }
                    else
                    {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    Log.i(String.valueOf(Rajendra.this), "========= player.getCurrentPosition() : " + player.getCurrentPosition());
                    Log.i(String.valueOf(Rajendra.this), "========= player.getCurrentPosition() : " + player.getDuration());


                    ivBack.setVisibility(View.VISIBLE);
                    break;
                case Player.STATE_BUFFERING:
                    if((player.getCurrentPosition() >= player.getDuration()) || (player.getCurrentPosition()==player.getDuration())) {
                        progressBar.setVisibility(View.GONE);
                    }
                    else
                    {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    ivBack.setVisibility(View.VISIBLE);


                    break;
                case Player.STATE_READY:


                    Log.i(String.valueOf(Rajendra.this), "========= player.getCurrentPosition() : " + player.getCurrentPosition());
                    Log.i(String.valueOf(Rajendra.this), "========= player.getCurrentPosition() : " + player.getDuration());
                    progressBar.setVisibility(View.GONE);
                    ivBack.setVisibility(View.GONE);
                    Handler handler = new Handler();
                    runnable1 = new Runnable() {
                        @Override
                        public void run() {
                            boolean touch=playerView.isControllerVisible();
                            if(touch)
                            {
                                ivbackward.setVisibility(View.VISIBLE);
                                ivforward.setVisibility(View.VISIBLE);
                            }
                            else{
                                ivbackward.setVisibility(View.GONE);
                                ivforward.setVisibility(View.GONE);
                            }

                            if (player.getCurrentPosition() >= player.getDuration()) {
                                player.seekTo(player.getDuration());
                                player.setPlayWhenReady(false);
                                progressBar.setVisibility(View.GONE);


                            }







                            handler.postDelayed(runnable1, 1000);
                        }
                    };
                    handler.postDelayed(runnable1, 0);

                    break;
                case Player.STATE_ENDED:
                    Log.i(String.valueOf(Rajendra.this), "========= player.getCurrentPosition() : " + player.getCurrentPosition());
                    Log.i(String.valueOf(Rajendra.this), "========= player.getCurrentPosition() : " + player.getDuration());
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
            switch (error.type) {
                case ExoPlaybackException.TYPE_SOURCE:
                    Log.e(TAG, "TYPE_SOURCE: " + error.getSourceException().getMessage());
                    break;

                case ExoPlaybackException.TYPE_RENDERER:
                    Log.e(TAG, "TYPE_RENDERER: " + error.getRendererException().getMessage());
                    break;

                case ExoPlaybackException.TYPE_UNEXPECTED:
                    Log.e(TAG, "TYPE_UNEXPECTED: " + error.getUnexpectedException().getMessage());
                    break;
            }
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

//issue regarding audio and video



    private void updateButtonVisibilities() {


        try {
//            Bundle bundle1=new Bundle();
//            if(bundle1.getString("TYPE")=="download"){
//                ivSettings.setVisibility(View.GONE);
//
//            }else {
                ivSettings.setVisibility(View.VISIBLE);

//            }
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
                            Bundle bundle=new Bundle();
//                            if(bundle.getString("TYPE")=="download"){
//                                ivSettings.setVisibility(View.GONE);
//
//                            }else {
                                ivSettings.setVisibility(View.VISIBLE);
                                ivSettings.setOnClickListener(this);
                                ivSettings.setTag(i);
//                            }


                        }
                    }
                }
            }
        } catch (Exception e) {
            // This will catch any exception, because they are all descended from Exception
            System.out.println("Error " + e.getMessage());

        }


    }

    public void alert() {
      //  final String[] items = {"OFF", "ON", "ENGISH", "SPANISH"};
        final String[] items = {"OFF", "ON",};
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
                    progressBar.setVisibility(View.GONE);
                    playerView.getSubtitleView().setVisibility(View.GONE);

                } else if (s.equalsIgnoreCase("ON")) {
                    checkedPosition = 1;
                    progressBar.setVisibility(View.GONE);
                    playerView.getSubtitleView().setVisibility(View.VISIBLE);
                } else if (s.equalsIgnoreCase("ENGISH")) {
                    checkedPosition = 2;
                    textFormat = Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT,
                            Format.NO_VALUE, "en-us");
                    //Log.e("srt link is",srt_link);
                    Uri uriSubtitle = Uri.parse(streamingUrl);
                    MediaSource contentMediaSource = new HlsMediaSource.Factory(
                            mediaDataSourceFactory).createMediaSource(uriSubtitle);
                    MediaSource[] mediaSources = new MediaSource[2]; //The Size must change depending on the Uris
                    mediaSources[0] = contentMediaSource;
                    //MediaSource subtitleSource = new SingleSampleMediaSource(uriSubtitle, mediaDataSourceFactory, textFormat, C.TIME_UNSET);
                    SingleSampleMediaSource subtitleSource = new SingleSampleMediaSource(Uri.parse(subTitleUrl), mediaDataSourceFactory,
                            Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, Format.NO_VALUE, "en-us", null),
                            C.TIME_UNSET);
                    //Uri uri = Uri.parse("http://www.storiesinflight.com/js_videosub/jellies.srt");
                    mediaSources[1] = subtitleSource;

                    MergingMediaSource mergedSource = new MergingMediaSource(mediaSources);
                    MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
                            : new ConcatenatingMediaSource(mediaSources);
                    playerView.getSubtitleView().setVisibility(View.VISIBLE);
                    player.prepare(mergedSource, false, false);
                    player.seekTo(player.getCurrentWindowIndex(), player.getCurrentPosition());

                } else if (s.equalsIgnoreCase("SPANISH")) {
                    checkedPosition = 3;

                    textFormat = Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT,
                            Format.NO_VALUE, "en-us");
                    //Log.e("srt link is",srt_link);
                    Uri uriSubtitle = Uri.parse(streamingUrl);
                    MediaSource contentMediaSource = new HlsMediaSource.Factory(
                            mediaDataSourceFactory).createMediaSource(uriSubtitle);
                    MediaSource[] mediaSources = new MediaSource[2]; //The Size must change depending on the Uris
                    mediaSources[0] = contentMediaSource;
                    //MediaSource subtitleSource = new SingleSampleMediaSource(uriSubtitle, mediaDataSourceFactory, textFormat, C.TIME_UNSET);
                    SingleSampleMediaSource subtitleSource = new SingleSampleMediaSource(Uri.parse("https://nanomediaservices-inct.streaming.media.azure.net/f7f59c2b-3364-4d0a-a025-dfb10255a1d6/Spanish.vtt"), mediaDataSourceFactory,
                            Format.createTextSampleFormat(null, MimeTypes.TEXT_VTT, Format.NO_VALUE, "en-us", null),
                            C.TIME_UNSET);
                    //Uri uri = Uri.parse("http://www.storiesinflight.com/js_videosub/jellies.srt");
                    mediaSources[1] = subtitleSource;

                    MergingMediaSource mergedSource = new MergingMediaSource(mediaSources);
                    MediaSource mediaSource = mediaSources.length == 1 ? mediaSources[0]
                            : new ConcatenatingMediaSource(mediaSources);
                    playerView.getSubtitleView().setVisibility(View.VISIBLE);
                    player.prepare(mergedSource, false, false);
                    player.seekTo(player.getCurrentWindowIndex(), player.getCurrentPosition());


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
                MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();

                if (mappedTrackInfo != null) {

                    for (int i = 0; i < mappedTrackInfo.getRendererCount(); i++) {
                        TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);

                        if (trackGroups.length != 0) {
                            player.getRendererType(i);
                        }
                    }
                    String title = "Subtitle";
                   // int rendererIndex = C.TRACK_TYPE_AUDIO;
                    int rendererIndex = 2;
                    int rendererType = mappedTrackInfo.getRendererType(rendererIndex);
                    // put 2 =rendererIndex for TRACK_TYPE_TEXT
                    boolean allowAdaptiveSelections =
                            rendererType == C.TRACK_TYPE_VIDEO
                                    || (rendererType == C.TRACK_TYPE_AUDIO
                                    && mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
                                    == MappedTrackInfo.RENDERER_SUPPORT_NO_TRACKS);
                    Pair<AlertDialog, TrackSelectionView> dialogPair = TrackSelectionView.getDialog(Rajendra.this, title, trackSelector, rendererIndex);
                    dialogPair.second.setShowDisableOption(false);
                    // dialogPair.second.setAllowAdaptiveSelections(true)
                    dialogPair.second.setAllowAdaptiveSelections(allowAdaptiveSelections);
                    dialogPair.first.show();
                    progressBar.setVisibility(View.GONE);

                }

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
                 //   int rendererIndex = 2;
                    int rendererType = mappedTrackInfo.getRendererType(rendererIndex);
                    // put 2 =rendererIndex for TRACK_TYPE_TEXT
                    boolean allowAdaptiveSelections =
                            rendererType == C.TRACK_TYPE_VIDEO
                                    || (rendererType == C.TRACK_TYPE_AUDIO
                                    && mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
                                    == MappedTrackInfo.RENDERER_SUPPORT_NO_TRACKS);
//                    Pair<AlertDialog, TrackSelectionView> dialogPair = TrackSelectionView.getDialog(Rajendra.this, title, trackSelector, rendererIndex);
                    Pair<AlertDialog, TrackS> dialogPair = TrackS.getDialog(Rajendra.this, title, trackSelector, rendererIndex);
                    dialogPair.second.setShowDisableOption(false);
                    // dialogPair.second.setAllowAdaptiveSelections(true)
                    dialogPair.second.setAllowAdaptiveSelections(allowAdaptiveSelections);
                    dialogPair.first.show();

                }
            }
        });

        TextView quality=window.findViewById(R.id.tv_quality);
        quality.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
                int videoRendererIndex=0;
                TrackGroupArray trackGroups;



//                MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
//                if (mappedTrackInfo != null) {
//                    String title = getString(R.string.video);
//                    int rendererIndex = (int) ivSettings.getTag();
//                    Pair<AlertDialog, TrackSelectionView> dialogPair = TrackSelectionView.getDialog(Rajendra.this, title, trackSelector, rendererIndex);
////                    Pair<AlertDialog, NewTrackSelection> dialogPair = NewTrackSelection.getDialog(Rajendra.this, title, trackSelector, rendererIndex);
//                    Log.i("Quality" ,""+dialogPair);
//
//                    dialogPair.second.setShowDisableOption(true);
////                     dialogPair.second.setAllowAdaptiveSelections(true);
//                    dialogPair.second.setAllowAdaptiveSelections(false);
//                    dialogPair.first.show();
//                }

//------------------------------------------------------

//            Log.i(TAG,""+bitRateList);

//
//                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
//
//                if (mappedTrackInfo != null) {
//                    CharSequence title = "Video";
//                    int rendererIndex = 2;
//                    int rendererType = mappedTrackInfo.getRendererType(rendererIndex);
//                    boolean allowAdaptiveSelections =
//                            rendererType == C.TRACK_TYPE_VIDEO
//                                    || (rendererType == C.TRACK_TYPE_AUDIO
//                                    && mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
//                                    == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_NO_TRACKS);
//                    Pair<AlertDialog, TrackSelectionView> dialogPair =
//                            TrackSelectionView.getDialog(Rajendra.this, title, trackSelector, rendererIndex);
//                    dialogPair.second.setShowDisableOption(false);
//                    dialogPair.second.setAllowAdaptiveSelections(allowAdaptiveSelections);
//                    dialogPair.first.show();
//                }



//-----------------------------------------------
//                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
//                if (mappedTrackInfo != null) {
//                    String title = "Video";
//
//                    int rendererIndex = (int) ivSettings.getTag();
//                    int rendererType = mappedTrackInfo.getRendererType(0);
//                    boolean allowAdaptiveSelections =
//                            rendererType == C.TRACK_TYPE_VIDEO
//                                    || (rendererType == C.TRACK_TYPE_AUDIO
//                                    && mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
//                                    == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_NO_TRACKS);
////                    Pair<AlertDialog, NewTrackSelection> dialogPair = NewTrackSelection
////                            .getDialog(Rajendra.this, "Video", trackSelector, 0,
////                                    player.getVideoFormat());
//                    Pair<AlertDialog, NewTrackSelection1> dialogPair = NewTrackSelection1
//                            .getDialog(Rajendra.this, title, trackSelector, rendererIndex,Rajendra.this,
//                                    quality.getText().toString(), player.getVideoFormat());
//                    Log.i("Quality" ,""+dialogPair);
//                    dialogPair.second.setShowDisableOption(true);
//                    dialogPair.second.setAllowAdaptiveSelections(false);
//                    dialogPair.first.show();
//
//                }
// ----------------------------------------------------------



                if (v == quality
                        && !isShowingTrackSelectionDialog
                        && TrackSelectionDialog.willHaveContent(trackSelector)) {
                    isShowingTrackSelectionDialog = true;

                    TrackSelectionDialog trackSelectionDialog =
                            TrackSelectionDialog.createForTrackSelector(
                                    trackSelector,
                                    /* onDismissListener= */ dismissedDialog -> isShowingTrackSelectionDialog = false);
                    trackSelectionDialog.show(getSupportFragmentManager(), /* tag= */ null);
                }
//-----------------------------------------------------------

//                TrackSelectionDialog trackSelectionDialog =new TrackSelectionDialog();
//                TrackSelectionDialog mytrackselection= TrackSelectionDialog.createForMappedTrackInfoAndParameters(R.string.app_name,
//                        mappedTrackInfo, trackSelector.getParameters(), false, false, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                DefaultTrackSelector.SelectionOverride selectionOverride = new DefaultTrackSelector.SelectionOverride(0,which);
//                                trackSelector.setParameters(
//                                        trackSelector
//                                                .buildUponParameters()
//                                                .setSelectionOverride(0, trackSelector.getCurrentMappedTrackInfo().getTrackGroups(0), selectionOverride));
//                                resizemanual=true;
//
//                            }
//
//
//                        }, new DialogInterface.OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialog) {
//
//                            }
//                        }
//                );
//
//                mytrackselection.show(getSupportFragmentManager(), /* tag= */ null);
//            }



//-------------------------------------------------
//                               for (int i = 0; i < mappedTrackInfo.length; i++) {
//                        trackGroups = mappedTrackInfo.getTrackGroups(i);
//                        if (trackGroups.length != 0) {
//                            switch (player.getRendererType(i)) {
//                                case C.TRACK_TYPE_VIDEO:
//                                    videoRendererIndex = i;
//
//                            }
//                        }
//                    }

//                }
//                Pair<Integer, Integer> tag = (Pair<Integer, Integer>) v.getTag();
//                int groupIndex = tag.first;
//                int trackIndex = tag.second;
//                DefaultTrackSelector.SelectionOverride override = new DefaultTrackSelector.SelectionOverride(groupIndex,trackIndex);
//             List<Integer> tracks = getTracksAdding(override, videoRendererIndex);

////
//                 AdaptiveTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//                  trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//                TrackSelection.Factory factory =
//                        new AdaptiveTrackSelection.Factory(bandwidthMeter);

//                TrackSelection.Factory factory = tracks.size()== 1 ? FIXED_FACTORY : videoTrackSelectionFactory;
//                override = new DefaultTrackSelector.SelectionOverride(groupIndex, videoRendererIndex);
//                int rendererIndex = (int) ivSettings.getTag();
//                selector.setSelectionOverride(rendererIndex, trackGroups, override);
//                trackSelector.setSelectionOverride(rendererIndex, trackGroups, override);

//                trackSelector.setParameters(
//                        trackSelector
//                                .buildUponParameters()
//                                .setMaxVideoBitrate(550000)
//                                .setPreferredAudioLanguage("en").build());

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
                progressBar.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                if (mappedTrackInfo != null) {
                    String title = "Subtitle";
                    int rendererIndex = 2;
                    Pair<AlertDialog, TrackS> dialogPair = TrackS.getDialog(Rajendra.this, title, trackSelector, rendererIndex);
                    dialogPair.second.setShowDisableOption(true);
                    // dialogPair.second.setAllowAdaptiveSelections(true)
                    dialogPair.second.setAllowAdaptiveSelections(false);
                    dialogPair.first.show();

//                alert();

                }
            }
        });

        dialog.show();

    }

    private List<Integer> getTracksAdding(DefaultTrackSelector.SelectionOverride override, int renderIndex){
        MappedTrackInfo mappedTrackInfo=trackSelector.getCurrentMappedTrackInfo();
      TrackGroupArray info=mappedTrackInfo.getTrackGroups(renderIndex);
      List<Integer> list=new ArrayList();
     for (int i=0;i<info.length;i++){
         TrackGroup track=info.get(i);
         for (int j=0;j<track.length;j++){
            list.add(j);
         }
     }
     return list;
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
            Intent intent = new Intent(context, Rajendra.class);
            intent.putExtra(Rajendra.PREFER_EXTENSION_DECODERS_EXTRA, preferExtensionDecoders);
            intent.putExtra(Rajendra.ABR_ALGORITHM_EXTRA, abrAlgorithm);
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
            intent.putExtra(Rajendra.DRM_SCHEME_EXTRA, drmScheme);
            intent.putExtra(Rajendra.DRM_LICENSE_URL_EXTRA, drmLicenseUrl);
            intent.putExtra(Rajendra.DRM_KEY_REQUEST_PROPERTIES_EXTRA, drmKeyRequestProperties);
            intent.putExtra(Rajendra.DRM_MULTI_SESSION_EXTRA, drmMultiSession);
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
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_box_yes_no);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);

        LinearLayout linearLayout = (LinearLayout) window.findViewById(R.id.ll_layout);

        ProgressBar progressBar1 = (ProgressBar) window.findViewById(R.id.progress_bar_resumePlay);
//        progressBar.setVisibility(View.VISIBLE);
        tv_title.setText(getString(R.string.resume_watching));
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        String hms = convertMilliSecondToHour(playbackPos);
        tv_message.setText(Html.fromHtml("" + message + " " + hms));
        tv_message.setMovementMethod(new ScrollingMovementMethod());
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                progressBar1.setVisibility(View.VISIBLE);

                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        playbackPosition = 0;
//                time=0;
                        isInitializePlayer = true;
                        initializePlayer();
                        Log.i("Initialize Player","dialog no");

                        dialog.dismiss();
                    }
                };new Handler().postDelayed(runnable,1000);


            }
        });
        window.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                window.findViewById(R.id.progress_bar_resumePlay).setVisibility(View.VISIBLE);
                progressBar1.setVisibility(View.VISIBLE);

                linearLayout.setAlpha((float) 0.7);

//                        if (progressBar.getVisibility()==View.VISIBLE){
//                            playbackPosition = playbackPos;
////                time=playbackPos;
//                            isInitializePlayer = true;
//                            Log.i("Initialize Player","dialog yes");
//
//                            initializePlayer();
////                          dialog.dismiss();
//
//                            Log.i("Initialize Progressbar","Visible");
//                            dialog.dismiss();
//
//                        }

                Runnable runnable=new Runnable() {
                    @Override
                    public void run() {
                        playbackPosition = playbackPos;
//                time=playbackPos;
                        isInitializePlayer = true;
                        Log.i("Initialize Player","dialog yes");

                        initializePlayer();
//                          dialog.dismiss();

                        Log.i("Initialize runnable","run");
                        dialog.dismiss();

                    }
                };
                new Handler().postDelayed( runnable, 1000 );



//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.dismiss();
//                    }
//                });

            }
        });
        dialog.show();
    }

    public class Aes128DataSource implements DataSource {

        private final DataSource upstream;
        private final byte[] encryptionKey;
        private final byte[] encryptionIv;

        private @Nullable
        CipherInputStream cipherInputStream;

        /**
         * @param upstream The upstream {@link DataSource}.
         * @param encryptionKey The encryption key.
         * @param encryptionIv The encryption initialization vector.
         */
        public Aes128DataSource(DataSource upstream, byte[] encryptionKey, byte[] encryptionIv) {
            this.upstream = upstream;
            this.encryptionKey = encryptionKey;
            this.encryptionIv = encryptionIv;
        }

        @Override
        public final void addTransferListener(TransferListener transferListener) {
            upstream.addTransferListener(transferListener);
        }

        @Override
        public final long open(DataSpec dataSpec) throws IOException {
            Cipher cipher;
            try {
                cipher = getCipherInstance();
            } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                throw new RuntimeException(e);
            }

            Key cipherKey = new SecretKeySpec(encryptionKey, "AES");
            AlgorithmParameterSpec cipherIV = new IvParameterSpec(encryptionIv);

            try {
                cipher.init(Cipher.DECRYPT_MODE, cipherKey, cipherIV);
            } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
                throw new RuntimeException(e);
            }

            DataSourceInputStream inputStream = new DataSourceInputStream(upstream, dataSpec);
            cipherInputStream = new CipherInputStream(inputStream, cipher);
            inputStream.open();

            return C.LENGTH_UNSET;
        }

        @Override
        public final int read(byte[] buffer, int offset, int readLength) throws IOException {
            Assertions.checkNotNull(cipherInputStream);
            int bytesRead = cipherInputStream.read(buffer, offset, readLength);
            if (bytesRead < 0) {
                return C.RESULT_END_OF_INPUT;
            }
            return bytesRead;
        }

        @Override
        public final @Nullable Uri getUri() {
            return upstream.getUri();
        }

        @Override
        public final Map<String, List<String>> getResponseHeaders() {
            return upstream.getResponseHeaders();
        }

        @Override
        public void close() throws IOException {
            if (cipherInputStream != null) {
                cipherInputStream = null;
                upstream.close();
            }
        }

        protected Cipher getCipherInstance() throws NoSuchPaddingException, NoSuchAlgorithmException {
            return Cipher.getInstance("AES/CBC/PKCS7Padding");
        }
    }



    public String convertMilliSecondToHour(long millis) {
        String hms = "";
        //  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        //  System.out.println(hms);

        //    }
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



    public void postContentTime(Long playbackpos){
        Log.i("PlayBackPosition",""+playbackpos);
        Log.i("PlayBack conteid,userid"," "+userid+"  "+ contentid);

//        Toast.makeText(context, ""+playbackpos, Toast.LENGTH_SHORT).show();

        Call<ContentViewTimeDTO> call= RetroClient.sdaemonApi().postContentViewTime(userid,contentid,playbackpos);
        call.enqueue(new Callback<ContentViewTimeDTO>() {
            @Override
            public void onResponse(Call<ContentViewTimeDTO> call, Response<ContentViewTimeDTO> response) {

                if (response.isSuccessful()){

                    ContentViewTimeDTO contentViewTimeDTO=response.body();



                }else {
//                    Toast.makeText(Rajendra.this, "Unsuccess "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContentViewTimeDTO> call, Throwable t) {
//                Toast.makeText(Rajendra.this, "Error :"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }




    public void setSelectedBitrate(String resolution) {
    }


}














