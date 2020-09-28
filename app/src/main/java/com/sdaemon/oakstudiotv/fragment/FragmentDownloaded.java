package com.sdaemon.oakstudiotv.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.DownloadedMovieAdapter;
import com.sdaemon.oakstudiotv.model.DownloadedMovieDetails;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDownloaded extends Fragment {
    StaggeredGridLayoutManager mLayoutManager;
    private View rootView;
    private DownloadedMovieAdapter categoryAdapter;
    private RecyclerView rv;
    private ArrayList<DownloadedMovieDetails> trailersByCategory = new ArrayList<>();

    public FragmentDownloaded() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_fragment_downloaded, container, false);
      
        setData();
        return  rootView;
    }
    private void setData() {
        rv = (RecyclerView) rootView.findViewById(R.id.rv_downloadedList);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        trailersByCategory.add(new DownloadedMovieDetails(R.drawable.view, "EXPANDABLE 2", "(2016)", 3, 123,"Uploaded 15 sep","5 Days left"));
        trailersByCategory.add(new DownloadedMovieDetails(R.drawable.view1, "ANT-MAN", "(2016)", 4, 1021,"Uploaded 1 sep","15 Days left"));
        trailersByCategory.add(new DownloadedMovieDetails(R.drawable.view2, "JUSTICE LEAGUE", "(2016)", 5, 500,"Uploaded 20 oct","8 Days left"));
        trailersByCategory.add(new DownloadedMovieDetails(R.drawable.view3, "BLACK PANTHER", "(2016)", 3, 123,"Uploaded 29 nov","1 Days left"));
        trailersByCategory.add(new DownloadedMovieDetails(R.drawable.view4, "MISSION IMPOSSIBLE", "(2016)", 5, 854,"Uploaded 12 aug","20 Days left"));
        categoryAdapter = new DownloadedMovieAdapter(getActivity(), trailersByCategory);
        rv.setAdapter(categoryAdapter);
    }

}
