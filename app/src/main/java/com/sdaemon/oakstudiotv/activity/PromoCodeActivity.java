package com.sdaemon.oakstudiotv.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.brainTreePayment.BrainTreeActivity;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.PlanFinalDTO;
import com.sdaemon.oakstudiotv.dto.PromoCodeDTO;
import com.sdaemon.oakstudiotv.utils.AppConstants;
import com.sdaemon.oakstudiotv.utils.AppSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoCodeActivity extends AppCompatActivity implements AppConstants, View.OnClickListener {
    Context context;
    InputMethodManager mInputMethodManager;
    private ImageView iv_tabback;
    private LinearLayout llDiscountAmount, llPromoCode;
    private EditText etPromoCode;
    private TextView tvPlanAmount, tvPlanName, tvDiscountAmount, tvPayableAmount, tvChangeCode, tvShowCode;
    private Button btnApplyCode, btnProceedToPay;
    private String name = "", coupanCode = "", uniqueId = "", planName = "";
    private int corePlanId = 0, periodId = 0;
    private double planCost = 0, resultAmount = 0, toatalPayableAmount = 0;
    AppSession appSession;
    int uniqueid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_code);
        context = this;

        try {
            appSession=AppSession.getInstance(context);
            if (appSession!=null){
                uniqueId=appSession.getUserDTO().getResult().getUniqueIdentifire();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            {
                if (bundle.containsKey("name"))
                    name = bundle.getString("name");
                Log.i(String.valueOf(PromoCodeActivity.this), "========== name:" + name);

                if (bundle.containsKey(PN_COREPLAN_ID))
                    corePlanId = bundle.getInt(PN_COREPLAN_ID);

                if (bundle.containsKey(PN_PERIOD_ID))
                    periodId = bundle.getInt(PN_PERIOD_ID);

                if (bundle.containsKey(PN_PLAN_COST))
                    planCost = bundle.getDouble(PN_PLAN_COST);
                toatalPayableAmount = planCost;

                if (bundle.containsKey(PN_PLAN_NAME))
                    planName = bundle.getString(PN_PLAN_NAME);


            }
        }
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvShowCode = (TextView) findViewById(R.id.tv_show_code);
        llPromoCode = (LinearLayout) findViewById(R.id.ll_promo_code);
        tvChangeCode = (TextView) findViewById(R.id.tv_change_code);
        tvChangeCode.setOnClickListener(this);
        tvChangeCode.setVisibility(View.GONE);

        llDiscountAmount = (LinearLayout) findViewById(R.id.ll_discount_amount);
        llDiscountAmount.setVisibility(View.GONE);
        tvDiscountAmount = (TextView) findViewById(R.id.tv_discount_amount);

        tvPayableAmount = (TextView) findViewById(R.id.tv_payable_amount);
        tvPayableAmount.setText("$" + planCost);


        tvPlanAmount = (TextView) findViewById(R.id.tv_plan_amount);

        tvPlanAmount.setText("$" + toatalPayableAmount);
        tvPlanName = (TextView) findViewById(R.id.tv_plan_name);
        tvPlanName.setText(planName);


        etPromoCode = (EditText) findViewById(R.id.et_promo_code);

        btnApplyCode = (Button) findViewById(R.id.btn_apply_code);
        btnApplyCode.setOnClickListener(this);

        btnProceedToPay = (Button) findViewById(R.id.btn_proceed_to_pay);
        btnProceedToPay.setOnClickListener(this);


       getData();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        menu.findItem(R.id.action_done).setVisible(false);
        menu.findItem(R.id.action_person).setVisible(false);
        menu.findItem(R.id.action_tuneUp).setVisible(false);
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
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_apply_code:
                mInputMethodManager.hideSoftInputFromWindow(btnApplyCode.getWindowToken(), 0);
                if (etPromoCode.getText().toString().trim() != null && !etPromoCode.getText().toString().trim().equalsIgnoreCase("")) {
                    coupanCode = etPromoCode.getText().toString().trim();
                    uniqueId = "6c675525-7728-44d2-94ae-d8b85581a944";
//paid();
                   getPromoCode(uniqueId, coupanCode, "" + corePlanId, "" + periodId);
                } else {
                    Toast.makeText(context, "Please Enter Code", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.tv_change_code:
                mInputMethodManager.hideSoftInputFromWindow(tvChangeCode.getWindowToken(), 0);
                llPromoCode.setVisibility(View.VISIBLE);
                llDiscountAmount.setVisibility(View.GONE);
                // llPayableAmount.setVisibility(View.GONE);
                tvChangeCode.setVisibility(View.GONE);
                etPromoCode.setText("");
                toatalPayableAmount = planCost;
                tvPayableAmount.setText("$" + toatalPayableAmount);
                break;


            case R.id.btn_proceed_to_pay:

                Log.i(String.valueOf(context), "========= FINAL: " + toatalPayableAmount);
                Toast.makeText(context, "Final: "+ toatalPayableAmount, Toast.LENGTH_LONG).show();

                mInputMethodManager.hideSoftInputFromWindow(btnProceedToPay.getWindowToken(), 0);
                Intent intent = new Intent(context, BrainTreeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("Value",toatalPayableAmount);
                bundle.putString("PlanName",planName);
                Log.i("PlanName :", " " + planName);

                intent.putExtras(bundle);
                startActivity(intent);
               // startActivityForResult(intent, 1);
              //  ((Activity) context).overridePendingTransition(0, 0);



                break;

        }
    }

    public void paid()
    {
        String abc="hello";
        if(etPromoCode.getText().toString().equals(abc))
        {

            showDialoge(context, "", "Code Accepted");
            toatalPayableAmount = planCost - 5;
            Log.e("listPlanID", "===" +toatalPayableAmount);
            tvPayableAmount.setText("$" + toatalPayableAmount);
        }

        else
        {
            showDialoge(context, "", "Invalid Code");
        }




    }





    private void getPromoCode(String uniqueId, String coupanCode, String corePlanId, String periodId) {

        //  progressBar.show();
        //    String android_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

        Call<PromoCodeDTO> call = RetroClient.sdaemonApi().getCoupanCode(uniqueId, coupanCode, corePlanId, periodId);
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<PromoCodeDTO>() {
            @Override
            public void onResponse(Call<PromoCodeDTO> call, Response<PromoCodeDTO> response) {
                Log.i(String.valueOf(context), "=========RESPONSE: " + response.body());
                setData(response.body());


            }

            @Override
            public void onFailure(Call<PromoCodeDTO> call, Throwable t) {

                showDialoge(context, "", "" + t.getMessage());
            }
        });
    }


    public void setData(PromoCodeDTO dto) {
        if (dto != null) {
            if (dto.getStatus() == 1) {
                double discountAmount = 0, discountRate = 0;
                if (dto.getCoupanDetailList() != null && dto.getCoupanDetailList().size() > 0) {
                    for (int i = 0; i < dto.getCoupanDetailList().size(); i++) {
                        if (dto.getCoupanDetailList().get(i).getDiscountType().equalsIgnoreCase("fix")) {
                            discountAmount = dto.getCoupanDetailList().get(i).getDiscountAmount();
                            // planCost = planCost - discountAmount;
                            resultAmount = planCost - discountAmount;
                            toatalPayableAmount = planCost - discountAmount;
                        } else if (dto.getCoupanDetailList().get(i).getDiscountType().equalsIgnoreCase("Percentage(%)")) {
                            discountRate = dto.getCoupanDetailList().get(i).getDiscountRate();
                            discountAmount = dto.getCoupanDetailList().get(i).getDiscountAmount();
                            //  resultAmount = planCost - (discountRate / 100) * planCost;
                            resultAmount = (discountRate * planCost) / 100;
                            Log.i(String.valueOf(context), "====== resultAmount 11 :  " + resultAmount);
                            if (resultAmount > discountAmount) {
                                resultAmount = discountAmount;
                            }
                            toatalPayableAmount = planCost - resultAmount;
                        }
                    }
                    llDiscountAmount.setVisibility(View.VISIBLE);
                    tvDiscountAmount.setText("$" + resultAmount);
                    tvShowCode.setText(etPromoCode.getText().toString().trim());
                    Log.i(String.valueOf(context), "====== resultAmount :  " + resultAmount);

                    tvPayableAmount.setText("$" + toatalPayableAmount);
                    Log.i(String.valueOf(context), "====== toatalPayableAmount :  " + toatalPayableAmount);


                    llPromoCode.setVisibility(View.GONE);
                    tvChangeCode.setVisibility(View.VISIBLE);
                    showDialoge(context, "", dto.getMessage());


                }

            } else if (dto.getStatus() == 0) {
                showDialoge(context, "", dto.getMessage());

            }

        }
    }


    private void getData() {
        //  progressBar.show();
        //    String android_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

        Call<PlanFinalDTO> call = RetroClient.sdaemonApi().getPlanFinalDesc();
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<PlanFinalDTO>() {
            @Override
            public void onResponse(Call<PlanFinalDTO> call, Response<PlanFinalDTO> response) {

                Log.i(String.valueOf(context), "=========RESPONSE: " + response.body());
                //      setData(response.body());


            }

            @Override
            public void onFailure(Call<PlanFinalDTO> call, Throwable t) {

                showDialoge(context, "", "" + t.getMessage());
            }
        });
    }

    public void showDialoge(Context context, String title, String msg) {
        try {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context, R.style.dialoge);
            builder.setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })

                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 1) {
//            String message=data.getStringExtra("MESSAGE");
//            Log.i(String.valueOf(context), "=========message: " + message);
//            Log.i(String.valueOf(context), "=========test: " + test);
//            finish();
        }
    }
}
