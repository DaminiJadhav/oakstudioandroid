package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dto.PlanDescriptionDTO;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.List;


public class PlanCompareAdapter extends RecyclerView.Adapter<PlanCompareAdapter.ViewHolder>{
    private List<PlanDescriptionDTO.Result> list;
    private Context context;
    private OnItemClickListner.OnClickCallback onClickCallback;


    public PlanCompareAdapter(Context context, List<PlanDescriptionDTO.Result> list,
                              OnItemClickListner.OnClickCallback onClickCallback) {
        this.context = context;
        this.list = list;
        this.onClickCallback = onClickCallback;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_compare, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setTag(holder);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PlanDescriptionDTO.Result dto = list.get(position);
        if (dto != null) {
             holder.tvName.setText(dto.getHDAvailable());
            holder.ivStatusBasic.setOnClickListener(new OnItemClickListner(position, onClickCallback, "play"));

        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llItem;
        TextView tvName;
        ImageView ivStatusBasic,ivStatusStandard,ivStatusPremium;

        public ViewHolder(View v) {
            super(v);
            llItem = v.findViewById(R.id.ll_item);
            tvName = v.findViewById(R.id.tv_name);
            ivStatusBasic = v.findViewById(R.id.iv_status_basic);
            ivStatusBasic = v.findViewById(R.id.iv_status_basic);
            ivStatusStandard = v.findViewById(R.id.iv_status_standard);
            ivStatusPremium = v.findViewById(R.id.iv_status_premium);



        }
    }


}

