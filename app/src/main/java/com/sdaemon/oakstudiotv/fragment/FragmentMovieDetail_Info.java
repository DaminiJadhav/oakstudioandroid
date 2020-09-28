package com.sdaemon.oakstudiotv.fragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
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
import android.graphics.drawable.Drawable;
import android.media.MediaDrm;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.OfflineLicenseHelper;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.source.dash.offline.DashDownloadAction;
import com.google.gson.Gson;
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
import com.sdaemon.oakstudiotv.utils.DialogUtils;
import com.sdaemon.oakstudiotv.utils.Utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.VERSION.SDK_INT;

public class FragmentMovieDetail_Info extends Fragment implements View.OnClickListener{
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
    private ProgressDialog progressDialog;


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

    Utilities utilities;

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


    public FragmentMovieDetail_Info() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragmentMovieDetail_Info(List<ContentInfo> list, String season, String episode, String rating) {
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        utilities=Utilities.getInstance(context);

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
        if (selectedseason != null && selectedepisode != null) {
            season.setVisibility(View.VISIBLE);
            tvepisode.setVisibility(View.VISIBLE);
            season.setText("Season " + selectedseason);
            tvepisode.setText("" + selectedepisode);

        }

        IntentFilter filter = new IntentFilter("hello");
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, filter);

        initilizer();

        return rootView;
    }


    public void initilizer(){
        String url = "https://oakstudio.azurewebsites.net";
        ivmovie = rootView.findViewById(R.id.iv_movieImage);

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

        mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(video);

        ivmovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showProgress();
                load_progres.setVisibility(View.VISIBLE);

//                pd.show();
                Uri uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
                if (uri != null) {
//                    load_progres.setVisibility(View.INVISIBLE);
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

//                hideProgress();
//                load_progres.setVisibility(View.VISIBLE);

                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                        load_progres.setVisibility(View.INVISIBLE);
                        if (mp.isPlaying()){
                            load_progres.setVisibility(View.INVISIBLE);

                        }

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
//                pd.dismiss();
                return false;
            }
        });

        btnWatchNow = (Button) rootView.findViewById(R.id.btnWatchNow);


        btnWatchNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!utilities.isNetworkAvailable()){
                    Toast.makeText(context, ""+getResources().getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show();
                }else {
                    PostcontentView();
                }
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

                }
            }
        });

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


    public void showDialoge() {
        String down_status=getResources().getString(R.string.download_status);
        String stop_download=getResources().getString(R.string.do_you_want_to_stop_download);


        try {
            androidx.appcompat.app.AlertDialog.Builder builder;
            builder = new androidx.appcompat.app.AlertDialog.Builder(context, R.style.dialoge);
            //            String centerNRed = "<div align:'center' ><span style='color:red' >"+title+"</span></div>";
//            builder.setTitle(Html.fromHtml(centerNRed))
            builder.setTitle(Html.fromHtml("<font color='#D93723'>"+down_status+"</font>"))
                    .setMessage(stop_download)
                    .setCancelable(false)
//                    .setIcon(R.drawable.ic_error_black_24dp)
                    .setPositiveButton(Html.fromHtml("<font color='#9AEB3D'>"+getResources().getString(R.string.yes)+"</font>"), new DialogInterface.OnClickListener() {
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
                    .setNegativeButton(Html.fromHtml("<font color='#9AEB3D'>"+getResources().getString(R.string.no)+"</font>"), new DialogInterface.OnClickListener() {
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
                    Intent watchintent=new Intent(context, Rajendra.class);
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


    @Override
    public void onStart() {
        video.start();

        super.onStart();
    }

    @Override
    public void onResume() {
        video.resume();

        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver);

    }
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showProgress() {
        if (progressDialog == null)
            progressDialog = DialogUtils.createProgressDialog(context);
        if (!progressDialog.isShowing())
            progressDialog.show();
    }


}
