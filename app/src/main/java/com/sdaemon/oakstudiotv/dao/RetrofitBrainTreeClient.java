package com.sdaemon.oakstudiotv.dao;

import com.sdaemon.oakstudiotv.utils.AppConstants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBrainTreeClient {
    private static Retrofit retrofit;

    public static SdaemonApi sdaemonApi() {

//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS);


        synchronized (Retrofit.class) {
            Retrofit.Builder rb = new Retrofit.Builder();
            rb.baseUrl(SdaemonApi.BASEPATH_BRAINTREE);
            rb.addConverterFactory(GsonConverterFactory.create());
            if(AppConstants.APP_DEBUGGER)
//                rb.client(httpClient.build());
                rb.client(new OkHttpClient.Builder().addInterceptor(new LogJsonInterceptor()).build());
            retrofit = rb.build();
        }
        return  retrofit.create(SdaemonApi.class);
    }


}
