package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.Country_list;

import java.util.ArrayList;

public class CoutryList_Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Animation animation;
    private ArrayList<Country_list> country_lists;

    public CoutryList_Adapter(Context context, ArrayList<Country_list> country_lists) {
        // TODO Auto-generated constructor stub
        this.country_lists = country_lists;
        this.context = context;
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
        rowView = inflater.inflate(R.layout.country_list_item, null);
        holder.tv_countryName = (TextView) rowView.findViewById(R.id.tv_countryName);
        holder.tv_countryCode = (TextView) rowView.findViewById(R.id.tv_countryCode);

        holder.tv_countryName.setText(country_lists.get(position).getCountryName());
        holder.tv_countryCode.setText(country_lists.get(position).getCountryCode());
        return rowView;
    }

    public class Holder {
        TextView tv_countryName,tv_countryCode;
    }
}
