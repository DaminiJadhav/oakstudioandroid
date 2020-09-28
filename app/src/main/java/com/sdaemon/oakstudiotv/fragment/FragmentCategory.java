package com.sdaemon.oakstudiotv.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.ButtonActivity;
import com.sdaemon.oakstudiotv.adapter.MovieAdapter;
import com.sdaemon.oakstudiotv.model.RetroPhoto;
import com.sdaemon.oakstudiotv.retrofit_utils.RetrofitUtils;
import com.sdaemon.oakstudiotv.retrofit_utils.restUtils.RestCallInterface;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCategory extends Fragment {

    StaggeredGridLayoutManager mLayoutManager;
    private View rootView;
    private MovieAdapter categoryAdapter;
    private Retrofit retrofit;
    private Context context;
    int position;
    private ProgressDialog progressDialog;
    private RecyclerView rv;
    private ArrayList<RetroPhoto> trailersByCategory = new ArrayList<>();


    private List<Object> mRecyclerViewItems = new ArrayList<>();


    public FragmentCategory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fragment_category, container, false);
        Intent intent=getActivity().getIntent();
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

        retrofit = RetrofitUtils.getRetrofitWithoutHeader();
        RestCallInterface restInterface = retrofit.create(RestCallInterface.class);
        //Calling method to get whether report
      //  Call<ArrayList<RetroPhoto>> call = restInterface.getAllPhotos();

      //  GenresID=1&Year=0&ReviewsId=0&StudioId=0&FeaturesId=0&MPAARatingId=0&ContentTypeID=0&CategoryId=0"

       Call<ArrayList<RetroPhoto>> call = restInterface.getAllPhotos("0","0","0","0","0","0","0","0");
        //Call<ArrayList<RetroPhoto>> call = restInterface.getAllPhotos("1","1","1","1","1","1","1","1");
        call.enqueue(new Callback<ArrayList<RetroPhoto>>() {
            @Override
            public void onResponse(Call<ArrayList<RetroPhoto>> call, Response<ArrayList<RetroPhoto>> response) {
                if (response.isSuccessful()) {
                    Log.i(getClass().getName(),"=========RESPONSE: "+response.body());
                    categoryAdapter =new MovieAdapter(getActivity(), response.body(),onClickCallback);
                    rv.setAdapter(categoryAdapter);

                }
            }
            @Override
            public void onFailure(Call<ArrayList<RetroPhoto>> call, Throwable t) {

                t.printStackTrace();
                Toast.makeText(context, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    }


