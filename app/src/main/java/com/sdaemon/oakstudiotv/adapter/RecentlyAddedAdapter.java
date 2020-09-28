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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.MovieDetailsActivity;
import com.sdaemon.oakstudiotv.model.RecentlyAdded;

import java.util.List;

public class RecentlyAddedAdapter extends RecyclerView.Adapter<RecentlyAddedAdapter.ViewHolder> {
Context mContext;
private static LayoutInflater inflater = null;
List<RecentlyAdded> recentlyAddeds;


    public RecentlyAddedAdapter(Context mContext, List<RecentlyAdded> recentlyAddeds) {
        this.mContext = mContext;
        this.recentlyAddeds = recentlyAddeds;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        viewHolder = getViewHolder(parent, inflater);
        return (RecentlyAddedAdapter.ViewHolder) viewHolder;

    }

    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater){
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list_movie, parent, false);
        viewHolder = new RecentlyAddedAdapter.ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url="https://oakstudio.azurewebsites.net/";

        RecentlyAdded recentlyAdded=recentlyAddeds.get(position);

        holder.movieName.setText(String.valueOf(recentlyAdded.getContentTitle()));
        holder.tv_views.setText(String.valueOf(recentlyAdded.getViewCount()));
        holder.movieYear.setText(String.valueOf(recentlyAdded.getYear()));
        holder.feature.setText(recentlyAdded.getFeaturesDescription());


//            viewHolder.movieImage.setImageResource(R.drawable.banner);

        Glide.with(mContext)
                .load(url+ recentlyAdded.getSquare_Image())
                .into(holder.movieImage);

        String rating = String.valueOf(recentlyAdded.getRatings()).toString();
        final float ratingValue = Float.parseFloat(rating);
        holder.ratingBar.setRating(ratingValue);
        holder.movieRating.setText(""+ratingValue);

        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
//                bundle.putInt("KEY_WISHLCONTID",table.getContentID());
                bundle.putInt("KEY_CONTENTIDS",recentlyAdded.getContentID());
                Log.i("Recently Content Id",""+recentlyAdded.getContentID());
                Intent intent=new Intent(mContext, MovieDetailsActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        })    ;
    }

    @Override
    public int getItemCount() {
        return recentlyAddeds.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView movieName,movieYear,movieRating,tv_views,feature;
        ImageView movieImage;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);
            Drawable drawable = ratingBar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);
            this.movieName = (TextView) itemView.findViewById(R.id.movieName);
            this.movieYear = (TextView) itemView.findViewById(R.id.movieYear);
            this.movieRating = (TextView) itemView.findViewById(R.id.movieRating);
            this.tv_views = (TextView) itemView.findViewById(R.id.tv_views);
            this.movieImage = (ImageView) itemView.findViewById(R.id.movieImage);
            this.feature=(TextView)itemView.findViewById(R.id.features);
        }
    }
}
