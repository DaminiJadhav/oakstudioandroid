package com.sdaemon.oakstudiotv.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.ReviewRatingActivity;
import com.sdaemon.oakstudiotv.adapter.ReviewList_Adapter;
import com.sdaemon.oakstudiotv.model.CategoiesList;
import com.sdaemon.oakstudiotv.model.ReviewList;
import com.sdaemon.oakstudiotv.model.ReviewsList;
import com.sdaemon.oakstudiotv.utils.NonScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserReviews extends Fragment {
private View rootView;
Context context;
    private NonScrollListView lv_review;
    private ArrayList<ReviewList> reviewListArrayList=new ArrayList<>();
    private List<ReviewsList> reviewsLists=new ArrayList<>();
    public ReviewList_Adapter reviewList_adapter;
    private List<CategoiesList> list;
    int contentid,reviewcontentid;
    private TextView review,reviewid,tvaddReview;
    Button watch;

    public FragmentUserReviews() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragmentUserReviews(List<ReviewsList> list,int contentid){
        this.reviewsLists=list;
        this.contentid=contentid;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_fragment_user_reviews, container, false);
        review=rootView.findViewById(R.id.tv_reviews);
        reviewid=rootView.findViewById(R.id.tv_reviewcount);
        watch=rootView.findViewById(R.id.btn_watch);
        lv_review=rootView.findViewById(R.id.lv_review);
        tvaddReview=rootView.findViewById(R.id.tv_addReview);
        reviewid.setText(""+reviewsLists.size());
        setData();
    return rootView;
    }

    private void setData() {
//        Integer count=reviewsLists.size();
//        reviewid.setText(count);
//        Log.i("ReviewCount",""+count);


//        for (int i=0;i<reviewsLists.size();i++) {
//            Integer custID = reviewsLists.get(i).getCustID();
//            reviewid.setText(custID.toString());
//        }
            reviewList_adapter = new ReviewList_Adapter(getActivity(), reviewsLists);
            lv_review.setAdapter(reviewList_adapter);


//        lv_review = (NonScrollListView) rootView.findViewById(R.id.lv_review);
//        tv_addReview = (TextView) rootView.findViewById(R.id.tv_addReview);
//
//
//        reviewListArrayList.add(new ReviewList(R.drawable.view3,"Norma","July 2018","Dimmduh comment should be the answer in this case. They should not be inner classes. Moving them would Identify the other issues with the Shape class that exist"));
//        reviewListArrayList.add(new ReviewList(R.drawable.view5,"Mark Henry","Jan 2017 ","Dimmduh comment should be the answer in this case. They should not be inner classes. Moving them would Identify the other issues with the Shape class that exist"));
//
////        reviewList_adapter=new ReviewList_Adapter(getActivity(),list);
//        reviewList_adapter = new ReviewList_Adapter(getActivity(), reviewListArrayList);
//        lv_review.setAdapter(reviewList_adapter);
//
        tvaddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (int i=0;i<reviewsLists.size();i++){
//                    Log.i("Review Content ID",""+reviewsLists.get(i).getContentID());
//                    reviewcontentid=reviewsLists.get(i).getContentID();
//                }
                Bundle bundle=new Bundle();
                bundle.putInt("KEY_CONTENTIDR",contentid);
                Log.i("Content ID review",""+contentid);
                Intent intent=new Intent(getActivity(), ReviewRatingActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

}
