package com.sdaemon.oakstudiotv.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by Vishwas Shardul on 25-10-2018 .
 */
public class Methods {
    public static final String EXTRA_ACTIVITY = "activityFlag";
    public static final String EXTRA_ACTIVITY_HOME = "activityHome";


    // TODO Check Device Network Connection ...
    public static boolean isOnline(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
