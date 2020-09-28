//package com.techindiana.oakstudiotv.activity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.techindiana.oakstudiotv.R;
//import com.techindiana.oakstudiotv.adapter.PlanInfoAdapter;
//import com.techindiana.oakstudiotv.adapter.PeriodAdapter;
//import com.techindiana.oakstudiotv.adapter.CostPriceAdapter;
//import com.techindiana.oakstudiotv.dao.RetroClient;
//import com.techindiana.oakstudiotv.dto.PlanFinalDTO;
//import com.techindiana.oakstudiotv.dto.TransactionDTO;
//import com.techindiana.oakstudiotv.utils.AppConstants;
//import com.techindiana.oakstudiotv.utils.OnItemClickListner;
//
//import java.util.ArrayList;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//
//public class PlanCompareFinalActivityOld extends AppCompatActivity implements AppConstants, View.OnClickListener {
//    Context context;
//    private Button btnProceedToPay;
//    private ImageView iv_tabback, ivBasic, ivStandard, ivPremium;
//    private LinearLayout llBasic, llStandard, llPremium;
//    TextView tvDuration, tvPriceBasic, tvPriceStandard, tvPricePremium;
//    ArrayList<PlanFinalDTO.Details> descList;
//    ArrayList<PlanFinalDTO.Cost> costList;
//    ArrayList<PlanFinalDTO.Cost> costListPrice;
//    LinearLayoutManager mLinearLayoutManager;
//    LinearLayoutManager mLinearLayoutManagerCost;
//    LinearLayoutManager mLinearLayoutManagerCostPrice;
//    PlanInfoAdapter adapter;
//    PeriodAdapter planCostAdapter;
//    CostPriceAdapter planCostPriceAdapter;
//    RecyclerView rvList, rvListCost, rvListPrice;
//    String test = "";
//    private Retrofit retrofit;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.plan_compare_final);
//        context = this;
//        descList = new ArrayList<>();
//        costList = new ArrayList<>();
//        costListPrice = new ArrayList<>();
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
//        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
//        iv_tabback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        btnProceedToPay = (Button) findViewById(R.id.btn_proceed_to_pay);
//        btnProceedToPay.setOnClickListener(this);
//
//        llBasic = (LinearLayout) findViewById(R.id.ll_basic);
//        llBasic.setOnClickListener(this);
//        llStandard = (LinearLayout) findViewById(R.id.ll_standard);
//        llStandard.setOnClickListener(this);
//        llPremium = (LinearLayout) findViewById(R.id.ll_premium);
//        llPremium.setOnClickListener(this);
//
//        ivBasic = (ImageView) findViewById(R.id.iv_basic);
//        ivStandard = (ImageView) findViewById(R.id.iv_standard);
//        ivPremium = (ImageView) findViewById(R.id.iv_premium);
//
//        llBasic.setBackgroundColor(getResources().getColor(R.color.red));
//        llStandard.setBackgroundColor(getResources().getColor(R.color.played));
//        llPremium.setBackgroundColor(getResources().getColor(R.color.played));
//        ivBasic.setVisibility(View.VISIBLE);
//        ivStandard.setVisibility(View.INVISIBLE);
//        ivPremium.setVisibility(View.INVISIBLE);
//
//        tvDuration = (TextView) findViewById(R.id.tv_duration);
//
//        tvPriceBasic = (TextView) findViewById(R.id.tv_price_basic);
//        tvPriceStandard = (TextView) findViewById(R.id.tv_price_standard);
//        tvPricePremium = (TextView) findViewById(R.id.tv_price_premium);
//
//
//        rvListCost = (RecyclerView) findViewById(R.id.rv_list_cost);
//        mLinearLayoutManagerCost = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        rvListCost.setLayoutManager(mLinearLayoutManagerCost);
//
//
//        rvListPrice = (RecyclerView) findViewById(R.id.rv_list_price);
//        mLinearLayoutManagerCostPrice = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        rvListPrice.setLayoutManager(mLinearLayoutManagerCostPrice);
//
//
//        rvList = (RecyclerView) findViewById(R.id.rv_list);
//        mLinearLayoutManager = new LinearLayoutManager(this);
//        rvList.setLayoutManager(mLinearLayoutManager);
////        adapter = new PlanInfoAdapter(context, list, onClickCallback);
////        rvList.setAdapter(adapter);
//        getData();
//
//
//    }
//
//    final OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
//        @Override
//        public void onClicked(View view, int position, String type) {
//            if (descList != null && descList.size() > position) {
//                if (type.equalsIgnoreCase("play")) {
//
//                } else {
//
//
//                }
//            }
//
//        }
//    };
//
//    final OnItemClickListner.OnClickCallback onClickCallbackCost = new OnItemClickListner.OnClickCallback() {
//        @Override
//        public void onClicked(View view, int position, String type) {
//            if (costList != null && costList.size() > position) {
//                if (type.equalsIgnoreCase("play")) {
//                    planCostAdapter.setCheckedValue(position);
//                    planCostAdapter.notifyDataSetChanged();
//                    //  planCostPriceAdapter.notifyDataSetChanged();
//
//
//                    tvDuration.setText(costList.get(position).getName());
//                    tvPriceBasic.setText("$" + costList.get(position).getOne());
//                    tvPriceStandard.setText("$" + costList.get(position).getTwo());
//                    tvPricePremium.setText("$" + costList.get(position).getThree());
//
////                    String name = costList.get(position).getName();
////                    planCostPriceAdapter = new CostPriceAdapter(context, costListPrice, onClickCallbackCost,name);
////                    rvListPrice.setAdapter(planCostPriceAdapter);
//
//                } else {
//
//
//                }
//            }
//
//        }
//    };
//
//
//    private void showSelected(String s) {
//        //   if(s.equalsIgnoreCase())
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        invalidateOptionsMenu();
//        menu.findItem(R.id.action_done).setVisible(false);
//        menu.findItem(R.id.action_person).setVisible(false);
//        menu.findItem(R.id.action_tuneUp).setVisible(false);
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_done) {
//            return true;
//        }
//        if (id == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.ll_basic:
//                llBasic.setBackgroundColor(getResources().getColor(R.color.red));
//                llStandard.setBackgroundColor(getResources().getColor(R.color.played));
//                llPremium.setBackgroundColor(getResources().getColor(R.color.played));
//                ivBasic.setVisibility(View.VISIBLE);
//                ivStandard.setVisibility(View.INVISIBLE);
//                ivPremium.setVisibility(View.INVISIBLE);
//                break;
//
//            case R.id.ll_standard:
//                llBasic.setBackgroundColor(getResources().getColor(R.color.played));
//                llStandard.setBackgroundColor(getResources().getColor(R.color.red));
//                llPremium.setBackgroundColor(getResources().getColor(R.color.played));
//                ivBasic.setVisibility(View.INVISIBLE);
//                ivStandard.setVisibility(View.VISIBLE);
//                ivPremium.setVisibility(View.INVISIBLE);
//                break;
//
//            case R.id.ll_premium:
//                llBasic.setBackgroundColor(getResources().getColor(R.color.played));
//                llStandard.setBackgroundColor(getResources().getColor(R.color.played));
//                llPremium.setBackgroundColor(getResources().getColor(R.color.red));
//                ivBasic.setVisibility(View.INVISIBLE);
//                ivStandard.setVisibility(View.INVISIBLE);
//                ivPremium.setVisibility(View.VISIBLE);
//                break;
//            case R.id.btn_proceed_to_pay:
//
//          Log.i(String.valueOf(context), "=========DD: " + transactionData());
//
//  test = "tesr";
//                Intent intent = new Intent(context, PaypalActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("name", "Rajendra");
//                intent.putExtras(bundle);
//                startActivityForResult(intent, 1);
//                ((Activity) context).overridePendingTransition(0, 0);
//
//
//
//
//
//                test = "tesr";
//                Intent intent = new Intent(context, PromoCodeActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("name", "Rajendra");
//                bundle.putString(PN_NAME, "Basic");
//                bundle.putString(PN_AMOUNT, "50");
//                intent.putExtras(bundle);
//                startActivityForResult(intent, 1);
//                ((Activity) context).overridePendingTransition(0, 0);
//
//
//
//
////
////                //    Call<PlanFinalDTO> call = RetroClient.sdaemonApi().transaction("0","2015","2","1","2019-03-28T07:14:40Z","1","2019-03-28T07:14:40Z","1");
////                Call<TransactionDTO> call = RetroClient.sdaemonApi().transactionObj(transactionData());
////                Log.e(getClass().getName(), "===" + call.request().url());
////
////                call.enqueue(new Callback<TransactionDTO>() {
////                    @Override
////                    public void onResponse(Call<TransactionDTO> call, Response<TransactionDTO> response) {
////                        Log.i(String.valueOf(context), "=========RESPONSE: " + response.body());
////                        //  setData(response.body());
////
////                    }
////
////                    @Override
////                    public void onFailure(Call<TransactionDTO> call, Throwable t) {
////
////                        showDialoge(context, "", "" + t.getMessage());
////                    }
////                });
//
//
//
//                break;
//
//
//        }
//    }
//
//
//    TransactionDTO transactionData() {
//        TransactionDTO dto = new TransactionDTO();
//        try {
//            dto.setSubscriptionID(0);
//            dto.setCustID(2012);
//            dto.setPlanID(2);
//            dto.setStatusID(1);
//            dto.setCreateDate("2019-03-28T07:14:40Z");
//            dto.setCreatedBy(1);
//            dto.setLastModifiedDate("2019-03-28T07:14:40Z");
//            dto.setLastModifiedBy(1);
//            Log.i(getClass().getName(), "=========== DTO dd :" + dto);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return dto;
//    }
//
//JSONObject dd() {
//        JSONObject jObj = new JSONObject();
//        try {
//            jObj.put("SubscriptionID", "0");
//            jObj.put("CustID", "2015");
//            jObj.put("PlanID", "2");
//            jObj.put("StatusID", "1");
//            jObj.put("CreateDate", "2019-03-28T07:14:40Z");
//            jObj.put("CreatedBy", "1");
//            jObj.put("LastModifiedDate", "2019-03-28T07:14:40Z");
//            jObj.put("LastModifiedBy", "1");
//
//            Log.i(getClass().getName(), "=========== jArr dd :" + jObj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return jObj;
//    }
//
//
//
//
//private void getData() {
//        //  progressBar.show();
//        //    String android_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
//
//        Call<PlanFinalDTO> call = RetroClient.sdaemonApi().getPlanFinalDesc();
//        Log.e(getClass().getName(), "===" + call.request().url());
//        call.enqueue(new Callback<PlanFinalDTO>() {
//            @Override
//            public void onResponse(Call<PlanFinalDTO> call, Response<PlanFinalDTO> response) {
//
//                Log.i(String.valueOf(context), "=========RESPONSE: " + response.body());
//                setData(response.body());
//
//
//            }
//
//            @Override
//            public void onFailure(Call<PlanFinalDTO> call, Throwable t) {
//
//                showDialoge(context, "", "" + t.getMessage());
//            }
//        });
//    }
//
//    public void showDialoge(Context context, String title, String msg) {
//        try {
//            AlertDialog.Builder builder;
//            builder = new AlertDialog.Builder(context, R.style.dialoge);
//            builder.setTitle(title)
//                    .setMessage(msg)
//                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//
//                    .show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void setData(PlanFinalDTO dto) {
//        descList.addAll(dto.getDetailList());
//        adapter = new PlanInfoAdapter(context, descList, onClickCallback);
//        rvList.setAdapter(adapter);
//
//        costList.addAll(dto.getCostList());
//        planCostAdapter = new PeriodAdapter(context, costList, onClickCallbackCost);
//        rvListCost.setAdapter(planCostAdapter);
//
//
//       // costListPrice.addAll(dto.getCostList());
//         planCostPriceAdapter = new CostPriceAdapter(context, costListPrice, onClickCallbackCost);
//         rvListPrice.setAdapter(planCostPriceAdapter);
//
//
//
//
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // check if the request code is same as what is passed  here it is 2
//        if (requestCode == 1) {
////            String message=data.getStringExtra("MESSAGE");
////            Log.i(String.valueOf(context), "=========message: " + message);
////            Log.i(String.valueOf(context), "=========test: " + test);
////            finish();
//        }
//    }
//
//
//}
