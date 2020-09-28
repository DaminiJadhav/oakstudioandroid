package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.MovieDetailsActivity;
import com.sdaemon.oakstudiotv.model.DownloadedMovieDetails;

import java.util.ArrayList;

/**
 * Created by Rahul Patil on 23-Apr-18.
 */

public class DownloadedMovieAdapter extends RecyclerView.Adapter<DownloadedMovieAdapter.ItemRowHolder>  {

    private static LayoutInflater inflater = null;
    Context mContext;
    Animation animation;
    public static  String itemTypeId="";
    private ArrayList<DownloadedMovieDetails> movieDetailsArrayList;

    public DownloadedMovieAdapter(Context mContext, ArrayList<DownloadedMovieDetails> movieDetailsArrayList) {
        this.mContext = mContext;
        this.movieDetailsArrayList = movieDetailsArrayList;

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
        View v1 = inflater.inflate(R.layout.item_list_downloaded_movie, parent, false);
        viewHolder = new ItemRowHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder viewHolder, int position) {

        viewHolder.movieName.setText(String.valueOf(movieDetailsArrayList.get(position).getMovieName()));
        viewHolder.tv_views.setText(String.valueOf(movieDetailsArrayList.get(position).getMovieViews()));
        viewHolder.movieYear.setText(String.valueOf(movieDetailsArrayList.get(position).getMovieYear()));
        viewHolder.movieRating.setText(String.valueOf(movieDetailsArrayList.get(position).getMovieRating()));
        viewHolder.tv_uploadDate.setText(String.valueOf(movieDetailsArrayList.get(position).getUploadedDate()));
        viewHolder.tv_remDays.setText(String.valueOf(movieDetailsArrayList.get(position).getRemaningDays()));


        viewHolder.movieImage.setImageResource(movieDetailsArrayList.get(position).getImage());
        //itemTypeId=movieDetailsArrayList.get(position).getId();
        viewHolder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, MovieDetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != movieDetailsArrayList ? movieDetailsArrayList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView movieName,movieYear,movieRating,tv_views,tv_uploadDate,tv_remDays;
        ImageView movieImage;

        ItemRowHolder(View view) {
            super(view);

            RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
            Drawable drawable = ratingBar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);
            this.movieName = (TextView) view.findViewById(R.id.movieName);
            this.movieYear = (TextView) view.findViewById(R.id.movieYear);
            this.movieRating = (TextView) view.findViewById(R.id.movieRating);
            this.tv_views = (TextView) view.findViewById(R.id.tv_views);
            this.tv_uploadDate = (TextView) view.findViewById(R.id.tv_uploadDate);
            this.tv_remDays = (TextView) view.findViewById(R.id.tv_remDays);
            this.movieImage = (ImageView) view.findViewById(R.id.movieImage);
        }
    }
}
