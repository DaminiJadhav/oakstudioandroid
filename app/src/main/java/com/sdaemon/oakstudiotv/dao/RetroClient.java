package com.sdaemon.oakstudiotv.dao;
import com.sdaemon.oakstudiotv.utils.AppConstants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ${Deven} on 9/14/18.
 */
public class RetroClient {

    private static Retrofit retrofit;

    public static SdaemonApi sdaemonApi() {
        synchronized (Retrofit.class) {
            Retrofit.Builder rb = new Retrofit.Builder();
            rb.baseUrl(SdaemonApi.BASEPATH);
            rb.addConverterFactory(GsonConverterFactory.create());
            if(AppConstants.APP_DEBUGGER)
                rb.client(new OkHttpClient.Builder().addInterceptor(new LogJsonInterceptor()).build());
            retrofit = rb.build();
        }
        return  retrofit.create(SdaemonApi.class);
    }



      /*private static Retrofit retrofit;

    public static SdaemonApi sdaemonApi() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        synchronized (Retrofit.class) {
            Retrofit.Builder rb = new Retrofit.Builder();
            rb.baseUrl(SdaemonApi.BASEPATH);
          //   rb.client(okHttpClient);
            rb.addConverterFactory(GsonConverterFactory.create());
            if(AppConstants.APP_DEBUGGER)
                rb.client(new OkHttpClient.Builder().addInterceptor(new LogJsonInterceptor()).build());
            retrofit = rb.build();
        }
        return  retrofit.create(SdaemonApi.class);
    }




*/

//    private static Retrofit retrofit;
//
//    public static SdaemonApi sdaemonApi() {
//
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .connectTimeout(100,TimeUnit.SECONDS)
//                .readTimeout(100,TimeUnit.SECONDS).build();
//
//        synchronized (Retrofit.class) {
//            Retrofit.Builder rb = new Retrofit.Builder();
//            rb.baseUrl(SdaemonApi.BASEPATH).client(client);
//            rb.addConverterFactory(GsonConverterFactory.create());
//            if(AppConstants.APP_DEBUGGER)
//                rb.client(new OkHttpClient.Builder().addInterceptor(new LogJsonInterceptor()).build());
//            retrofit = rb.build();
//        }
//        return  retrofit.create(SdaemonApi.class);
//
//    }







}
