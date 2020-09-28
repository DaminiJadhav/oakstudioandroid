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

import android.app.Notification;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadManager.TaskState;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.scheduler.PlatformScheduler;
import com.google.android.exoplayer2.ui.DownloadNotificationUtil;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.utils.AppSubClass;

import java.util.HashMap;

/** A service for downloading media. */
public class DemoDownloadService extends DownloadService {

  private static final String CHANNEL_ID = "download_channel";
  private static final int JOB_ID = 1;
  private static final int FOREGROUND_NOTIFICATION_ID = 1;
    private static final String TAG = "Something";

  public DemoDownloadService() {
    super(
        FOREGROUND_NOTIFICATION_ID,
        DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
        CHANNEL_ID,
        R.string.exo_download_notification_channel_name);
  }

  @Override
  protected DownloadManager getDownloadManager() {
    return ((AppSubClass) getApplication()).getDownloadManager();
  }

  @Override
  protected PlatformScheduler getScheduler() {
    return Util.SDK_INT >= 21 ? new PlatformScheduler(this, JOB_ID) : null;
  }





    @Override
  protected Notification getForegroundNotification(TaskState[] taskStates) {
        float totalPercentage = 0;
        int downloadTaskCount = 0;

        HashMap<Uri,Integer> progressMap= new HashMap<>();

        boolean allDownloadPercentagesUnknown = true;
        boolean haveDownloadedBytes = false;
        boolean haveDownloadTasks = false;
        boolean haveRemoveTasks = false;

        for (TaskState taskState : taskStates) {
            if (taskState.state != TaskState.STATE_STARTED && taskState.state != TaskState.STATE_COMPLETED) {
                continue;
            }
            if (taskState.action.isRemoveAction) {
                continue;
            }
            if (taskState.downloadPercentage != C.PERCENTAGE_UNSET) {
                totalPercentage += taskState.downloadPercentage;
                progressMap.put(taskState.action.uri, (int) taskState.downloadPercentage);
            }
            downloadTaskCount++;
        }
        sendIntentHash(progressMap);
        sendIntentHash1(progressMap);
       /* Set keys = progressMap.keySet();
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            Uri key = (Uri) i.next();
            Integer value =  progressMap.get(key);
            Log.i("yes","notifi_p "+key.toString() + " = " + value);
        }
*/







        for (TaskState taskState : taskStates) {
            if (taskState.state != TaskState.STATE_STARTED
                    && taskState.state != TaskState.STATE_COMPLETED) {
                continue;
            }
            if (taskState.action.isRemoveAction) {
                haveRemoveTasks = true;
                continue;
            }
            haveDownloadTasks = true;
            if (taskState.downloadPercentage != C.PERCENTAGE_UNSET) {
                allDownloadPercentagesUnknown = false;
                totalPercentage += taskState.downloadPercentage;
            }
            haveDownloadedBytes |= taskState.downloadedBytes > 0;
            downloadTaskCount++;
        }

        int progress = 0;
        boolean indeterminate = true;
        if (haveDownloadTasks) {
            progress = (int) (totalPercentage / downloadTaskCount);
            indeterminate = allDownloadPercentagesUnknown && haveDownloadedBytes;

            Log.i(TAG,"notifi "+progress);
//            sendIntent(progress);

        }



    return DownloadNotificationUtil.buildProgressNotification(
        /* context= */ this,
      //  R.drawable.exo_controls_play,
        R.drawable.logo,
            CHANNEL_ID,
        /* contentIntent= */ null,
        /* message= */ null,
        taskStates);
  }

    private void sendIntentHash(HashMap<Uri,Integer>map)
    {
        Intent intent = new Intent("hello");
        //  intent.putExtra("progress",progress);
        intent.putExtra("map",map);
        LocalBroadcastManager.getInstance(DemoDownloadService.this).sendBroadcast(intent);
    }


    private void sendIntentHash1(HashMap<Uri,Integer> map)
    {
        Intent intent = new Intent("Download_Url");
        //  intent.putExtra("progress",progress);
        intent.putExtra("Download_Map",map);
        LocalBroadcastManager.getInstance(DemoDownloadService.this).sendBroadcast(intent);
    }

    private void sendIntent(int progress) {
        Intent intent = new Intent("hello");
        intent.putExtra("progress",progress);
        LocalBroadcastManager.getInstance(DemoDownloadService.this).sendBroadcast(intent);
    }

    @Override
  protected void onTaskStateChanged(TaskState taskState) {
    if (taskState.action.isRemoveAction) {
      return;
    }
    Notification notification = null;
    if (taskState.state == TaskState.STATE_COMPLETED) {
      notification =
          DownloadNotificationUtil.buildDownloadCompletedNotification(
              /* context= */ this,
             // R.drawable.exo_controls_play,
                  R.drawable.logo,
              CHANNEL_ID,
              /* contentIntent= */ null,
              Util.fromUtf8Bytes(taskState.action.data));
    } else if (taskState.state == TaskState.STATE_FAILED) {
      notification =
          DownloadNotificationUtil.buildDownloadFailedNotification(
              /* context= */ this,
           //   R.drawable.exo_controls_play,
                  R.drawable.logo,

              CHANNEL_ID,
              /* contentIntent= */ null,
              Util.fromUtf8Bytes(taskState.action.data));
    }
    int notificationId = FOREGROUND_NOTIFICATION_ID + 1 + taskState.taskId;
    NotificationUtil.setNotification(this, notificationId, notification);


  }
}
