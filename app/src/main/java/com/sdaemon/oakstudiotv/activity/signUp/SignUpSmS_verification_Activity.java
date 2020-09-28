package com.sdaemon.oakstudiotv.activity.signUp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.Country_codeList_Activity;

public class SignUpSmS_verification_Activity extends AppCompatActivity {
    private Button btn_Continue;
    private TextView tv_selectCountry;
    private ImageView iv_tabback;
    private TextView tv_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_sm_s_verification_);//
        initialize();
    }

    private void initialize() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_logo_with_back);

        btn_Continue = (Button) findViewById(R.id.btn_Continue);
        tv_selectCountry = (TextView) findViewById(R.id.tv_selectCountry);
        iv_tabback=(ImageView) findViewById(R.id.iv_tabback);

        tv_number = (TextView) findViewById(R.id.tv_number);
        String text = "<font color=#9AEB3D>1</font> <font color=#505053>/3</font>";
        tv_number.setText(Html.fromHtml(text));



        btn_Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpSmS_verification_Activity.this, SignUp_VerificationActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        tv_selectCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpSmS_verification_Activity.this, Country_codeList_Activity.class);
                startActivity(intent);
               // finish();
            }
        });

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
