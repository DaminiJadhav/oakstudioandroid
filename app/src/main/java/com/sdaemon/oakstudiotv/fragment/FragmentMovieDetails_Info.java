package com.sdaemon.oakstudiotv.fragment;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaDrm;
import android.media.MediaPlayer;
import android.media.NotProvisionedException;
import android.media.ResourceBusyException;
import android.media.UnsupportedSchemeException;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.OfflineLicenseHelper;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.source.dash.DashUtil;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.offline.DashDownloadAction;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.DemoDownloadService;
import com.sdaemon.oakstudiotv.activity.DownloadTracker;
import com.sdaemon.oakstudiotv.activity.Rajendra;
import com.sdaemon.oakstudiotv.activity.SelectMovieDetailsActivity;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.ContentViewHistoryDTO;
import com.sdaemon.oakstudiotv.model.ContentInfo;
import com.sdaemon.oakstudiotv.model.VideoDTO;
import com.sdaemon.oakstudiotv.utils.AppConstant;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.MediaDrm.KEY_TYPE_OFFLINE;
import static android.media.MediaDrm.KEY_TYPE_RELEASE;
import static android.os.Build.VERSION.SDK_INT;
import static com.google.android.exoplayer2.C.WIDEVINE_UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMovieDetails_Info extends Fragment implements View.OnClickListener {
    private View rootView;
    VideoView video;
    ImageView ivmovie, ivDownload;
    Button btnselectseason;
    MediaController mediaController;
    TextView season, moviefields, rating, minute, cc, showlikes, moviedes, tvdate, tvdownloadper, tvepisode;
    public List<ContentInfo> list = new ArrayList<>();
    private Button btnWatchNow;
    ProgressBar progressBar,load_progres;
    Context context;
    ArrayList<String> yourList;
    ArrayList<VideoDTO> list2;
    int status = 0;
    private Handler handler = new Handler();
    AlertDialog.Builder builder;
    Drawable drawable;
    DownloadTracker downloadTracker;
    ArrayList<VideoDTO> videoList;
    ProgressDialog pd;
    AppSession appSession;
    String time;

    String moviename,viewcount,trailar_img,contenttype;
    int year;

    static boolean download_status = false;

    ArrayList<String> songsList = new ArrayList();
    private String streamingUrl = "https://oakstudio-usso.streaming.media.azure.net/6b411695-6eab-424d-ba4c-49c2b870f9f1/1THE_LION_KING_Trailer_(4K_ULTRA.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd";
    private String streamingurls = "https://oakstudio-usso.streaming.media.azure.net/c5c0d161-eb47-412a-b34e-ef9ff93ba309/BalshaebThackeray.ism/manifest(format=mpd-time-csf).mpd";
    private String type = "";
    public String TAG = "play";
    String strTemp = "0";
    private long playbackPosition = 0;
    private int offLinePosition = -1;
    String getToken;
    OfflineLicenseHelper offlineLicenseHelper;
    byte[] offlineLicenseKeySetId2;
    String drmLicenseUrl;
    String[] keyRequestPropertiesArray;
    DefaultDrmSessionManager<FrameworkMediaCrypto> drmManager;
    DefaultDrmSessionManager drmSessionManager;
    HttpMediaDrmCallback drmCallback;
    MediaDrm mediaDrm;
    RenderersFactory renderersFactory;
    public static int value1=0;
    RenderersFactory renderersFactory1;
    boolean isInitializePlayer = false;
    private int currentWindow = -1;
    Bundle savedInstanceState;
    private Boolean playWhenReady = false;
    Boolean startdownload = false;
    String urlkey;
    Integer value;
    String selectedseason, selectedepisode,ratingvalue;
    int userId,contentId;


    private String KEY_PLAY_WHEN_READY = "play_when_ready";
    private String KEY_WINDOW = "window";

    public static final String DRM_SCHEME_EXTRA = "drm_scheme";
    public static final String DRM_LICENSE_URL_EXTRA = "drm_license_url";
    public static final String DRM_KEY_REQUEST_PROPERTIES_EXTRA = "drm_key_request_properties";
    public static final String DRM_MULTI_SESSION_EXTRA = "drm_multi_session";
    public static final String PREFER_EXTENSION_DECODERS_EXTRA = "prefer_extension_decoders";
    public static final String ABR_ALGORITHM_EXTRA = "abr_algorithm";

    public static final String PREFER_EXTENSION_DECODERS = "prefer_extension_decoders";
    public static final String DRM_SCHEME_UUID_EXTRA = "drm_scheme_uuid";
    public static final String DRM_LICENSE_URL = "https://oakstudio.keydelivery.southcentralus.media.azure.net/Widevine/?kid=e0c90a42-1e00-47a7-9071-9726e9dbc448";
    public static final String DRM_KEY_REQUEST_PROPERTIES = "drm_key_request_properties";
    public static final String EXTRA_IS_OFFLINE = "extra_is_offline";


    public FragmentMovieDetails_Info() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragmentMovieDetails_Info(List<ContentInfo> list, String season, String episode,String rating) {
        this.list = list;
        this.selectedseason = season;
        this.selectedepisode = episode;
        this.ratingvalue=rating;

    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onRecive download");
            if (intent.getAction().equals("hello")) {
                if (SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    HashMap<Uri, Integer> hashMap = (HashMap<Uri, Integer>) intent.getSerializableExtra("map");
                    //int prog4ress1= intent.getIntExtra("progress", 0);
                    //  Log.i(TAG,"notification "+progress1);
                    Set keys = hashMap.keySet();
                    for (Iterator i = keys.iterator(); i.hasNext(); ) {
                        Uri key = (Uri) i.next();
                        value = hashMap.get(key);

                        Intent valueIntent=new Intent("Download_Intent");
                        valueIntent.putExtra("DOWNLOAD_COUNT",value);
                        context.sendBroadcast(valueIntent);
                        Log.i("Offline Download Count",""+value);



                        // if(key.toString().equals(streamingUrl.toString()))
                        {


                            Log.i("yes", "notifi_p " + key.toString() + " = " + value);
                            value1=value;
                            ivDownload.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            tvdownloadper.setVisibility(View.VISIBLE);
                            Log.i("Download URL", "Download");
                            progressBar.setProgress(value);
                            progressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                            progressBar.setProgressDrawable(drawable);
                            tvdownloadper.setText(String.format("%d%%", value));
                            if (value == 100) {
                                ivDownload.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                tvdownloadper.setVisibility(View.GONE);
                                ivDownload.setImageResource(R.drawable.complete_download);
                                ivDownload.setClickable(false);
                                ivDownload.setEnabled(false);
                            }
                        }
                    }
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_fragment_movie_details__info, container, false);
        season = rootView.findViewById(R.id.tv_season);
        tvepisode = rootView.findViewById(R.id.tv_episode);
        moviefields = rootView.findViewById(R.id.tv_moviefield);
        rating = rootView.findViewById(R.id.tv_rating);
        minute = rootView.findViewById(R.id.tv_min);
        tvdate = rootView.findViewById(R.id.tv_date);
        cc = rootView.findViewById(R.id.tv_cc);
        showlikes = rootView.findViewById(R.id.tv_showlike);
        moviedes = rootView.findViewById(R.id.tv_movieDescription);
        btnselectseason = rootView.findViewById(R.id.btn_selectseason);
        video = (VideoView) rootView.findViewById(R.id.video);
        load_progres = rootView.findViewById(R.id.progress_bar);
//        load_progres.setVisibility(View.VISIBLE);

        btnselectseason.setOnClickListener(this::onClick);
        Log.i("RatingValue",""+ratingvalue);
        context = rootView.getContext();
        appSession = AppSession.getInstance(context);

    try {
        if (appSession != null) {
            userId = appSession.getUserDTO().getResult().getCustomerId();
        }
    }catch (Exception e){
        e.printStackTrace();
    }


        Resources resources = getResources();
        ivDownload = rootView.findViewById(R.id.iv_download);


//        if (ivDownload.isClickable()){
//            ivDownload.setEnabled(false);
//            ivDownload.setClickable(false);
//        }
//        ivDownload=rootView.findViewById(R.id.iv_play_download);

        tvdownloadper = rootView.findViewById(R.id.download_percentage);
        drawable = resources.getDrawable(R.drawable.circularprogressbar);
        progressBar = rootView.findViewById(R.id.circularProgressbar);
        savedInstanceState = new Bundle();
        this.savedInstanceState = savedInstanceState;

        tvdownloadper.setVisibility(View.INVISIBLE);

        AppSubClass application = (AppSubClass) getActivity().getApplication();
        downloadTracker = application.getDownloadTracker();
        list2 = new ArrayList<>();
        if (status >= 1 && status <= 100) {
        }
//        boolean b = downloadTracker.isDownloaded(Uri.parse("https://oakstudio-usso.streaming.media.azure.net/c5c0d161-eb47-412a-b34e-ef9ff93ba309/BalshaebThackeray.ism/manifest(format=mpd-time-csf).mpd"));
        boolean b = downloadTracker.isDownloaded(Uri.parse(streamingUrl));

        if (b == true) {
            ivDownload.setVisibility(View.VISIBLE);
            ivDownload.setImageResource(R.drawable.complete_download);
            ivDownload.setClickable(false);
            ivDownload.setEnabled(false);
        } else {
            ivDownload.setVisibility(View.VISIBLE);
            ivDownload.setImageResource(R.drawable.download_icon);
            ivDownload.setClickable(true);

        }


//        String seasonNo=appSession.getSeasonNo();
//        if (seasonNo!=null){
//            season.setText("Season "+seasonNo);
//        }else {
//            season.setText("");
//        }
        if (selectedseason != null && selectedepisode != null) {
            season.setVisibility(View.VISIBLE);
            tvepisode.setVisibility(View.VISIBLE);
            season.setText("Season " + selectedseason);
            tvepisode.setText("" + selectedepisode);

        }


        // drmCallback = new HttpMediaDrmCallback("http://myAudience",
        drmCallback = new HttpMediaDrmCallback("https://oakstudio.keydelivery.southcentralus.media.azure.net/Widevine/?kid=4361ec0e-87c2-45ff-8e6e-ea77248d75ef",
                new DefaultHttpDataSourceFactory("user-agent"));
       /* Map<String, String> keyRequestProperties;
            keyRequestProperties = new HashMap<>();
            keyRequestProperties.put("Authorization","Bearer=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiIxY2YxNWQ4MS02MmQ3LTQ1YTYtOWRkNC0xMGRkN2NjMGNjMzYiLCJpc3MiOiJodHRwczovL2JyZWFrZG93bi5tZSIsImF1ZCI6IkJyZWFrZG93blVzZXIiLCJleHAiOjE1NzMwMDQ4NTAsIm5iZiI6MTU3Mjk2MTU5MH0.UX6B2EOgTbZRFkLtSKK8cKIJFCyS3TktPRO0ADxNfHE");
*/
        drmCallback.setKeyRequestProperty("Authorization", "Bearer=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiIwOTZlNDczYi0zZGUzLTQzYWUtYTI4ZC0zYjg2ZmJmNzBkYjkiLCJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6bWF4dXNlcyI6IjEwMCIsIm5iZiI6MTU4MDI5Mjg3MywiZXhwIjoxNTgwODkzMTczLCJpc3MiOiJteUlzc3VlciIsImF1ZCI6Im15QXVkaWVuY2UifQ.XrV25wI65xN6n2hQuFxhN_Uj-p1dpTavfNl7ALJLsZ4");
//        drmCallback.setKeyRequestProperty("Authorization","Bearer=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiIwOTZlNDczYi0zZGUzLTQzYWUtYTI4ZC0zYjg2ZmJmNzBkYjkiLCJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6bWF4dXNlcyI6IjEwMCIsIm5iZiI6MTU3OTkyOTQ2OSwiZXhwIjoxNTc5OTg5NzY5LCJpc3MiOiJteUlzc3VlciIsImF1ZCI6Im15QXVkaWVuY2UifQ.yGi-imgDdDtf7o0lPARwpIqrWVr4lcNe_f3x8cbxxmo");

        try {
            drmSessionManager = new DefaultDrmSessionManager(WIDEVINE_UUID,
                    FrameworkMediaDrm.newInstance(WIDEVINE_UUID), drmCallback, null, handler, null, true, 3);
        } catch (UnsupportedDrmException e) {
            e.printStackTrace();
        }


        Bundle bundle = getActivity().getIntent().getExtras();
        IntentFilter filter = new IntentFilter("hello");
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, filter);
        if (bundle != null) {
            {
                if (bundle.containsKey("TYPE"))
                    type = bundle.getString("TYPE");
                Log.i("Offline download type", "" + type);
                if (bundle.containsKey("PLAYBACK_POSITION")) {
                    strTemp = bundle.getString("PLAYBACK_POSITION");
                    playbackPosition = Long.parseLong(strTemp);
                    Log.i(getClass().getName(), "===========playbackPosition : " + playbackPosition);

                }
                if (bundle.containsKey("OFF_LINE_POSITION"))
                    offLinePosition = bundle.getInt("OFF_LINE_POSITION");
                Log.i("Offline download pos", "" + type);


                if (bundle.containsKey("PN_JSON_OBJECT")) {
                    String jsonData = bundle.getString("PN_JSON_OBJECT");
                    Log.i("Offline download json", "" + jsonData);


                    if (!TextUtils.isEmpty(jsonData)) {
//                        list2 = new ArrayList<VideoDTO>();
                        list2 = new Gson().fromJson(jsonData, new TypeToken<ArrayList<VideoDTO>>() {
                        }.getType());
                        Log.i("Offline down json list", "" + list2);

//                        list = new Gson().fromJson(jsonData, new TypeToken<ArrayList<VideoDTO>>() {
//                        }.getType());
                    }
                    Log.i(getClass().getName(), "===========VideoUrl SIZE: " + list.size());

                }
                if (bundle.containsKey("tokenvalue"))
                    getToken = bundle.getString("tokenvalue");
            }
        }

        if (playbackPosition > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

//                    dialogResumePlayBack(getResources().getString(R.string.would_you_like_to_resume_watching), playbackPosition);
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


        if (type.equalsIgnoreCase("download")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    ivPlayDownload.setVisibility(View.VISIBLE);
                    streamingUrl = songsList.get(offLinePosition);
                    Log.i("Streaming URL", "" + streamingUrl);
//                    ivPlayDownload.performClick();


                }
            }, 2000);

        } else {

//            ivPlayDownload.setVisibility(View.GONE);
        }

        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
            currentWindow = savedInstanceState.getInt(KEY_WINDOW);
            //  playbackPosition = savedInstanceState.getLong(KEY_POSITION);

        }


        initialize1();
