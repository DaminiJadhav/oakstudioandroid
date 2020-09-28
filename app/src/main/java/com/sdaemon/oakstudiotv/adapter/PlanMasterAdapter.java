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
import com.sdaemon.oakstudiotv.dto.PlanMasterDTO;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.List;


public class PlanMasterAdapter extends RecyclerView.Adapter<PlanMasterAdapter.ViewHolder>{
    private List<PlanMasterDTO> list;
    private Context context;
    private OnItemClickListner.OnClickCallback onClickCallback;


    public PlanMasterAdapter(Context context, List<PlanMasterDTO> list,
                             OnItemClickListner.OnClickCallback onClickCallback) {
        this.context = context;
        this.list = list;
        this.onClickCallback = onClickCallback;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_master, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setTag(holder);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PlanMasterDTO dto = list.get(position);
        if (dto != null) {
            holder.tvPlanName.setText(dto.getSubDescription());
            if(dto.getAllowStatus1()==true){
              holder.ivStatus.setImageResource(R.drawable.ic_check);
            }
            else {
                holder.ivStatus.setImageResource(R.drawable.ic_clear);
            }
            holder.ivStatus.setOnClickListener(new OnItemClickListner(position, onClickCallback, "remove"));

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
        TextView tvPlanName;
        ImageView ivStatus;

        public ViewHolder(View v) {
            super(v);
            llItem = v.findViewById(R.id.ll_item);
            tvPlanName = v.findViewById(R.id.tv_plan_name);
            ivStatus = v.findViewById(R.id.iv_status);


        }
    }


}

