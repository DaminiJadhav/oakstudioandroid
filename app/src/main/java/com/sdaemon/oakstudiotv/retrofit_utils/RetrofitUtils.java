package com.sdaemon.oakstudiotv.retrofit_utils;


import com.sdaemon.oakstudiotv.retrofit_utils.restUtils.RestURLs;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Rahul Patil on 14-02-2018.
 */
public class RetrofitUtils {
    static Retrofit retrofit;
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(RestURLs.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(RestURLs.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(RestURLs.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new AddHeaderInterceptor())
                    .build();

            //making object of RestAdapter
            retrofit = new Retrofit.Builder()
                    .baseUrl(RestURLs.COMMON_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofitWithoutHeader() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(RestURLs.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(RestURLs.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(RestURLs.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new AddHeaderInterceptor())
                    .build();

            //making object of RestAdapter
            retrofit = new Retrofit.Builder()
                    .baseUrl(RestURLs.COMMON_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
