package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.ReviewRatingActivity;
import com.sdaemon.oakstudiotv.model.ReviewList;
import com.sdaemon.oakstudiotv.model.ReviewsList;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.DateUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReviewList_Adapter extends BaseAdapter {
//1187,1188,1189
    private static LayoutInflater inflater = null;
    Context context;
    Animation animation;
    private List<ReviewsList> reviewsLists;
    private ArrayList<ReviewList> reviewListArrayList;
    AppSession appSession;
    int reviewid;
    Integer customerId;

    public ReviewList_Adapter(Context context, List<ReviewsList> reviewList) {
        // TODO Auto-generated constructor stub
        this.reviewsLists = reviewList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return reviewsLists.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String url="https://oakstudio.azurewebsites.net/";

        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.reviews_list_item, null);
        holder.tv_Name = (TextView) rowView.findViewById(R.id.tv_Name);
        holder.tv_date = (TextView) rowView.findViewById(R.id.tv_date);
        holder.tv_review = (TextView) rowView.findViewById(R.id.tv_review);
        holder.iv_picture = (CircularImageView) rowView.findViewById(R.id.iv_picture);
//        holder.reviewRatibgbar = (RatingBar) rowView.findViewById(R.id.reviewRatibgbar);
        holder.tvratingcount = (TextView) rowView.findViewById(R.id.tv_ratingcount);

        holder.btnedit = rowView.findViewById(R.id.btn_edit);


        holder.tv_Name.setText(reviewsLists.get(position).getFirstName());
        Log.i("Cust Name",""+reviewsLists.get(position).getFirstName());
//        Log.i("Cust Login Name",""+appSession.getUserDTO().getResult().getFirstName());


//        Locale loc = new Locale("en", "US");
        String d= DateFormat.getDateInstance().format(DateUtils.getUTCToLocalDate(reviewsLists.get(position).getCreateDate()));

        holder.tv_date.setText(d);
        holder.tv_review.setText(reviewsLists.get(position).getReview());
//        Glide.with(context).load(url+reviewsLists.get(position).getProfilePhoto()).into(holder.iv_picture);



//        holder.reviewRatibgbar.setRating(reviewsLists.get(position).getStartRating());
        try {
            appSession = AppSession.getInstance(context);
            Log.i("Cust Id", "" + appSession.getUserDTO().getResult().getCustomerId());
            customerId = appSession.getUserDTO().getResult().getCustomerId();
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("Cust Id list",""+reviewsLists.get(position).getCustID());
        Integer customerIdlist=reviewsLists.get(position).getCustID();

            if (customerIdlist.equals(customerId)){
                Log.i("Cust review Id",""+reviewsLists.get(position).getContentReviewID());
                Log.i("Cust Review position",""+position);


//                holder.btnedit.setVisibility(View.VISIBLE);
                holder.btnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reviewid=reviewsLists.get(position).getContentReviewID();
                    Log.i("Cust Review position",""+position);
                    Bundle bundle = new Bundle();
                    bundle.putString("KEY_EDITREVIEW", "EditReview");
                    bundle.putString("Review",holder.tv_review.getText().toString().trim());
                    bundle.putInt("KEY_REVIEWID",reviewid);
                    Intent intent = new Intent(context, ReviewRatingActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });
         }else {
                holder.btnedit.setVisibility(View.INVISIBLE);
            }


//        holder.tv_Name.setText(reviewListArrayList.get(position).getUserName());
//        holder.tv_year.setText(reviewListArrayList.get(position).getYear());
//        holder.tv_review.setText(reviewListArrayList.get(position).getReviewDesc());
//
//        holder.iv_picture.setImageResource(reviewListArrayList.get(position).getUserImage());

        holder.reviewrating = (RatingBar)rowView.findViewById(R.id.reviewRatibgbar);
        Drawable drawable = holder.reviewrating.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);

        String rating = String.valueOf(reviewsLists.get(position).getStartRating()).toString();
        final float ratingValue = Float.parseFloat(rating);
        holder.reviewrating.setRating(ratingValue);
        holder.tvratingcount.setText(""+ratingValue);

       /* holder.reviewRatibgbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Intent intent=new Intent(context, ReviewRatingActivity.class);
                context.startActivity(intent);

            }
        });*/

        //Glide.with(ReviewList_Adapter.this).load(reviewListArrayList.get(position).getActorPic()).into(holder.iv_picture);

        return rowView;
    }

    public class Holder {
        TextView tv_Name,tv_date,tv_review,tvratingcount;
        CircularImageView iv_picture;
        RatingBar reviewrating;
        Button btnedit;

    }
}
