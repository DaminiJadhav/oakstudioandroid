package com.sdaemon.oakstudiotv.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdaemon.oakstudiotv.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBalance_details extends Fragment {

private View rootview;
    public FragmentBalance_details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_fragment_balance_details, container, false);

        return rootview;
    }

}
