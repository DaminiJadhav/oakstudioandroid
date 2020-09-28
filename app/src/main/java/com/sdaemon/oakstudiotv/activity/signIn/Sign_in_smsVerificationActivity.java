package com.sdaemon.oakstudiotv.activity.signIn;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.Country_codeList_Activity;

public class Sign_in_smsVerificationActivity extends AppCompatActivity implements View.OnClickListener {
private Button btn_Continue;
    private TextView tv_countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sms_verification);

        initialize();
    }

    private void initialize() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);

        btn_Continue=(Button)findViewById(R.id.btn_Continue);
        tv_countryList=(TextView) findViewById(R.id.tv_countryList);

        btn_Continue.setOnClickListener(this);
        tv_countryList.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_Continue:
                Intent intent=new Intent(Sign_in_smsVerificationActivity.this,SignIn_VerificationActivity.class);
                startActivity(intent);
               // finish();
                break;

            case R.id.tv_countryList:
                Intent intent1=new Intent(Sign_in_smsVerificationActivity.this,Country_codeList_Activity.class);
                startActivity(intent1);
                // finish();
                break;
        }

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
