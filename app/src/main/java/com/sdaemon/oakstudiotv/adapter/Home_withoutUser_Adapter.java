package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dto.SignInPageSliderDTO;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class Home_withoutUser_Adapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    ArrayList<String> image;
    SignInPageSliderDTO signInSliderData;
    private Integer[] images = {R.drawable.banner, R.drawable.view3, R.drawable.view,R.drawable.view4,R.drawable.view1};

    public Home_withoutUser_Adapter(Context context, ArrayList<String> images, SignInPageSliderDTO signInSliderData) {
        this.context = context;
        this.image=images;
        this.signInSliderData=signInSliderData;

    }

    public Home_withoutUser_Adapter(Context context) {
        this.context = context;


    }



    @Override
    public int getCount() {
        if (image==null){
            return 0;
        }else {
            return image.size();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        String url="https://oakstudio.azurewebsites.net";

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_list_home_without_user, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView moviename=(TextView) view.findViewById(R.id.tv_bannerMovieName);
        TextView movieyear=(TextView) view.findViewById(R.id.tv_bannerMovieyear);

//        imageView.setImageResource(images[position]);

        Glide.with(context)
                .load(url+image.get(position))
                .into(imageView);

        Log.i("Image URL",""+url+image.get(position));

        moviename.setText(""+signInSliderData.getTable().get(position).getContentTitle());
        movieyear.setText(""+signInSliderData.getTable().get(position).getYear());


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}