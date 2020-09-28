package com.sdaemon.oakstudiotv.activity;

import android.content.Context;
import android.os.Build;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.utils.AppSession;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT = "fragment";
    AppSession appSession;
    Context context;
    TextView tvpolicy;
    ImageView iv_back;
    LinearLayout llprogress;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        tvpolicy=findViewById(R.id.tv_policy);
        progressBar=(ProgressBar) findViewById(R.id.ll_progress);
        context=this;

        progressBar.setVisibility(View.VISIBLE);

        initialize();
        try {
//            progressBar.setVisibility(View.GONE);
        appSession=AppSession.getInstance(context);
       String privacypolicy=appSession.getAbout().get(0).getPrivacyPolicy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setVisibility(View.GONE);
            tvpolicy.setText(Html.fromHtml(privacypolicy, Html.FROM_HTML_MODE_LEGACY));

        }
        else
            progressBar.setVisibility(View.GONE);
            tvpolicy.setText(Html.fromHtml(privacypolicy));
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
//                Intent privacyIntent = new Intent(PrivacyPolicyActivity.this, HelpandInfoActivity.class);
//                startActivity(privacyIntent);
//                getSupportFragmentManager().beginTransaction()
//                        .add(android.R.id.content, new FragmentHelpandInfo()).commit();
                finish();
//                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        final FragmentHelpandInfo fragment = (FragmentHelpandInfo) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);

        super.onBackPressed();
//        fragment.onBackPressed();

    }
}
