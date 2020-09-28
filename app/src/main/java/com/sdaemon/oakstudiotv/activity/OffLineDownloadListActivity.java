package com.sdaemon.oakstudiotv.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.offline.DownloadAction;

import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.source.hls.offline.HlsDownloadAction;
import com.google.gson.Gson;
import com.sdaemon.oakstudiotv.BuildConfig;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.OffLineDownloadAdapter;
import com.sdaemon.oakstudiotv.model.VideoDTO;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class OffLineDownloadListActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private ImageView iv_tabback;
    RecyclerView rvList;
    DownloadTracker downloadTracker;

    private LinearLayoutManager mLinearLayoutManager;
    private OffLineDownloadAdapter adapter;
    ArrayList<VideoDTO> list;
    AppSession appSession;
    Context context;
    int removePosition = -1;
    String delete = "";
    private DownloadManager downloadManager;

    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.off_line_download_list);
        context = OffLineDownloadListActivity.this;
        appSession = AppSession.getInstance(getApplicationContext());
        downloadManager = ((AppSubClass) getApplication()).getDownloadManager();
        initialize();
    }

    private void initialize() {
        //  String videoUrl= "http://azuremediaservicedemo-inct.streaming.media.azure.net/01042abd-a7e7-4fa9-a5de-77ea51b2b9d0/AQUAMAN Trailer 2 (4K ULTRA HD) .ism/manifest(format=m3u8-aapl-v3,filter=Premium)";
        String videoUrl = "Sdaemon Infotech";
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        list = new ArrayList<>();
        /* list.add(new VideoDTO(videoUrl));
        list.add(new VideoDTO(videoUrl));
        list.add(new VideoDTO(videoUrl));
        list.add(new VideoDTO(videoUrl));
        list.add(new VideoDTO(videoUrl));
        list.add(new VideoDTO(videoUrl));*/
        //   Log.i(String.valueOf(OffLineDownloadListActivity.this),"=========LIST SIZE : "+list.size());

        if (appSession != null) {
            list = appSession.getOfflineDownloadList();
            Log.i(String.valueOf(OffLineDownloadListActivity.this), "=========SESSION LIST SIZE : " + list.size());
            if (list.size() < 1) {
                Toast.makeText(OffLineDownloadListActivity.this, "NO OFFLINE DOWNLOADED VIDEO", Toast.LENGTH_LONG).show();
            }
        }
        mLinearLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(mLinearLayoutManager);
        adapter = new OffLineDownloadAdapter(this, list, onClickCallback);
        rvList.setAdapter(adapter);

    }

    final OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if (list != null && list.size() > position) {
                if (type.equalsIgnoreCase("play")) {
                    // Toast.makeText(OffLineDownloadListActivity.this, "DOWNLOAD", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(OffLineDownloadListActivity.this, Rajendra.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TYPE", "download");

                    bundle.putString("tokenvalue",list.get(position).getToken());
                    Log.i("Offline Download token",""+list.get(position).getToken());
                    Log.i("safd", list.get(position).getToken());
                    //  bundle.putString("URL",  list.get(position).getVideoUrl());

                    bundle.putInt("OFF_LINE_POSITION", position);
                    Log.i("Offline position",""+position);

                    bundle.putString("PN_JSON_OBJECT", new Gson().toJson(list));
                    Log.i("Gson Data",""+list);

                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (type.equalsIgnoreCase("remove")) {
                    delete = list.get(position).getVideoUrl();
                    removePosition = position;
                    dialogConfirm(getResources().getString(R.string.are_you_sure_you_want_to_remove_this_video));
                } else if (type.equalsIgnoreCase("pause")) {
                    Toast.makeText(OffLineDownloadListActivity.this, "Stop", Toast.LENGTH_LONG).show();
                    //   downloadManager.stopDownloads();
                    //  Log.i(String.valueOf(OffLineDownloadListActivity.this),"=======getAllTaskStates: "+downloadManager.getAllTaskStates());

                    URL url = null;
                    HttpURLConnection connection = null;
                    int downloaded = 0;
                    FileOutputStream fos = null;
                    BufferedInputStream in = null;

                    try {
                        url = new URL(list.get(position).getVideoUrl());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        connection = (HttpURLConnection) url.openConnection();
                        // connection = (HttpURLConnection)url.openStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //   if(ISSUE_DOWNLOAD_STATUS.intValue()==ECMConstant.ECM_DOWNLOADING){
                    //  File file=new File(DESTINATION_PATH);
                    File file = new File("" + url);
                    //  if(file.exists()){
                    downloaded = (int) file.length();
                    Log.i(String.valueOf(OffLineDownloadListActivity.this), "=============file.length() : " + file.length());
                    connection.setRequestProperty("Range", "bytes=" + (file.length()) + "-");
                    //  }
//                    }
                    // else{
//                        connection.setRequestProperty("Range", "bytes=" + downloaded + "-");
//                    }

                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    //    progressBar.setMax(connection.getContentLength());
                    try {
                        in = new BufferedInputStream(connection.getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        //   FileOutputStream fos=(downloaded==0)? new FileOutputStream(DESTINATION_PATH): new FileOutputStream(DESTINATION_PATH,true);
                        fos = (downloaded == 0) ? new FileOutputStream("" + url) : new FileOutputStream("" + url, true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
                    byte[] data = new byte[1024];
                    int x = 0;

                    try {
                        while ((x = in.read(data, 0, 1024)) >= 0) {
                            bout.write(data, 0, x);
                            downloaded += x;
                            //   progressBar.setProgress(downloaded);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (type.equalsIgnoreCase("resume")) {
                    Toast.makeText(OffLineDownloadListActivity.this, "Resume", Toast.LENGTH_LONG).show();


                } else if (type.equalsIgnoreCase("start")) {
                    Toast.makeText(OffLineDownloadListActivity.this, "Start", Toast.LENGTH_LONG).show();
                    downloadManager.startDownloads();
                    AppSubClass application = (AppSubClass) getApplication();
                    downloadTracker = application.getDownloadTracker();
                    try {
                        DownloadService.start(OffLineDownloadListActivity.this, DemoDownloadService.class);
                    } catch (IllegalStateException e) {
                        DownloadService.startForeground(OffLineDownloadListActivity.this, DemoDownloadService.class);
                    }
                    //   downloadTracker.toggleDownload(this, sample.name, uriSample.uri, uriSample.extension);
                    downloadTracker.toggleDownload(OffLineDownloadListActivity.this, "HLS", Uri.parse(list.get(position).getVideoUrl()), "");

                } else {


                }
            }

        }
    };

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
//                shareIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
//                shareIntent.setPackage("com.whatsapp");
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
           /* case R.id.btn_basic:
                Intent intent=new Intent(OffLineDownloadListActivity.this, Rajendra.class);
             //   Intent intent=new Intent(ButtonActivity.this, VideoPlayerActivity.class);
                Bundle bundle=new Bundle();
                //bundle.putString("URL","http://azuremediaservicedemo-inct.streaming.media.azure.net/01042abd-a7e7-4fa9-a5de-77ea51b2b9d0/AQUAMAN Trailer 2 (4K ULTRA HD) .ism/manifest(format=m3u8-aapl-v3,filter=Basic)");
                bundle.putString("URL","https://azuremediaservicedemo-inct.streaming.media.azure.net/953fd80f-c7d8-4610-a945-9c2b663dcc45/Sony Bravia OLED 4K Demo.ism/manifest(format=m3u8-aapl,filter=Basic)");
                bundle.putString("PN_JSON_OBJECT", new Gson().toJson(list));
                intent.putExtras(bundle);
                startActivity(intent);
                break;*/
        }
    }

    public void dialogConfirm(String message) {
        final Dialog dialog = new Dialog(OffLineDownloadListActivity.this);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_box_yes_no);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.confirm));
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText(Html.fromHtml("" + message));
        tv_message.setMovementMethod(new ScrollingMovementMethod());
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        window.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (appSession != null) {
                    list.remove(removePosition);


                   /* AppSubClass application = (AppSubClass) getApplication();
                    downloadTracker = application.getDownloadTracker();


                    for (int i = 0; i < list.size(); i++) {
                        boolean b = downloadTracker.isDownloaded(Uri.parse(list.get(i).getVideoUrl()));
                        if (b == true) {

                            DownloadAction downloadAction = HlsDownloadAction.createRemoveAction(Uri.parse(list.get(i).getVideoUrl()), null);
                            DownloadService.startWithAction(OffLineDownloadListActivity.this, DemoDownloadService.class, downloadAction, true);
                        } else {

                        }


                    }

                    list.clear();;*/

                    DownloadAction downloadAction = HlsDownloadAction.createRemoveAction(Uri.parse(delete), null);
                    DownloadService.startWithAction(OffLineDownloadListActivity.this, DemoDownloadService.class, downloadAction, true);

                    appSession.setOfflineDownloadList(list);
                    if (appSession.getOfflineDownloadList() != null && appSession.getOfflineDownloadList().size() < 1) {
                        Toast.makeText(OffLineDownloadListActivity.this, "NO OFFLINE DOWNLOADED VIDEO", Toast.LENGTH_LONG).show();
                    }
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
