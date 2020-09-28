package com.sdaemon.oakstudiotv.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.ButtonActivity;
import com.sdaemon.oakstudiotv.adapter.MovieAdapterSdaemon;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.CategoryDTO;
import com.sdaemon.oakstudiotv.model.RetroPhoto;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategorySdaemonApiOld extends Fragment {

    StaggeredGridLayoutManager mLayoutManager;
    private View rootView;
    private MovieAdapterSdaemon categoryAdapter;
 //   private MovieAdapter categoryAdapter;
    private Retrofit retrofit;
    private Context context;
    int position;
    private ProgressDialog progressDialog;
    private RecyclerView rv;
    private ArrayList<RetroPhoto> trailersByCategory = new ArrayList<>();
   AppSession appSession;



    private List<Object> mRecyclerViewItems = new ArrayList<>();


    public FragmentCategorySdaemonApiOld() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fragment_category, container, false);
        Intent intent=getActivity().getIntent();
        appSession=AppSession.getInstance(context);
        setData();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();

        //Toast.makeText(getActivity(), "resume", Toast.LENGTH_SHORT).show();
    }

    private void setData() {

        rv = (RecyclerView) rootView.findViewById(R.id.recycler_categoryTrailers);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

/*
        trailersByCategory.add(new MovieDetails(R.drawable.view, "EXPANDABLE 2", "(2016)", 3, 123));
        trailersByCategory.add(new MovieDetails(R.drawable.view1, "ANT-MAN", "(2016)", 4, 1021));
        trailersByCategory.add(new MovieDetails(R.drawable.view2, "JUSTICE LEAGUE", "(2016)", 5, 500));
        trailersByCategory.add(new MovieDetails(R.drawable.view3, "BLACK PANTHER", "(2016)", 3, 123));
        trailersByCategory.add(new MovieDetails(R.drawable.view4, "MISSION IMPOSSIBLE", "(2016)", 5, 854));*/

//        getCategory();

}

    OnItemClickListner.OnClickCallback onClickCallback = new OnItemClickListner.OnClickCallback() {
        @Override
        public void onClicked(View view, int position, String type) {
            if(type.equalsIgnoreCase("image")){
               //Toast.makeText(context,"Image Click",Toast.LENGTH_LONG).show();
                /*if (getArguments() != null) {
                    String mParam1 = getArguments().getString("params");
                    Toast.makeText(getActivity(),mParam1,Toast.LENGTH_LONG).show();
                }*/
             //  context.startActivity(new Intent(context, MovieDetailsActivity.class));
               context.startActivity(new Intent(context, ButtonActivity.class));

            }
            else {

            }
        }
    };

    private void getCategory() {
        // progressBar.show();
        Call<ArrayList<CategoryDTO>> call =  RetroClient.sdaemonApi().getCategory("1","5","1","1","1","1","1","1");
       // Call<ArrayList<CategoryDTO>> call =  RetroClient.sdaemonApi().getCategory();

        Log.e(getClass().getName(), "===" + call.request().url());
        call.enqueue(new Callback<ArrayList<CategoryDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryDTO>> call, Response<ArrayList<CategoryDTO>> response) {
               // Toast.makeText(context, "login successfully", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    Log.i(getClass().getName(), "=========RESPONSE: " + response.body());
                    ArrayList<CategoryDTO> list = new ArrayList<>();
                    list.addAll(response.body());
//                    categoryAdapter =new MovieAdapterSdaemon(context, list,onClickCallback);
//                    rv.setAdapter(categoryAdapter);
               }
                else if (response.code() == 409) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.i(getClass().getName(),"=========RESPONSE: 409 ");
                        String msg = jObjError.getString("message");
                        showDialoge(context, "", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.i(getClass().getName(),"=========RESPONSE: else ");
                        String msg = jObjError.getString("message");
                        showDialoge(context, "", msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryDTO>> call, Throwable t) {
                //    progressBar.dismiss();

                Log.i(getClass().getName(),"--------------- onFailure "+ t.getMessage());
                showDialoge(context, "", "" + t.getMessage());
            }
        });
    }

    public void showDialoge(Context context, String title, String msg) {
        try {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context, R.style.dialoge);
            builder.setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })

                    .show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    }


