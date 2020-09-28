package com.sdaemon.oakstudiotv.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.MyFavouriteTVShowAdapter;
import com.sdaemon.oakstudiotv.adapter.MyFavourite_Movies_Adapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.WishListDTO;
import com.sdaemon.oakstudiotv.model.MyFavourite_MovieDetails;
import com.sdaemon.oakstudiotv.model.Table;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyFavouriteMovies extends Fragment  {
    private View rootView;
    private MyFavourite_Movies_Adapter myFavouriteMoviesAdapter;
    private MyFavouriteTVShowAdapter myFavouriteTVShowAdapter;
    private LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager mLayoutManager;
    private RecyclerView rv;AppSession appSession;
    int userid;
    private ArrayList<MyFavourite_MovieDetails> myFavourite_movieDetailsArrayList = new ArrayList<>();
    Context context;
    ProgressBar progressBar;
    LinearLayout lloffline;
    ImageView ivoffline;
    TextView tvoffline;
    Utilities utilities;
    List<Table> table;
    SwipeRefreshLayout Refreshlayout;

    public FragmentMyFavouriteMovies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_fragment_my_favourite_movies, container, false);
        context=this.getContext();
//        lloffline=rootView.findViewById(R.id.ll_offline);
        ivoffline=rootView.findViewById(R.id.iv_offline);
        tvoffline=rootView.findViewById(R.id.tv_offline);

        utilities=Utilities.getInstance(context);

        try {
            appSession = AppSession.getInstance(context);
            if (appSession != null) {
                userid = appSession.getUserDTO().getResult().getCustomerId();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       setData();
        return  rootView;
    }

    private void setData() {
        rv = (RecyclerView) rootView.findViewById(R.id.recycler_myFavouriteMovies);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_favouritemovie);

//        Refreshlayout=rootView.findViewById(R.id.refresh_watchmovie);
//        Refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                dorefresh();
//                Refreshlayout.setRefreshing(false);
//            }
//        });
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        table=new ArrayList<>();


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
                getMyMovieList();
                Log.i("Favourite Movie","Null");

            }
        }

//        myFavourite_movieDetailsArrayList.add(new MyFavourite_MovieDetails(R.drawable.view1,"ANT-MAN","(2016)",3,123));
//        myFavourite_movieDetailsArrayList.add(new MyFavourite_MovieDetails(R.drawable.view2,"JUSTICE LEAGUE","(2016)",4,1021));
//        myFavourite_movieDetailsArrayList.add(new MyFavourite_MovieDetails(R.drawable.view3,"BLACK PANTHER","(2016)",5,500));
//        myFavourite_movieDetailsArrayList.add(new MyFavourite_MovieDetails(R.drawable.view4,"MISSION IMPOSSIBLE","(2016)",3,123));
//        myFavourite_movieDetailsArrayList.add(new MyFavourite_MovieDetails(R.drawable.view,"EXPANDABLES 2","(2016)",5,854));
//        myFavourite_movieDetailsArrayList.add(new MyFavourite_MovieDetails(R.drawable.banner,"BANNER ","(2017)",4,654));

        //        myFavouriteMoviesAdapter = new MyFavourite_Movies_Adapter(getActivity(),myFavourite_movieDetailsArrayList);
//        rv.setAdapter(myFavouriteMoviesAdapter);
    }

    public void getMyMovieList(){
//        Call<WishListDTO> call= RetroClient.sdaemonApi().getFavouriteListData(8707,1);

        Call<WishListDTO> call= RetroClient.sdaemonApi().getFavouriteListData(userid,1);
        Log.i(getClass().getName(),"==="+call.request().url());
        call.enqueue(new Callback<WishListDTO>() {
            @Override
            public void onResponse(Call<WishListDTO> call, Response<WishListDTO> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
//                    Refreshlayout.setRefreshing(false);


//                    Toast.makeText(context, "Success Movie ..........", Toast.LENGTH_SHORT).show();

//                    Toast.makeText(context, "Success Movie", Toast.LENGTH_SHORT).show();
                    WishListDTO wishListDTO=response.body();
                    table=wishListDTO.getContentDetail().getTable();
//                    Log.i("Favourite Movie",""+table.get(1).getContentTitle());


                    myFavouriteTVShowAdapter=new MyFavouriteTVShowAdapter(context,table);
                    rv.setAdapter(myFavouriteTVShowAdapter);

                }else {
//                    progressBar.setVisibility(View.GONE);
//                    ivoffline.setVisibility(View.VISIBLE);
//                    tvoffline.setVisibility(View.VISIBLE);
//                    tvoffline.setText(context.getResources().getString(R.string.favourite_movie));
//                    ivoffline.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                }
            }

            @Override
            public void onFailure(Call<WishListDTO> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
//                Refreshlayout.setRefreshing(false);

//                Toast.makeText(context, "Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.i("Favourite Movie err",""+t.getMessage());

            }
        });

    }


    public void dorefresh(){
        progressBar.setVisibility(View.VISIBLE);

        table.clear();
        getMyMovieList();

    }
//    @Override
//    public void onDestroy() {
//        if (table!=null){
//            table.clear();
//        }
//        super.onDestroy();
//    }
}
