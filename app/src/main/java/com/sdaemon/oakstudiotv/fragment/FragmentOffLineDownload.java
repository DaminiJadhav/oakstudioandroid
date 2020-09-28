package com.sdaemon.oakstudiotv.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.source.dash.offline.DashDownloadAction;
import com.google.android.exoplayer2.source.hls.offline.HlsDownloadAction;
import com.google.gson.Gson;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.DemoDownloadService;
import com.sdaemon.oakstudiotv.activity.DownloadTracker;
import com.sdaemon.oakstudiotv.activity.MainActivity;
import com.sdaemon.oakstudiotv.activity.Rajendra;
import com.sdaemon.oakstudiotv.adapter.OfflineMovieAdapter;
import com.sdaemon.oakstudiotv.model.VideoDTO;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.AppSubClass;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;
import java.util.ArrayList;
import java.util.Calendar;

public class FragmentOffLineDownload extends Fragment implements View.OnClickListener {
    View rootView;
    private Context context;
    private Context contextTemp;
    private ImageView iv_tabback;
    RecyclerView rvList;
    StaggeredGridLayoutManager mLayoutManager;
    private OfflineMovieAdapter adapter;
    ArrayList<VideoDTO> list;
    AppSession appSession;
    int removePosition = -1;
    String delete="";
    private DownloadManager downloadManager;
    DownloadTracker downloadTracker;
    ProgressBar progressBar;
    TextView tvdownload;
    ImageView ivdownload;
    Button btndownload;
    SwipeRefreshLayout Refreshlayout;



    public FragmentOffLineDownload() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_fragment_downloaded, container, false);
        return  rootView;
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  context =getActivity();
        context =getActivity().getApplicationContext();
        contextTemp =getActivity();

        appSession = AppSession.getInstance(context);
      //  downloadManager = ((AppSubClass)context).getDownloadManager();
        downloadManager = ((AppSubClass)context).getDownloadManager();
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_download);
        tvdownload = (TextView) rootView.findViewById(R.id.tv_download);
        ivdownload = (ImageView) rootView.findViewById(R.id.iv_download);
        btndownload = (Button) rootView.findViewById(R.id.btn_download);
        btndownload.setOnClickListener(this::onClick);

//        Refreshlayout=view.findViewById(R.id.refresh_download);
//        Refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData();
//                Refreshlayout.setRefreshing(false);
//            }
//        });

//        progressBar.setVisibility(View.VISIBLE);

        initialize();
