package com.sdaemon.oakstudiotv.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;

public class SelectYour_PlanActivity extends AppCompatActivity {
ImageView ivcurrentplan;
TextView tvcurrentplan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_your__plan);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);
        ivcurrentplan=(ImageView) findViewById(R.id.iv_currentplan);
        tvcurrentplan=(TextView) findViewById(R.id.tv_currentplan);

//        ivcurrentplan.setImageDrawable(getResources().getDrawable(R.drawable.failed));
//        tvcurrentplan.setText("Currently No Plan Available");


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
