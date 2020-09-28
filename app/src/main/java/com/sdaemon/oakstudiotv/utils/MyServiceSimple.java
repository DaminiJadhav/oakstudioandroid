package com.sdaemon.oakstudiotv.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class MyServiceSimple extends Service implements AppConstants{
    AppSession appSession;
    String date = "";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();


    }
    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

        appSession = AppSession.getInstance(this);
        if(appSession!=null){
            date = appSession.getOfflineDate();
            Log.i(String.valueOf(this),"===========Service Date Time : "+date);

        }
        Calendar c = Calendar.getInstance();
        // SimpleDateFormat dateformat = new SimpleDateFormat("d/M/yyyy h:m aa");
       // SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy hh:mm:ss");
        SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy");
        String dateafter = dateformat.format(c.getTime());
        Log.i(String.valueOf(this), "###################### Date: " + dateafter);
        returnDays(date,dateafter);


//        //   String date = "03/26/2012 11:00:00";
//        //  String dateafter = "03/26/2012 11:59:00";
//
//        Calendar c = Calendar.getInstance();
//        // SimpleDateFormat dateformat = new SimpleDateFormat("d/M/yyyy h:m aa");
//        SimpleDateFormat dateformat = new SimpleDateFormat("M/d/yyyy hh:mm:ss");
//        String dateafter = dateformat.format(c.getTime());
//        Log.i(String.valueOf(this), "###################### Date: " + dateafter);
//
//
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "M/d/yyyy hh:mm:ss");
//        Date convertedDate = new Date();
//        Date convertedDate2 = new Date();
//        try {
//            convertedDate = dateFormat.parse(date);
//            convertedDate2 = dateFormat.parse(dateafter);
//            if (convertedDate2.after(convertedDate)) {
//              //  txtView.setText("true");
//                Log.i(String.valueOf(this), "###################### TRUE");
//            } else {
//               // txtView.setText("false");
//                Log.i(String.valueOf(this), "###################### FALSE");
//            }
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show(); appSession.setOfflineDate(null);


    }

/*
  //  https://stackoverflow.com/questions/42553017/android-calculate-days-between-two-dates/42553096
    public static final String DATE_FORMAT = "d/M/yyyy";  //or use "M/d/yyyy"

    public static long getDaysBetweenDates(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAY);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }
    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return unit.convert(timeDiff, TimeUnit.MILLISECOND);
        }
    }
    */



    private void returnDays(String startDate,String endDate){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter  formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
//            LocalDate  start = LocalDate.parse("2/3/2017",formatter);
//            LocalDate  end = LocalDate.parse("3/3/2017",formatter);



              LocalDate  start = LocalDate.parse(startDate,formatter);
              LocalDate  end = LocalDate.parse(endDate,formatter);


            System.out.println(ChronoUnit.DAYS.between(start, end)); // 28
            Log.i(String.valueOf(this), "@@@@@@@@@@@@@@@ :"+ChronoUnit.DAYS.between(start, end));

            Bundle bundle = new Bundle();
            bundle.putString(PN_TYPE,""+ChronoUnit.DAYS.between(start, end));
            sendBroadcast(new Intent().setAction(REFRESH_LIST).putExtras(bundle));

        }

    }

}