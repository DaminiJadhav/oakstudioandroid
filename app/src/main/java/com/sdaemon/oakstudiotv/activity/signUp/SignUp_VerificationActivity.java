package com.sdaemon.oakstudiotv.activity.signUp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;

public class SignUp_VerificationActivity extends AppCompatActivity {
private EditText editTextfour;
    private ImageView iv_tabback;
    private TextView tv_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__verification);

        initialize();
    }

    private void initialize() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_logo_with_back);
        editTextfour=(EditText)findViewById(R.id.editTextfour);
        iv_tabback=(ImageView) findViewById(R.id.iv_tabback);

        tv_number = (TextView) findViewById(R.id.tv_number);
        String text = "<font color=#9AEB3D>1</font> <font color=#505053>/3</font>";
        tv_number.setText(Html.fromHtml(text));

        editTextfour.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                   // submit.performClick();
                    Intent intent=new Intent(SignUp_VerificationActivity.this, SignUp_Plan_Activity.class);
                    startActivity(intent);
                    //finish();
                }
                return false;
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
