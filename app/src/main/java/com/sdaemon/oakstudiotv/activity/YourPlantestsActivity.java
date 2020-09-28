package com.sdaemon.oakstudiotv.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cardinalcommerce.shared.userinterfaces.ProgressDialog;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.Plan_test_adapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.PlantestDTO;
import com.sdaemon.oakstudiotv.model.Datum;
import com.sdaemon.oakstudiotv.model.Periodlist;
import com.sdaemon.oakstudiotv.model.plan_test_DTO;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;
import com.sdaemon.oakstudiotv.utils.Utilities;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourPlantestsActivity extends AppCompatActivity {
    private LinearLayout ll_yourPlan, ll_monthlyPrice,llProgress;
    RecyclerView rvList;
    ImageView iv_back;
    private LinearLayoutManager mLinearLayoutManager;
    private Plan_test_adapter adapter;
    ArrayList<PlantestDTO> list1;
    ArrayList<Datum> list2;
    ArrayList<Periodlist>list3;
    ArrayList<plan_test_DTO> list;
    ProgressDialog progressDialog;
    private Context context;
    ProgressBar progressBar;
    Utilities utilities;
    LinearLayout lloffline;
    ImageView ivoffline;
    TextView tvoffline,tvchoseplan;
    SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_plantests);

//        llProgress=(LinearLayout) findViewById(R.id.ll_progress);
        progressBar=(ProgressBar) findViewById(R.id.progress_bar);




        initialize();






//        progressDialog=new ProgressDialog(this,);


    }

    private void initialize() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_back=(ImageView) findViewById(R.id.iv_tabback);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivoffline=findViewById(R.id.iv_offline);
        tvoffline=findViewById(R.id.tv_offline);
        tvchoseplan=findViewById(R.id.tv_chooseplan);
        tvchoseplan.setVisibility(View.VISIBLE);

        refreshLayout=findViewById(R.id.swipeRefresh_plan);


        utilities=Utilities.getInstance(context);

//        if (!utilities.isNetworkAvailable()){
////            lloffline.setVisibility(View.VISIBLE);
//            ivoffline.setVisibility(View.VISIBLE);
//            tvoffline.setVisibility(View.VISIBLE);
//            tvoffline.setText(getResources().getString(R.string.you_re_offline));
//            ivoffline.setImageResource(R.drawable.ic_cloud_off_black_24dp);
//
//        }else {
//            ivoffline.setVisibility(View.GONE);
//            tvoffline.setVisibility(View.GONE);

            getPlantestData();
//        }

        refreshLayout.setOnRefreshListener(this::getPlantestData);

//        list = new ArrayList<>();
//        list.add(new plan_test_DTO("Basic", "Basic, $8.99 per month (up from $7.99 in 2018). Netflix's basic plan doesn't provide high definition viewing and its programs can only be watched on one screen at a time.", 0));
//        list.add(new plan_test_DTO("Standard", "Standard, $12.99 per month (up from $10.99 in 2018). The Netflix standard offers HD videos and allows for two simultaneous viewings..", 1));
//        list.add(new plan_test_DTO("Premiun", "Premium, $15.99 per month (up from $13.99 in 2018). The top tier offering includes the ability to watch four screens at the same time. It's also the only item on the Netflix that offers a 4K viewing option..", 0));
//        list.add(new plan_test_DTO("Basic", "Basic, $15.99 per month (up from $13.99 in 2018). The top tier offering includes the ability to watch four screens at the same time. It's also the only item on the Netflix that offers a 4K viewing option..", 0));
//        list.add(new plan_test_DTO("Premiun", "Premium, $15.99 per month (up from $13.99 in 2018). The top tier offering includes the ability to watch four screens at the same time. It's also the only item on the Netflix that offers a 4K viewing option..", 0));
//        list.add(new plan_test_DTO("Premiun", "Premium, $15.99 per month (up from $13.99 in 2018). The top tier offering includes the ability to watch four screens at the same time. It's also the only item on the Netflix that offers a 4K viewing option..", 0));

        rvList = (RecyclerView) findViewById(R.id.rv_list1);
        mLinearLayoutManager = new LinearLayoutManager(context);
//        rvList.setLayoutManager(mLinearLayoutManager);
//        ((SimpleItemAnimator) rvList.getItemAnimator()).setSupportsChangeAnimations(false);
//
//        adapter = new Plan_test_adapter(YourPlantestsActivity.this, list, onClickCallback);
//        rvList.setAdapter(adapter);
//        rvList.setHasFixedSize(true);
    }

    final OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
//            llProgress.setVisibility(View.GONE);
            if (list1 != null && list1.size() > position) {
                if (type.equalsIgnoreCase("test")) {
                    try{
                        Log.e("list", "===" + "");
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    Intent intent=new Intent(YourPlantestsActivity.this,PlanSelectionActivity.class);
                    Bundle bundle=new Bundle();
                    list3 = new ArrayList<>();
                    list3.addAll(list2.get(position).getPeriodlists());

                    list1.get(0).getData().get(position).getCorePlanName();
                    bundle.putParcelableArrayList("arraylist",list3);
                    bundle.putString("TYPE", list1.get(0).getData().get(position).getCorePlanid());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
//            getPlantestDate();
//                    Intent intent = new Intent(YourPlantestActivity.this, PlanSelectionActivity.class);
//                    startActivity(intent);
        }
    };


    private void getPlantestData(){
        ivoffline.setVisibility(View.GONE);
        tvoffline.setVisibility(View.GONE);
        tvchoseplan.setVisibility(View.VISIBLE);


        progressBar.setVisibility(View.VISIBLE);

        Call<PlantestDTO> call= RetroClient.sdaemonApi().getPlanDetail();
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<PlantestDTO>() {
            @Override
            public void onResponse(Call<PlantestDTO> call, Response<PlantestDTO> response) {
                if (response.isSuccessful()){

                    progressBar.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);

//                    Toast.makeText(YourPlantestsActivity.this, "Plan success", Toast.LENGTH_SHORT).show();
                    list1=new ArrayList<>();
                    list1.add(response.body());
                    list2=new ArrayList<>();
                    int si=list1.get(0).getData().size();
                    for (int i = 0; i <si ; i++) {
                        list2.add(list1.get(0).getData().get(i));
                    }
                    rvList.setLayoutManager(mLinearLayoutManager);
                    adapter = new Plan_test_adapter(context, list2, onClickCallback);
                    rvList.setAdapter(adapter);


//                        Toast.makeText(YourPlantestsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
                else if (response.code() == 409) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.i(getClass().getName(), "=========RESPONSE: 409 ");
                        String msg = jObjError.getString("message");
                        showDialoge(context, "", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.i(getClass().getName(), "=========RESPONSE: else ");
                        String msg = jObjError.getString("message");
                        showDialoge(context, "", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<PlantestDTO> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);

                Log.i(getClass().getName(), "--------------- onFailure " + t.getMessage());
                showDialoge(context, "", "" + t.getMessage());
                Toast.makeText(YourPlantestsActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        }

    public void showDialoge(Context context, String title, String msg) {
        try {
            progressBar.setVisibility(View.GONE);
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context, R.style.dialoge);
            builder.setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showProgress(){

    }
}
