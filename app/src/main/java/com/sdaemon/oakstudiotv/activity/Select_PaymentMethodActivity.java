package com.sdaemon.oakstudiotv.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.fragment.FragmentPayment_details;

public class Select_PaymentMethodActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_cCard,ll_bankacc,ll_payPAl;
    private CheckBox cb_cCard,cb_bankAcc,ch_paypal;
    public static String payMethod="";
    private ImageView iv_tabback;
    Boolean cb_card=false,cb_bankac=false,cb_paypal=false;
    FragmentPayment_details fragmentPayment_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__payment_method);

        initialize();

    }

    private void initialize() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_logo_with_back);
        iv_tabback=(ImageView) findViewById(R.id.iv_tabback);



        ll_cCard=(LinearLayout)findViewById(R.id.ll_cCard);
        ll_bankacc=(LinearLayout)findViewById(R.id.ll_bankacc);
        ll_payPAl=(LinearLayout)findViewById(R.id.ll_payPAl);

        cb_cCard=(CheckBox) findViewById(R.id.cb_cCard);
        cb_bankAcc=(CheckBox)findViewById(R.id.cb_bankAcc);
        ch_paypal=(CheckBox)findViewById(R.id.ch_paypal);

        ll_cCard.setOnClickListener(this);
        ll_bankacc.setOnClickListener(this);
        ll_payPAl.setOnClickListener(this);


        Log.i("FragmentPayment",""+FragmentPayment_details.strEditText);
        cb_cCard.setVisibility(View.VISIBLE);

      fragmentPayment_details=new FragmentPayment_details();

      if (fragmentPayment_details.strEditText!=null) {
          cb_cCard.setVisibility(View.INVISIBLE);

          if (fragmentPayment_details.strEditText.equals(getResources().getString(R.string.credit_card))) {
              cb_cCard.setChecked(true);
              cb_cCard.setVisibility(View.VISIBLE);

          } else if (FragmentPayment_details.strEditText.equals(getResources().getString(R.string.bank_account))) {
              cb_bankAcc.setChecked(true);
              cb_bankAcc.setVisibility(View.VISIBLE);

          } else {
              ch_paypal.setVisibility(View.VISIBLE);
              ch_paypal.setChecked(true);
          }
      }
//        if (cb_card==true){
//            cb_cCard.setChecked(true);
//            cb_bankAcc.setChecked(false);
//            ch_paypal.setChecked(false);
//        }
//        if (cb_bankac==true){
//            cb_cCard.setChecked(false);
//            cb_bankAcc.setChecked(true);
//            ch_paypal.setChecked(false);
//        }
//        if (cb_paypal==true){
//            cb_cCard.setChecked(false);
//            cb_bankAcc.setChecked(false);
//            ch_paypal.setChecked(true);
//        }




        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        menu.findItem(R.id.action_person).setVisible(false);
        menu.findItem(R.id.action_tuneUp).setVisible(false);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
            invalidateOptionsMenu();
            menu.findItem(R.id.action_done).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            return true;
        }
        if (id==android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_cCard:
                cb_card=true;

                cb_cCard.setChecked(true);
                cb_bankAcc.setChecked(false);
                ch_paypal.setChecked(false);


                cb_cCard.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                intent.putExtra("editTextValue", getResources().getString(R.string.credit_card));
                setResult(RESULT_OK, intent);
                finish();

                break;
            case R.id.ll_bankacc:
                cb_bankac=true;

                cb_cCard.setChecked(false);
                cb_bankAcc.setChecked(true);
                ch_paypal.setChecked(false);

                cb_bankAcc.setVisibility(View.VISIBLE);
                cb_cCard.setVisibility(View.GONE);
                ch_paypal.setVisibility(View.GONE);

                Intent intent1 = new Intent();
                intent1.putExtra("editTextValue", getResources().getString(R.string.bank_account));
                setResult(RESULT_OK, intent1);
                finish();
                break;
            case R.id.ll_payPAl:
                cb_paypal=true;

                cb_cCard.setChecked(false);
                cb_bankAcc.setChecked(false);
                ch_paypal.setChecked(true);

                cb_cCard.setVisibility(View.GONE);
                cb_bankAcc.setVisibility(View.GONE);
                ch_paypal.setVisibility(View.VISIBLE);

                Intent intent2 = new Intent();
                intent2.putExtra("editTextValue", getResources().getString(R.string.paypal));
                setResult(RESULT_OK, intent2);
                finish();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
