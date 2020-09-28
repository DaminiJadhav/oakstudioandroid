package com.sdaemon.oakstudiotv.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dto.NotificationDTO;
import com.sdaemon.oakstudiotv.model.Notification_details;
import com.sdaemon.oakstudiotv.utils.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rahul Patil on 23-Apr-18.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ItemRowHolder>  {

    private static LayoutInflater inflater = null;
    Context mContext;
    Animation animation;
    public static  String itemTypeId="";
    private List<NotificationDTO> notification_detailsArrayList;

    public NotificationAdapter(Context mContext, List<NotificationDTO> notification) {
        this.mContext = mContext;
        this.notification_detailsArrayList = notification;

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
        View v1 = inflater.inflate(R.layout.item_list_profile, parent, false);
        viewHolder = new ItemRowHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder viewHolder, int position) {

//        viewHolder.tv_head.setText(String.valueOf("\u2022 "+notification_detailsArrayList.get(position).getHeading()));
//        viewHolder.tv_desc.setText(String.valueOf(notification_detailsArrayList.get(position).getDesc()));

       /* Glide.with(mContext)
                .load( notification_detailsArrayList.get(position).getImage())
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.movieImage);*/
       // viewHolder.movieImage.setImageResource(notification_detailsArrayList.get(position).getImage());
        //itemTypeId=notification_detailsArrayList.get(position).getId();

       // Toast.makeText(mContext, ""+itemTypeId, Toast.LENGTH_SHORT).show();

        viewHolder.tv_head.setText(notification_detailsArrayList.get(position).getTitle());
        viewHolder.tv_desc.setText(notification_detailsArrayList.get(position).getMassage());
        String date= DateFormat.getDateInstance().format(DateUtils.getUTCToLocalDate(notification_detailsArrayList.get(position).getNotifyDate()));

        viewHolder.tv_date.setText((date));

    }

    @Override
    public int getItemCount() {
        return (null != notification_detailsArrayList ? notification_detailsArrayList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView tv_head,tv_desc,tv_date;

        ItemRowHolder(View view) {
            super(view);

            this.tv_head = (TextView) view.findViewById(R.id.tv_head);
            this.tv_desc = (TextView) view.findViewById(R.id.tv_desc);
            this.tv_date = (TextView) view.findViewById(R.id.notification_date);


        }
    }


    public Date getUTCToLocalDate(String date) {
        Date inputDate = new Date();
        if (date != null && !date.isEmpty()) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                inputDate = simpleDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return inputDate;
    }
}
