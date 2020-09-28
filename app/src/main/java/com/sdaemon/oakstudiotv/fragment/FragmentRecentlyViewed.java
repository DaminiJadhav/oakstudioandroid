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
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.RecentlyViewed_list_Adapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.DeleteContentViewHistoryDTO;
import com.sdaemon.oakstudiotv.dto.RecentlyWatchDTO;
import com.sdaemon.oakstudiotv.model.RecentlyWatched;
import com.sdaemon.oakstudiotv.model.Recetly_ViewedDetails;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;
import com.sdaemon.oakstudiotv.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRecentlyViewed extends Fragment {
    private View rootView;
    TextView tvnoviewvideo;
    private RecentlyViewed_list_Adapter recentlyViewed_list_adapter;
    private LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager mLayoutManager;
    Context context;
    private RecyclerView rv;
    private ArrayList<Recetly_ViewedDetails> recetly_viewedDetails = new ArrayList<>();
    AppSession appSession;
    int userid;
    String uniqueid;
    ProgressBar progressBar;
    GifImageView gifImageView;
    LinearLayout lloffline;
    ImageView ivoffline;
    TextView tvoffline;
    Utilities utilities;
    SwipeRefreshLayout RefreshLayout;
    int contentId;
    List<RecentlyWatched> recentlyWatcheds=new ArrayList<>();


    public FragmentRecentlyViewed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_recently_viewed, container, false);
        context=this.getContext();
        try {
            appSession = AppSession.getInstance(context);
            userid = appSession.getUserDTO().getResult().getCustomerId();
            uniqueid = appSession.getUserDTO().getResult().getUniqueIdentifire();
        }catch (Exception e){
            e.printStackTrace();
        }

        setData();
        return rootView;
    }

    private void setData() {

        rv = (RecyclerView) rootView.findViewById(R.id.recycler_recentlyViewed);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progres_recentlyview);
//        RefreshLayout=rootView.findViewById(R.id.refresh_recentlyview);
//
//        RefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                dorefresh();
//                RefreshLayout.setRefreshing(false);
//            }
//        });
//        RefreshLayout.setRefreshing(true);



//        gifImageView = (GifImageView) rootView.findViewById(R.id.loading_gifimageview);
        ivoffline=rootView.findViewById(R.id.iv_offline);
        tvoffline=rootView.findViewById(R.id.tv_offline);


        progressBar.setVisibility(View.VISIBLE);


        utilities=Utilities.getInstance(context);





//        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        rv.setLayoutManager(linearLayoutManager);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());


        if (!utilities.isNetworkAvailable()){
//            lloffline.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            ivoffline.setVisibility(View.VISIBLE);
            tvoffline.setVisibility(View.VISIBLE);
            tvoffline.setText(getResources().getString(R.string.you_re_offline));
            ivoffline.setImageResource(R.drawable.ic_cloud_off_black_24dp);

        }else {
//            lloffline.setVisibility(View.GONE);
            ivoffline.setVisibility(View.GONE);
            tvoffline.setVisibility(View.GONE);

//            gifImageView.setVisibility(View.VISIBLE);
            getRecentlyView();
        }


        recetly_viewedDetails.add(new Recetly_ViewedDetails(R.drawable.view,"EXPANDABLES 2","(2016)",3,123));
        recetly_viewedDetails.add(new Recetly_ViewedDetails(R.drawable.view4,"MISSION IMPOSSIBLE","(2016)",4,1021));
        recetly_viewedDetails.add(new Recetly_ViewedDetails(R.drawable.view3,"BLACK PANTHER","(2016)",5,500));
        recetly_viewedDetails.add(new Recetly_ViewedDetails(R.drawable.view2,"JUSTICE LEAGUE","(2016)",3,123));
        recetly_viewedDetails.add(new Recetly_ViewedDetails(R.drawable.view1,"ANT-MAN","(2016)",5,854));
