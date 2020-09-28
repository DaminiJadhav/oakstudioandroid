package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.Language_list;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.ArrayList;
import java.util.Locale;

public class LanguageList_Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Animation animation;
    private ArrayList<Language_list> country_lists;
    private OnItemClickListner.OnClickCallback onClickCallback;
    public ImageView ivcheck;



    public LanguageList_Adapter(Context context, OnItemClickListner.OnClickCallback onClickCallback, ArrayList<Language_list> country_lists) {
        // TODO Auto-generated constructor stub
        this.country_lists = country_lists;
        this.context = context;
        this.onClickCallback = onClickCallback;
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
        rowView = inflater.inflate(R.layout.lang_list_item, null);
        holder.tv_genreName = (TextView) rowView.findViewById(R.id.tv_genreName);
        holder.layout_lang = (LinearLayout) rowView.findViewById(R.id.ll_language);
        ivcheck = (ImageView) rowView.findViewById(R.id.iv_check);
        holder.tv_genreName.setText(country_lists.get(position).getLanguage());

//        if (holder.layout_lang.isClickable()) {
//            Log.i("Clickable","click");
            holder.layout_lang.setOnClickListener(new OnItemClickListner(position, onClickCallback, ""));
//        }

//        if (holder.layout_lang.getVisibility()==View.VISIBLE)


//        holder.layout_lang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, ""+country_lists.get(position).getLanguage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
        return rowView;
    }

    public class Holder {
        TextView tv_genreName;
        public LinearLayout layout_lang;
    }



    public void showSelectLanguage(){
        ivcheck.setVisibility(View.VISIBLE);

    }



}
