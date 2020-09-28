package com.sdaemon.oakstudiotv.fragment;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sdaemon.oakstudiotv.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPlanCompare extends Fragment implements View.OnClickListener {
    private LinearLayout ll_yourPlan;
    private View rootView;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.plan_compare, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        initialize();

    }


    private void initialize() {
//        ll_yourPlan = (LinearLayout) rootView.findViewById(R.id.ll_yourPlan);
//        ll_yourPlan.setOnClickListener(this);
//





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ll_yourPlan:
//                Intent intent = new Intent(getActivity(), SelectYour_PlanActivity.class);
//                startActivity(intent);
//                break;


        }
    }




}
