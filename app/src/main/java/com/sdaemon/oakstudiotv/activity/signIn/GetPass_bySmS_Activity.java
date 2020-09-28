package com.sdaemon.oakstudiotv.activity.signIn;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.Country_codeList_Activity;

public class GetPass_bySmS_Activity extends AppCompatActivity implements View.OnClickListener {
private Button btn_continue;
private TextView selectCountry;
    private ImageView iv_tabback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pass_by_sm_s_);

        initialize();
    }

    private void initialize() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_logo_with_back);

        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);

        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_continue=(Button)findViewById(R.id.btn_continue);
        selectCountry=(TextView)findViewById(R.id.selectCountry);

        btn_continue.setOnClickListener(this);
        selectCountry.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_continue:
                Intent intent=new Intent(GetPass_bySmS_Activity.this,SignIn_VerificationActivity.class);
                startActivity(intent);
             //   finish();
                break;

            case R.id.selectCountry:
                Intent intent1=new Intent(GetPass_bySmS_Activity.this,Country_codeList_Activity.class);
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
