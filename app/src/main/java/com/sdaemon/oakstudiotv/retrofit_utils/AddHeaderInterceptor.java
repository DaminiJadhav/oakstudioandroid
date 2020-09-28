package com.sdaemon.oakstudiotv.retrofit_utils;


import com.sdaemon.oakstudiotv.retrofit_utils.restUtils.RestURLs;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Rahul Patil on 14-02-2018.
 */

public class AddHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("apitoken", RestURLs.API_KEY);


        return chain.proceed(builder.build());
    }
}