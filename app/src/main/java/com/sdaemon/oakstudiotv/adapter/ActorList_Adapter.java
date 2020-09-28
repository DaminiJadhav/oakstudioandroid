package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.ActorList;
import com.sdaemon.oakstudiotv.model.CastAndCrewList;

import java.util.ArrayList;
import java.util.List;

public class ActorList_Adapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    Animation animation;
    private ArrayList<ActorList> actorListArrayList;
    private List<CastAndCrewList> castAndCrewLists;

    public ActorList_Adapter(Context context, List<CastAndCrewList> actorListArrayList) {
        // TODO Auto-generated constructor stub
        this.castAndCrewLists = actorListArrayList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return castAndCrewLists.size();
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
        String url="https://oakstudio.azurewebsites.net/";

        View rowView;
        rowView = inflater.inflate(R.layout.creator_list_item, null);
        holder.tv_Name = (TextView) rowView.findViewById(R.id.tv_Name);
        holder.tv_desc = (TextView) rowView.findViewById(R.id.tv_desc);
        holder.iv_picture = (CircularImageView) rowView.findViewById(R.id.iv_picture);

        holder.tv_Name.setText(castAndCrewLists.get(position).getCastAndCrewName());
        holder.tv_desc.setText(castAndCrewLists.get(position).getCastAndCrewInfo());

//        holder.iv_picture.setImageResource(actorListArrayList.get(position).getActorPic());

        Glide.with(context).load(url+castAndCrewLists.get(position).getProfilephoto()).into(holder.iv_picture);

        return rowView;
    }

    public class Holder {
        TextView tv_Name,tv_desc;
        CircularImageView iv_picture;
    }

}