//        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        return rootView;
    }

    private void initialize1() {
        String url = "https://oakstudio.azurewebsites.net";
        ivmovie = rootView.findViewById(R.id.iv_movieImage);


//        MediaController mediaController;
//        ProgressDialog pd;

        pd = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        pd.setIndeterminate(true);
        pd.setMessage("Buffering video please wait...");
        pd.setProgress(70);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(true);
//        pd.show();
        String video_url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
        mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(video);
        String url1 = "https://oakstudio-usso.streaming.media.azure.net/6b411695-6eab-424d-ba4c-49c2b870f9f1/1THE_LION_KING_Trailer_(4K_ULTRA.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd";
        if (url1.contains("encryption=cenc")) {
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    // Create a URL for the desired page
                    URL url2 = new URL("https://oakstudio-usso.streaming.media.azure.net/6b411695-6eab-424d-ba4c-49c2b870f9f1/1THE_LION_KING_Trailer_(4K_ULTRA.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd");
                    // Read all the text returned by the server
                    BufferedReader in = new BufferedReader(new InputStreamReader(url2.openStream()));
                    String str;
                    while ((str = in.readLine()) != null) {
                        // str is one line of text; readLine() strips the newline character(s)
                        Pattern pattern = Pattern.compile("(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                                        + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                                        + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
                                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                        Matcher matcher = pattern.matcher(str);
                        while (matcher.find()) {
                            System.out.println(matcher.start() + 1 + " : " + matcher.end());
                            System.out.println(str.substring(matcher.start() + 1, matcher.end()));
                            String s = str.substring(matcher.start() + 1, matcher.end());
                            yourList = new ArrayList<>();
                            if (s.contains("keydelivery")) {
                                yourList.add(s);
                                Set<String> set = new HashSet<>(yourList);
                                yourList.clear();
                                yourList.addAll(set);
                            }
                        }
                    }
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (yourList != null) {
                Log.d("d", "initialize: " + yourList.get(0));
                urlkey = yourList.get(0);
//                initialize1();

            }
        }


        for (int i = 0; i < list.size(); i++) {
            contenttype=list.get(i).getContentType();
            moviefields.setText(list.get(i).getContentType());
            rating.setText(list.get(i).getRatingDescription());
            moviedes.setText(list.get(i).getContentDescription());
            moviename = list.get(i).getContentTitle();

            contentId=list.get(i).getContentID();

            year=list.get(i).getYear();
            viewcount=list.get(i).getViewCount();
            trailar_img=url+list.get(i).getTrailer_Image();
            Locale loc = new Locale("en", "US");
            String d = DateFormat.getDateInstance(DateFormat.DEFAULT, loc).format(getUTCToLocalDate(list.get(i).getReleaseDate()));
            tvdate.setText("(" + d + ")");
            tvdate.setVisibility(View.VISIBLE);
            Log.i("Content Type", "" + list.get(i).getContentType());

            if (list.get(i).getContentType().equals("Web Series")) {
                tvdate.setVisibility(View.GONE);
                tvepisode.setVisibility(View.VISIBLE);
                season.setVisibility(View.VISIBLE);
//                season.setText("Season 1 ");
                btnselectseason.setVisibility(View.VISIBLE);
            }

//            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
//            Date date = format.parse(list.get(i).getReleaseDate());
//            season.setText("1 Season("+list.get(i).getReleaseDate()+")");
            Log.i("Image Url", "" + url + list.get(i).getTrailer_Image());

            Glide.with(context)
                    .load(url + list.get(i).getTrailer_Image())
                    .into(ivmovie);

// .placeholder(R.mipmap.view)
//            ivmovie.set;
        }

        // ContentDTO contentDTO =appSession.getMovieDetail();
        //   Toast.makeText(context, ""+contentDTO.getContentDetail().getTable().get(0).getContentTitle(), Toast.LENGTH_SHORT).show();
        btnWatchNow = (Button) rootView.findViewById(R.id.btnWatchNow);

        ivmovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_progres.setVisibility(View.VISIBLE);
//                pd.show();
                Uri uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
                if (uri != null) {
                    load_progres.setVisibility(View.INVISIBLE);
//                    pd.dismiss();
                    video.setVisibility(View.VISIBLE);
                    ivmovie.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.INVISIBLE);
                    video.setMediaController(mediaController);
                    video.setVideoURI(uri);
                    video.start();
                }
            }
        });
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START)
                            load_progres.setVisibility(View.VISIBLE);