//        if (list.size()==0){
//            tvdownload.setVisibility(View.VISIBLE);
//            ivdownload.setVisibility(View.VISIBLE);
//            btndownload.setVisibility(View.VISIBLE);
//            tvdownload.setText("Currently not downloaded videos");
//        }
    }

    private void initialize() {
        //  String videoUrl= "http://azuremediaservicedemo-inct.streaming.media.azure.net/01042abd-a7e7-4fa9-a5de-77ea51b2b9d0/AQUAMAN Trailer 2 (4K ULTRA HD) .ism/manifest(format=m3u8-aapl-v3,filter=Premium)";
        String videoUrl= "Sdaemon Infotech";
        rvList = (RecyclerView) rootView.findViewById(R.id.rv_downloadedList);
        list = new ArrayList<>();
        if (appSession != null) {
            list = appSession.getOfflineDownloadList();
            Log.i(String.valueOf(FragmentOffLineDownload.this),"=========SESSION LIST SIZE : "+list.size());

            for (int i = 0; i <list.size() ; i++) {
                Calendar startDate = Calendar.getInstance();
                int mYear = startDate.get(Calendar.YEAR);
                int mMonth = startDate.get(Calendar.MONTH)+1;
                int mDay = startDate.get(Calendar.DAY_OF_MONTH) ;
                startDate.set(mYear, mMonth, mDay);

                long startDateMillis = startDate.getTimeInMillis();
                if (list.get(i).getDate_time()!=null){

                if(list.get(i).getDate_time()<=startDateMillis)
                {
                    String s=list.get(i).getVideoUrl();
                    list.remove(i);

                    DownloadAction downloadAction = HlsDownloadAction.createRemoveAction(Uri.parse(s) ,null);
                    DownloadService.startWithAction(context, DemoDownloadService.class, downloadAction, true);
                    appSession.setOfflineDownloadList(list);
                    if(appSession.getOfflineDownloadList()!=null && appSession.getOfflineDownloadList().size()<1){
//                        Toast.makeText(context, "NO OFFLINE DOWNLOADED VIDEO", Toast.LENGTH_LONG).show();
                    }
                }
                }

            }
            if(list.size()<1){
//                Toast.makeText(context, "NO OFFLINE DOWNLOADED VIDEO", Toast.LENGTH_LONG).show();
            }
        }
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
        adapter = new OfflineMovieAdapter(context,contextTemp, list, onClickCallback);
        if (list.size()>0){
            Log.i("OfflineDownload list 1",""+list.size());
//            Toast.makeText(context, "downloaded", Toast.LENGTH_SHORT).show();
//            progressBar.setVisibility(View.GONE);
            rvList.setAdapter(adapter);
        }else {
            Log.i("OfflineDownload list 2",""+list.size());
//            Toast.makeText(context, "No download", Toast.LENGTH_SHORT).show();
//            progressBar.setVisibility(View.VISIBLE);
            tvdownload.setVisibility(View.VISIBLE);
            ivdownload.setVisibility(View.VISIBLE);
//            btndownload.setVisibility(View.VISIBLE);
            tvdownload.setText(getResources().getString(R.string.no_video_download));
        }


    }
    final OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {

            delete = list.get(position).getVideoUrl();
            removePosition = position;

            if (list != null && list.size() > position) {
                if (type.equalsIgnoreCase("play")) {
                    // Toast.makeText(OffLineDownloadListActivity.this, "DOWNLOAD", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, Rajendra.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("TYPE", "download");
                    //   bundle.putString("URL",  list.get(position).getVideoUrl());
                    bundle.putInt("OFF_LINE_POSITION", position);
                    bundle.putString("tokenvalue",list.get(position).getToken());
                    bundle.putString("PN_JSON_OBJECT", new Gson().toJson(list));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if (type.equalsIgnoreCase("remove")) {
                    Log.i("OfflineDownload list 3",""+list.size());
//                    delete = list.get(position).getVideoUrl();
//                    removePosition = position;
                    dialogConfirm(getResources().getString(R.string.are_you_sure_you_want_to_remove_this_video));
                }
                else if (type.equalsIgnoreCase("downloaded_delete")) {

                    if (appSession != null) {
//                    Toast.makeText(context, "removePosition " + removePosition, Toast.LENGTH_LONG).show();
                    list.remove(removePosition);



                        DownloadAction downloadAction = DashDownloadAction.createRemoveAction(Uri.parse(delete), null);
                    DownloadService.startWithAction(context, DemoDownloadService.class, downloadAction, true);
                    appSession.setOfflineDownloadList(list);
                    if (appSession.getOfflineDownloadList() != null && appSession.getOfflineDownloadList().size() < 1) {
//                        Toast.makeText(context, "NO OFFLINE DOWNLOADED VIDEO", Toast.LENGTH_LONG).show();
                        tvdownload.setVisibility(View.VISIBLE);
                        ivdownload.setVisibility(View.VISIBLE);
//                    btndownload.setVisibility(View.VISIBLE);
                        tvdownload.setText(getString(R.string.no_video_download));
                    }
                }


                    System.out.println("LongClick 1: " + removePosition);

//                    list.clear();
//                    list=appSession.getOfflineDownloadList();

                    adapter.notifyItemRemoved(removePosition);
                    adapter.notifyItemRangeChanged(removePosition, list.size());
//                    adapter.notifyDataSetChanged();
                    adapter.dialogdismiss();

//                    Fragment currentFragment = getFragmentManager().findFragmentById(R.id.frame_layout_download);
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    if (Build.VERSION.SDK_INT >= 26) {
//                        ft.setReorderingAllowed(false);
//                    }
//
//                    ft.detach(currentFragment).attach(currentFragment).commit();

//                    Fragment currentFragment = getFragmentManager().findFragmentById(R.id.frame_layout_download);
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.detach(currentFragment);
//                    fragmentTransaction.attach(currentFragment);
//                    fragmentTransaction.commit();

//
//                    if (list==null) {
//                        tvdownload.setVisibility(View.VISIBLE);
//                        ivdownload.setVisibility(View.VISIBLE);
////                    btndownload.setVisibility(View.VISIBLE);
//                        tvdownload.setText(getString(R.string.no_video_download));
//                    }
                }
                else if (type.equalsIgnoreCase("pause")) {
//                    Toast.makeText(context, "Stop", Toast.LENGTH_LONG).show();
       /*             //   downloadManager.stopDownloads();
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
                            //       progressBar.setProgress(downloaded);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                } else if (type.equalsIgnoreCase("start")) {
//                    Toast.makeText(context, "Start", Toast.LENGTH_LONG).show();
                    downloadManager.startDownloads();
                  //  AppSubClass application = (AppSubClass) getApplication();
                    AppSubClass application = (AppSubClass) context;
                    downloadTracker = application.getDownloadTracker();
                    try {
                        DownloadService.start(context, DemoDownloadService.class);
                    } catch (IllegalStateException e) {
                        DownloadService.startForeground(context, DemoDownloadService.class);
                    }
                    //   downloadTracker.toggleDownload(this, sample.name, uriSample.uri, uriSample.extension);
                    downloadTracker.toggleDownload((Activity) context, "HLS", Uri.parse(list.get(position).getVideoUrl()), "");

                } else {

                }
            }

        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){

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

            case R.id.btn_download:
                Intent intent=new Intent(context, MainActivity.class);
                startActivity(intent);
                break;

        }
    }
    public void dialogConfirm(String message) {
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
                    DownloadAction downloadAction = HlsDownloadAction.createRemoveAction(Uri.parse(delete) ,null);
                    DownloadService.startWithAction(context, DemoDownloadService.class, downloadAction, true);
                    appSession.setOfflineDownloadList(list);
                    if(appSession.getOfflineDownloadList()!=null && appSession.getOfflineDownloadList().size()<1){
//                        Toast.makeText(context, "NO OFFLINE DOWNLOADED VIDEO", Toast.LENGTH_LONG).show();
                    }
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void refreshData() {

//        list=appSession.getOfflineDownloadList();
//        adapter = new OfflineMovieAdapter(context,contextTemp, list, onClickCallback);
//        rvList.setAdapter(adapter);
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(FragmentOffLineDownload.this).attach(FragmentOffLineDownload.this).commit();

//        adapter.notifyDataSetChanged();

//        rvList.invalidate();
    }
    @Override
    public void onDestroy() {
//        adapter.myOnDestroy();
        super.onDestroy();

    }
}
