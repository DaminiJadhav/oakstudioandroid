package com.sdaemon.oakstudiotv.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.util.Log;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.YearList_Adapter;
import com.sdaemon.oakstudiotv.model.GetYearResp;
import com.sdaemon.oakstudiotv.retrofit_utils.RetrofitUtils;
import com.sdaemon.oakstudiotv.retrofit_utils.restUtils.RestCallInterface;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.DialogUtils;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;
import com.sdaemon.oakstudiotv.utils.Utilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class YearActivity extends AppCompatActivity {
    private ListView yearListView;
    private YearList_Adapter genreList_adapter;
    private ImageView iv_tabback;
    private Retrofit retrofit;
    private Context context;
    private ProgressDialog progressDialog;
    public static int year_position;
    public static  String year_name;
    private ArrayList<GetYearResp> getYear = new ArrayList<>();
    AppSession appSession;
    private String strname;
    boolean year=false;
    ImageView ivoffline;
    TextView tvoffline;
    Utilities utilities;
    SwipeRefreshLayout refreshLayout;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            if (bundle.containsKey("KEY_YEAR"))
                strname = bundle.getString("KEY_YEAR");
        }
        context = this;
        appSession = AppSession.getInstance(context);
        initialize();

    }

    private void initialize() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);

        context = YearActivity.this;
        retrofit = RetrofitUtils.getRetrofitWithoutHeader();
        yearListView = (ListView) findViewById(R.id.yearListView);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);

        ivoffline=findViewById(R.id.iv_offline);
        tvoffline=findViewById(R.id.tv_offline);
        utilities=Utilities.getInstance(context);
        refreshLayout=findViewById(R.id.refreshFilter);
        progressBar=findViewById(R.id.progressbar_year);


//        refreshLayout.setOnRefreshListener(this::getGenre_Webservice);



        if (!utilities.isNetworkAvailable()){
            ivoffline.setVisibility(View.VISIBLE);
            tvoffline.setVisibility(View.VISIBLE);
            tvoffline.setText(getResources().getString(R.string.you_re_offline));
            ivoffline.setImageResource(R.drawable.ic_cloud_off_black_24dp);

        }else {
            ivoffline.setVisibility(View.GONE);
            tvoffline.setVisibility(View.GONE);

            getGenre_Webservice();
        }




        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getGenre_Webservice() {
        progressBar.setVisibility(View.VISIBLE);

        //Creating Rest Services
        RestCallInterface restInterface = retrofit.create(RestCallInterface.class);
        //Calling method to get whether report
        Call<ArrayList<GetYearResp>> call = restInterface.getYearList();
        call.enqueue(new Callback<ArrayList<GetYearResp>>() {
            @Override
            public void onResponse(Call<ArrayList<GetYearResp>> call, Response<ArrayList<GetYearResp>> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    ivoffline.setVisibility(View.GONE);
                    tvoffline.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);

                    genreList_adapter = new YearList_Adapter(YearActivity.this, response.body(),onClickCallback,strname);
                   getYear.addAll(response.body());
                    yearListView.setAdapter(genreList_adapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetYearResp>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                hideProgress();
                refreshLayout.setRefreshing(false);

                t.printStackTrace();
                Toast.makeText(context, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(year==false)
        {
            appSession.setYearID("");
            appSession.setYearIDposition(0);

            appSession.setTVshowYearID("");
            appSession.setTVYearIDposition(0);


        }
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
//        if(year==false)
//        {
//            appSession.setYearID("");
//            appSession.setYearIDposition(0);
//        }
        super.onPause();
    }

    @Override
    protected void onStop() {
//        if(year==false)
//        {
//            appSession.setYearID("");
//            appSession.setYearIDposition(0);
//        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        if(year==false)
//        {
//            appSession.setYearID("");
//            appSession.setYearIDposition(0);
//        }
        super.onDestroy();
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
        menu.findItem(R.id.action_tuneUp).setVisible(false);
        menu.findItem(R.id.action_person).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_done:
                Intent intent = new Intent();
                intent.putExtra("KEY_YEAR", strname);
                setResult(RESULT_OK, intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showProgress() {
        if (progressDialog == null)
            progressDialog = DialogUtils.createProgressDialog(context);
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if (type.equalsIgnoreCase("")) {
                Toast.makeText(context, "" + getYear.get(position).getDescription().toString(), Toast.LENGTH_LONG).show();
                strname=getYear.get(position).getDescription();
                if (appSession.getMovieType()==true) {
                    appSession.setYearID(getYear.get(position).getDescription().toString());
                    appSession.setYearIDposition(getYear.get(position).getID());
                    Log.i("Year ID",""+getYear.get(position).getID()+  " "+appSession.getMovieType());

                }else {
                    appSession.setTVshowYearID(getYear.get(position).getDescription().toString());
                    appSession.setTVYearIDposition(getYear.get(position).getID());

                    Log.i("Year ID",""+getYear.get(position).getID()+  " "+appSession.getMovieType());

                }


                year_position=getYear.get(0).getID();
                year_name=getYear.get(0).getDescription().toString();

                genreList_adapter.clickYearposition(position);
                genreList_adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(context, ""+type.length(), Toast.LENGTH_SHORT).show();
            }
        }
//     appSession.setYearID(getYear.get(position).getDescription());
////                appSession.setYearIDposition(getYear.get(position).getID());
    };
}
