package com.sdaemon.oakstudiotv.activity;

import android.content.Context;
import android.os.Build;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.utils.AppSession;

public class CookiePolicyActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT = "fragment";
    TextView tvcookie;
    AppSession appSession;
    Context context;
    ImageView iv_back;
//    LinearLayout llprogres;
    ProgressBar llprogres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie_policy);
        initialize();

        tvcookie = (TextView) findViewById(R.id.tv_cookiepolicy);
//        llprogres=(LinearLayout) findViewById(R.id.ll_progress);
        llprogres=(ProgressBar) findViewById(R.id.ll_progress);

        context = this;
        llprogres.setVisibility(View.VISIBLE);

        try {
            appSession = AppSession.getInstance(context);
            String content = appSession.getAbout().get(0).getCookiePolicy();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                llprogres.setVisibility(View.GONE);
                tvcookie.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));

            } else
                llprogres.setVisibility(View.GONE);
                tvcookie.setText(Html.fromHtml(content));
        }catch (Exception e){

        }
    }
    private void initialize(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_back=(ImageView) findViewById(R.id.iv_tabback);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent cookieIntent = new Intent(CookiePolicyActivity.this, HelpandInfoActivity.class);
//                startActivity(cookieIntent);
//                llprogres.setVisibility(View.GONE);
//                getSupportFragmentManager().beginTransaction()
//                        .add(android.R.id.content, new FragmentHelpandInfo()).commit();
//                getSupportFragmentManager().popBackStackImmediate();

                   finish();
//                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
////            this.finish();
//        } else {
//            getSupportFragmentManager().popBackStack();
//        }

//        final FragmentHelpandInfo fragment = (FragmentHelpandInfo) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        super.onBackPressed();
//        fragment.onBackPressed();
    }

}
