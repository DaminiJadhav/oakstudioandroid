package com.sdaemon.oakstudiotv.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.CamcorderProfile;
import android.media.MediaDrm;
import android.media.UnsupportedSchemeException;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.sdaemon.oakstudiotv.BuildConfig;
import com.sdaemon.oakstudiotv.R;

import com.sdaemon.oakstudiotv.dto.PlaybackDTO;
import com.sdaemon.oakstudiotv.model.VideoDTO;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.DatabaseHelperPlayBack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.os.Build.VERSION.SDK_INT;


public class ButtonActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnBasic, btnStandard, btnPre;
    private Context mContext;
    private ImageView iv_tabback;
    ArrayList<VideoDTO> videoList;
    private AppSession appSession;
    private DatabaseHelperPlayBack databaseHelperPlayBack;
    private ArrayList<PlaybackDTO> playBackList;
    ArrayList<String>yourList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_activity);
        appSession = AppSession.getInstance(getApplicationContext());
//        if(videoList!=null){
//            videoList.clear();
//
//        }
        initialize();
    }

    private void initialize() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        btnBasic = (Button) findViewById(R.id.btn_basic);
        btnBasic.setOnClickListener(this);
        btnStandard = (Button) findViewById(R.id.btn_standard);
        btnStandard.setOnClickListener(this);
        btnPre = (Button) findViewById(R.id.btn_premium);
        btnPre.setOnClickListener(this);

        getWVDrmInfo();

        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                // Create a URL for the desired page
                URL url = new URL(" https://oakstudio-usso.streaming.media.azure.net/6b411695-6eab-424d-ba4c-49c2b870f9f1/1THE_LION_KING_Trailer_(4K_ULTRA.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd");
                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                while ((str = in.readLine()) != null) {
                    // str is one line of text; readLine() strips the newline character(s)
                    Pattern pattern = Pattern.compile("(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
                            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                    Matcher matcher = pattern.matcher(str);
                    while (matcher.find()) {
                        System.out.println(matcher.start()+1 + " : " + matcher.end());
                        System.out.println(str.substring(matcher.start()+1,matcher.end()));
                        String s=str.substring(matcher.start()+1,matcher.end());
                        yourList=new ArrayList<>();
                        if(s.contains("keydelivery"))
                        {
                            yourList.add(s);
                            Set<String> set = new HashSet<>(yourList);
                            yourList.clear();
                            yourList.addAll(set);
                        }
                    }
                }
                in.close();
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
        }
      //  Log.d("d", "initialize: "+yourList.get(0));




        //String videoUrlSimple =  "https://sdaemonazuremedia-inct.streaming.media.azure.net/28eb6791-58b6-4ef8-a086-a1d807164e21/AQUAMAN.ism/manifest(format=m3u8-aapl).m3u8";

        //  String videoUrlSimple =  "https://nanomediaservices-inct.streaming.media.azure.net/f7f59c2b-3364-4d0a-a025-dfb10255a1d6/AQUAMAN.ism/manifest(format=m3u8-aapl).m3u8";
//          String videoUrlSimple =  "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8";
        //  String videoUrlSimple =  "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8";
        //  String videoUrlSimple =  "http://184.72.239.149/vod/smil:BigBuckBunny.smil/playlist.m3u8";


        //  String videoUrlSimple =  "https://nanomediaservices-inct.streaming.media.azure.net/f7f59c2b-3364-4d0a-a025-dfb10255a1d6/AQUAMAN.ism/manifest(format=m3u8-aapl).m3u8";

        //  String videoUrlSimple = "https://oakstudio-usso.streaming.media.azure.net/a79335cb-eb85-4a24-9a8c-824abf3325af/BigBuckBunny.ism/manifest(format=m3u8-aapl).m3u8";
        //  String videoUrlSimple = "https://oakstudio-usso.streaming.media.azure.net/f377460d-95e9-4f25-9330-12880d6ad31b/BigBuckBunny.ism/manifest(format=m3u8-aapl).m3u8";
      //   String videoUrlSimple =  "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"; // 5
        //    String videoUrlSimple =  "https://oakstudio-usso.streaming.media.azure.net/c5868280-b45a-41bc-adde-478afa6ccae7/BigBuckBunny.ism/manifest(format=m3u8-aapl)"; // 5
        //    String videoUrlSimple = "https://oakstudio-usso.streaming.media.azure.net/c5868280-b45a-41bc-adde-478afa6ccae7/BigBuckBunny.ism/manifest(format=m3u8-aapl).m3u8"; // 5
       //  String videoUrlSimple =  "https://oakstudio-usso.streaming.media.azure.net/442322e0-c52f-4ce5-8895-4cada54ff1d5/BigBuckBunny.ism/manifest(format=m3u8-aapl).m3u8";
      //   String videoUrlSimple =  "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8";
       //  String videoUrlSimple =  "https://mnmedias.api.telequebec.tv/m3u8/29880.m3u8";
      //  String videoUrlSimple ="https://oakstudio-usso.streaming.media.azure.net/54b02c2f-b915-4632-a942-de2edf568ac3/BigBuckBunny.ism/manifest(format=m3u8-aapl).m3u8";
         String videoUrlMultiAdudio = "https://amssamples.streaming.mediaservices.windows.net/55034fb3-11af-43e4-a4de-d1b3ca56c5aa/ElephantsDream_MultiAudio.ism/manifest(format=m3u8-aapl).m3u8";



