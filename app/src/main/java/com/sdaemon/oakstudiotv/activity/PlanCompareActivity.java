package com.sdaemon.oakstudiotv.activity;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class PlanCompareActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    private ImageView iv_tabback, ivBasic, ivStandard, ivPremium;
    private RadioGroup radioGroup;
    private RadioButton genderradioButton;
    private LinearLayout llBasic, llStandard, llPremium;
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
            Toast.makeText(PlanCompareActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PlanCompareActivity.this, genderradioButton.getText(), Toast.LENGTH_SHORT).show();

        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                Toast.makeText(PlanCompareActivity.this, radioButton.getText(), Toast.LENGTH_LONG).show();
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

            }

            @Override
            public void onFailure(Call<PlanDescriptionDTO> call, Throwable t) {

                showDialoge(context, "", "" + t.getMessage());
            }
        });
    }








    private void getCategory() {
        // progressBar.show();
        Call<PlanDescriptionDTO> call =  RetroClient.sdaemonApi().getPlanDescription("2");
        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<PlanDescriptionDTO>() {
            @Override
            public void onResponse(Call<PlanDescriptionDTO> call, Response<PlanDescriptionDTO> response) {
                // Toast.makeText(context, "login successfully", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    //  public ResponseDTO jobList(String res)
                    Log.i(String.valueOf(context), "=========RESPONSE: " + response.body());

//                    for (int i = 0; i <response.body().getResultList().size() ; i++) {
//                           Log.i(getClass().getName(), "========= desc : " + response.body().getResultList().get(i).getSubDescription());
//                    }




/*
                  String jsonStr = response.body().re().toString();

                    if (jsonStr != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(jsonStr);

                            // Getting JSON Array node
                            JSONArray result = null;

                            try {
                                result = jsonObj.getJSONArray("result");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // looping through All Contacts
                            for (int i = 0; i < result.length(); i++) {
                              //  JSONObject r = result.getJSONObject(i);

                              //  String id = r.getString("id");


                                JSONObject jsonObject = result.getJSONObject(0);
                                for(int n = 0; n < result.length(); n++)
                                {
                                    jsonObject =   result.getJSONObject(n);
                                }


                                String test   = jsonObject.getString("SubDescription");
                                Log.i(getClass().getName(), "========= test: " + test);











//
//                                // tmp hash map for single contact
//                                HashMap<String, String> contact = new HashMap<>();
//
//                                // adding each child node to HashMap key => value
//                                contact.put("id", id);
//                                contact.put("name", name);
//                                contact.put("email", email);
//                                contact.put("mobile", mobile);
//
//                                // adding contact to contact list
//                                contactList.add(contact);
                            }
                        } catch (final JsonIOException e) {
                            Log.e(String.valueOf(context), "Json parsing error: " + e.getMessage());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG)
                                            .show();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }





















                    //   ArrayList<CategoryDTO> list = new ArrayList<>();
                  //  list.addAll(response.body());

//                    categoryAdapter =new MovieAdapterSdaemon(context, list,onClickCallback);
//                    rv.setAdapter(categoryAdapter);
//

                }
                else if (response.code() == 409) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.i(getClass().getName(),"=========RESPONSE: 409 ");
                        String msg = jObjError.getString("message");
                        showDialoge(context, "", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.i(getClass().getName(),"=========RESPONSE: else ");
                        String msg = jObjError.getString("message");
                        showDialoge(context, "", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
                }
            }

            @Override
            public void onFailure(Call<PlanDescriptionDTO> call, Throwable t) {
                //    progressBar.dismiss();

                Log.i(getClass().getName(),"--------------- onFailure "+ t.getMessage());
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
}
