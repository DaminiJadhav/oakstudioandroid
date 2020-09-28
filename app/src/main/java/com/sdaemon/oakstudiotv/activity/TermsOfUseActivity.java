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

public class TermsOfUseActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT = "fragment";
    TextView tvterms;
    AppSession appSession;
    Context context;
    ImageView iv_back;
    LinearLayout llprogres;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);
        tvterms=findViewById(R.id.tv_termsofuse);
        progressBar=(ProgressBar) findViewById(R.id.ll_progress);

        context = this;
        initialize();

        progressBar.setVisibility(View.VISIBLE);
        try {
            appSession = AppSession.getInstance(context);
//            progressBar.setVisibility(View.GONE);

            String content = appSession.getAbout().get(0).getTermsOfUse();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progressBar.setVisibility(View.GONE);
                tvterms.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));

            } else
                progressBar.setVisibility(View.GONE);
                tvterms.setText(Html.fromHtml(content));
        }catch (Exception e){
        }

    }

    private void initialize(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_back=(ImageView) findViewById(R.id.iv_tabback);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent helpIntent = new Intent(TermsOfUseActivity.this, HelpandInfoActivity.class);
//                startActivity(helpIntent);
//                getSupportFragmentManager().beginTransaction()
//                        .replace(android.R.id.content, new FragmentHelpandInfo()).commit();
//                getSupportFragmentManager().popBackStackImmediate();
//                fragmentTransaction.addToBackStack("your_tag");
                finish();
//                onBackPressed();
            }
        });
    }
//
    @Override
    public void onBackPressed() {

//        final FragmentHelpandInfo fragment = (FragmentHelpandInfo) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);

        super.onBackPressed();
//        fragment.onBackPressed();
    }
}