//        String videoUrlSimple="https://oakstudio-usso.streaming.media.azure.net/29448586-fe0b-4913-8449-e10a6006dd23/CAPTAIN-MARVEL-Trailer-(4K-ULTRA.ism/manifest(format=mpd-time-cmaf)";
//        String videoUrlSimple="https://oakstudio-usso.streaming.media.azure.net/29448586-fe0b-4913-8449-e10a6006dd23/CAPTAIN-MARVEL-Trailer-(4K-ULTRA.ism/manifest";
//        String videoUrlSimple="https://oakstudio-usso.streaming.media.azure.net/00f29a29-defc-4a50-925c-6528a8a92537/CAPTAIN-MARVEL-Trailer-(4K-ULTRA.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd";
//        String videoUrlSimple="https://oakstudio-usso.streaming.media.azure.net/eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE1NzM4ODQ0ODQsImV4cCI6MTU3Mzg4Nzc4NCwiaXNzIjoibXlJc3N1ZXIiLCJhdWQiOiJteUF1ZGllbmNlIn0.vlz4Ce_3QjHAMzm5YqdnpMxfXdJl61Z9B7xdAA9BVuY/CAPTAIN-MARVEL-Trailer-(4K-ULTRA.ism/manifest(encryption=cenc).mpd";
//        String videoUrlSimple="http://amssamples.streaming.mediaservices.windows.net/683f7e47-bd83-4427-b0a3-26a6c4547782/BigBuckBunny.ism/manifest(format=mpd-time-csf).mpd";
//        String videoUrlSimple="https://s3.amazonaws.com/_bc_dml/example-content/sintel_dash/sintel_vod.mpd";  //NW

//
//        String videoUrlSimple="https://amssamples.streaming.mediaservices.windows.net/55034fb3-11af-43e4-a4de-d1b3ca56c5aa/ElephantsDream_MultiAudio.ism/manifest(format=mpd-time-csf).mpd";   //2
//        String videoUrlSimple="http://livesim.dashif.org/livesim/start_1800/testpic_2s/Manifest.mpd";     //1
//        String videoUrlSimple="https://bitmovin-a.akamaihd.net/content/playhouse-vr/mpds/105560.mpd";   //2
//          String videoUrlSimple="http://dash.edgesuite.net/akamai/bbb_30fps/bbb_30fps.mpd";           //3
//        String videoUrlSimple="https://oakstudio-usso.streaming.media.azure.net/6b411695-6eab-424d-ba4c-49c2b870f9f1/1THE_LION_KING_Trailer_(4K_ULTRA.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd";           //3

//        String videoUrlSimple="http://demo.unified-streaming.com/video/tears-of-steel/tears-of-steel.ism/.mpd"; //no match quality
        String videoUrlSimple="https://oakstudio-usso.streaming.media.azure.net/c5c0d161-eb47-412a-b34e-ef9ff93ba309/BalshaebThackeray.ism/manifest(format=mpd-time-csf).mpd";  //1
//        String videoUrlSimple="https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd";//2
//        String videoUrlSimple="http://amssamples.streaming.mediaservices.windows.net/683f7e47-bd83-4427-b0a3-26a6c4547782/BigBuckBunny.ism/manifest(format=mpd-time-csf).mpd"; //2
//        String videoUrlSimple="https://oakstudio-usso.streaming.media.azure.net/5b98333f-3d6f-44d3-8184-03dbd1e8cc47/BalshaebThackeray.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd";  //1


