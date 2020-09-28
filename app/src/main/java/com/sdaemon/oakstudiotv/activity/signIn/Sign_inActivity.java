package com.sdaemon.oakstudiotv.activity.signIn;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sdaemon.oakstudiotv.R;

public class Sign_inActivity extends AppCompatActivity implements View.OnClickListener {
private Button btn_sign_withEmail,btn_sign_withPhoneNo;
    private ImageView iv_tabback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initialize();

    }

    private void initialize() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        //getSupportActionBar().setTitle("My Profile");

        btn_sign_withEmail=(Button)findViewById(R.id.btn_sign_withEmail);
        btn_sign_withPhoneNo=(Button)findViewById(R.id.btn_sign_withPhoneNo);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);

        btn_sign_withEmail.setOnClickListener(this);
        btn_sign_withPhoneNo.setOnClickListener(this);
        iv_tabback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_sign_withEmail:
                Intent intent=new Intent(Sign_inActivity.this,Signin_withEmail_Activity.class);
                startActivity(intent);
                //finish();
                break;

            case R.id.btn_sign_withPhoneNo:
                Intent intent1=new Intent(Sign_inActivity.this,Sign_in_smsVerificationActivity.class);//
                startActivity(intent1);
               // finish();
                break;

            case R.id.iv_tabback:
                finish();
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
