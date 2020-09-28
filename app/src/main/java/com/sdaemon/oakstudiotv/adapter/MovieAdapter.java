package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.RetroPhoto;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.ArrayList;

/**
 * Created by Rahul Patil on 23-Apr-18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ItemRowHolder>  {

    private static LayoutInflater inflater = null;
    Context mContext;
    Animation animation;
    public static  String itemTypeId="";
    private ArrayList<RetroPhoto> movieDetailsArrayList;
    private OnItemClickListner.OnClickCallback onClickCallback;

    public MovieAdapter(Context mContext, ArrayList<RetroPhoto> movieDetailsArrayList, OnItemClickListner.OnClickCallback onClickCallback) {
        this.mContext = mContext;
        this.movieDetailsArrayList = movieDetailsArrayList;
        this.onClickCallback = onClickCallback;

    }
    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        viewHolder = getViewHolder(viewGroup, inflater);
        return (ItemRowHolder) viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list_movie, parent, false);
        viewHolder = new ItemRowHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder viewHolder, int position) {
        RetroPhoto dto = movieDetailsArrayList.get(position);
        if(dto!=null) {
            Log.i(getClass().getName(), "===============" + movieDetailsArrayList.get(0).getContentDescription());
            viewHolder.movieName.setText(String.valueOf(dto.getContentDescription()));
            viewHolder.tv_views.setText(String.valueOf(dto.getViewCount()));
            viewHolder.movieYear.setText(String.valueOf(dto.getReleaseYear()));
            viewHolder.movieRating.setText(String.valueOf(dto.getStartRating()));
            viewHolder.feature.setText(String.valueOf(dto.getFeaturesDescription()));
            viewHolder.movieImage.setImageResource(R.drawable.banner);
            //itemTypeId=movieDetailsArrayList.get(position).getId();
            String saf = String.valueOf(dto.getStartRating()).toString();
            final float ratingValue = Float.parseFloat(saf);
            viewHolder.ratingBar.setRating(ratingValue);
//        viewHolder.movieImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, MovieDetailsActivity.class));
//            }
//        });
            viewHolder.movieImage.setOnClickListener(new OnItemClickListner(position, onClickCallback, "image"));
        }
    }

    @Override
    public int getItemCount() {
        return (null != movieDetailsArrayList ? movieDetailsArrayList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView movieName,movieYear,movieRating,tv_views,feature;
        ImageView movieImage;
        RatingBar ratingBar;

        ItemRowHolder(View view) {
            super(view);
            ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
            Drawable drawable = ratingBar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);
            this.movieName = (TextView) view.findViewById(R.id.movieName);
            this.movieYear = (TextView) view.findViewById(R.id.movieYear);
            this.movieRating = (TextView) view.findViewById(R.id.movieRating);
            this.tv_views = (TextView) view.findViewById(R.id.tv_views);
            this.movieImage = (ImageView) view.findViewById(R.id.movieImage);
            this.feature=(TextView)view.findViewById(R.id.features);

        }
    }
}
