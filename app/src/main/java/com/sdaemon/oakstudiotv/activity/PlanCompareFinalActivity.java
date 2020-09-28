package com.sdaemon.oakstudiotv.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.CorePlanAdapter;
import com.sdaemon.oakstudiotv.adapter.CostPriceAdapter;
import com.sdaemon.oakstudiotv.adapter.PeriodAdapter;
import com.sdaemon.oakstudiotv.adapter.PlanInfoAdapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.PlanFinalDTO;
import com.sdaemon.oakstudiotv.dto.TransactionDTO;
import com.sdaemon.oakstudiotv.utils.AppConstants;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlanCompareFinalActivity extends AppCompatActivity implements AppConstants, View.OnClickListener {
    Context context;
    private Button btnProceedToPay;
    private ImageView iv_tabback;
    TextView tvDuration;
    ArrayList<PlanFinalDTO.Details> descList;
    ArrayList<PlanFinalDTO.Period> periodList;
    ArrayList<PlanFinalDTO.Cost> costList;
    ArrayList<PlanFinalDTO.Cost> costListApi;
    ArrayList<PlanFinalDTO.CorePlan> corePlanList;
    LinearLayoutManager mLinearLayoutManager;
    LinearLayoutManager mLinearLayoutManagerPeriod;
    LinearLayoutManager mLinearLayoutManagerCorePlan;
    LinearLayoutManager mLinearLayoutManagerCost;
    PlanInfoAdapter planInfoAdapter;
    PeriodAdapter periodAdapter;
    CorePlanAdapter corePlanAdapter;
    CostPriceAdapter costPriceAdapter;
    RecyclerView rvList, rvListPeriod, rvListCorePlan, rvListCost;
    String test = "", corePlanNameApi = "";
    private Retrofit retrofit;
    int periodIdPeriod = 0;
    int corePlanIdApi = 0;
    int periodIdApi = 0;
    double planCost = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_compare_final_new);
        context = this;
        descList = new ArrayList<>();
        periodList = new ArrayList<>();
        corePlanList = new ArrayList<>();
        costList = new ArrayList<>();
        costListApi = new ArrayList<>();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnProceedToPay = (Button) findViewById(R.id.btn_proceed_to_pay);
        btnProceedToPay.setOnClickListener(this);


        tvDuration = (TextView) findViewById(R.id.tv_duration);


        rvListPeriod = (RecyclerView) findViewById(R.id.rv_list_period);
        mLinearLayoutManagerPeriod = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvListPeriod.setLayoutManager(mLinearLayoutManagerPeriod);


        rvListCorePlan = (RecyclerView) findViewById(R.id.rv_list_core_plan);
        mLinearLayoutManagerCorePlan = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvListCorePlan.setLayoutManager(mLinearLayoutManagerCorePlan);


        rvListCost = (RecyclerView) findViewById(R.id.rv_list_cost);
        mLinearLayoutManagerCost = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvListCost.setLayoutManager(mLinearLayoutManagerCost);


        rvList = (RecyclerView) findViewById(R.id.rv_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(mLinearLayoutManager);

        getData();


    }

    final OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if (descList != null && descList.size() > position) {
                if (type.equalsIgnoreCase("play")) {

                } else {


                }
            }

        }
    };

    final OnItemClickListner.OnClickCallback onClickCallbackPeriod = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if (periodList != null && periodList.size() > position) {
                if (type.equalsIgnoreCase("play")) {
                    periodAdapter.setCheckedValue(position);
                    periodAdapter.notifyDataSetChanged();
                    periodIdApi = periodList.get(position).getPeriodId();
                    periodIdPeriod = periodList.get(position).getPeriodId();
                    ArrayList<PlanFinalDTO.Cost> costListTemp = new ArrayList<>();
                    PlanFinalDTO.Cost dto = null;
                    Log.i(String.valueOf(context), "=========periodIdPeriod: " + periodIdPeriod);
                    for (int j = 0; j < costList.size(); j++) {
                        if ((periodIdPeriod == costList.get(j).getPeriodId())) {
                            Log.i(String.valueOf(context), "=========Name: " + costList.get(j).getAmount());
                            dto = new PlanFinalDTO.Cost();
                            dto.setAmount(costList.get(j).getAmount());
                            costListTemp.add(dto);
                            costListApi.clear();
                            costListApi.addAll(costListTemp);

                        }
                    }
                    costPriceAdapter = new CostPriceAdapter(context, costListTemp, onClickCallbackCorePlan);
                    rvListCost.setAdapter(costPriceAdapter);

                    ArrayList<PlanFinalDTO.Details> detailListTemp = new ArrayList<>();
                    detailListTemp.clear();
                    PlanFinalDTO.Details detailDTO = null;

                    for (int i = 0; i < corePlanList.size(); i++) {
                        for (int j = i; j < descList.size(); j++) {
                            if ((corePlanList.get(i).getCorePlanId() == descList.get(j).getCorePlanId())) {
                                Log.i(String.valueOf(context), "=========corePlanIdCore : " + corePlanList.get(i).getCorePlanId());
                                detailDTO = new PlanFinalDTO.Details();
                                detailDTO = descList.get(j);
                                detailListTemp.add(detailDTO);

                            }
                        }
                    }

//                    Log.i(String.valueOf(context), "=========detailListTemp Size: " + detailListTemp.size());
//
//                    for (int k = 0; k < detailListTemp.size(); k++) {
//                        Log.i(String.valueOf(context), "=========SubDescription : " + detailListTemp.get(k).getSubDescription());
//                        //  Log.i(String.valueOf(context), "=========AllowStatus : " + detailListTemp.get(k).getAllowStatus());
//
//                    }

                  /*  for (int k = 0; k < detailListTemp.size(); k = k+12) {
                        Log.i(String.valueOf(context), "=========SubDescription : " + detailListTemp.get(k).getSubDescription());
                        Log.i(String.valueOf(context), "=========AllowStatus : " + detailListTemp.get(k).getAllowStatus());


                    }

*/


//                  PlanFinalDTO.Status statusDTO = null;
//                    PlanFinalDTO.Details dtoDD = null;
//                    ArrayList<PlanFinalDTO.Details> tempList = new ArrayList<>();
//                    tempList.clear();
//
//                    for (int i = 0; i < 12; i++) {
//                        Log.i(String.valueOf(context), "=========SubDescription : " + detailListTemp.get(i).getSubDescription());
//
//                    }
//
//                    for (int i = 0; i < 48; i++) {
//                        for (int k = i; k < detailListTemp.size(); k = k + 12) {
//
//                            //   Log.i(String.valueOf(context), "=========SubDescription : " + detailListTemp.get(k).getSubDescription());
//                            Log.i(String.valueOf(context), "=========AllowStatus : " + detailListTemp.get(k).getAllowStatus());
//
//                            //  Log.i(String.valueOf(context), "=========III : " + i + " kkk : " + k + "AllowStatus : " + detailListTemp.get(k).getAllowStatus());
//
//                            dtoDD = new PlanFinalDTO.Details();
//                            dtoDD = detailListTemp.get(k);
//                            tempList.add(dtoDD);
//
//
//                     }
//
//                    }
//
//                  Log.i(String.valueOf(context), "=========tempList SIZE : " + tempList.size());



                    planInfoAdapter = new PlanInfoAdapter(context, detailListTemp, corePlanList, onClickCallback);
                    rvList.setAdapter(planInfoAdapter);



                /*    for (int k = 0; k < 12; k++) {
                        Log.i(String.valueOf(context), "=========SubDescription 11 : " + detailListTemp.get(k).getSubDescription());
                        Log.i(String.valueOf(context), "=========AllowStatus 11 : " + detailListTemp.get(k).getAllowStatus());


                    }


                    for (int k = 12; k < 24; k++) {
                        Log.i(String.valueOf(context), "=========SubDescription 22 : " + detailListTemp.get(k).getSubDescription());
                        Log.i(String.valueOf(context), "=========AllowStatus 22 : " + detailListTemp.get(k).getAllowStatus());


                    }
                    for (int k = 24; k < 36; k++) {
                        Log.i(String.valueOf(context), "=========SubDescription 33 : " + detailListTemp.get(k).getSubDescription());
                        Log.i(String.valueOf(context), "=========AllowStatus 33 : " + detailListTemp.get(k).getAllowStatus());


                    }
                    for (int k = 36; k < 48; k++) {
                        Log.i(String.valueOf(context), "=========SubDescription 44 : " + detailListTemp.get(k).getSubDescription());
                        Log.i(String.valueOf(context), "=========AllowStatus 44 : " + detailListTemp.get(k).getAllowStatus());


                    }
*/

                /*   ArrayList<PlanFinalDTO.Details> detailListTemp = new ArrayList<>();
                    detailListTemp.clear();
                    PlanFinalDTO.Details detailDTO = null;
                    int corePlanIdCore = 0;

                    for (int j = 0; j < corePlanList.size(); j++) {
                        // for (int j = 0; j < 1; j++) {
                      //  corePlanIdCore = corePlanList.get(j).getCorePlanId();
                        corePlanIdCore = corePlanList.get(0).getCorePlanId();

                    }

                    for (int i = 0; i < descList.size(); i++) {
                        if ((corePlanIdCore == descList.get(i).getCorePlanId())) {
                            detailDTO = new PlanFinalDTO.Details();
                            detailDTO = descList.get(i);
                            detailListTemp.add(detailDTO);

                        }
                      //  break;
                    }
                    Log.i(String.valueOf(context), "=========detailListTemp Size: " + detailListTemp.size());
//
                    for (int k = 0; k < detailListTemp.size(); k++) {
                        Log.i(String.valueOf(context), "=========SubDescription : " + detailListTemp.get(k).getSubDescription());
                        Log.i(String.valueOf(context), "=========AllowStatus : " + detailListTemp.get(k).getAllowStatus());


                    }

                    planInfoAdapter = new PlanInfoAdapter(context, detailListTemp, corePlanList, onClickCallback);
                    rvList.setAdapter(planInfoAdapter);
                    */


                } else {


                }
            }

        }
    };


    final OnItemClickListner.OnClickCallback onClickCallbackCorePlan = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if (corePlanList != null && corePlanList.size() > position) {
                if (type.equalsIgnoreCase("play")) {
                    corePlanAdapter.setCheckedValue(position);
                    corePlanAdapter.notifyDataSetChanged();
                    corePlanIdApi = corePlanList.get(position).getCorePlanId();
                    corePlanNameApi = corePlanList.get(position).getCorePlanName();
/*
                    int corePlanId = corePlanList.get(position).getCorePlanId();
                    for (int i = 0; i <costList.size() ; i++) {

                        if(corePlanId==costList.get(i).getCorePlanId()){
                            planCost = costList.get(i).getAmount();
                        }

                    }

                    */

                    planCost = costListApi.get(position).getAmount();

                    rvListCost.smoothScrollToPosition(position);


                } else {


                }
            }

        }
    };


    private void showSelected(String s) {
        //   if(s.equalsIgnoreCase())

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

            case R.id.btn_proceed_to_pay:

                //      Log.i(String.valueOf(context), "=========DD: " + transactionData());

              /*  test = "tesr";
                Intent intent = new Intent(context, PaypalActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", "Rajendra");
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                ((Activity) context).overridePendingTransition(0, 0);
*/


                Log.i(String.valueOf(context), "=========planCost: " + planCost);


                test = "tesr";
                Intent intent = new Intent(context, PromoCodeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", "Rajendra");
                bundle.putInt(PN_COREPLAN_ID, corePlanIdApi);
                bundle.putInt(PN_PERIOD_ID, periodIdApi);
                bundle.putDouble(PN_PLAN_COST, planCost);
                bundle.putString(PN_PLAN_NAME, corePlanNameApi);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                ((Activity) context).overridePendingTransition(0, 0);


                //    Call<PlanFinalDTO> call = RetroClient.sdaemonApi().transaction("0","2015","2","1","2019-03-28T07:14:40Z","1","2019-03-28T07:14:40Z","1");
                Call<TransactionDTO> call = RetroClient.sdaemonApi().transactionObj(transactionData());
                Log.e(getClass().getName(), "===" + call.request().url());

                call.enqueue(new Callback<TransactionDTO>() {
                    @Override
                    public void onResponse(Call<TransactionDTO> call, Response<TransactionDTO> response) {
                        Log.i(String.valueOf(context), "=========RESPONSE: " + response.body());
                        //  setData(response.body());

                    }

                    @Override
                    public void onFailure(Call<TransactionDTO> call, Throwable t) {

                        showDialoge(context, "", "" + t.getMessage());
                    }
                });


                break;


        }
    }


    TransactionDTO transactionData() {
        TransactionDTO dto = new TransactionDTO();
        try {
            dto.setSubscriptionID(0);
            dto.setCustID(2012);
            dto.setPlanID(2);
            dto.setStatusID(1);
            dto.setCreateDate("2019-03-28T07:14:40Z");
            dto.setCreatedBy(1);
            dto.setLastModifiedDate("2019-03-28T07:14:40Z");
            dto.setLastModifiedBy(1);
            Log.i(getClass().getName(), "=========== DTO dd :" + dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
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
                setData(response.body());
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

    public void setData(PlanFinalDTO dto) {
        if (dto!=null) {
            periodList.addAll(dto.getPeriodList());
            periodAdapter = new PeriodAdapter(context, periodList, onClickCallbackPeriod);
            rvListPeriod.setAdapter(periodAdapter);

            corePlanList.addAll(dto.getCorePlanList());
            corePlanAdapter = new CorePlanAdapter(context, corePlanList, onClickCallbackCorePlan);
            rvListCorePlan.setAdapter(corePlanAdapter);

            costList.addAll(dto.getCostList());

            descList.clear();
            descList.addAll(dto.getDetailList());
//        planInfoAdapter = new PlanInfoAdapter(context, descList, corePlanList, onClickCallback);
//        rvList.setAdapter(planInfoAdapter);
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
