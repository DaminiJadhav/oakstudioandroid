package com.sdaemon.oakstudiotv.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BroadcastReceiverClass extends BroadcastReceiver {
    ProgressBar progressBar;
    TextView textView;
    Integer value;
    Context context;

    public BroadcastReceiverClass(Context context,ProgressBar progressBar,TextView textView){
        this.context=context;
        this.progressBar=progressBar;
        this.textView=textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        value=intent.getIntExtra("DOWNLOAD_COUNT",1);
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView.setText("100%");
        Log.i("Offline Progress bar",""+value);
//        context.unregisterReceiver(new BroadcastReceiverClass());
    }



}
