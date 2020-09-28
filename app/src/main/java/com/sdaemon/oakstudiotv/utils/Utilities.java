package com.sdaemon.oakstudiotv.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;

import java.util.regex.Pattern;

import static android.Manifest.permission.READ_PHONE_STATE;


public class Utilities{
    private ConnectivityManager cm;
    private static Context context;
    private static Utilities utilities = null;
    /* Static 'instance' method */

    public static Utilities getInstance(Context mContext) {
        context = mContext;
        if (utilities == null)
            utilities = new Utilities();
        return utilities;
    }
    /**
     * Method for checking network availability
     */
    public boolean isNetworkAvailable() {
        try {
            cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            // if no network is available networkInfo will be null
            // otherwise check if we are connected
            if (networkInfo != null && networkInfo.isConnected())
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * method for email validation
     */
    /**
     * Validation regular expression
     */
    Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^([a-zA-Z0-9._-]+)@{1}(([a-zA-Z0-9_-]{1,67})|([a-zA-Z0-9-]+\\.[a-zA-Z0-9-]{1,67}))\\.(([a-zA-Z]{2,6})(\\.[a-zA-Z]{2,6})?)$");

    public boolean checkEmail(String email) {
        try {
            return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
        } catch (NullPointerException exception) {
            return false;
        }
    }
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }


    /**
     * /**
     * Method for getting device unique number i.e IMEI
     */
    public String getIMEIorDeviceId() {
        String imei = "";
        try {

            if (ActivityCompat.checkSelfPermission(context, READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                        READ_PHONE_STATE)) {
                    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    imei = tm.getDeviceId();
//                get_imei_data();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{READ_PHONE_STATE},
                            20);
                }
            } else {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                imei = tm.getDeviceId();
            }

            //imei = "" + System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }



}

