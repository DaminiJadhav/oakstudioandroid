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

public class Register_withEmail_Activity extends AppCompatActivity implements View.OnClickListener {
private Button btn_createAcc;
    private ImageView iv_tabback;
    private TextView tv_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_with_email);

        initialize();
    }

    private void initialize() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_logo_with_back);

        btn_createAcc=(Button)findViewById(R.id.btn_createAcc);
        iv_tabback=(ImageView) findViewById(R.id.iv_tabback);

        tv_number = (TextView) findViewById(R.id.tv_number);
        String text = "<font color=#9AEB3D>1</font> <font color=#505053>/3</font>";
        tv_number.setText(Html.fromHtml(text));

        btn_createAcc.setOnClickListener(this);
        iv_tabback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_createAcc:
                Intent intent=new Intent(Register_withEmail_Activity.this,SignUpSmS_verification_Activity.class);//
                startActivity(intent);
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
