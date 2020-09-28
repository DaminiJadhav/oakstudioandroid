package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.ChoosePlanList;

import java.util.ArrayList;

/**
 * Created by Rahul Patil on 23-Apr-18.
 */

public class ChoosePlanAdapter extends RecyclerView.Adapter<ChoosePlanAdapter.ItemRowHolder>  {

    private static LayoutInflater inflater = null;
    Context mContext;
    Animation animation;
    public static  String itemTypeId="";
    private ArrayList<ChoosePlanList> choosePlanListArrayList;

    public ChoosePlanAdapter(Context mContext, ArrayList<ChoosePlanList> choosePlanListArrayList) {
        this.mContext = mContext;
        this.choosePlanListArrayList = choosePlanListArrayList;

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
        View v1 = inflater.inflate(R.layout.item_list_choose_plan, parent, false);
        viewHolder = new ItemRowHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder viewHolder, int position) {

        viewHolder.tv_planName.setText(String.valueOf(choosePlanListArrayList.get(position).getPlanName()));
        viewHolder.tv_planAmount.setText(String.valueOf(choosePlanListArrayList.get(position).getPlanAmount()));
        viewHolder.tv_noOFscreens.setText(String.valueOf(choosePlanListArrayList.get(position).getNoScreens()));
        viewHolder.tv_quqrterPrice.setText(String.valueOf(choosePlanListArrayList.get(position).getQraterPrice()));
        viewHolder.tv_anualPrice.setText(String.valueOf(choosePlanListArrayList.get(position).getAnualPrice()));


        if(choosePlanListArrayList.get(position).getHdAvailable()==1)
        {
            viewHolder.iv_hdAvailable.setImageResource(R.drawable.ic_check);
            viewHolder.iv_ultraHD.setImageResource(R.drawable.ic_check);
            viewHolder.iv_watchOnTV_Phone.setImageResource(R.drawable.ic_check);
            viewHolder.iv_umLim_Tv_Movies.setImageResource(R.drawable.ic_check);
            viewHolder.iv_cancelAnyTime.setImageResource(R.drawable.ic_check);
            viewHolder.iv_firstFree.setImageResource(R.drawable.ic_check);
        }
        else {
//            viewHolder.iv_hdAvailable.setImageResource(R.drawable.ic_clear);
//            viewHolder.iv_ultraHD.setImageResource(R.drawable.ic_clear);
//            viewHolder.iv_watchOnTV_Phone.setImageResource(R.drawable.ic_clear);
//            viewHolder.iv_umLim_Tv_Movies.setImageResource(R.drawable.ic_clear);
//            viewHolder.iv_cancelAnyTime.setImageResource(R.drawable.ic_clear);
//            viewHolder.iv_firstFree.setImageResource(R.drawable.ic_clear);
        }

    }

    @Override
    public int getItemCount() {
        return (null != choosePlanListArrayList ? choosePlanListArrayList.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView tv_planName,tv_planAmount,tv_noOFscreens,tv_quqrterPrice,tv_anualPrice;

        ImageView iv_hdAvailable,iv_ultraHD,iv_watchOnTV_Phone,
                  iv_umLim_Tv_Movies,iv_cancelAnyTime,iv_firstFree;

        ItemRowHolder(View view) {
            super(view);

            this.tv_planName = (TextView) view.findViewById(R.id.tv_planName);
            this.tv_planAmount = (TextView) view.findViewById(R.id.tv_planAmount);
            this.tv_noOFscreens = (TextView) view.findViewById(R.id.tv_noOFscreens);
            this.tv_quqrterPrice = (TextView) view.findViewById(R.id.tv_quqrterPrice);
            this.tv_anualPrice = (TextView) view.findViewById(R.id.tv_anualPrice);

            this.iv_hdAvailable = (ImageView) view.findViewById(R.id.iv_hdAvailable);
            this.iv_ultraHD = (ImageView) view.findViewById(R.id.iv_ultraHD);
            this.iv_watchOnTV_Phone = (ImageView) view.findViewById(R.id.iv_watchOnTV_Phone);
            this.iv_umLim_Tv_Movies = (ImageView) view.findViewById(R.id.iv_umLim_Tv_Movies);
            this.iv_cancelAnyTime = (ImageView) view.findViewById(R.id.iv_cancelAnyTime);
            this.iv_firstFree = (ImageView) view.findViewById(R.id.iv_firstFree);
        }
    }
}