//        recentlyViewed_list_adapter = new RecentlyViewed_list_Adapter(getActivity(),recetly_viewedDetails);
//        rv.setAdapter(recentlyViewed_list_adapter);
    }

    public void getRecentlyView(){
        Log.i("Recentely View"," "+userid+"  "+uniqueid);
        Call<RecentlyWatchDTO> call= RetroClient.sdaemonApi().getRecentlyWatchConetnt(userid,uniqueid);

//        Call<RecentlyWatchDTO> call= RetroClient.sdaemonApi().getRecentlyWatchConetnt(8707,"35971c47-d1ab-45f9-b9a9-b96c4a8535c4");
        call.enqueue(new Callback<RecentlyWatchDTO>() {
            @Override
            public void onResponse(Call<RecentlyWatchDTO> call, Response<RecentlyWatchDTO> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    ivoffline.setVisibility(View.GONE);
                    tvoffline.setVisibility(View.GONE);
//                    RefreshLayout.setRefreshing(false);

//                    gifImageView.setVisibility(View.GONE);

//                    Toast.makeText(context, "Success ", Toast.LENGTH_SHORT).show();


                   recentlyWatcheds=response.body().getContentDetail().getRecentlyWatcheds();
                   List<RecentlyWatched> watched = fetchResults(recentlyWatcheds);

                    Log.i("Recently List",""+watched.size());



                    for (int i=0;i<recentlyWatcheds.size();i++) {
                       contentId = recentlyWatcheds.get(i).getContentId();
                        Log.i("Recently View", ""+contentId);
                    }
                    recentlyViewed_list_adapter = new RecentlyViewed_list_Adapter(getActivity(),recentlyWatcheds);
                    rv.setAdapter(recentlyViewed_list_adapter);



                }else {
                        progressBar.setVisibility(View.GONE);
                        ivoffline.setVisibility(View.VISIBLE);
                        tvoffline.setVisibility(View.VISIBLE);
                        tvoffline.setText(context.getResources().getString(R.string.no_recently_view));
                        ivoffline.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    }


            }

            @Override
            public void onFailure(Call<RecentlyWatchDTO> call, Throwable t) {
                Toast.makeText(context, "Error ", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
//                RefreshLayout.setRefreshing(false);

//                gifImageView.setVisibility(View.GONE);

            }
        });
    }


    public void DeleteContentViewHistory(int position){
//        AppSession appSession=AppSession.getInstance(mContext);
//        int userid=appSession.getUserDTO().getResult().getCustomerId();
        Log.i("Delete History","  "+userid+"   "+contentId);
        Call<DeleteContentViewHistoryDTO> call= RetroClient.sdaemonApi().deleteContentViewHistory(userid,contentId);
        call.enqueue(new Callback<DeleteContentViewHistoryDTO>() {
            @Override
            public void onResponse(Call<DeleteContentViewHistoryDTO> call, Response<DeleteContentViewHistoryDTO> response) {
//                recentlyWatcheds.remove(pos);
//                notifyDataSetChanged();


//                recentlyViewed_list_adapter.notifyItemRemoved(position);

//                recentlyWatcheds.remove(position);
                recentlyViewed_list_adapter.notifyDataSetChanged();
                recentlyViewed_list_adapter.dialogdismiss();


//                recentlyWatcheds.clear();
//                recentlyWatcheds.addAll(recentlyWatcheds);
//                recentlyViewed_list_adapter.notifyDataSetChanged();

//                Toast.makeText(context, ""+response.message(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<DeleteContentViewHistoryDTO> call, Throwable t) {
//                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    final OnItemClickListner.OnClickCallback onClickCallback=new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if (type.equalsIgnoreCase("DeleteRecentlyView")){
//                DeleteContentViewHistory(position);
//                recentlyWatcheds.clear();
//                recentlyViewed_list_adapter.notifyDataSetChanged();

//                List<RecentlyWatched> list=fetchResults(recentlyWatcheds);
//                Log.i("Recently List 1",""+list.size());



            }
        }
    };


    public void dorefresh(){
        progressBar.setVisibility(View.VISIBLE);

        recentlyWatcheds.clear();
        getRecentlyView();

    }


    private List<RecentlyWatched> fetchResults(List<RecentlyWatched> response) {
        Log.i("Recently watch response",""+response.get(0).getSquare_Image());
        return response;
//        RecentlyWatched getlist = response.body();
//        ContentDTO getlist = response.body();
//        return getlist.getContentDetail().getContentInfos();
    }

}
