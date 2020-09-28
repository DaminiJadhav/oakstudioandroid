package com.sdaemon.oakstudiotv.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.ContentViewHistoryDTO;
import com.sdaemon.oakstudiotv.dto.EpisodeDTO;
import com.sdaemon.oakstudiotv.dto.SeasonDTO;
import com.sdaemon.oakstudiotv.model.VideoDTO;
import com.sdaemon.oakstudiotv.utils.AppSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.VERSION.SDK_INT;

public class SelectMovieDetailsActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    private ImageView iv_tabback,ivmovie;
    Button select,btnwatch;
    Spinner seasonspinner,episodespinner;
    List<String> seasonid;
    List<String> episode;
    List<SeasonDTO.Seasons> getseason;

    int getSeasonID,getSeasonNo;;
    ImageView ivdownarrow;
    VideoView videoView;
    MediaController mediaController;
    ArrayList<String> yourList;
    ProgressBar progressBar;
    ProgressDialog pd;
    SeasonDTO seasonDTO;
    EpisodeDTO episodeDTO;
    int contentId,userId;
    AppSession appSession;
    String selectepisode,trailor_episode_image;
    ArrayList<VideoDTO> videolist;
    String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_movie_details);
        progressBar=(ProgressBar) findViewById(R.id.progress_bar);
        ivdownarrow=findViewById(R.id.iv_down_season);
//        progressBar.setVisibility(View.VISIBLE);

        initialize();
        getWebSeriesSeason();
    }


    private void initialize() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);

        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        videoView=findViewById(R.id.video);
        select=findViewById(R.id.btn_select);
        btnwatch=findViewById(R.id.btn_watchNow);
        btnwatch.setOnClickListener(this::onClick);

        select.setOnClickListener(this::onClick);
        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivmovie=findViewById(R.id.iv_movieImage);
        seasonspinner=findViewById(R.id.spinner_season);
        seasonspinner.setOnItemSelectedListener(this);
//        seasonspinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        seasonid=new ArrayList<String>();
//        seasonspinner.setPrompt("Select your favorite Planet!");
        episodespinner=findViewById(R.id.episode_season);

//        episodespinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        episodespinner.setOnItemSelectedListener(this);
        episode=new ArrayList<String>();
        getseason =new ArrayList<SeasonDTO.Seasons>();

        appSession=AppSession.getInstance(this);
        if (appSession!=null){
            userId=appSession.getUserDTO().getResult().getCustomerId();
        }


        ivmovie.setOnClickListener(this::onClick);

        pd =new ProgressDialog(this , R.style.MyAlertDialogStyle);
        pd.setIndeterminate(true);
        pd.setMessage("Buffering video please wait...");
        pd.setProgress(100);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(true);
