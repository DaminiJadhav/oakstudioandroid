package com.sdaemon.oakstudiotv.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.sdaemon.oakstudiotv.adapter.FeaturesList_Adapter;
import com.sdaemon.oakstudiotv.model.GetFeaturesResp;
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

public class FeaturesActivity extends AppCompatActivity {

    private ListView featuresListView;
    private FeaturesList_Adapter genreList_adapter;
    private ImageView iv_tabback;
    private Retrofit retrofit;
    private Context context;
    private ProgressDialog progressDialog;
    AppSession appSession;
    Boolean feature=false;
    public static int feature_position;
    public static  String feature_name;
    private ArrayList<GetFeaturesResp> getfeature = new ArrayList<>();
    private String featureName;
    ImageView ivoffline;
    TextView tvoffline;
    Utilities utilities;
    SwipeRefreshLayout refreshLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            if (bundle.containsKey("KEY_FEATURE"))
                featureName = bundle.getString("KEY_FEATURE");

        }
        context = this;
        appSession = AppSession.getInstance(context);
        initialize();

    }

    private void initialize() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);

        context = FeaturesActivity.this;
        retrofit = RetrofitUtils.getRetrofitWithoutHeader();
        featuresListView = (ListView) findViewById(R.id.featuresListView);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);

        Log.i("Feature Movie Type",""+appSession.getMovieType());



        ivoffline=findViewById(R.id.iv_offline);
        tvoffline=findViewById(R.id.tv_offline);
        utilities=Utilities.getInstance(context);
        refreshLayout=findViewById(R.id.refreshFilter);
        progressBar=findViewById(R.id.progressbar_feature);


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
//                onBackPressed();
                finish();
            }
        });

    }

    private void getGenre_Webservice() {
        progressBar.setVisibility(View.VISIBLE);

//        showProgress();
        //Creating Rest Services
        RestCallInterface restInterface = retrofit.create(RestCallInterface.class);
        //Calling method to get whether report
        Call<ArrayList<GetFeaturesResp>> call = restInterface.GetFeaturesList();
        call.enqueue(new Callback<ArrayList<GetFeaturesResp>>() {
            @Override
            public void onResponse(Call<ArrayList<GetFeaturesResp>> call, Response<ArrayList<GetFeaturesResp>> response) {
//                hideProgress();
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    ivoffline.setVisibility(View.GONE);
                    tvoffline.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);

                    genreList_adapter = new FeaturesList_Adapter(FeaturesActivity.this, response.body(),onClickCallback,featureName);
                   getfeature.addAll(response.body());
                    featuresListView.setAdapter(genreList_adapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetFeaturesResp>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

//                hideProgress();
                t.printStackTrace();
                refreshLayout.setRefreshing(false);

                Toast.makeText(context, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(feature==false)
        {
            appSession.setFeatureID("");
            appSession.setFeatureIDposition(0);

            appSession.setTVshowFeatureID("");
            appSession.setTVFeatureIDposition(0);


        }
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
//        if(feature==false)
//        {
//            appSession.setFeatureID("");
//            appSession.setFeatureIDposition(0);
//        }
        super.onPause();
    }

    @Override
    protected void onStop() {
//        if(feature==false)
//        {
//            appSession.setFeatureID("");
//            appSession.setFeatureIDposition(0);
//        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        if(feature==false)
//        {
//            appSession.setFeatureID("");
//            appSession.setFeatureIDposition(0);
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
                intent.putExtra("KEY_FEATURE", featureName);
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
                Toast.makeText(context, "" + getfeature.get(position).getDescription().toString(), Toast.LENGTH_LONG).show();
                featureName=getfeature.get(position).getDescription();

                if (appSession.getMovieType()==true) {
                    appSession.setFeatureID(getfeature.get(position).getDescription().toString());
                    appSession.setFeatureIDposition(getfeature.get(position).getID());
                    Log.i("Feature ID", "" + getfeature.get(position).getID()+ " "+appSession.getMovieType());
                }else {
                    appSession.setTVshowFeatureID(getfeature.get(position).getDescription().toString());
                    appSession.setTVFeatureIDposition(getfeature.get(position).getID());

                    Log.i("Feature ID", "" + getfeature.get(position).getID()+ " "+appSession.getMovieType());

                }

                feature_position=getfeature.get(0).getID();
                feature_name=getfeature.get(0).getDescription().toString();
                genreList_adapter.clickfeatureposition(position);
                genreList_adapter.notifyDataSetChanged();
            } else {
//                Toast.makeText(context, ""+type.length(), Toast.LENGTH_SHORT).show();
            }
        }
//     appSession.setYearID(getYear.get(position).getDescription());
////                appSession.setYearIDposition(getYear.get(position).getID());
    };

}
