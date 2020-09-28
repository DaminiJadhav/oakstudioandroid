/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sdaemon.oakstudiotv.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.exoplayer2.offline.ActionFile;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.offline.DownloadHelper;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadManager.TaskState;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.offline.TrackKey;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.offline.DashDownloadHelper;
import com.google.android.exoplayer2.ui.DefaultTrackNameProvider;
import com.google.android.exoplayer2.ui.TrackNameProvider;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.Util;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.ContentInfo;
import com.sdaemon.oakstudiotv.model.VideoDTO;
import com.sdaemon.oakstudiotv.utils.AppConstant;
import com.sdaemon.oakstudiotv.utils.AppSession;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * Tracks media that has been downloaded.
 *
 * <p>Tracked downloads are persisted using an {@link ActionFile}, however in a real application
 * it's expected that state will be stored directly in the application's media database, so that it
 * can be queried efficiently together with other information about the media.
 */
public class DownloadTracker implements DownloadManager.Listener {

  /** Listens for changes in the tracked downloads. */
  public interface Listener {

    /** Called when the tracked downloads changed. */
    void onDownloadsChanged();
  }

 // private static final String TAG = "DownloadTracker";
    boolean check;
 private AppSession appSession;
 public Uri streamingUrl;
  private final Context context;
  private final DataSource.Factory dataSourceFactory;
  private final TrackNameProvider trackNameProvider;
  private final CopyOnWriteArraySet<Listener> listeners;
  private final HashMap<Uri, DownloadAction> trackedDownloadStates;
  private final ActionFile actionFile;
  private final Handler actionFileWriteHandler;

  public DownloadTracker(
      Context context,
      DataSource.Factory dataSourceFactory,
      File actionFile,
      DownloadAction.Deserializer... deserializers) {
    this.context = context.getApplicationContext();
    this.dataSourceFactory = dataSourceFactory;
    this.actionFile = new ActionFile(actionFile);
    trackNameProvider = new DefaultTrackNameProvider(context.getResources());
    listeners = new CopyOnWriteArraySet<>();
    trackedDownloadStates = new HashMap<>();
    HandlerThread actionFileWriteThread = new HandlerThread("DownloadTracker");
    actionFileWriteThread.start();
    actionFileWriteHandler = new Handler(actionFileWriteThread.getLooper());
       loadTrackedActions(
     deserializers.length > 0 ? deserializers : DownloadAction.getDefaultDeserializers());

  }

  public void addListener(Listener listener) {
    listeners.add(listener);
  }

  public void removeListener(Listener listener) {
    listeners.remove(listener);
  }

  public boolean isDownloaded(Uri uri) {
    return trackedDownloadStates.containsKey(uri);
  }

  @SuppressWarnings("unchecked")
  public List<StreamKey> getOfflineStreamKeys(Uri uri) {
    if (!trackedDownloadStates.containsKey(uri)) {
      return Collections.emptyList();
    }
    return trackedDownloadStates.get(uri).getKeys();
  }

    public static class NotificationReceiver extends BroadcastReceiver {


        static  int notificationId = 0;
        int positionID = 0;
        static int visitorID = 0;
        int empID = 0;
        static Long date_end=0L;
        static String date_video="";
        AppSession appSession;
        static String token = "";
        List<ContentInfo> contentInfos;

        static String contentTitle,contentType,contentBannerImage,contentViewCount,contentRating;
        static int contentYear;

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub


            Bundle extras = intent.getExtras();
            String action = intent.getAction();

            Log.i("Bundle extras" ,""+extras);
            Log.i("Bundle action" ,""+action);


