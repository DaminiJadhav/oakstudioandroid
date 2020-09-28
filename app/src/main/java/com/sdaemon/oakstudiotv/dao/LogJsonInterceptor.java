package com.sdaemon.oakstudiotv.dao;

import android.annotation.SuppressLint;
import android.util.Log;
import com.sdaemon.oakstudiotv.BuildConfig;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by ${Deven} on 9/24/18.
 */
public class LogJsonInterceptor implements Interceptor {
    @SuppressLint("LongLogTag")
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String rawJson = response.body().string();
        Log.d(BuildConfig.APPLICATION_ID, String.format("==Request URL: %s", request.url()));
        Log.d(BuildConfig.APPLICATION_ID, String.format("==Response is: %s", rawJson));

        // Re-create the response before returning it because body can be read only once
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), rawJson)).build();
    }
}