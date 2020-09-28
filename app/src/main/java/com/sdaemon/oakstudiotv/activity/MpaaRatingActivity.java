package com.sdaemon.oakstudiotv.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.sdaemon.oakstudiotv.adapter.MpaaRatingList_Adapter;
import com.sdaemon.oakstudiotv.model.GetMpaaRatingResp;
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

public class MpaaRatingActivity extends AppCompatActivity {

    private ListView mpaaRatingListView;
    private MpaaRatingList_Adapter genreList_adapter;
    private ImageView iv_tabback;
    private Retrofit retrofit;
    private Context context;
    private ProgressDialog progressDialog;
    AppSession appSession;
    public static int rating_position;
    public static  String rating_name;
    private ArrayList<GetMpaaRatingResp> getrating = new ArrayList<>();
    boolean rating=false;
    private String ratingName;
    ImageView ivoffline;
    TextView tvoffline;
    Utilities utilities;
    SwipeRefreshLayout refreshLayout;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpaa_rating);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            if (bundle.containsKey("KEY_RATING"))
                ratingName = bundle.getString("KEY_RATING");

        }
        context = this;
        appSession = AppSession.getInstance(context);

        initialize();

    }

    private void initialize() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);

        context = MpaaRatingActivity.this;
        retrofit = RetrofitUtils.getRetrofitWithoutHeader();
        mpaaRatingListView = (ListView) findViewById(R.id.mpaaRatingListView);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);

        Log.i("Rating Movie Type",""+appSession.getMovieType());


        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ivoffline=findViewById(R.id.iv_offline);
        tvoffline=findViewById(R.id.tv_offline);
        utilities=Utilities.getInstance(context);
        refreshLayout=findViewById(R.id.refreshFilter);
        progressBar=findViewById(R.id.progressbar_rating);

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


    }

    private void getGenre_Webservice() {

//        showProgress();
        progressBar.setVisibility(View.VISIBLE);

        //Creating Rest Services
        RestCallInterface restInterface = retrofit.create(RestCallInterface.class);
        //Calling method to get whether report
        Call<ArrayList<GetMpaaRatingResp>> call = restInterface.GetMPAARatingList();
        call.enqueue(new Callback<ArrayList<GetMpaaRatingResp>>() {
            @Override
            public void onResponse(Call<ArrayList<GetMpaaRatingResp>> call, Response<ArrayList<GetMpaaRatingResp>> response) {
//                hideProgress();
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    ivoffline.setVisibility(View.GONE);
                    tvoffline.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);

                    genreList_adapter = new MpaaRatingList_Adapter(MpaaRatingActivity.this, response.body(),onClickCallback,ratingName);
                    getrating.addAll(response.body());
                    mpaaRatingListView.setAdapter(genreList_adapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetMpaaRatingResp>> call, Throwable t) {
//                hideProgress();
                progressBar.setVisibility(View.GONE);

                t.printStackTrace();
                refreshLayout.setRefreshing(false);

                Toast.makeText(context, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(rating==false)
        {
            appSession.setRatingID("");
            appSession.setRatingIDposition(0);

            appSession.setTVshowRatingID("");
            appSession.setTVRatingIDposition(0);


        }
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
//        if(rating==false)
//        {
//            appSession.setRatingID("");
//            appSession.setRatingIDposition(0);
//        }
        super.onPause();
    }

    @Override
    protected void onStop() {
//        if(rating==false)
//        {
//            appSession.setRatingID("");
//            appSession.setRatingIDposition(0);
//        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        if(rating==false)
//        {
//            appSession.setRatingID("");
//            appSession.setRatingIDposition(0);
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
                intent.putExtra("KEY_RATING", ratingName);
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
            if(type.equalsIgnoreCase("")){
                Toast.makeText(context,""+ getrating.get(position).getDescription().toString(),Toast.LENGTH_LONG).show();
                ratingName =  getrating.get(position).getDescription().toString();

                if (appSession.getMovieType()==true) {
                    appSession.setRatingID(getrating.get(position).getDescription().toString());
                    appSession.setRatingIDposition(getrating.get(position).getID());
                    Log.i("Rating ID", "" + getrating.get(position).getID()+ " "+appSession.getMovieType());
                }else {
                    appSession.setTVshowRatingID(getrating.get(position).getDescription().toString());
                    appSession.setTVRatingIDposition(getrating.get(position).getID());

                    Log.i("Rating ID", "" + getrating.get(position).getID()+ " "+appSession.getMovieType());

                }
                rating_position=getrating.get(0).getID();
                rating_name=getrating.get(0).getDescription().toString();
                genreList_adapter.clickratingposition(position);
                genreList_adapter.notifyDataSetChanged();



            }
            else {

            }
        }
    };
}
