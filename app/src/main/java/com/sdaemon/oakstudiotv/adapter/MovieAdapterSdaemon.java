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

import com.bumptech.glide.Glide;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.ContentInfoFilter;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.List;

/**
 * Created by Rahul Patil on 23-Apr-18.
 */

public class MovieAdapterSdaemon extends RecyclerView.Adapter<MovieAdapterSdaemon.ItemRowHolder>  {

    private static LayoutInflater inflater = null;
    Context mContext;
    Animation animation;
    public static  String itemTypeId="";
    private List<ContentInfoFilter> movieDetailsArrayList;
//    private ArrayList<ContentInfo> movieDetailsArrayList;
    private OnItemClickListner.OnClickCallback onClickCallback;
    AppSession appSession;
    public MovieAdapterSdaemon(Context mContext, List<ContentInfoFilter> movieDetailsArrayList, OnItemClickListner.OnClickCallback onClickCallback) {
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
        appSession=AppSession.getInstance(mContext);
        ContentInfoFilter dto= movieDetailsArrayList.get(position);
        String url="https://oakstudio.azurewebsites.net/";

//        ContentInfo dto = movieDetailsArrayList.get(position);
        if(dto!=null) {
            Log.i(getClass().getName(), "===============" + movieDetailsArrayList.get(0).getContentDescription());
            viewHolder.movieName.setText(String.valueOf(dto.getContentTitle()));
            Log.i("content name",""+dto.getContentTitle());

            viewHolder.tv_views.setText(String.valueOf(dto.getViewCount()));
            viewHolder.movieYear.setText(String.valueOf(dto.getYear()));
            viewHolder.feature.setText(dto.getFeaturesDescription());


//            viewHolder.movieImage.setImageResource(R.drawable.banner);

            Glide.with(mContext)
                    .load(url+ dto.getSqImage())
                    .into(viewHolder.movieImage);

//                                .placeholder(R.mipmap.view)


            String rating = String.valueOf(dto.getRatings()).toString();
            final float ratingValue = Float.parseFloat(rating);
            viewHolder.ratingBar.setRating(ratingValue);
            viewHolder.movieRating.setText(""+ratingValue);

//            appSession.setContentID(dto.getContentID());
//            Log.i("Content Id",""+dto.getContentID());



            viewHolder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle=new Bundle();
//                bundle.putInt("KEY_CONTENTIDS",movieDetailsArrayList.get(position).getContentID());
//                Intent intent=new Intent(mContext, MovieDetailsActivity.class);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
//                mContext.startActivity(new Intent(mContext, MovieDetailsActivity.class));
            }
        });
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
