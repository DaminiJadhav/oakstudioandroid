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

public class SignUPActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_register_withEmail,btn_signUpwithPhone;
    private ImageView iv_tabback;
    private TextView tv_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);

        btn_register_withEmail = (Button) findViewById(R.id.btn_register_withEmail);
        btn_signUpwithPhone = (Button) findViewById(R.id.btn_signUpwithPhone);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);

        tv_number = (TextView) findViewById(R.id.tv_number);
        String text = "<font color=#9AEB3D>1</font> <font color=#505053>/3</font>";
        tv_number.setText(Html.fromHtml(text));


        iv_tabback.setVisibility(View.VISIBLE);

        btn_register_withEmail.setOnClickListener(this);
        btn_signUpwithPhone.setOnClickListener(this);
        iv_tabback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register_withEmail:
                Intent intent = new Intent(SignUPActivity.this, Register_withEmail_Activity.class);
                startActivity(intent);
               // finish();
                break;

            case R.id.btn_signUpwithPhone:
                Intent in=new Intent(SignUPActivity.this,SignUpSmS_verification_Activity.class);//
                startActivity(in);
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