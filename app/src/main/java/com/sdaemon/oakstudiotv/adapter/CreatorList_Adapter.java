package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.CreatorsList;

import java.util.ArrayList;

public class CreatorList_Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Animation animation;
    private ArrayList<CreatorsList> creatorsLists;

    public CreatorList_Adapter(Context context, ArrayList<CreatorsList> creatorsLists) {
        // TODO Auto-generated constructor stub
        this.creatorsLists = creatorsLists;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return creatorsLists.size();
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
        rowView = inflater.inflate(R.layout.creator_list_item, null);
        holder.tv_Name = (TextView) rowView.findViewById(R.id.tv_Name);
        holder.tv_desc = (TextView) rowView.findViewById(R.id.tv_desc);

        holder.tv_Name.setText(creatorsLists.get(position).getCreatorName());
        holder.tv_desc.setText(creatorsLists.get(position).getCreatorDesc());
        return rowView;
    }

    public class Holder {
        TextView tv_Name,tv_desc;
        CircularImageView iv_picture;
    }
}
