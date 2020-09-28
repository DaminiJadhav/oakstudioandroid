package com.sdaemon.oakstudiotv.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.SelectYour_PlanActivity;
import com.sdaemon.oakstudiotv.activity.Select_PriceActivity;
//import com.techindiana.oakstudiotv.activity.YourPlantestActivity;
import com.sdaemon.oakstudiotv.activity.YourPlantestsActivity;
import com.sdaemon.oakstudiotv.adapter.PlanMasterAdapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.PlanMasterDTO;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentYourPlanAPI extends Fragment implements View.OnClickListener {
    private LinearLayout ll_yourPlan, ll_monthlyPrice,ll_changePlan;
    private TextView tvBasic, tvQuartly, tvAnnul, tvPlanCompare;
    private View rootView;
    private Context context;
    private PlanMasterAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView rvList;
    ArrayList<PlanMasterDTO> list;

    public FragmentYourPlanAPI() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_your_plan_api, container, false);

        //  initialize();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        list = new ArrayList<>();
        initialize();
        getMonthYearly();
        //  getCategory();
    }


    private void initialize() {
        ll_yourPlan = (LinearLayout) rootView.findViewById(R.id.ll_yourPlan);
        tvPlanCompare = (TextView) rootView.findViewById(R.id.tv_plan_compare);
        tvPlanCompare.setOnClickListener(this);


        ll_monthlyPrice = (LinearLayout) rootView.findViewById(R.id.ll_monthlyPrice);
        ll_yourPlan.setOnClickListener(this);
        ll_monthlyPrice.setOnClickListener(this);

        ll_changePlan = (LinearLayout) rootView.findViewById(R.id.ll_change_plan);
        ll_changePlan.setOnClickListener(this);



        tvBasic = (TextView) rootView.findViewById(R.id.tv_basic);
        tvQuartly = (TextView) rootView.findViewById(R.id.tv_quartly);
        tvAnnul = (TextView) rootView.findViewById(R.id.tv_annul);
        rvList = (RecyclerView) rootView.findViewById(R.id.rv_list);
        list = new ArrayList<>();
        mLinearLayoutManager = new LinearLayoutManager(context);
        rvList.setLayoutManager(mLinearLayoutManager);
        adapter = new PlanMasterAdapter(context, list, onClickCallback);
        rvList.setAdapter(adapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_yourPlan:
                Intent intent = new Intent(getActivity(), SelectYour_PlanActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_monthlyPrice:
                Intent intent1 = new Intent(getActivity(), Select_PriceActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_plan_compare:
                //  showFragment(new FragmentPlanCompare(), "FragmentPlanCompare");
                //  Intent intent2 = new Intent(getActivity(), PlanCompareActivity.class);
                //  Intent intent2 = new Intent(getActivity(), PlanCompareActivityLinearLayout.class);
                // Intent intent2 = new Intent(getActivity(), PlanCompareActivityNew.class);


//                Intent intent2 = new Intent(getActivity(), PlanCompareFinalActivity.class);
//                startActivity(intent2);
                break;
            case R.id.ll_change_plan:
                Intent changePlanIntent = new Intent(getActivity(), YourPlantestsActivity.class);
                startActivity(changePlanIntent);
        }
    }

    private void showFragment(Fragment targetFragment, String className) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, targetFragment, className);
        ft.commitAllowingStateLoss();
    }


    final OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if (list != null && list.size() > position) {
                if (type.equalsIgnoreCase("play")) {
                    // Toast.makeText(OffLineDownloadListActivity.this, "DOWNLOAD", Toast.LENGTH_LONG).show();

                } else {
                    // Toast.makeText(OffLineDownloadListActivity.this, "DOWNLOAD", Toast.LENGTH_LONG).show();

                }
            }

        }
    };

    private void getCategory() {
        // progressBar.show();
        Call<ArrayList<PlanMasterDTO>> call = RetroClient.sdaemonApi().getBasic();
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<ArrayList<PlanMasterDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<PlanMasterDTO>> call, Response<ArrayList<PlanMasterDTO>> response) {
                // Toast.makeText(context, "login successfully", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    //  public ResponseDTO jobList(String res)
                    Log.i(getClass().getName(), "=========RESPONSE: " + response.body());
                    ArrayList<PlanMasterDTO> list = new ArrayList<>();
                    list.addAll(response.body());
                    adapter = new PlanMasterAdapter(context, list, onClickCallback);
                    rvList.setAdapter(adapter);

                } else if (response.code() == 409) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.i(getClass().getName(), "=========RESPONSE: 409 ");
                        String msg = jObjError.getString("message");
                        showDialoge(context, "", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
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
            public void onFailure(Call<ArrayList<PlanMasterDTO>> call, Throwable t) {
                //    progressBar.dismiss();

                Log.i(getClass().getName(), "--------------- onFailure " + t.getMessage());
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


    private void getMonthYearly() {
        // progressBar.show();
        Call<ArrayList<PlanMasterDTO>> call = RetroClient.sdaemonApi().getMonthYearly();
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<ArrayList<PlanMasterDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<PlanMasterDTO>> call, Response<ArrayList<PlanMasterDTO>> response) {
                // Toast.makeText(context, "login successfully", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    //  public ResponseDTO jobList(String res)
                    Log.i(getClass().getName(), "=========RESPONSE: " + response.body());
                    ArrayList<PlanMasterDTO> list = new ArrayList<>();
                    list.addAll(response.body());
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getCorePlanName().equalsIgnoreCase("Basic")) {
                            //  if(list.get(i).getCorePlanName().equalsIgnoreCase("Standard")){
                            // if(list.get(i).getCorePlanName().equalsIgnoreCase("Premium")){

                            tvBasic.setText("$" + "" + list.get(i).getMonthly());
                            tvQuartly.setText("$" + "" + list.get(i).getQuatarly());
                            tvAnnul.setText("$" + "" + list.get(i).getYearly());
                            getCategory();

                        }

                    }

                } else if (response.code() == 409) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.i(getClass().getName(), "=========RESPONSE: 409 ");
                        String msg = jObjError.getString("message");
                        showDialoge(context, "", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
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
            public void onFailure(Call<ArrayList<PlanMasterDTO>> call, Throwable t) {
                //    progressBar.dismiss();

                Log.i(getClass().getName(), "--------------- onFailure " + t.getMessage());
                showDialoge(context, "", "" + t.getMessage());
            }
        });
    }


}
