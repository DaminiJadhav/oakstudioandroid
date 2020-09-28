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


public class PeriodAdapter extends RecyclerView.Adapter<PeriodAdapter.ViewHolder> {
    private List<PlanFinalDTO.Period> list;
    private Context context;
    private OnItemClickListner.OnClickCallback onClickCallback;
    int isChecked = -1;


    public PeriodAdapter(Context context, List<PlanFinalDTO.Period> list,
                         OnItemClickListner.OnClickCallback onClickCallback) {
        this.context = context;
        this.list = list;
        this.onClickCallback = onClickCallback;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_radio_button, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setTag(holder);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PlanFinalDTO.Period dto = list.get(position);
        if (dto != null) {
            holder.tvName.setText(dto.getName());

            if (position == isChecked) {
                holder.ivSelect.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));
            }
            else {
                holder.ivSelect.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clear));
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
        LinearLayout llItem;
        TextView tvName;
        ImageView ivSelect;

        public ViewHolder(View v) {
            super(v);
            llItem = v.findViewById(R.id.ll_item);
            tvName = v.findViewById(R.id.tv_name);
            ivSelect = v.findViewById(R.id.iv_select);



        }
    }

    public void setCheckedValue(int isChecked) {

        this.isChecked = isChecked;
    }




}

