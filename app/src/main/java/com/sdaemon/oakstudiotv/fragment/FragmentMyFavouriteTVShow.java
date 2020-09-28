package com.sdaemon.oakstudiotv.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.MyFavouriteTVShowAdapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.WishListDTO;
import com.sdaemon.oakstudiotv.model.Table;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMyFavouriteTVShow extends Fragment  {
    RecyclerView recyclerView;
    MyFavouriteTVShowAdapter myFavouriteTVShowAdapter;
    StaggeredGridLayoutManager mLayoutManager;
    Context context;
    AppSession appSession;
    int userid;
    ProgressBar progressBar;
    LinearLayout lloffline;
    ImageView ivoffline;
    TextView tvoffline;
    Utilities utilities;
    List<Table> table;
    SwipeRefreshLayout swipeRefreshLayout;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_tv_shows,container,false);
        recyclerView=view.findViewById(R.id.rv_my_tv_show);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_favouritservice);

//        progressBar.setVisibility(View.VISIBLE);

//        swipeRefreshLayout=view.findViewById(R.id.refresh_watchmovie);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                dorefresh();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        table=new ArrayList<>();

        context=this.getContext();
        try {
            appSession = AppSession.getInstance(context);
            userid = appSession.getUserDTO().getResult().getCustomerId();
        }catch (Exception e){
            e.printStackTrace();
        }


//        lloffline=view.findViewById(R.id.ll_offline);
        ivoffline=view.findViewById(R.id.iv_offline);
        tvoffline=view.findViewById(R.id.tv_offline);

        utilities=Utilities.getInstance(context);
        if (!utilities.isNetworkAvailable()){
//            lloffline.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

            ivoffline.setVisibility(View.VISIBLE);
            tvoffline.setVisibility(View.VISIBLE);
            tvoffline.setText(getResources().getString(R.string.you_re_offline));
            ivoffline.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        }else {
//            lloffline.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            ivoffline.setVisibility(View.GONE);
            tvoffline.setVisibility(View.GONE);
            if (table!=null & table.size()>0){
                Log.i("Favourite Movie","Not null");

            }else {
                getMyTVShows();
                Log.i("Favourite Movie","Null");

            }
        }

        return view;
    }

    public void getMyTVShows(){
        Call<WishListDTO> call= RetroClient.sdaemonApi().getFavouriteListData(userid,3);
        Log.i(getClass().getName(),"==="+call.request().url());
        call.enqueue(new Callback<WishListDTO>() {
            @Override
            public void onResponse(Call<WishListDTO> call, Response<WishListDTO> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);

//                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    WishListDTO wishListDTO=response.body();
                    table=wishListDTO.getContentDetail().getTable();
                    Log.i("Favourite MyTVShow",""+table.get(1).getContentTitle());
                    myFavouriteTVShowAdapter=new MyFavouriteTVShowAdapter(context,table);
                    recyclerView.setAdapter(myFavouriteTVShowAdapter);
                }else {
//                    progressBar.setVisibility(View.GONE);
//                    ivoffline.setVisibility(View.VISIBLE);
//                    tvoffline.setVisibility(View.VISIBLE);
//                    tvoffline.setText(context.getResources().getString(R.string.favourite_tvseries));
//                    ivoffline.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                }
            }

            @Override
            public void onFailure(Call<WishListDTO> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, "Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.i("Favourite MyTVShow",""+t.getMessage());

            }
        });

    }

    public void dorefresh(){
        progressBar.setVisibility(View.VISIBLE);

        table.clear();
        getMyTVShows();

    }

//    @Override
//    public void onDestroy() {
//        if (table!=null){
//            table.clear();
//        }
//        super.onDestroy();
//    }
}