//        pd.show();
//        progressBar.setVisibility(View.VISIBLE);
        String url="https://oakstudio.azurewebsites.net";
        String video_url = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4";
        mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        String url1 = "";

        if (url1.contains("encryption=cenc")){
            if (SDK_INT > 8){
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {

                    URL url2 = new URL("https://oakstudio-usso.streaming.media.azure.net/5b98333f-3d6f-44d3-8184-03dbd1e8cc47/BalshaebThackeray.ism/manifest(format=mpd-time-csf,encryption=cenc).mpd");
                    BufferedReader reader=new BufferedReader(new InputStreamReader(url2.openStream()));
                    String str;
                    while ((str = reader.readLine()) != null) {

                        Pattern pattern = Pattern.compile("(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                                        + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                                        + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
                                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
                        Matcher matcher=pattern.matcher(str);
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
                    reader.close();
                }catch (MalformedURLException e){


                }catch (IOException e){
                }
            }
            Log.d("d", "initialize: " + yourList.get(0));

        }





        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {

                        if (mediaPlayer.isPlaying()){
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                        if (i==mediaPlayer.MEDIA_INFO_BUFFERING_START)
                                progressBar.setVisibility(View.VISIBLE);
//                              pd.show();
                        if (i==mediaPlayer.MEDIA_INFO_BUFFERING_END)
                                progressBar.setVisibility(View.INVISIBLE);
//                              pd.dismiss();
                        return false;
                    }
                });
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//                pd.dismiss();
//                progressBar.setVisibility(View.INVISIBLE);
                return false;
            }
        });




    }


    public void getWebSeriesSeason(){
//        seasonid.add("Select season");
        Call<SeasonDTO> seasonDTOCall= RetroClient.sdaemonApi().getSeason(5);
        seasonDTOCall.enqueue(new Callback<SeasonDTO>() {
            @Override
            public void onResponse(Call<SeasonDTO> call, Response<SeasonDTO> response) {
                if (response.isSuccessful()){
                    Toast.makeText(SelectMovieDetailsActivity.this, "Success"+response.message(), Toast.LENGTH_SHORT).show();
                    seasonDTO=response.body();
                   for (int i=0;i<seasonDTO.getSeasons().size();i++) {
//                       seasonID=seasonDTO.getSeasons().get(i).getSeasonId();
                       seasonid.add("Season "+seasonDTO.getSeasons().get(i).getSeasonNo().toString());
                       getseason.add(seasonDTO.getSeasons().get(i));
                       Log.i("Season No",""+seasonDTO.getSeasons().get(i).getSeasonNo());
                   }

//                   seasonspinner.setSelection(0,true);
//                    View view=seasonspinner.getSelectedView();
//                    ((TextView) view).setTextColor(getResources().getColor(R.color.white));


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectMovieDetailsActivity.this, android.R.layout.simple_spinner_item, seasonid);
//                    ivdownarrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_white_24dp));
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    seasonspinner.setAdapter(adapter);

//                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.text, countries);

//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectMovieDetailsActivity.this,android.R.layout.simple_spinner_item,seasonid);
//                    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_items);
//                     seasonspinner.setAdapter(adapter);


//
//                        ArrayAdapter<String> adapter1 = ArrayAdapter.createFromResource(SelectMovieDetailsActivity.this, seasonDTO.getSeasons().get(1).getSeasonNo(), R.layout.simple_spinner_items);
//                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        seasonspinner.setAdapter(adapter1);
                }

            }


            @Override
            public void onFailure(Call<SeasonDTO> call, Throwable t) {
                Toast.makeText(SelectMovieDetailsActivity.this, "Error :" +t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void  getseasonepisode(int getSeasonID){
//        episode.add("Select episode");

        Log.i("Season ID",""+getSeasonID);
        Call<EpisodeDTO> episodeDTOCall=RetroClient.sdaemonApi().getEpisode(getSeasonID);
        episodeDTOCall.enqueue(new Callback<EpisodeDTO>() {
            @Override
            public void onResponse(Call<EpisodeDTO> call, Response<EpisodeDTO> response) {
                if (response.isSuccessful()){
                    episode.clear();
                    Toast.makeText(SelectMovieDetailsActivity.this, "Success  :" +response.message(), Toast.LENGTH_SHORT).show();
                    Log.i("Episode ",""+response.message());
                    episodeDTO=response.body();
                    for (int i=0;i<response.body().getEpisodes().size();i++){
                        int position=i+1;
                        Log.i("Episode Index",""+i);
                        Log.i("Episode Position",""+position);
                        episode.add("Episode "+position);
//                        contentId=episodeDTO.getEpisodes().get(i).getContentID();
//                        episode.add(""+response.body().getEpisodes().get(i).getContentTitle());

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectMovieDetailsActivity.this, android.R.layout.simple_spinner_item, episode);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    episodespinner.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<EpisodeDTO> call, Throwable t) {
                Toast.makeText(SelectMovieDetailsActivity.this, "Error :" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        menu.findItem(R.id.action_like).setVisible(false);
        menu.findItem(R.id.action_share).setVisible(false);
        menu.findItem(R.id.action_person).setVisible(false);
        menu.findItem(R.id.action_tuneUp).setVisible(false);
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
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareSubText = "WhatsApp - The Great Chat App";
            String shareBodyText = "https://play.google.com/store/apps/details?id=com.whatsapp&hl=en";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubText);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(shareIntent, "Share With"));
            return true;
        }
        if (id == android.R.id.home){
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_movieImage:
                progressBar.setVisibility(View.VISIBLE);
//                pd.show();
                Uri uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
                if (uri!=null) {
//                    pd.dismiss();
                    videoView.setVisibility(View.VISIBLE);
                    ivmovie.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.INVISIBLE);
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(uri);
                    videoView.start();
                }
                break;

            case R.id.btn_select:
                Bundle bundle=new Bundle();
                Intent intent=new Intent(this,MovieDetailsActivity.class);
                bundle.putInt("KEY_CONTENTIDS",contentId);
                bundle.putString("KEY_SELECT_SEASON",String.valueOf(getSeasonNo));
                bundle.putString("KEY_SELECT_EPISODE",String.valueOf(selectepisode));
                Log.i("Episode Content Id 1",""+contentId);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_watchNow:
                PostcontentView();

                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Toast.makeText(adapterView.getContext(),
//                "Selected Item : " + adapterView.getItemAtPosition(i).toString(),
//                Toast.LENGTH_SHORT).show();
        select.setVisibility(View.VISIBLE);
        btnwatch.setVisibility(View.VISIBLE);

        if(adapterView.getId() == R.id.spinner_season) {
                getSeasonID = getseason.get(i).getSeasonId();
                getSeasonNo = getseason.get(i).getSeasonNo();

//                appSession.setSeasonNo(getSeasonNo);

//                Bundle extra=new Bundle();
//                extra.putInt("KEY_SEASEON_NO", Integer.parseInt(""+getSeasonNo));
//                Log.i("Season No send success ",""+getSeasonNo);
//                FragmentMovieDetails_Info fragobj = new FragmentMovieDetails_Info();
//                fragobj.setArguments(extra);

                Log.i("Season onItemSelected ", "" + getSeasonID);
                getseasonepisode(getSeasonID);

        }
        if(adapterView.getId() == R.id.episode_season) {
            String url="https://oakstudio.azurewebsites.net/";

            contentId=episodeDTO.getEpisodes().get(i).getContentID();
            trailor_episode_image=episodeDTO.getEpisodes().get(i).getContentImageURL();
//            Toast.makeText(adapterView.getContext(),
//                    "Selected Item : " + adapterView.getItemAtPosition(i).toString(),
//                    Toast.LENGTH_SHORT).show();
           selectepisode= adapterView.getItemAtPosition(i).toString();
            Glide.with(this)
                    .load(url+ trailor_episode_image)
                    .into(ivmovie);


            Log.i("Episode contentID ", "" + contentId+ " Trailor image "+ trailor_episode_image);

        }
    }


    public void PostcontentView(){
//        Call<ContentViewHistoryDTO> call= RetroClient.sdaemonApi().postContentViewHistory(4,6175);
        Log.i("ContentViewHistory","Season" + "   ContentID : "+contentId+ "  UserID : "+userId);

        Call<ContentViewHistoryDTO> call= RetroClient.sdaemonApi().postContentViewHistory(contentId,userId);

        call.enqueue(new Callback<ContentViewHistoryDTO>() {
            @Override
            public void onResponse(Call<ContentViewHistoryDTO> call, Response<ContentViewHistoryDTO> response) {
                if (response.isSuccessful()){
                    Toast.makeText(SelectMovieDetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    ContentViewHistoryDTO viewHistoryDTO=response.body();
                    time=viewHistoryDTO.getHistory().getSeenUpTo();

                    Log.i("ContentViewHistory","Season " +response.message()+ "  Time"+time);


                    String videoUrlSimple="https://oakstudio-usso.streaming.media.azure.net/c5c0d161-eb47-412a-b34e-ef9ff93ba309/BalshaebThackeray.ism/manifest(format=mpd-time-csf).mpd";  //1
                    videolist=new ArrayList<>();
                    videolist.add(new VideoDTO(videoUrlSimple));
                    Log.i("Video URL",""+videolist.size());
                    Bundle extra=new Bundle();
                    Intent watchintent=new Intent(SelectMovieDetailsActivity.this,Rajendra.class);
                    extra.putInt("KEY_WatchContentID",contentId);
                    extra.putString("PLAYBACK_POSITION",time);
                    Log.i("Season Movie view Time",""+time);
                    extra.putString("PN_JSON_OBJECT",new Gson().toJson(videolist));
                    watchintent.putExtras(extra);
                    startActivity(watchintent);

                }else {
                    Toast.makeText(SelectMovieDetailsActivity.this, "Unsuccess"  +response.message(), Toast.LENGTH_SHORT).show();
                    Log.i("ContentViewHistory","Season " +response.message());
                }
            }

            @Override
            public void onFailure(Call<ContentViewHistoryDTO> call, Throwable t) {
                Toast.makeText(SelectMovieDetailsActivity.this, "Error :" +t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("ContentViewHistory","Season " +t.getMessage());


            }
        });
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