//        String videoUrlSimple="https://storage.googleapis.com/wvmedia/clear/h264/tears/tears_sd.mpd";
//        String videoUrlSimple="https://storage.googleapis.com/wvmedia/clear/h264/tears/tears_hd.mpd";
//        String videoUrlSimple="http://profficialsite.origin.mediaservices.windows.net/c51358ea-9a5e-4322-8951-897d640fdfd7/tearsofsteel_4k.ism/manifest(format=mpd-time-csf).mpd";   //NM
//        String videoUrlSimple="https://oakstudio-usso.streaming.media.azure.net/343c5154-6e13-4488-9c82-58be66cc3d2c/BalshaebThackeray.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd";
//        String videoUrlSimple="https://oakstudio-usso.streaming.media.azure.net/215ef336-d76c-4122-a026-1749068dc113/BalshaebThackeray.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd";




        String mail="https://oakstudio-usso.streaming.media.azure.net/e5d85a61-fb12-48d8-b932-7826044a3a34/BalshaebThackeray.ism/manifest(format=fmp4-v20)";
        String newee=mail.replace("fmp4-v20)","mpd-time-csf).mpd");
        Log.d("f", "initialize: "+newee);

        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        Log.d("rdp", "Max Width: " + profile.videoFrameWidth);
        Log.d("rdp", "Max Height: " + profile.videoFrameHeight);


        videoList = new ArrayList<>();
        videoList.add(new VideoDTO(videoUrlSimple));
        //videoList.add(new VideoDTO(videoUrlMultiAdudio));
        //  videoList.add(new VideoDTO(videoUrlSimple));
        Log.i(String.valueOf(ButtonActivity.this), "========= VIDEO LIST SIZE : " + videoList.size());

    }

    private static final UUID WIDEVINE_UUID = new UUID(0xEDEF8BA979D64ACEL, 0xA3C827DCD51D21EDL);
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressWarnings("ResourceType")
    private void getWVDrmInfo() {
        MediaDrm mediaDrm = null;
        try {
            mediaDrm = new MediaDrm(WIDEVINE_UUID);
            String vendor = mediaDrm.getPropertyString(MediaDrm.PROPERTY_VENDOR);
            String version = mediaDrm.getPropertyString(MediaDrm.PROPERTY_VERSION);
            String description = mediaDrm.getPropertyString(MediaDrm.PROPERTY_DESCRIPTION);
            String algorithms = mediaDrm.getPropertyString(MediaDrm.PROPERTY_ALGORITHMS);
            String securityLevel = mediaDrm.getPropertyString("securityLevel");
            String systemId = mediaDrm.getPropertyString("systemId");
            String hdcpLevel = mediaDrm.getPropertyString("hdcpLevel");
            String maxHdcpLevel = mediaDrm.getPropertyString("maxHdcpLevel");
            String usageReportingSupport = mediaDrm.getPropertyString("usageReportingSupport");
            String maxNumberOfSessions = mediaDrm.getPropertyString("maxNumberOfSessions");
            String numberOfOpenSessions = mediaDrm.getPropertyString("numberOfOpenSessions");
            mediaDrm.release();
        } catch (UnsupportedSchemeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        menu.findItem(R.id.action_like).setVisible(true);
        menu.findItem(R.id.action_share).setVisible(true);
        menu.findItem(R.id.action_tuneUp).setVisible(false);
        menu.findItem(R.id.action_person).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_like) {
            return true;
        }
        if (id == R.id.action_share) {

            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_basic:
                databaseHelperPlayBack = new DatabaseHelperPlayBack(this);
                playBackList = databaseHelperPlayBack.getAllTeachers();
                Log.i(String.valueOf(ButtonActivity.this), "========= playBackList : " + playBackList.size());
                String playBackPosition = "0";
                for (int i = 0; i < playBackList.size(); i++) {
                    Log.i(String.valueOf(ButtonActivity.this), "=========URL : " + playBackList.get(i).getUrlId());
                    Log.i(String.valueOf(ButtonActivity.this), "=========URL ID : " + playBackList.get(i).getPlayBackPosition());

                    if ("Rajendra".equalsIgnoreCase(playBackList.get(i).getUrlId())) {
                        //  Toast.makeText(ButtonActivity.this, " if"+playBackList.get(i).getPlayBackPosition(), Toast.LENGTH_SHORT).show();
                        playBackPosition = playBackList.get(i).getPlayBackPosition();
                    } else {
                        //  Toast.makeText(ButtonActivity.this, "else"+playBackList.get(i).getPlayBackPosition(), Toast.LENGTH_SHORT).show();
                        playBackPosition = "0";
                    }

                }
                Intent intent = new Intent(ButtonActivity.this, Rajendra.class);
                Bundle bundle = new Bundle();
                bundle.putString("TYPE", "");
                bundle.putString("PLAYBACK_POSITION", playBackPosition);
                bundle.putString("PN_JSON_OBJECT", new Gson().toJson(videoList));
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btn_standard:
                break;
            case R.id.btn_premium:
                intent = new Intent(ButtonActivity.this, OffLineDownloadListActivity.class);
                startActivity(intent);

                //  databaseHelperTeacher = new DatabaseHelperPlayBack(this);
//                teachersModelArrayList = databaseHelperTeacher.getAllTeachers();
//
//                Log.i(String.valueOf(ButtonActivity.this),"========= teachersModelArrayList : "+teachersModelArrayList.size());
//                for (int i = 0; i <teachersModelArrayList.size() ; i++) {
//                    Log.i(String.valueOf(ButtonActivity.this),"=========URL : "+teachersModelArrayList.get(i).getUrlId());
//                    Log.i(String.valueOf(ButtonActivity.this),"=========URL ID : "+teachersModelArrayList.get(i).getPlayBackPosition());
//
//                    if("Rajendra".equalsIgnoreCase(teachersModelArrayList.get(i).getUrlId())){
//                        Toast.makeText(ButtonActivity.this, " if"+teachersModelArrayList.get(i).getPlayBackPosition(), Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(ButtonActivity.this, "else"+teachersModelArrayList.get(i).getPlayBackPosition(), Toast.LENGTH_SHORT).show();
//                    }
//
//                }

                break;
        }
    }


}
