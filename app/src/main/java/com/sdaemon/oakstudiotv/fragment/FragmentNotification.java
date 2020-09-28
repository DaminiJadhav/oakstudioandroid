package com.sdaemon.oakstudiotv.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.NotificationAdapter;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.NotificationDTO;
import com.sdaemon.oakstudiotv.model.Notification_details;
import com.sdaemon.oakstudiotv.utils.AppSession;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNotification extends Fragment {
    private static final int PAGE_START = 0;
    StaggeredGridLayoutManager mLayoutManager;
    private String categoryID;
    private View rootView;
    private NotificationAdapter categoryAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rv;
    private ProgressDialog progressDialog;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;
    // private Retrofit retrofit;
    private ArrayList<Notification_details> trailersByCategory = new ArrayList<>();
    private List<NotificationDTO> trailersByCategory1 = new ArrayList<>();

    private List<Object> mRecyclerViewItems = new ArrayList<>();

    AppSession appSession;
    String uniqueId;

    public FragmentNotification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_category, container, false);

        setData();
        return rootView;
    }

    private void setData() {

        rv = (RecyclerView) rootView.findViewById(R.id.recycler_categoryTrailers);

//        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        rv.setLayoutManager(linearLayoutManager);

        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());


        appSession=AppSession.getInstance(getContext());
        uniqueId=appSession.getUserDTO().getResult().getUniqueIdentifire();



//        trailersByCategory.add(new Notification_details("LOREM IPSUM", "No one should have to worry about their messages being intercepted and read by the government, even if that message is “lo.” That’s why we joined @ACLU in challenging the NSA’s upstream surveillance practices "));
//        trailersByCategory.add(new Notification_details("LOREM IPSUM DOLOR SIT AMET", "In October 1963, a black and white Parisian stray cat named Félicette became the first cat launched into space. Her flight lasted fifteen minutes, reaching a height of 156 kilometres. The cat was recovered safely after the capsule parachuted to Earth. "));
//        trailersByCategory.add(new Notification_details("LOREM", " We believe that everyone, everywhere should have access to knowledge, which is why we are proud to announce on this #InternetDay that we’ve joined @theGNI as an observer:"));
//        trailersByCategory.add(new Notification_details("LOREM IPSUM DOLOR ", "In October 1963, a black and white Parisian stray cat named Félicette became the first cat launched into space. Her flight lasted fifteen minutes, reaching a height of 156 kilometres. The cat was recovered safely after the capsule parachuted to Earth. "));
//        trailersByCategory.add(new Notification_details("SIT AMET", "We believe that everyone, everywhere should have access to knowledge, which is why we are proud to announce on this #InternetDay that we’ve joined @theGNI as an observer:"));
//        categoryAdapter = new NotificationAdapter(getActivity(), trailersByCategory);
//        rv.setAdapter(categoryAdapter);


        GetNotification();
    }


    public void GetNotification(){

        Call<List<NotificationDTO>> call= RetroClient.sdaemonApi().getNotification(uniqueId);
        call.enqueue(new Callback<List<NotificationDTO>>() {
            @Override
            public void onResponse(Call<List<NotificationDTO>> call, Response<List<NotificationDTO>> response) {
                if (response.isSuccessful()){
//                    Toast.makeText(getContext(), "success "+response.message(), Toast.LENGTH_SHORT).show();

                    trailersByCategory1=response.body();
                    categoryAdapter = new NotificationAdapter(getActivity(), trailersByCategory1);
                    rv.setAdapter(categoryAdapter);

                }else {
//                    Toast.makeText(getContext(), "unsuccess "+response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<NotificationDTO>> call, Throwable t) {
//                Toast.makeText(getContext(), "error "+t.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });
    }

}
