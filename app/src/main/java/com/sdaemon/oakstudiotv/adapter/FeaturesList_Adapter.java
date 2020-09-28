package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.GetFeaturesResp;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.ArrayList;

public class FeaturesList_Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Animation animation;
    int row_feature;
    int clickPosition=-1;
    private OnItemClickListner.OnClickCallback onClickCallback;
    String name = "";
    private ArrayList<GetFeaturesResp> country_lists;

    public FeaturesList_Adapter(Context context, ArrayList<GetFeaturesResp> country_lists, OnItemClickListner.OnClickCallback onClickCallback, String name) {
        // TODO Auto-generated constructor stub
        this.country_lists = country_lists;
        this.context = context;
        this.onClickCallback=onClickCallback;
        this.name=name;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return country_lists.size();
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

        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.genre_list_item, null);
        holder.tv_genreName = (TextView) rowView.findViewById(R.id.tv_genreName);

        holder.tv_genreName.setText(String.valueOf(country_lists.get(position).getDescription()));

        holder.llFeature = (LinearLayout) rowView.findViewById(R.id.ll_select);
        holder.llFeature.setOnClickListener(new OnItemClickListner(position, onClickCallback, ""));


//        holder.llFeature.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                row_feature=position;
//                notifyDataSetChanged();
//            }
//        });
        if(clickPosition==position){
            notifyDataSetChanged();
            holder.tv_genreName.setTextColor(Color.parseColor("#D93723"));
        }
        else
        {
            notifyDataSetChanged();
            holder.tv_genreName.setTextColor(Color.parseColor("#FFFFFF"));
        }
        return rowView;
    }

    public class Holder {
        TextView tv_genreName;
        LinearLayout llFeature;
    }
    public void clickfeatureposition(int clickPosition)
    {
        this.clickPosition=clickPosition;
    }
}
