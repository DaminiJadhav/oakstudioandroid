package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dto.PlanFinalDTO;
import com.sdaemon.oakstudiotv.utils.AppConstants;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.List;


public class CostPriceAdapter extends RecyclerView.Adapter<CostPriceAdapter.ViewHolder> implements AppConstants {
    private List<PlanFinalDTO.Cost> list;
    private Context context;
    private OnItemClickListner.OnClickCallback onClickCallback;
    int isChecked = -1;
    public CostPriceAdapter(Context context, List<PlanFinalDTO.Cost> list,
                            OnItemClickListner.OnClickCallback onClickCallback) {
        this.context = context;
        this.list = list;
        this.onClickCallback = onClickCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cost_price, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setTag(holder);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PlanFinalDTO.Cost dto = list.get(position);
        if (dto != null) {
            holder.tvPrice.setText(""+dto.getAmount());
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
        TextView tvPrice;


        public ViewHolder(View v) {
            super(v);
            llItem = v.findViewById(R.id.ll_item);
            tvPrice = v.findViewById(R.id.tv_price);

        }
    }





}