//                            pd.show();
                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
                            load_progres.setVisibility(View.INVISIBLE);
//                            pd.dismiss();
                        return false;
                    }
                });
            }
        });
        video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                pd.dismiss();
                return false;
            }
        });
        btnWatchNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String videoUrlSimple="https://oakstudio-usso.streaming.media.azure.net/c5c0d161-eb47-412a-b34e-ef9ff93ba309/BalshaebThackeray.ism/manifest(format=mpd-time-csf).mpd";  //1

//                videoList=new ArrayList<>();


                PostcontentView();
//                list2.add(new VideoDTO(videoUrlSimple));
//                Log.i("Video URL",""+list2.size());
//                Bundle bundle=new Bundle();
//                Intent watchintent=new Intent(context,Rajendra.class);
//                bundle.putInt("KEY_WatchContentID",contentId);
//                bundle.putString("KEY_MOVIE_TIME",time);
//                Log.i("Movie Time",""+time);
//
//                bundle.putString("PN_JSON_OBJECT",new Gson().toJson(list2));
//                watchintent.putExtras(bundle);
//                startActivity(watchintent);
            }
        });

        ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadVideos();
//                download();


            }
        });
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = tvdownloadper.getText().toString();
                String intp = value.substring(0, value.length() - 1);
                int converted = Integer.parseInt(intp);
                if (converted < 100) {
//                    dialogResumePlayBack();
                    Log.i("Download status", "" + download_status);

                    showDialoge();

//                    if (showDialoge()){
//                        Log.i("Download status",""+download_status);
//                        ivDownload.setVisibility(View.VISIBLE);
//                        ivDownload.setImageResource(R.drawable.download_icon);
//                        ivDownload.setClickable(true);
//                        progressBar.setVisibility(View.GONE);
//                        tvdownloadper.setVisibility(View.GONE);
//                    }else {
//                        Toast.makeText(context, "download false", Toast.LENGTH_SHORT).show();
//                    }

                }
            }
        });

    }

    public void dialogResumePlayBack() {
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
        tv_title.setText("Download Status");
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText("Do you want to stop download");
        tv_message.setMovementMethod(new ScrollingMovementMethod());
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        window.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ivDownload.setVisibility(View.VISIBLE);
                ivDownload.setImageResource(R.drawable.download_icon);
                ivDownload.setClickable(true);
                progressBar.setVisibility(View.GONE);
                tvdownloadper.setVisibility(View.GONE);

                list2 = appSession.getOfflineDownloadList();
                for (int i = 0; i < list2.size(); i++) {
                    if (list2.get(i).getVideoUrl().equals("https://oakstudio-usso.streaming.media.azure.net/c5c0d161-eb47-412a-b34e-ef9ff93ba309/BalshaebThackeray.ism/manifest(format=mpd-time-csf).mpd")) {
                        list2.remove(i);

                    }
                }

                appSession.setOfflineDownloadList(list2);
                DownloadAction downloadAction = DashDownloadAction.createRemoveAction(Uri.parse("https://oakstudio-usso.streaming.media.azure.net/c5c0d161-eb47-412a-b34e-ef9ff93ba309/BalshaebThackeray.ism/manifest(format=mpd-time-csf).mpd"), null);
                DownloadService.startWithAction(context, DemoDownloadService.class, downloadAction, true);


                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void showDialoge() {
        try {
            androidx.appcompat.app.AlertDialog.Builder builder;
            builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.dialoge);
            //            String centerNRed = "<div align:'center' ><span style='color:red' >"+title+"</span></div>";
//            builder.setTitle(Html.fromHtml(centerNRed))
            builder.setTitle(Html.fromHtml("<font color='#D93723'>Download Status</font>"))
                    .setMessage("Do you want to stop download")
                    .setCancelable(false)
//                    .setIcon(R.drawable.ic_error_black_24dp)
                    .setPositiveButton(Html.fromHtml("<font color='#9AEB3D'>Yes</font>"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            list2 = appSession.getOfflineDownloadList();
                            for (int i = 0; i < list2.size(); i++) {
                                if (list2.get(i).getVideoUrl().equals("http://dash.edgesuite.net/akamai/bbb_30fps/bbb_30fps.mpd")) {
                                    list2.remove(i);


                                }
                            }

                            appSession.setOfflineDownloadList(list2);
                            DownloadAction downloadAction = DashDownloadAction.createRemoveAction(Uri.parse("http://dash.edgesuite.net/akamai/bbb_30fps/bbb_30fps.mpd"), null);
                            DownloadService.startWithAction(context, DemoDownloadService.class, downloadAction, true);

                            //                            download_status=true;


                            progressBar.setVisibility(View.GONE);
                           if (progressBar.getVisibility()==View.GONE)
                            {
                                download1();
                            }
                              /*  progressBar.setVisibility(View.GONE);
                                tvdownloadper.setVisibility(View.GONE);
                                ivDownload.setVisibility(View.VISIBLE);
                                ivDownload.setImageResource(R.drawable.download_icon);
                                ivDownload.setClickable(true);*/

                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(Html.fromHtml("<font color='#9AEB3D'>No</font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
//                            download_status=false;

                            dialog.dismiss();

                        }
                    }).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void initializePlayer() {
        if (type.equalsIgnoreCase("download")) {
        } else {
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory("userAgent");
                httpDataSourceFactory.getDefaultRequestProperties().set("Authorization", "Bearer=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiIwOTZlNDczYi0zZGUzLTQzYWUtYTI4ZC0zYjg2ZmJmNzBkYjkiLCJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6bWF4dXNlcyI6IjEwMCIsIm5iZiI6MTU4MDI5Mjg3MywiZXhwIjoxNTgwODkzMTczLCJpc3MiOiJteUlzc3VlciIsImF1ZCI6Im15QXVkaWVuY2UifQ.XrV25wI65xN6n2hQuFxhN_Uj-p1dpTavfNl7ALJLsZ4");
//                httpDataSourceFactory.getDefaultRequestProperties().set("Authorization", "Bearer=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6Y29udGVudGtleWlkZW50aWZpZXIiOiIwOTZlNDczYi0zZGUzLTQzYWUtYTI4ZC0zYjg2ZmJmNzBkYjkiLCJ1cm46bWljcm9zb2Z0OmF6dXJlOm1lZGlhc2VydmljZXM6bWF4dXNlcyI6IjEwMCIsIm5iZiI6MTU3OTkyOTQ2OSwiZXhwIjoxNTc5OTg5NzY5LCJpc3MiOiJteUlzc3VlciIsImF1ZCI6Im15QXVkaWVuY2UifQ.yGi-imgDdDtf7o0lPARwpIqrWVr4lcNe_f3x8cbxxmo");
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
                    offlineLicenseHelper = OfflineLicenseHelper.newWidevineInstance(urlkey, httpDataSourceFactory);
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
//                    else {
//                        Toast.makeText(context, "Token expire", Toast.LENGTH_SHORT).show();
//                    }
                } catch (DrmSession.DrmSessionException e) {
                    e.printStackTrace();
                }
            }
        }


        Intent intent = getActivity().getIntent();
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


//        drmManager = buildDrmSessionManager("Hello Agent", C.WIDEVINE_UUID,drmLicenseUrl,keyRequestPropertiesArray,true);
        if (type.equalsIgnoreCase("download")) {
            //  byte[] offlineKeySetId = Base64.decode(appSession.getOfflinekey(), Base64.DEFAULT);
//            if (offlineKeySetId!=null){

            byte[] offlineKeySetId = Base64.decode(getToken, Base64.DEFAULT);
            // byte [] off={107,115,105,100,49,69,51,51,56,52,68,70};
            drmManager.setMode(DefaultDrmSessionManager.MODE_PLAYBACK, offlineKeySetId);
            try {
                MediaDrm drm = new MediaDrm(WIDEVINE_UUID);
                try {
                    byte a[] = drm.openSession();
                    int a1 = KEY_TYPE_OFFLINE;
                    int b1 = KEY_TYPE_RELEASE;
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
        } else {
        }


    }



    private void releaseMediaDrm() {
        if (mediaDrm != null) {
            mediaDrm.release();
            mediaDrm = null;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_selectseason:
                Intent intent = new Intent(getActivity(), SelectMovieDetailsActivity.class);
                startActivity(intent);
                getActivity().finish();


//                int seasonNo=getArguments().getInt("KEY_SEASEON_NO");
//                Log.i("Season No. get success",""+seasonNo);
//                season.setText("Season "+seasonNo);
        }
    }


    public Date getUTCToLocalDate(String date) {
        Date inputDate = new Date();
        if (date != null && !date.isEmpty()) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                inputDate = simpleDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return inputDate;
    }
    /*public void download() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status < 100) {
                    status += 1;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(status);
                            progressBar.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                            progressBar.setProgressDrawable(drawable);
                            tvdownloadper.setText(String.format("%d%%", status));
                        }
                    });
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }).start();
    }*/


    public void download1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (value1 < 100) {
                    value1 += 1;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            tvdownloadper.setVisibility(View.GONE);
                            ivDownload.setVisibility(View.VISIBLE);
                            ivDownload.setImageResource(R.drawable.download_icon);
                            ivDownload.setClickable(true);

                        }
                    });
                        try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }).start();
    }


    public void downloadVideos() {
        AppSubClass application = (AppSubClass) getActivity().getApplication();
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
        if (offlineLicenseKeySetId2 != null) {
            bundle.putString("token", Base64.encodeToString(offlineLicenseKeySetId2, Base64.DEFAULT));
        }
        // bundle.putString("token","dad");
        int days = 2;
        String currentDateTimeString =
                //  DateFormat.getDateTimeInstance().format(new Date());
                DateFormat.getDateInstance().format(new Date());
        Calendar startDate = Calendar.getInstance();
        int mYear = startDate.get(Calendar.YEAR);
        int mMonth = startDate.get(Calendar.MONTH) + 1;
        int mDay = startDate.get(Calendar.DAY_OF_MONTH);
        startDate.set(mYear, mMonth, mDay);
        long startDateMillis = startDate.getTimeInMillis();


        Calendar endDate = Calendar.getInstance();
        int mYear_2days = endDate.get(Calendar.YEAR);
        int mMonth_2days = endDate.get(Calendar.MONTH) + 1;
        int mDay_2days = endDate.get(Calendar.DAY_OF_MONTH) + days;
        endDate.set(mYear_2days, mMonth_2days, mDay_2days);
        long endDateMillis = endDate.getTimeInMillis();
        long differenceMillis = endDateMillis - startDateMillis;
        int daysDifference = (int) (differenceMillis / (1000 * 60 * 60 * 24));
        bundle.putString("date", currentDateTimeString);


//        bundle.putSerializable("KEY_CONTENTINFO", (Serializable) list);

//        bundle.putParcelableArrayList("KEY_CONTENTINFO", (ArrayList<? extends Parcelable>) list);

        Log.i(" Download current date", "" + currentDateTimeString);
        bundle.putLong("end_date_time", endDateMillis);
        Log.i(" Download end date", "" + endDateMillis);


            bundle.putString("Content_Title",moviename);
            bundle.putInt("Content_Year",year);
            bundle.putString("Content_ViewCount",viewcount);
            bundle.putString("Content_BannerImage",trailar_img);
            bundle.putString("Content_Type",contenttype);
//            String rating = String.valueOf(list.get(i).getRatings()).toString();
//            final float ratingValue = Float.parseFloat(rating);
            bundle.putString("Content_Rating",ratingvalue);





        Intent yesReceive2 = new Intent(context, DownloadTracker.NotificationReceiver.class);
//        yesReceive2.putExtra("KEY_CONTENTINFO", (Serializable) list);
        yesReceive2.putExtras(bundle);
        yesReceive2.setAction(AppConstant.YES_ACTION);
        PendingIntent pendingIntentYes2 = PendingIntent.getBroadcast(context, uniqueInt, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (1 * 1000), pendingIntentYes2);
//        downloadTracker.toggleDownload(getActivity(), moviename, Uri.parse("https://oakstudio-usso.streaming.media.azure.net/00dc2eda-0330-47f2-b2a8-fa8447e1f0de/THE-LION-KING-Trailer-(4K-ULTRA-.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd"), "");

//        downloadTracker.toggleDownload(getActivity(), "HLS", Uri.parse("https://oakstudio-usso.streaming.media.azure.net/6b411695-6eab-424d-ba4c-49c2b870f9f1/1THE_LION_KING_Trailer_(4K_ULTRA.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd"), "");
        downloadTracker.toggleDownload(getActivity(), moviename, Uri.parse("http://dash.edgesuite.net/akamai/bbb_30fps/bbb_30fps.mpd"), "");
//        downloadTracker.toggleDownload(getActivity(), moviename, Uri.parse("https://oakstudio-usso.streaming.media.azure.net/c5c0d161-eb47-412a-b34e-ef9ff93ba309/BalshaebThackeray.ism/manifest(format=mpd-time-csf).mpd"), "");

        Log.i("Streaming URL Download", "" + streamingUrl);

        Gson gson = new Gson();
        String JsonStr = gson.toJson(list);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("KEY_CONTENTINFOM", JsonStr);
        editor.apply();
        Log.i("Offline ContentInfo",""+JsonStr);

//        download();

        // downloadTracker.toggleDownload(this,"HLS", Uri.parse(streamingUrl), "m3u8");

        if (appSession != null) {

        }
    }

    public void PostcontentView(){
//        Call<ContentViewHistoryDTO> call= RetroClient.sdaemonApi().postContentViewHistory(4,8707);
        Log.i("ContentViewHistory ID","ContentID : "+contentId+ "UserID : "+userId);

        Call<ContentViewHistoryDTO> call= RetroClient.sdaemonApi().postContentViewHistory(contentId,userId);

        call.enqueue(new Callback<ContentViewHistoryDTO>() {
            @Override
            public void onResponse(Call<ContentViewHistoryDTO> call, Response<ContentViewHistoryDTO> response) {
                if (response.isSuccessful()){
//                    Toast.makeText(context, ""+response.body().getMessage() , Toast.LENGTH_SHORT).show();
                    ContentViewHistoryDTO viewHistoryDTO=response.body();
                    time=viewHistoryDTO.getHistory().getSeenUpTo();

                    String videoUrlSimple="https://oakstudio-usso.streaming.media.azure.net/c5c0d161-eb47-412a-b34e-ef9ff93ba309/BalshaebThackeray.ism/manifest(format=mpd-time-csf).mpd";  //1

                    Log.i("ContentViewHistory",""+response.message()+ "  Time :"+time);
                    list2.add(new VideoDTO(videoUrlSimple));
                    Log.i("Video URL",""+list2.size());
                    Bundle bundle=new Bundle();
                    Intent watchintent=new Intent(context,Rajendra.class);
                    bundle.putInt("KEY_WatchContentID",contentId);
                    bundle.putString("PLAYBACK_POSITION",time);
                    Log.i("Movie Time",""+time);

                    bundle.putString("PN_JSON_OBJECT",new Gson().toJson(list2));
                    watchintent.putExtras(bundle);
                    startActivity(watchintent);

                }else {
//                    Toast.makeText(context, ""  +response.message(), Toast.LENGTH_SHORT).show();
                    Log.i("ContentViewHistory",""+response.message());
                }
            }

            @Override
            public void onFailure(Call<ContentViewHistoryDTO> call, Throwable t) {
//                Toast.makeText(context, "Error :" +t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("ContentViewHistory",""+t.getMessage());


            }
        });
    }




    @Override
    public void onStart() {
        super.onStart();
        video.start();
        if (isInitializePlayer) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        video.resume();
        if (isInitializePlayer) {
            // initializePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        updateStartPosition();
        outState.putBoolean(KEY_PLAY_WHEN_READY, playWhenReady);
        outState.putInt(KEY_WINDOW, currentWindow);
        //  outState.putLong(KEY_POSITION, playbackPosition);
        super.onSaveInstanceState(outState);
    }

    private void updateStartPosition() {

        playbackPosition = video.getCurrentPosition();

//        currentWindow = video.getCurrentWindowIndex();
//        playWhenReady = player.getPlayWhenReady();


    }




}
