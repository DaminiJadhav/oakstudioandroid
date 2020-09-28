package com.sdaemon.oakstudiotv.activity.signIn;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.StartInActivity;
import com.sdaemon.oakstudiotv.activity.signUp.SignUPActivity;

public class Signin_withEmail_Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_forgotPass;
    private Dialog dialog;
    private Context context;
    private Button btn_sign,btn_createNew_Acc;
    private TextInputEditText et_email, et_pass;
    private ProgressDialog progressDialog;
    private ImageView iv_tabback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_with_email_);

        initialize();
    }

    private void initialize() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_logo_with_back);
        //getSupportActionBar().setTitle("My Profile");

        context = Signin_withEmail_Activity.this;
        tv_forgotPass = (TextView) findViewById(R.id.tv_forgotPass);
        btn_sign = (Button) findViewById(R.id.btn_sign);
        btn_createNew_Acc = (Button) findViewById(R.id.btn_createNew_Acc);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);

        tv_forgotPass.setOnClickListener(this);
        btn_sign.setOnClickListener(this);
        btn_createNew_Acc.setOnClickListener(this);
        iv_tabback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forgotPass:
                dialogForgotPassword();
                break;

            case R.id.btn_sign:
                Intent intent=new Intent(context,StartInActivity.class);
                startActivity(intent);
                //finish();
                break;

            case R.id.btn_createNew_Acc:
                /*Intent intent1=new Intent(context,Register_withEmail_Activity.class);
                startActivity(intent1);*/
                Intent intent1=new Intent(context,SignUPActivity.class);
                startActivity(intent1);
               // finish();
                break;

            case R.id.iv_tabback:
                finish();
                break;
        }
    }

    private void dialogForgotPassword() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.forgot_pass_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        lp.y = 200; // top margin
        dialog.getWindow().setAttributes(lp);

        TextView tv_email = (TextView) dialog.findViewById(R.id.tv_email);
        TextView tv_phnNumber = (TextView) dialog.findViewById(R.id.tv_phnNumber);

        tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,GetPass_byEmail_Activity.class);
                startActivity(intent);
                //finish();

            }
        });

        tv_phnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,GetPass_bySmS_Activity.class);
                startActivity(intent);
                //finish();
            }
        });
        dialog.show();

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
