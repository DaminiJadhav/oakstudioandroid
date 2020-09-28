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
import com.sdaemon.oakstudiotv.adapter.StudioList_Adapter;
import com.sdaemon.oakstudiotv.model.GetStudioResp;
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

public class StudioActivity extends AppCompatActivity {
    private ListView studioListView;
    private StudioList_Adapter genreList_adapter;
    private ImageView iv_tabback;
    private Retrofit retrofit;
    private Context context;
    private ProgressDialog progressDialog;
    public static int studio_position;
    public static  String studio_name;
    Boolean studio=false;
    private ArrayList<GetStudioResp> getstudio = new ArrayList<>();
    private String studioName;
    AppSession appSession;
    ImageView ivoffline;
    TextView tvoffline;
    Utilities utilities;
    SwipeRefreshLayout refreshLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studio);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            if (bundle.containsKey("KEY_STUDIO"))
                studioName = bundle.getString("KEY_STUDIO");

        }
        context = this;
        appSession = AppSession.getInstance(context);
        initialize();

    }

    private void initialize() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);

        context = StudioActivity.this;
        retrofit = RetrofitUtils.getRetrofitWithoutHeader();
        studioListView = (ListView) findViewById(R.id.studioListView);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);

        Log.i("Studio Movie Type",""+appSession.getMovieType());


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
        progressBar=findViewById(R.id.progressbar_studio);


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
        progressBar.setVisibility(View.VISIBLE);

//        showProgress();
        //Creating Rest Services
        RestCallInterface restInterface = retrofit.create(RestCallInterface.class);
        //Calling method to get whether report
        Call<ArrayList<GetStudioResp>> call = restInterface.GetStudioList();
        call.enqueue(new Callback<ArrayList<GetStudioResp>>() {
            @Override
            public void onResponse(Call<ArrayList<GetStudioResp>> call, Response<ArrayList<GetStudioResp>> response) {
//                hideProgress();
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    ivoffline.setVisibility(View.GONE);
                    tvoffline.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);

                    genreList_adapter = new StudioList_Adapter(StudioActivity.this, response.body(),onClickCallback,studioName);
                    getstudio.addAll(response.body());
                    studioListView.setAdapter(genreList_adapter);
                   ;


                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetStudioResp>> call, Throwable t) {
//                hideProgress();
                progressBar.setVisibility(View.GONE);

                refreshLayout.setRefreshing(false);

                t.printStackTrace();
                Toast.makeText(context, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(studio==false)
        {
            appSession.setStudioID("");
            appSession.setStudioIDposition(0);

            appSession.setTVshowStudioID("");
            appSession.setTVStudioIDposition(0);


        }
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
//        if(studio==false)
//        {
//            appSession.setStudioID("");
//            appSession.setStudioIDposition(0);
//        }
        super.onPause();
    }

    @Override
    protected void onStop() {
//        if(studio==false)
//        {
//            appSession.setStudioID("");
//            appSession.setStudioIDposition(0);
//        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        if(studio==false)
//        {
//            appSession.setStudioID("");
//            appSession.setStudioIDposition(0);
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
                intent.putExtra("KEY_STUDIO", studioName);
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
                Toast.makeText(context,""+ getstudio.get(position).getDescription().toString(),Toast.LENGTH_LONG).show();
                studioName =  getstudio.get(position).getDescription().toString();

                if (appSession.getMovieType()==true) {
                    appSession.setStudioID(getstudio.get(position).getDescription().toString());
                    appSession.setStudioIDposition(getstudio.get(position).getID());
                    Log.i("Studio ID", "" + getstudio.get(position).getID()+ ""+appSession.getMovieType());
                }else {
                    appSession.setTVshowStudioID(getstudio.get(position).getDescription().toString());
                    appSession.setTVStudioIDposition(getstudio.get(position).getID());
                    Log.i("Genre ID", "" + getstudio.get(position).getID()+ " "+appSession.getMovieType());
                }

                studio_position=getstudio.get(0).getID();
                studio_name=getstudio.get(0).getDescription().toString();
                genreList_adapter.clickstudioposition(position);
                genreList_adapter.notifyDataSetChanged();
            }
        }
    };
}
