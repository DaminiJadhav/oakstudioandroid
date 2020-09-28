package com.sdaemon.oakstudiotv.activity.signIn;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.StartInActivity;

public class SignIn_VerificationActivity extends AppCompatActivity {
    private EditText edittxtone,edittxttwo,edittxtthree, editTextfour;
    String edtxtone,edtxttwo,edtxtthree,edtxtfour;
    private ImageView iv_tabback;
    TextView incorrect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in__verification);

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

        edittxtone=(EditText)findViewById(R.id.editTextone);
        edittxttwo=(EditText)findViewById(R.id.editTexttwo);
        edittxtthree=(EditText)findViewById(R.id.editTextthree);
        editTextfour=(EditText)findViewById(R.id.editTextfour);
        incorrect=(TextView) findViewById(R.id.tv_incorrect);


        editTextfour.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    // submit.performClick();
//                    editTextfour.setError("");

                    edtxtone=edittxtone.getText().toString().trim();
                    edtxttwo=edittxttwo.getText().toString().trim();
                    edtxtthree=edittxtthree.getText().toString().trim();
                    edtxtfour=editTextfour.getText().toString().trim();

                    if (!TextUtils.isEmpty(edtxtone) && !TextUtils.isEmpty(edtxttwo) && !TextUtils.isEmpty(edtxtthree) && !TextUtils.isEmpty(edtxtfour)){
                        incorrect.setVisibility(View.INVISIBLE);
                        Intent intent=new Intent(SignIn_VerificationActivity.this, StartInActivity.class);
                        startActivity(intent);
                    }

                    //finish();
                }
                return false;
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
