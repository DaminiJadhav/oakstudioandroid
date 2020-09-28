package com.sdaemon.oakstudiotv.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.RecentlyAddedAdapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.RecentelyAddedDTO;
import com.sdaemon.oakstudiotv.model.RecentlyAdded;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.Utilities;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRecentlyAdded extends Fragment {
RecentlyAddedAdapter recentlyAddedAdapter;
StaggeredGridLayoutManager mLayoutManager;
RecyclerView recyclerView;
AppSession appSession;
Context context;
int userid;
String uniqueid;
ProgressBar progressBar;
GifImageView gifImageView;
Utilities utilities;
LinearLayout lloffline;
    ImageView ivoffline;
    TextView tvoffline;
    SwipeRefreshLayout RefreshLayout;
    List<RecentlyAdded> recentlyAdded;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_recently_added,container,false);

         context=this.getContext();
            try {
                appSession = AppSession.getInstance(context);
                userid = appSession.getUserDTO().getResult().getCustomerId();
                uniqueid = appSession.getUserDTO().getResult().getUniqueIdentifire();
            }catch (Exception e){
                e.printStackTrace();
            }

        setData();

        return view;
    }




    private void setData() {
        recyclerView=(RecyclerView) view.findViewById(R.id.rv_recently_add);
        progressBar = (ProgressBar) view.findViewById(R.id.progres_recentlyadd);

//        RefreshLayout=view.findViewById(R.id.refresh_recentlyadded);
//
//        RefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                dorefresh();
//                RefreshLayout.setRefreshing(false);
//            }
//        });
//        gifImageView=(GifImageView) view.findViewById(R.id.loading_gifimageview);


//        progressBar.setVisibility(View.VISIBLE);
//        gifImageView.setVisibility(View.VISIBLE);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        ivoffline=view.findViewById(R.id.iv_offline);
        tvoffline=view.findViewById(R.id.tv_offline);

        utilities=Utilities.getInstance(context);
        if (!utilities.isNetworkAvailable()){
            progressBar.setVisibility(View.GONE);

//            lloffline.setVisibility(View.VISIBLE);
            ivoffline.setVisibility(View.VISIBLE);
            tvoffline.setVisibility(View.VISIBLE);
            tvoffline.setText(getResources().getString(R.string.you_re_offline));
            ivoffline.setImageResource(R.drawable.ic_cloud_off_black_24dp);

        }else {
//            lloffline.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            ivoffline.setVisibility(View.GONE);
            tvoffline.setVisibility(View.GONE);
//            gifImageView.setVisibility(View.VISIBLE);
                recentlyAdded();
        }


    }

    public void recentlyAdded(){

        Call<RecentelyAddedDTO> call= RetroClient.sdaemonApi().getRecentlyAddedContent(userid,uniqueid);
        call.enqueue(new Callback<RecentelyAddedDTO>() {
            @Override
            public void onResponse(Call<RecentelyAddedDTO> call, Response<RecentelyAddedDTO> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
//                    RefreshLayout.setRefreshing(false);

//                    gifImageView.setVisibility(View.GONE);
                    RecentelyAddedDTO recentelyAddedDTO=response.body();
                 recentlyAdded=recentelyAddedDTO.getContentDetail().getRecentlyAdded();
                    for (int i=0;i<recentlyAdded.size();i++) {
                        int contentId = recentlyAdded.get(i).getContentID();
//                        Log.i("Recently Added", ""+contentId);
                    }
                    recentlyAddedAdapter=new RecentlyAddedAdapter(getActivity(),recentlyAdded);
                    recyclerView.setAdapter(recentlyAddedAdapter);
//                    Toast.makeText(context, "Success "+response.body().getMessage(),Toast.LENGTH_SHORT).show();


                }else {
                    progressBar.setVisibility(View.GONE);
                    ivoffline.setVisibility(View.VISIBLE);
                    tvoffline.setVisibility(View.VISIBLE);
                    tvoffline.setText(context.getResources().getString(R.string.no_recently_added));
                    ivoffline.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                }
            }

            @Override
            public void onFailure(Call<RecentelyAddedDTO> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
//                RefreshLayout.setRefreshing(false);
//                gifImageView.setVisibility(View.GONE);

            }
        });

    }
    public void dorefresh(){
        progressBar.setVisibility(View.VISIBLE);

        recentlyAdded.clear();
        recentlyAdded();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
     /*   if (recentlyAdded!=null) {
            recentlyAdded.clear();
        }*/
    }
}
