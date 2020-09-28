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
import com.sdaemon.oakstudiotv.adapter.GenreList_Adapter;
import com.sdaemon.oakstudiotv.model.GetGenreResp;
import com.sdaemon.oakstudiotv.retrofit_utils.RetrofitUtils;
import com.sdaemon.oakstudiotv.retrofit_utils.restUtils.RestCallInterface;
import com.sdaemon.oakstudiotv.utils.AppConstants;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.DialogUtils;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;
import com.sdaemon.oakstudiotv.utils.Utilities;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GenreActivity extends AppCompatActivity implements AppConstants {

    private ListView simpleList;
    private GenreList_Adapter genreList_adapter;
    private ImageView iv_tabback;
    private Retrofit retrofit;
    boolean gentre=false;
    private Context context;
   public static int gentre_position;
   public static  String gentre_name;
    AppSession appSession;
    ImageView ivoffline;
    TextView tvoffline;
    Utilities utilities;
    SwipeRefreshLayout refreshLayout;

    private ProgressDialog progressDialog;
    ProgressBar progressBar;
    private ArrayList<GetGenreResp> getGenre = new ArrayList<>();
    private String strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            if (bundle.containsKey(PN_NAME))
                strName = bundle.getString(PN_NAME);

            }
        context = this;
        appSession = AppSession.getInstance(context);
        initialize();

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

    private void initialize() {

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout_with_back);

        context = GenreActivity.this;
        retrofit = RetrofitUtils.getRetrofitWithoutHeader();
        simpleList = (ListView) findViewById(R.id.simpleListView);
        iv_tabback = (ImageView) findViewById(R.id.iv_tabback);

        ivoffline=findViewById(R.id.iv_offline);
        tvoffline=findViewById(R.id.tv_offline);
        utilities=Utilities.getInstance(context);
        refreshLayout=findViewById(R.id.refreshFilter);
        progressBar=findViewById(R.id.progressbar_genre);


//        refreshLayout.setOnRefreshListener(this::getGenre_Webservice);


        iv_tabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getGenre_Webservice() {

//        showProgress();
        progressBar.setVisibility(View.VISIBLE);
        //Creating Rest Services
        RestCallInterface restInterface = retrofit.create(RestCallInterface.class);
        //Calling method to get whether report
        Call<ArrayList<GetGenreResp>> call = restInterface.getGenresList();
        call.enqueue(new Callback<ArrayList<GetGenreResp>>() {
            @Override
            public void onResponse(Call<ArrayList<GetGenreResp>> call, Response<ArrayList<GetGenreResp>> response) {
//                hideProgress();
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);

                    ivoffline.setVisibility(View.GONE);
                    tvoffline.setVisibility(View.GONE);

                    refreshLayout.setRefreshing(false);

                    genreList_adapter = new GenreList_Adapter(GenreActivity.this, response.body(),onClickCallback,strName);
                    getGenre.addAll(response.body());
                    simpleList.setAdapter(genreList_adapter);


                }
            }
            @Override
            public void onFailure(Call<ArrayList<GetGenreResp>> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);

                hideProgress();
                t.printStackTrace();
                Toast.makeText(context, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(gentre==false)
        {
            appSession.setGenreID("");
            appSession.setGenreIDposition(0);

            appSession.setTVshowGenreID("");
            appSession.setTVGenreIDposition(0);


        }
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
//        if(gentre==false)
//        {
//            appSession.setGenreID("");
//            appSession.setGenreIDposition(0);
//        }
        super.onPause();
    }

    @Override
    protected void onStop() {
//        if(gentre==false)
//        {
//            appSession.setGenreID("");
//            appSession.setGenreIDposition(0);
//        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        if(gentre==false)
//        {
//            appSession.setGenreID("");
//            appSession.setGenreIDposition(0);
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
                intent.putExtra(PN_NAME, strName);
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
                Toast.makeText(context,""+ getGenre.get(position).getDescription().toString(),Toast.LENGTH_LONG).show();
                strName =  getGenre.get(position).getDescription().toString();

                if (appSession.getMovieType()==true) {
                    appSession.setGenreID(getGenre.get(position).getDescription().toString());
                    appSession.setGenreIDposition(getGenre.get(position).getID());
                    Log.i("Genre ID", "" + getGenre.get(position).getID()+  " "+appSession.getMovieType());
                }else {
                    appSession.setTVshowGenreID(getGenre.get(position).getDescription().toString());
                    appSession.setTVGenreIDposition(getGenre.get(position).getID());
                    Log.i("Genre ID", "" + getGenre.get(position).getID()+ " "+appSession.getMovieType());
                }
//
                gentre_position=getGenre.get(0).getID();
                gentre_name=getGenre.get(0).getDescription().toString();
                genreList_adapter.clickPosition(position);
                genreList_adapter.notifyDataSetChanged();
                gentre=true;








// set MyFragment Arguments
                //  context.startActivity(new Intent(context, MovieDetailsActivity.class));
               /* Intent intent=new Intent(GenreActivity.this,FilterActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("genre",getGenre.get(position).getDescription().toString());
                intent.putExtras(bundle);
             startActivity(intent);
                finish();*/
            }
            else {

            }
        }
    };

}
