//package com.techindiana.oakstudiotv.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.techindiana.oakstudiotv.R;
//import com.techindiana.oakstudiotv.dto.PlanFinalDTO;
//import com.techindiana.oakstudiotv.utils.OnItemClickListner;
//
//import java.util.List;
//
//
//public class PlanCompareFinalAdapterOld extends RecyclerView.Adapter<PlanCompareFinalAdapterOld.ViewHolder> {
//    private List<PlanFinalDTO.Details> list;
//    private Context context;
//    private OnItemClickListner.OnClickCallback onClickCallback;
//
//
//    public PlanCompareFinalAdapterOld(Context context, List<PlanFinalDTO.Details> list,
//                                      OnItemClickListner.OnClickCallback onClickCallback) {
//        this.context = context;
//        this.list = list;
//        this.onClickCallback = onClickCallback;
//
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_compare_old, parent, false);
//        ViewHolder holder = new ViewHolder(v);
//        v.setTag(holder);
//        return holder;
//    }
//
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        PlanFinalDTO.Details dto = list.get(position);
//        if (dto != null) {
//            holder.tvName.setText(dto.getSubDescription());
//
//            if (dto.getOne() == 1) {
//                holder.ivStatusBasic.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));
//            } else {
//                holder.ivStatusBasic.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clear));
//            }
//            if (dto.getTwo() == 1) {
//                holder.ivStatusStandard.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));
//            } else {
//                holder.ivStatusStandard.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clear));
//            }
//            if (dto.getThree() == 1) {
//                holder.ivStatusPremium.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));
//            } else {
//                holder.ivStatusPremium.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clear));
//            }
//
//
//            holder.ivStatusBasic.setOnClickListener(new OnItemClickListner(position, onClickCallback, "play"));
//
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return 0;
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//
//    protected class ViewHolder extends RecyclerView.ViewHolder {
//        LinearLayout llItem;
//        TextView tvName;
//        ImageView ivStatusBasic, ivStatusStandard, ivStatusPremium;
//
//        public ViewHolder(View v) {
//            super(v);
//            llItem = v.findViewById(R.id.ll_item);
//            tvName = v.findViewById(R.id.tv_name);
//            ivStatusBasic = v.findViewById(R.id.iv_status_basic);
//            ivStatusBasic = v.findViewById(R.id.iv_status_basic);
//            ivStatusStandard = v.findViewById(R.id.iv_status_standard);
//            ivStatusPremium = v.findViewById(R.id.iv_status_premium);
//
//
//        }
//    }
//
//
//}
//
