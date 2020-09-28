package com.sdaemon.oakstudiotv.activity.signIn;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;

public class GetPass_byEmail_Activity extends AppCompatActivity {
    private ImageView iv_tabback;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pass_by_email_);

        initialize();
    }

    private void initialize() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_logo_with_back);

        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        tv = (TextView) findViewById(R.id.tv);

        String text = "<font color=#FFFFFF>and click</font> <font color=#9AEB3D>CHANGE</font> <font color=#FFFFFF>to continue</font>";
        tv.setText(Html.fromHtml(text));
        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
