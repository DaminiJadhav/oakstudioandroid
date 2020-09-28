package com.sdaemon.oakstudiotv.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.PlanCompareAdapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.PlanDescriptionDTO;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanCompareActivityLinearLayout extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private ImageView iv_tabback, ivBasic, ivStandard, ivPremium;
    private RadioGroup radioGroup;
    private RadioButton genderradioButton;
    private LinearLayout llBasic, llStandard, llPremium,llViewName,llViewBasic,llViewStandard,llViewPremium;
    ArrayList<PlanDescriptionDTO.Result> list;
    LinearLayoutManager mLinearLayoutManager;
    PlanCompareAdapter adapter;
    RecyclerView rvList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_compare);
        context = this;
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);
        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        llViewName = (LinearLayout)findViewById(R.id.ll_view_name);
        llViewBasic = (LinearLayout)findViewById(R.id.ll_view_basic);
        llViewStandard = (LinearLayout)findViewById(R.id.ll_view_standard);
        llViewPremium = (LinearLayout)findViewById(R.id.ll_view_premium);




        llBasic = (LinearLayout) findViewById(R.id.ll_basic);
        llBasic.setOnClickListener(this);
        llStandard = (LinearLayout) findViewById(R.id.ll_standard);
        llStandard.setOnClickListener(this);
        llPremium = (LinearLayout) findViewById(R.id.ll_premium);
        llPremium.setOnClickListener(this);

        ivBasic = (ImageView) findViewById(R.id.iv_basic);
        ivStandard = (ImageView) findViewById(R.id.iv_standard);
        ivPremium = (ImageView) findViewById(R.id.iv_premium);

        llBasic.setBackgroundColor(getResources().getColor(R.color.red));
        llStandard.setBackgroundColor(getResources().getColor(R.color.played));
        llPremium.setBackgroundColor(getResources().getColor(R.color.played));
        ivBasic.setVisibility(View.VISIBLE);
        ivStandard.setVisibility(View.INVISIBLE);
        ivPremium.setVisibility(View.INVISIBLE);


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        genderradioButton = (RadioButton) findViewById(selectedId);

        if (selectedId == -1) {
            Toast.makeText(PlanCompareActivityLinearLayout.this, "Nothing selected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PlanCompareActivityLinearLayout.this, genderradioButton.getText(), Toast.LENGTH_SHORT).show();

        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(PlanCompareActivityLinearLayout.this, radioButton.getText(), Toast.LENGTH_LONG).show();
                showSelected(radioButton.getText().toString());
            }
        });


        list = new ArrayList<>();
//        VideoDTO dto = new VideoDTO();
//        dto.setVideoUrl("HD");
//        list.add(dto);
//
//        dto = new VideoDTO();
//        dto.setVideoUrl("HD 4k");
//        list.add(dto);
//
//        dto = new VideoDTO();
//        dto.setVideoUrl("HDkjkdjg ngvkljdfjgjD");
//        list.add(dto);
//        dto = new VideoDTO();
//        dto.setVideoUrl("vbdc cvnmdcn nvmcn");
//        list.add(dto);

        rvList = (RecyclerView) findViewById(R.id.rv_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(mLinearLayoutManager);
        adapter = new PlanCompareAdapter(context, list, onClickCallback);
        rvList.setAdapter(adapter);
     //  getCategory();

        getLogIn();


    }

    final OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if (list != null && list.size() > position) {
                if (type.equalsIgnoreCase("play")) {

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
        menu.findItem(R.id.action_done).setVisible(true);
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
            case R.id.ll_basic:
                llBasic.setBackgroundColor(getResources().getColor(R.color.red));
                llStandard.setBackgroundColor(getResources().getColor(R.color.played));
                llPremium.setBackgroundColor(getResources().getColor(R.color.played));
                ivBasic.setVisibility(View.VISIBLE);
                ivStandard.setVisibility(View.INVISIBLE);
                ivPremium.setVisibility(View.INVISIBLE);
                break;

            case R.id.ll_standard:
                llBasic.setBackgroundColor(getResources().getColor(R.color.played));
                llStandard.setBackgroundColor(getResources().getColor(R.color.red));
                llPremium.setBackgroundColor(getResources().getColor(R.color.played));
                ivBasic.setVisibility(View.INVISIBLE);
                ivStandard.setVisibility(View.VISIBLE);
                ivPremium.setVisibility(View.INVISIBLE);
                break;

            case R.id.ll_premium:
                llBasic.setBackgroundColor(getResources().getColor(R.color.played));
                llStandard.setBackgroundColor(getResources().getColor(R.color.played));
                llPremium.setBackgroundColor(getResources().getColor(R.color.red));
                ivBasic.setVisibility(View.INVISIBLE);
                ivStandard.setVisibility(View.INVISIBLE);
                ivPremium.setVisibility(View.VISIBLE);
                break;


        }
    }

    private void getLogIn() {
        //  progressBar.show();
        //    String android_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);

        Call<PlanDescriptionDTO> call = RetroClient.sdaemonApi().getPlanDescription("2");
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<PlanDescriptionDTO>() {
            @Override
            public void onResponse(Call<PlanDescriptionDTO> call, Response<PlanDescriptionDTO> response) {

                Log.i(String.valueOf(context), "=========RESPONSE: " + response.body());
//
//               for (int i = 0; i <response.body().getResultList().size() ; i++) {
//                     Log.i(getClass().getName(), "========= desc : " + response.body().getResultList().get(i).getName());
//               }

                list.addAll(response.body().getResultList());
                adapter = new PlanCompareAdapter(context, list, onClickCallback);
                rvList.setAdapter(adapter);

                setData();

            }

            @Override
            public void onFailure(Call<PlanDescriptionDTO> call, Throwable t) {

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
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setData(){
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        //  if (result.getDestinationDetailsList() != null && result.getDestinationDetailsList().size() > 0) {
        if (llViewName.getChildCount() >= 1)
            llViewName.removeAllViews();

        for (int i = 0; i < 4; i++) {
            View view = inflater.inflate(R.layout.item_address, llViewName, false);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            tvName.setText("Rajendra");
            llViewName.addView(view);
        }
        // }


        if (llViewBasic.getChildCount() >= 1)
            llViewBasic.removeAllViews();

        for (int i = 0; i < 4; i++) {

            View view = inflater.inflate(R.layout.item_status, llViewBasic, false);
            ImageView ivStatus = (ImageView) view.findViewById(R.id.iv_status);
            ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));
            llViewBasic.addView(view);
        }


        if (llViewStandard.getChildCount() >= 1)
            llViewStandard.removeAllViews();

        for (int i = 0; i < 4; i++) {

            View view = inflater.inflate(R.layout.item_status, llViewStandard, false);
            ImageView ivStatus = (ImageView) view.findViewById(R.id.iv_status);
            ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clear));
            llViewStandard.addView(view);
        }




        if (llViewPremium.getChildCount() >= 1)
            llViewPremium.removeAllViews();

        for (int i = 0; i < 4; i++) {

            View view = inflater.inflate(R.layout.item_status, llViewPremium, false);
            ImageView ivStatus = (ImageView) view.findViewById(R.id.iv_status);
            ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clear));
            llViewPremium.addView(view);
        }




    }
}
