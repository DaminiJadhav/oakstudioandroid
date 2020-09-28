package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
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
import com.sdaemon.oakstudiotv.model.MyFavourite_MovieDetails;
import com.sdaemon.oakstudiotv.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Patil on 23-Apr-18.
 */

public class MyFavourite_Movies_Adapter extends RecyclerView.Adapter<MyFavourite_Movies_Adapter.ItemRowHolder> {

    public static String itemTypeId = "";
    private static LayoutInflater inflater = null;
    Context mContext;
    Animation animation;
    private ArrayList<MyFavourite_MovieDetails> myFavouriteMovieDetailsArrayList;
    private List<Table> tables;


    public MyFavourite_Movies_Adapter(Context mContext, ArrayList<MyFavourite_MovieDetails> myFavouriteMovieDetailsArrayList) {
        this.mContext = mContext;
        this.myFavouriteMovieDetailsArrayList = myFavouriteMovieDetailsArrayList;

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

        viewHolder.movieName.setText(String.valueOf(myFavouriteMovieDetailsArrayList.get(position).getMovieName()));
        viewHolder.tv_views.setText(String.valueOf(myFavouriteMovieDetailsArrayList.get(position).getMovieViews()));
        viewHolder.movieYear.setText(String.valueOf(myFavouriteMovieDetailsArrayList.get(position).getMovieYear()));
        viewHolder.movieRating.setText(String.valueOf(myFavouriteMovieDetailsArrayList.get(position).getMovieRating()));


        viewHolder.movieImage.setImageResource(myFavouriteMovieDetailsArrayList.get(position).getImage());
        //itemTypeId=myFavouriteMovieDetailsArrayList.get(position).getId();

        // Toast.makeText(mContext, ""+itemTypeId, Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return (null != myFavouriteMovieDetailsArrayList ? myFavouriteMovieDetailsArrayList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView movieName, movieYear, movieRating, tv_views;
        ImageView movieImage;

        ItemRowHolder(View view) {
            super(view);

            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            Drawable drawable = ratingBar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);

            this.movieName = (TextView) view.findViewById(R.id.movieName);
            this.movieYear = (TextView) view.findViewById(R.id.movieYear);
            this.movieRating = (TextView) view.findViewById(R.id.movieRating);
            this.tv_views = (TextView) view.findViewById(R.id.tv_views);
            this.movieImage = (ImageView) view.findViewById(R.id.movieImage);
        }
    }
}
