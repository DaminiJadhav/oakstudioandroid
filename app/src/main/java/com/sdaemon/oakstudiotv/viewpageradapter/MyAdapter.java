package com.sdaemon.oakstudiotv.viewpageradapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.ContentInfoFilter;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends PagerAdapter {
 
    private ArrayList<String> images;
    List<ContentInfoFilter> contentInfoFilter;

    private LayoutInflater inflater;
    private Context contxt;
    ImageView myImage;
    RatingBar ratingbar;
    TextView tvmoviename,tvyear,tvrating,tvfeature,tvview;

    public MyAdapter(Context context, ArrayList<String> images,List<ContentInfoFilter> contentInfoFilter) {
        this.contxt = context;
        this.images=images;
        this.contentInfoFilter=contentInfoFilter;
//        inflater = LayoutInflater.from(contxt);
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
 
    @Override
    public int getCount() {
        return images.size();

    }
 
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        String url="https://oakstudio.azurewebsites.net/";
        View myImageLayout = LayoutInflater.from(contxt).inflate(R.layout.imageviewlayout, view, false);
        myImage = myImageLayout.findViewById(R.id.iv_movie);

//        View myImageLayout = inflater.inflate(R.layout.item_list_movie, view, false);


        tvmoviename = myImageLayout.findViewById(R.id.tv_bannerMovieName);
        tvyear = myImageLayout.findViewById(R.id.tv_year);
        tvfeature = myImageLayout.findViewById(R.id.tv_featureDescription);
        tvrating = myImageLayout.findViewById(R.id.movieRating);
        tvview = myImageLayout.findViewById(R.id.tv_views);

        if (position<=4) {
            tvmoviename.setText(contentInfoFilter.get(position).getContentTitle());
            tvyear.setText("" + contentInfoFilter.get(position).getYear());
            tvfeature.setText(contentInfoFilter.get(position).getFeaturesDescription());
            tvview.setText(contentInfoFilter.get(position).getViewCount());

            ratingbar = myImageLayout.findViewById(R.id.rateBar);
            Drawable drawable = ratingbar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);
            String rating = String.valueOf(contentInfoFilter.get(position).getRatings()).toString();
            final float ratingValue = Float.parseFloat(rating);
            ratingbar.setRating(ratingValue);
            tvrating.setText("" + ratingValue);
        }





//        tvrating.setText(contentInfoFilter.get(position).getYear());




//        myImage.setImageResource(images.get(position));
        Glide.with(contxt)
                .load(url+images.get(position))
                .into(myImage);
//          .placeholder(R.mipmap.view)

        view.addView(myImageLayout, 0);
        return myImageLayout;
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab" +position;
//        return super.getPageTitle(position);
    }

}