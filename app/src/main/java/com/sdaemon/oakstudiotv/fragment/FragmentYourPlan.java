package com.sdaemon.oakstudiotv.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.SelectYour_PlanActivity;
import com.sdaemon.oakstudiotv.activity.Select_PriceActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentYourPlan extends Fragment implements View.OnClickListener {
private LinearLayout ll_yourPlan,ll_monthlyPrice;
private View rootView;
private Context context;

    public FragmentYourPlan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_fragment_your_plan, container, false);

      //  initialize();
        return  rootView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        initialize();

    }

    private void initialize() {
        ll_yourPlan=(LinearLayout)rootView.findViewById(R.id.ll_yourPlan);
        ll_monthlyPrice=(LinearLayout)rootView.findViewById(R.id.ll_monthlyPrice);

        ll_yourPlan.setOnClickListener(this);
        ll_monthlyPrice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
         {
            case R.id.ll_yourPlan:
                Intent intent=new Intent(getActivity(),SelectYour_PlanActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_monthlyPrice:
                Intent intent1=new Intent(getActivity(),Select_PriceActivity.class);
                startActivity(intent1);
                break;
        }
    }

}
