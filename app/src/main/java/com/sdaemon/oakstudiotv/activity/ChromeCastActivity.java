package com.sdaemon.oakstudiotv.activity;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.cast.CastPlayer;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastState;
import com.google.android.gms.cast.framework.CastStateListener;
import com.google.android.gms.common.images.WebImage;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.utils.AppConstants;

import androidx.mediarouter.app.MediaRouteButton;

public class ChromeCastActivity extends AppCompatActivity implements AppConstants, View.OnClickListener {
    Context context;
    InputMethodManager mInputMethodManager;
    private ImageView iv_tabback;
    private String name = "";
    MediaRouteButton mMediaRouteButton;
    CastContext mCastContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chrome_cast);
        context = this;
        mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            {
                if (bundle.containsKey("name"))
                    name = bundle.getString("name");
                Log.i(String.valueOf(ChromeCastActivity.this), "========== name:" + name);

            }
        }
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCastContext = CastContext.getSharedInstance(context);
        mMediaRouteButton = (MediaRouteButton) findViewById(R.id.media_route_button);
        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), mMediaRouteButton);

        String videoUrl = "https://github.com/mediaelement/mediaelement-files/blob/master/big_buck_bunny.mp4?raw=true";
        MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
        movieMetadata.putString(MediaMetadata.KEY_TITLE, "Test Stream");
        movieMetadata.putString(MediaMetadata.KEY_ALBUM_ARTIST, "Test Artist");
        movieMetadata.addImage(new WebImage(Uri.parse("https://github.com/mkaflowski/HybridMediaPlayer/blob/master/images/cover.jpg?raw=true")));
        MediaInfo mediaInfo = new MediaInfo.Builder(videoUrl)
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setContentType(MimeTypes.VIDEO_UNKNOWN)
                .setMetadata(movieMetadata).build();

//array of media sources
        final MediaQueueItem[] mediaItems = {new MediaQueueItem.Builder(mediaInfo).build()};

        if (mCastContext.getCastState() != CastState.NO_DEVICES_AVAILABLE)
            mMediaRouteButton.setVisibility(View.VISIBLE);

        mCastContext.addCastStateListener(new CastStateListener() {
            @Override
            public void onCastStateChanged(int state) {
                if (state == CastState.NO_DEVICES_AVAILABLE)
                    mMediaRouteButton.setVisibility(View.GONE);
                else {
                    if (mMediaRouteButton.getVisibility() == View.GONE)
                        mMediaRouteButton.setVisibility(View.VISIBLE);
                }
            }
        });

        final CastPlayer castPlayer = new CastPlayer(mCastContext);
        castPlayer.loadItems(mediaItems, 0, 1, Player.REPEAT_MODE_OFF);
        castPlayer.setSessionAvailabilityListener(new CastPlayer.SessionAvailabilityListener() {
            @Override
            public void onCastSessionAvailable() {

                Log.i(String.valueOf(ChromeCastActivity.this), "========== onCastSessionAvailable:");
                //   castPlayer.loadItems(mediaItems, 0, time, Player.REPEAT_MODE_OFF);
                castPlayer.loadItems(mediaItems, 0, 5000, Player.REPEAT_MODE_OFF);
            }

            @Override
            public void onCastSessionUnavailable() {
                Log.i(String.valueOf(ChromeCastActivity.this), "========== onCastSessionUnavailable:");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(),
                menu,
                R.id.media_route_menu_item);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        menu.findItem(R.id.action_done).setVisible(false);
        menu.findItem(R.id.action_person).setVisible(false);
        menu.findItem(R.id.action_tuneUp).setVisible(false);
        menu.findItem(R.id.media_route_menu_item).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.media_route_menu_item) {
            Toast.makeText(context, "Cast", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.btn_apply_code:
//                mInputMethodManager.hideSoftInputFromWindow(btnApplyCode.getWindowToken(), 0);
//
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 1) {
//            String message=data.getStringExtra("MESSAGE");
//            Log.i(String.valueOf(context), "=========message: " + message);
//            Log.i(String.valueOf(context), "=========test: " + test);
//            finish();
        }
    }
}