            if (AppConstant.YES_ACTION.equals(action)) {
                if (extras != null) {

                    notificationId = extras.getInt("NotificationId");
                    visitorID = extras.getInt("visitor");
                    empID = extras.getInt("empID");
                    date_video =extras.getString("date");
                    Log.i("DownloadT Date",""+date_video);
                    date_end=extras.getLong("end_date_time");
                    Log.i("DownloadT end Date",""+date_end);
                    token = extras.getString("token");
                    Log.i("fs", "onReceive: "+token);
                    contentInfos=new ArrayList<>();


                    contentTitle=extras.getString("Content_Title");
                    contentType=extras.getString("Content_Type");
                    contentYear=extras.getInt("Content_Year");
                    contentRating=extras.getString("Content_Rating");
                    contentViewCount=extras.getString("Content_ViewCount");
                    contentBannerImage=extras.getString("Content_BannerImage");
//
                    Log.i("DownloadT ContentTitle",""+contentTitle);
                    Log.i("DownloadT ContentType",""+contentType);
                    Log.i("DownloadT ContentYear",""+contentYear);
                    Log.i("DownloadT ContentRating",""+contentRating);
                    Log.i("DownloadT Contentcount",""+contentViewCount);
                    Log.i("DownloadT ContentImg",""+contentBannerImage);





                }
            }

        }

    }




    public void toggleDownload(Activity activity, String name, Uri uri, String extension) {
    if (isDownloaded(uri)) {
//      System.out.println("================DOWNLOAD TRACKER 11 hls name: "+name);
//      System.out.println("================DOWNLOAD TRACKER 11 extension: "+extension);
//      System.out.println("================DOWNLOAD TRACKER 11 hls BYTES: "+Util.getUtf8Bytes(name));
     DownloadAction removeAction =
          getDownloadHelper(uri, extension).getRemoveAction(Util.getUtf8Bytes(name));
      startServiceWithAction(removeAction);




     /* DownloadAction downloadAction = DashDownloadAction.createRemoveAction(uri, Util.getUtf8Bytes(name));
      DownloadService.startWithAction(context, DemoDownloadService.class, downloadAction, true);

*/
    } else {
     /* System.out.println("================DOWNLOAD TRACKER 22 hls name: "+name);
      System.out.println("================DOWNLOAD TRACKER 22 extension: "+extension);
      System.out.println("================DOWNLOAD TRACKER 22 hls BYTES: "+Util.getUtf8Bytes(name));
*/
      StartDownloadDialogHelper helper =
          new StartDownloadDialogHelper(activity, getDownloadHelper(uri, extension), name);
      helper.prepare();
        streamingUrl=uri;
    }
  }

  // DownloadManager.Listener

  @Override
  public void onInitialized(DownloadManager downloadManager) {
    // Do nothing.
  }

  @Override
  public void onTaskStateChanged(DownloadManager downloadManager, TaskState taskState) {
    DownloadAction action = taskState.action;
    Uri uri = action.uri;
    if ((action.isRemoveAction && taskState.state == TaskState.STATE_COMPLETED)
        || (!action.isRemoveAction && taskState.state == TaskState.STATE_FAILED)) {
      // A download has been removed, or has failed. Stop tracking it.
      if (trackedDownloadStates.remove(uri) != null) {
        handleTrackedDownloadStatesChanged();
      }
    }
  }

  @Override
  public void onIdle(DownloadManager downloadManager) {
    // Do nothing.
  }

  // Internal methods

  private void loadTrackedActions(DownloadAction.Deserializer[] deserializers) {
    try {
      DownloadAction[] allActions = actionFile.load(deserializers);
      for (DownloadAction action : allActions) {
        trackedDownloadStates.put(action.uri, action);
      }
    } catch (IOException e) {
      Log.e(String.valueOf(context), "Failed to load tracked actions", e);
    }
  }

  private void handleTrackedDownloadStatesChanged() {
    for (Listener listener : listeners) {
      listener.onDownloadsChanged();
    }
    final DownloadAction[] actions = trackedDownloadStates.values().toArray(new DownloadAction[0]);
    actionFileWriteHandler.post(
            new Runnable() {
              @Override
              public void run() {
                try {
                  actionFile.store(actions);
                } catch (IOException e) {
                  e.printStackTrace();
                  //  Log.e(TAG, "Failed to store tracked actions", e);
                  Log.e(String.valueOf(context), "Failed to store tracked actions" + e);
                }
              }
            });
  }

  private void startDownload(DownloadAction action) {
    if (trackedDownloadStates.containsKey(action.uri)) {
      // This content is already being downloaded. Do nothing.
      return;
  }
    trackedDownloadStates.put(action.uri, action);
    handleTrackedDownloadStatesChanged();
    startServiceWithAction(action);
  }

  private void startServiceWithAction(DownloadAction action) {
    DownloadService.startWithAction(context, DemoDownloadService.class, action, false);
  }

  private DownloadHelper getDownloadHelper(Uri uri, String extension) {
   int type = Util.inferContentType(uri, extension);
 //  switch (type) {
     // case C.TYPE_DASH:
        return new DashDownloadHelper(uri, dataSourceFactory);
//      case C.TYPE_SS:
//        return new SsDownloadHelper(uri, dataSourceFactory);
    // case C.TYPE_HLS:
 //   return new HlsDownloadHelper(uri, dataSourceFactory);

         //  case C.TYPE_OTHER:
     //  return new ProgressiveDownloadHelper(uri);
  //  default:
      //  throw new IllegalStateException("Unsupported type: " + type);
 // }
  }

  private final class StartDownloadDialogHelper
          implements DownloadHelper.Callback, DialogInterface.OnClickListener {

    private final DownloadHelper downloadHelper;
    private final String name;
    int data=1;
    private final AlertDialog.Builder builder;
    private final View dialogView;
    private final List<TrackKey> trackKeys;
    private final ArrayAdapter<String> trackTitles;
    private final ListView representationList;

      public StartDownloadDialogHelper(
              Activity activity, DownloadHelper downloadHelper, String name) {
          this.downloadHelper = downloadHelper;
          this.name = name;

          builder =
                  new AlertDialog.Builder(activity,R.style.download_dialog)
                          .setTitle(context.getResources().getString(R.string.download_option))
                          .setPositiveButton(context.getResources().getString(R.string.start_download), this)
                          .setNegativeButton(android.R.string.cancel, null);
          // Inflate with the builder's context to ensure the correct style is used.
          LayoutInflater dialogInflater = LayoutInflater.from(builder.getContext());
          dialogView = dialogInflater.inflate(R.layout.start_download_dialog, null);
          trackKeys = new ArrayList<>();
          trackTitles =
                  new ArrayAdapter<>(
                          //  builder.getContext(), android.R.layout.simple_list_item_multiple_choice);
                          builder.getContext(), android.R.layout.simple_list_item_single_choice);
          representationList = dialogView.findViewById(R.id.representation_list);
          //representationList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
          representationList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
          representationList.setAdapter(trackTitles);
      }
      public void prepare() {
          downloadHelper.prepare(this);
      }

      @Override
      public void onPrepared(DownloadHelper helper) {
          int high = 0, medium = 0, low = 0;

          boolean smaller1=true;
          boolean small1=true;
          boolean medium1=true;
          boolean large1=true;

          appSession = AppSession.getInstance(context);
          appSession.setvalueID("nonhd");
          String value="";
          value=appSession.getvalueID();


          List<Integer>larger=new ArrayList<>();
          for (int i = 0; i < downloadHelper.getPeriodCount(); i++) {
              TrackGroupArray trackGroups = downloadHelper.getTrackGroups(i);
              for (int j = 0; j < trackGroups.length; j++) {
                  TrackGroup trackGroup = trackGroups.get(j);
                  for (int k = 0; k < trackGroup.length; k++) {

                     //trackKeys.add(new TrackKey(i, j, k));
                      // trackTitlexs.add(trackNameProvider.getTrackName(trackGroup.getFormat(k)));


                      if (trackGroup.getFormat(k).height == 720 && high<=0) {
                          high++;
//                          int i1=trackGroup.indexOf(trackGroup.getFormat(k));
//                          trackTitles.add(""+trackGroup.getFormat(k).height);
//                          if (trackTitles.isEnabled(0))
                          if((value.equals("ultrahd" )))
                          {
                              trackTitles.add(context.getResources().getString(R.string.high)+"\n"+context.getResources().getString(R.string.high_descriptiom));
                          }

                      }
                      else if (trackGroup.getFormat(k).height == 432 && medium<=0) {
                          medium++;

                          if(value.equals("ultrahd") || value.equals("hd"))
                          {
                              trackTitles.add(context.getResources().getString(R.string.Medium)+"\n"+context.getResources().getString(R.string.Medium_descriptiom));
                          }

                      } else  if (trackGroup.getFormat(k).height == 360 && low<=0) {
                          low++;

                          if(value.equals("ultrahd") || value.equals("hd") || value.equals("nonhd"))

                              trackTitles.add(context.getResources().getString(R.string.low) + "\n" + context.getResources().getString(R.string.Low_descriptiom));

                      }

//                        String no= Arrays.toString(new int[]{trackGroup.getFormat(k).height});
//                      Log.i("Set track selection val",""+no);



                      trackTitles.sort(new Comparator<String>() {
                          @Override
                          public int compare(String s, String t1) {
 //                              return s.compareTo(t1);
//                                return t1.charAt(t1.length()-1);
//                              return s.charAt(s.length()-1);
                              return t1.charAt(t1.length()-1)-s.charAt(s.length()-1);
//                              return s.charAt(4);

                          }
                      });


//------------------------------------------------------------------------------------
//                      if (large1==true) {
//                          if (large <= 0 && large1==true) {
//                              for (int z = 0; z < trackGroup.length; z++) {
//                                  if (trackGroup.getFormat(z).height == 720) {
//                                      String str = "Large";
//                                      trackTitles.add(context.getResources().getString(R.string.large)+"\n"+context.getResources().getString(R.string.high_descriptiom));
//                                      large1=false;
//                                  }
//
//                              }
//                          }
//                          large1=false;
//                          large++;
//                      }
//
//                      if (medium1==true) {
//                          if (medium <= 0 && medium1 == true){
//                              for (int a = 0; a < trackGroup.length; a++) {
//                                  if (trackGroup.getFormat(a).height == 432) {
//                                      String str = "Medium";
//                                      trackTitles.add(context.getResources().getString(R.string.Medium)+"\n"+context.getResources().getString(R.string.Medium_descriptiom));
//                                      medium1=false;
//
//                                  }
//                              }
//
//                      }
//                        medium1=false;
//                         medium++;
//                      }
//
//                      if (small1==true) {
//                          if (small <= 0 && small1 == true) {
//                              for (int b = 0; b < trackGroup.length; b++) {
//
//                                  if (small1==true) {
//                                      if (trackGroup.getFormat(b).height == 360) {
//                                          String str = "Small";
//                                          trackTitles.add(context.getResources().getString(R.string.small) + "\n" + context.getResources().getString(R.string.small_descriptiom));
//                                          small1=false;
//
//                                      }
//                                  }
//                              }
//                          }
//                  //      small1=false;
//                  //        small++;
//                      }


//---------------------------------------------------------------------------------------------------------
//                      if ((trackGroup.getFormat(k).height == 180 && smaller1==true)) {
//                          if (smaller <= 0 && smaller1==true)
//                              trackTitles.add(context.getResources().getString(R.string.smaller)+"\n"+context.getResources().getString(R.string.Low_descriptiom));
//                          trackKeys.add(new TrackKey(i, j, k));
////                          trackTitles.insert("smaller",1);
////                          trackKeys.add(1,new TrackKey(i,j,k));
//                          smaller1=false;
//                          smaller++;
//                      } else if (trackGroup.getFormat(k).height == 360 && small1==true) {
//                          if (small <= 0 && small1==true)
//                              trackTitles.add(context.getResources().getString(R.string.small) + "\n" + context.getResources().getString(R.string.Medium_descriptiom));
//                          trackKeys.add(new TrackKey(i, j, k));
////                              trackTitles.insert("small",2);
////                          trackKeys.add(2,new TrackKey(i,j,k));
//                          small1=false;
//                          small++;
//                      }
//                      else if (trackGroup.getFormat(k).height == 432 && medium1==true) {
//                          if (medium <= 0 && medium1==true)
//                              trackTitles.add(context.getResources().getString(R.string.Medium)+"\n"+context.getResources().getString(R.string.high_descriptiom));
//                          trackKeys.add(new TrackKey(i, j, k));
////                              trackTitles.insert("medium",3);
////                          trackKeys.add(3,new TrackKey(i,j,k));
//                          medium1=false;
//                          medium++;
//                      }else if (trackGroup.getFormat(k).height == 720 && large1==true) {
//                          if (large <= 0 && large1==true)
//                              trackTitles.add(context.getResources().getString(R.string.large)+"\n"+context.getResources().getString(R.string.high_descriptiom));
//                          trackKeys.add(new TrackKey(i, j, k));
////                              trackTitles.insert("high",0);
////                          trackKeys.add(0,new TrackKey(i,j,k));
//                          large1=false;
//                          large++;
//                      }

//                      else

                /*    if(trackTitles.getCount()==0)
                    {
//                        Toast.makeText(context, "Sorry this video is not supporting downloadable format", Toast.LENGTH_LONG).show();

                        trackTitles.add("Sorry this video is not supporting downloadable format");
                        data=2;
                    }
*/



                      if ((trackGroup.getFormat(k).sampleMimeType != null)) {
//                          Collections.sort(trackKeys);
                          trackKeys.add(new TrackKey(i, j, k));
                      }
                  }
              }
          }

          if (trackTitles.getCount()==0){
              data=2;
//                          trackTitles.add("Sorry this video is not supporting downloadable format");
//              builder.setMessage("Sorry this video is not supporting downloadable format");
//              builder.setPositiveButton("OK", this);
//              builder.show();
          }

          if (!trackKeys.isEmpty()) {
              builder.setView(dialogView);
          }
          if(data==2)
          {
//              builder.setTitle("    Download Option ");
//              Drawable dr = context.getResources().getDrawable(R.drawable.logo);
//                        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
//                        Drawable d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, 75, 75, true));
//              builder.setIcon(R.drawable.iv_error);
              builder.setMessage("\n"+context.getResources().getString(R.string.download_error));
              builder.setPositiveButton("OK", this);
//              builder.create().show();
          }
//          else
//          {
              builder.create().show();
//          }

      }

      @Override
      public void onPrepareError(DownloadHelper helper, IOException e) {
          Toast.makeText(
                  context.getApplicationContext(), R.string.download_start_error, Toast.LENGTH_LONG)
                  .show();
          Log.e(String.valueOf(context), "Failed to start download" + e);
      }
      @Override
      public void onClick(DialogInterface dialog, int which) {
          boolean trackinfo=true;
          ArrayList<TrackKey> selectedTrackKeys = new ArrayList<>();
          Log.e(String.valueOf(context), "===representationList.getCount(): " + representationList.getCount());
          Log.e(String.valueOf(context), "===representationList.getChildCount(): " + representationList.getChildCount());
          //  for (int i = 0; i < representationList.getChildCount(); i++) {
          // for (int i = 0; i < representationList.getCount()+1; i++) {
          for (int i = 0; i < representationList.getCount(); i++) {
              if (representationList.isItemChecked(i))  {
                  selectedTrackKeys.add(trackKeys.get(i));
              }
          }
          if (selectedTrackKeys.isEmpty()) {
              trackinfo=false;
          }
          for (int i = 0; i < trackKeys.size(); i++) {
              if (trackKeys.get(i).groupIndex == 1) {
                  selectedTrackKeys.add(trackKeys.get(i));
              }
          }
          if ((!selectedTrackKeys.isEmpty() && trackinfo==true)) {
              // We have selected keys, or we're dealing with single stream content.
              DownloadAction downloadAction =
                      downloadHelper.getDownloadAction(Util.getUtf8Bytes(name), selectedTrackKeys);
              startDownload(downloadAction);
              int s = NotificationReceiver.visitorID;
              String s1 = NotificationReceiver.date_video;
              Log.i("DownloadT Date",""+ s1 );

              Long date = NotificationReceiver.date_end;
              Log.i("DownloadT end Date",""+ date );


              String token = NotificationReceiver.token;

              String contenttitle= NotificationReceiver.contentTitle;
              String contenttype= NotificationReceiver.contentType;
              int contentyear= NotificationReceiver.contentYear;
              String contentrating= NotificationReceiver.contentRating;
              String contentimag= NotificationReceiver.contentBannerImage;
              String contentviewcount= NotificationReceiver.contentViewCount;

              appSession = AppSession.getInstance(context);
              ArrayList<VideoDTO> videoList = new ArrayList<>();
              if (videoList == null)
                  videoList = new ArrayList<>();
              videoList.addAll(appSession.getOfflineDownloadList());
//              videoList.add(new VideoDTO("" + streamingUrl, s1, date));
//              videoList.add(new VideoDTO("" + streamingUrl, s1, date, token));

              videoList.add(new VideoDTO("" + streamingUrl, s1, date, token,contenttitle,contenttype,contentyear,contentimag,contentviewcount,contentrating));

              appSession.setOfflineDownloadList(videoList);
          }
      }
  }
}

