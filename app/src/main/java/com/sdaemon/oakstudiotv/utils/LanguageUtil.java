package com.sdaemon.oakstudiotv.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.google.android.exoplayer2.util.Log;
import com.nimbusds.jose.util.Resource;

import java.util.Locale;

public class LanguageUtil {

//   private static Context context;
    static AppSession appSession;

//   public LanguageUtil(Context context){
//        this.context=context;
//    }

    public static void setLocale(Context context,String lang){
        appSession=AppSession.getInstance(context);
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration=new Configuration();
        configuration.locale=locale;
        Resources resource=context.getResources();
        resource.updateConfiguration(configuration,resource.getDisplayMetrics());
//        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

//        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
//        editor.putString("My_lang",lang);
//        Log.i("Language",""+lang);
//        editor.apply();

        appSession.setLanguage(lang);
    }

    public static void  loadLocal(Context context){
        appSession=AppSession.getInstance(context);

        if (appSession.getLanguage()!=null) {
            String language = appSession.getLanguage();
            Log.i("Language 1", "" + language);

//        SharedPreferences editor=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
//        String language=editor.getString("My_lang","");
            setLocale(context,language);
        }
    }

}
