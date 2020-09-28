package com.sdaemon.oakstudiotv.fragment;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.adapter.ActorList_Adapter;
import com.sdaemon.oakstudiotv.adapter.CreatorList_Adapter;
import com.sdaemon.oakstudiotv.model.ActorList;
import com.sdaemon.oakstudiotv.model.CastAndCrewList;
import com.sdaemon.oakstudiotv.model.CategoiesList;
import com.sdaemon.oakstudiotv.model.CreatorsList;
import com.sdaemon.oakstudiotv.utils.NonScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCreators_Cast extends Fragment {
private View rootView;
    private NonScrollListView lv_creators,lv_actors;
    private ArrayList<CreatorsList> creatorsListArrayList=new ArrayList<>();
    private ArrayList<ActorList> actorListArrayList=new ArrayList<>();
    private List<CategoiesList> list;
    private List<CastAndCrewList> castlist;

    public  CreatorList_Adapter creatorsListAdapter;
    public ActorList_Adapter actorList_adapter;

    public FragmentCreators_Cast() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragmentCreators_Cast(List<CastAndCrewList> list) {
        this.castlist=list;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_fragment_creators__cast, container, false);


        initialize();
     return rootView;
    }

    private void initialize() {

        lv_creators = (NonScrollListView) rootView.findViewById(R.id.lv_creators);
        lv_actors = (NonScrollListView) rootView.findViewById(R.id.lv_actors);

//          list.get(0).getCategoryDescription();

        creatorsListArrayList.add(new CreatorsList(R.drawable.view,"Bill","1988 in America"));
        creatorsListArrayList.add(new CreatorsList(R.drawable.view,"Mark","1985 in Newzealand"));

        actorListArrayList.add(new ActorList(R.drawable.view2,"Mark","1980 in China"));
        actorListArrayList.add(new ActorList(R.drawable.view2,"Mark","1991 in Newzealand"));
        actorListArrayList.add(new ActorList(R.drawable.view3,"Mark","1991 in Newzealand"));
        actorListArrayList.add(new ActorList(R.drawable.view4,"Mark","1991 in Newzealand"));


//        creatorsListAdapter=new CreatorList_Adapter(getActivity(),list);
//        creatorsListAdapter = new CreatorList_Adapter(getActivity(), creatorsListArrayList);
//        lv_creators.setAdapter(creatorsListAdapter);

        actorList_adapter = new ActorList_Adapter(getActivity(), castlist);
        lv_actors.setAdapter(actorList_adapter);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getContext(), "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(getContext(), "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
