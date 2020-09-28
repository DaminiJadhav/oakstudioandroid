package com.sdaemon.oakstudiotv.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.github.anastr.speedviewlib.SpeedView;
import com.sdaemon.oakstudiotv.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckInternetSpeedActivity extends AppCompatActivity {
    private static final String TAG = "TasksSample";
    LinearLayout llprogress;
    ProgressBar progressBar;
    SpeedView speedView;
    Button btn_speed;
    double speed;
    Context context;
    public long startTime;
    public long endTime;
    public long fileSize;
    // bandwidth in kbps
    public int POOR_BANDWIDTH = 150;
    public int AVERAGE_BANDWIDTH = 550;
    public int GOOD_BANDWIDTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_internet_speed);

        context = this;
        initialize();


    }

    private void initialize() {
        speedView = (SpeedView) findViewById(R.id.speedView);
        btn_speed = findViewById(R.id.btn_speedtest);
//        llprogress=(LinearLayout) findViewById(R.id.ll_progress);
//        llprogress.setVisibility(View.INVISIBLE);

        progressBar=(ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        btn_speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                checkSpeedTest();
            }
        });


    }

    public void checkSpeedTest() {
//        progressBar.setVisibility(View.INVISIBLE);
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").build();

            startTime = System.currentTimeMillis();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        Log.d(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    InputStream input = response.body().byteStream();
                    try {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        while (input.read(buffer) != -1) {
                            bos.write(buffer);
                        }
                        byte[] docBuffer = bos.toByteArray();
                        fileSize = bos.size();
                    } finally {
                        input.close();
                    }
                    endTime = System.currentTimeMillis();
                    // calculate how long it took by subtracting endtime from starttime

                    double timeTakenMills = Math.floor(endTime - startTime);  // time taken in milliseconds
                    double timeTakenSecs = timeTakenMills / 1000;  // divide by 1000 to get time in seconds
                    final int kilobytePerSec = (int) Math.round(1024 / timeTakenSecs);
                    if (kilobytePerSec <= POOR_BANDWIDTH) {
                        // slow connection
                    }
                    // get the download speed by dividing the file size by time taken to download
                    speed = fileSize / timeTakenMills;
                    Log.d(TAG, "Time taken in secs: " + timeTakenSecs);
                    Log.d(TAG, "kilobyte per sec: " + kilobytePerSec);
                    Log.d(TAG, "Download Speed: " + speed);
                    Log.d(TAG, "File size: " + fileSize);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (speed > 0.00) {
                                progressBar.setVisibility(View.INVISIBLE);
                                speedView.speedTo((float) speed);
                            }
                        }
                    });
                }

            });


        } else {
            Toast.makeText(context, "Network Not Available", Toast.LENGTH_SHORT).show();
        }

    }




}
