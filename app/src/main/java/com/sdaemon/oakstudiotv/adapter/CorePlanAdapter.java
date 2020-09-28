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
import com.sdaemon.oakstudiotv.dto.PlanFinalDTO;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.List;


public class CorePlanAdapter extends RecyclerView.Adapter<CorePlanAdapter.ViewHolder> {
    private List<PlanFinalDTO.CorePlan> list;
    private Context context;
    private OnItemClickListner.OnClickCallback onClickCallback;
    int isChecked = -1;


    public CorePlanAdapter(Context context, List<PlanFinalDTO.CorePlan> list,
                           OnItemClickListner.OnClickCallback onClickCallback) {
        this.context = context;
        this.list = list;
        this.onClickCallback = onClickCallback;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_core_paln, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setTag(holder);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PlanFinalDTO.CorePlan dto = list.get(position);
        if (dto != null) {
            holder.tvPlanName.setText(dto.getCorePlanName());

            if (position == isChecked) {
               // holder.ivImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));
                holder.ivImage.setVisibility(View.VISIBLE);
                holder.llMain.setBackgroundColor(context.getResources().getColor(R.color.red));

            } else {
                holder.ivImage.setVisibility(View.INVISIBLE);
                holder.llMain.setBackgroundColor(context.getResources().getColor(R.color.played));
            }
            holder.llItem.setOnClickListener(new OnItemClickListner(position, onClickCallback, "play"));

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
        LinearLayout llItem, llMain;
        TextView tvPlanName;
        ImageView ivImage;

        public ViewHolder(View v) {
            super(v);
            llItem = v.findViewById(R.id.ll_item);
            llMain = v.findViewById(R.id.ll_main);
            tvPlanName = v.findViewById(R.id.tv_plan_name);
            ivImage = v.findViewById(R.id.iv_image);


        }
    }

    public void setCheckedValue(int isChecked) {

        this.isChecked = isChecked;
    }


}

