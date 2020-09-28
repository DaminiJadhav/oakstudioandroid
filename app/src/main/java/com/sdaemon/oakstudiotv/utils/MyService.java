package com.sdaemon.oakstudiotv.utils;


import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import android.util.Log;


import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.SplashActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lenovo-aio on 27/12/16.
 */

public class MyService extends Service implements AppConstants {
    Context context;
    private Calendar prevC, nextC;
    private AppSession appSession;
    private boolean isSend = false;
    private int position = 0;
    private String title = "", message = "";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        appSession =  AppSession.getInstance(this);
        prevC = Calendar.getInstance();
        nextC = Calendar.getInstance();
        Log.i(getClass().getName(), "===========================================position " + position);
        super.onCreate();
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Calendar c = Calendar.getInstance();
        nextC = c;
     //   position = appSession.getPosition();
        SimpleDateFormat dateformat = new SimpleDateFormat("d/M/yyyy h:m aa");
        String datetime = dateformat.format(c.getTime());
        long difference = nextC.getTimeInMillis() - prevC.getTimeInMillis();
//        Log.i(getClass().getName(), "---------------------------difference" + difference + "\n" + difference / (1000 * 60));
        int time = (int) difference / (1000 * 60);
        isSend = false;
        boolean isAppOpen = false;
        ActivityManager am = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(am
                .getRunningAppProcesses().size());
        for (ActivityManager.RunningTaskInfo runningTaskInfo : tasks) {
            if (runningTaskInfo.topActivity.getPackageName().equals(getApplicationContext().getPackageName()))
                isAppOpen = true;
            break;
        }

        prevC = nextC;
        Log.i(getClass().getName(), "=======SEND=====================isAppOpen " + isAppOpen + "\n  TIME  " + HH_MM_AM_PM.format(c.getTime()) + "\nposition " + position  + "\nSIZE " + appSession.getNotificationList().size());

        showLocalNotification(getApplicationContext());

        if (appSession.getNotificationList() != null && position <= appSession.getNotificationList().size()) {

        } else if (appSession.getNotificationList() != null) {

            position = 0;
        }

//        if (isAppOpen && !isSend) {
//            isSend = true;
//            intent = new Intent(getApplicationContext(), NotificationDialogActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
//                    | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//            if (appSession.getLanguage().equals("hi")) {
//                title = appSession.getNotificationList().get(position).getTitleHindi();
//                message = appSession.getNotificationList().get(position).getMessageHI();
//            } else {
//                title = appSession.getNotificationList().get(position).getTitleEn();
//                message = appSession.getNotificationList().get(position).getMessageEN();
//            }
//            intent.putExtra(PN_TITLE, title);
//            intent.putExtra(MESSAGE_EN, message);
//
//            Log.i("================= ", "TYPE " + appSession.getNotificationList().get(position).getType() + "\n message " + message);
//            startActivity(intent);
//            PowerManager pm = (PowerManager) getApplicationContext()
//                    .getSystemService(Context.POWER_SERVICE);
//            PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
//                    | PowerManager.ACQUIRE_CAUSES_WAKEUP
//                    | PowerManager.ON_AFTER_RELEASE, getClass().getName());
//            wakeLock.acquire();
//
//        } else if (!isSend) {
//            showLocalNotification(getApplicationContext());
//        }
        if (!isAppOpen) {
      //     showLocalNotification(getApplicationContext());
            position++;
        }

        if (time > 0) {
//            prevC = nextC;
//            Log.i(getClass().getName(), "=======SEND=====================isAppOpen "+isAppOpen);
//            BaseFragment.showLocalNotification(getApplicationContext(), "Test Title", "Test Message");
        }


        appSession.setPosition(position);
    }

    public static Intent intent;

    public void showLocalNotification(Context context) {
        //https://developer.android.com/guide/practices/ui_guidelines/icon_design_status_bar.html
        intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(PN_TITLE, appSession.getNotificationList().get(position).getTitleEn());
        intent.putExtra(PN_LINK, appSession.getNotificationList().get(position).getLinkEn());
        intent.putExtra(MESSAGE_EN, appSession.getNotificationList().get(position).getMessageEN());

        intent.putExtra(TYPE, appSession.getNotificationList().get(position).getType());
        intent.putExtra(NOTIFICATION, NOTIFICATION);

        Log.i("=============", ": " + appSession.getNotificationList().get(position).getTitleEn());
        Log.i("==========", ": " + appSession.getNotificationList().get(position).getTitleHindi());
        Log.i("===========", ": " + appSession.getNotificationList().get(position).getLinkHindi());
        Log.i("============", ": " + appSession.getNotificationList().get(position).getMessageEN());
        Log.i("=============", ": " + appSession.getNotificationList().get(position).getMessageHI());
        Log.i("=============", ": " + appSession.getNotificationList().get(position).getType());

            title = appSession.getNotificationList().get(position).getTitleEn();
            message = appSession.getNotificationList().get(position).getMessageEN();

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(R.drawable.logo);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(123 /* ID of notification */, notificationBuilder.build());
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}