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
import com.sdaemon.oakstudiotv.model.Table;

import java.util.List;

public class MyFavouriteTVShowAdapter extends RecyclerView.Adapter<MyFavouriteTVShowAdapter.ViewHolder>{
    Context mContext;
    private static LayoutInflater inflater = null;
    List<Table> tables;

    public MyFavouriteTVShowAdapter(Context mContext, List<Table> tables) {
        this.mContext = mContext;
        this.tables = tables;
     }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        viewHolder = getViewHolder(parent, inflater);
        return (MyFavouriteTVShowAdapter.ViewHolder) viewHolder;
}

    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater){
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list_movie, parent, false);
        viewHolder = new MyFavouriteTVShowAdapter.ViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url="https://oakstudio.azurewebsites.net/";

        Table table=tables.get(position);

        holder.movieName.setText(String.valueOf(table.getContentTitle()));
        holder.tv_views.setText(String.valueOf(table.getViewCount()));
        holder.movieYear.setText(String.valueOf(table.getYear()));
        holder.feature.setText(table.getFeaturesDescription());


//            viewHolder.movieImage.setImageResource(R.drawable.banner);

        Glide.with(mContext)
                .load(url+ table.getSqImage())
                .into(holder.movieImage);

//           .placeholder(R.mipmap.view4)

        String rating = String.valueOf(table.getRatings()).toString();
        final float ratingValue = Float.parseFloat(rating);
        holder.ratingBar.setRating(ratingValue);
        holder.movieRating.setText(""+ratingValue);


        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
//                bundle.putInt("KEY_WISHLCONTID",table.getContentID());
                bundle.putInt("KEY_CONTENTIDS",table.getContentID());
                bundle.putString("KEY_LIKEWISHLIST","Like");
                Log.i("My TV show Content Id",""+table.getContentID());
                Intent intent=new Intent(mContext, MovieDetailsActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
//                ((MovieDetailsActivity) mContext).finish();
    //                ((Activity)view.getContext()).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return tables.size();
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
