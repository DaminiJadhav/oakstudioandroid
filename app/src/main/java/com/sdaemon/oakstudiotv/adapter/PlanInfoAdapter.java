package com.sdaemon.oakstudiotv.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dto.PlanFinalDTO;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.ArrayList;
import java.util.List;


public class PlanInfoAdapter extends RecyclerView.Adapter<PlanInfoAdapter.ViewHolder> {
    private List<PlanFinalDTO.Details> list;
    private List<PlanFinalDTO.Details> tempList = new ArrayList<>();
    private List<PlanFinalDTO.Status> statusList = new ArrayList<>();
    private List<PlanFinalDTO.CorePlan> corePlanList;
    private Context context;
    private OnItemClickListner.OnClickCallback onClickCallback;
    PlanFinalDTO.Details detailDTO = null;
    PlanFinalDTO.Status statusDTO = null;
    String name = "";


    public PlanInfoAdapter(Context context, List<PlanFinalDTO.Details> list, List<PlanFinalDTO.CorePlan> corePlanList,
                           OnItemClickListner.OnClickCallback onClickCallback) {
        this.context = context;
        this.list = list;
        this.corePlanList = corePlanList;
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
        PlanFinalDTO.Details dto = list.get(position);
        //  PlanFinalDTO.CorePlan corePlanDTO = corePlanList.get(position);
        if (dto != null) {
            holder.tvName.setText(dto.getSubDescription());
            //  holder.ivStatusBasic.setOnClickListener(new OnItemClickListner(position, onClickCallback, "play"));
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            if (holder.llViewImage.getChildCount() >= 1)
                holder.llViewImage.removeAllViews();

         /*  for (int i = 0; i < list.size(); i++) {
                View view = inflater.inflate(R.layout.item_plan_compare_image, holder.llViewImage, false);
                ImageView ivStatus = (ImageView) view.findViewById(R.id.iv_image_status);
               if (dto.getAllowStatus()) {
                  ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));
              } else {
                    ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clear));
               }
                holder.llViewImage.addView(view);
            }

*/

        /* //  for (int i = 0; i < corePlanList.size(); i++) {
           for (int i = 0; i < list.size(); i++) {

                //  for (int j = 0; j < 1; j++) {
                View view = inflater.inflate(R.layout.item_plan_compare_image, holder.llViewImage, false);
                ImageView ivStatus = (ImageView) view.findViewById(R.id.iv_image_status);
               for (int j = 0; j <corePlanList.size() ; j++) {

                   if (corePlanList.get(j).getCorePlanId() == dto.getCorePlanId()) {
                    Log.i(String.valueOf(context), "CCCCCCC : " + corePlanList.get(0).getCorePlanId());
                    if (dto.getAllowStatus()) {
                        ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));
                    } else {
                        ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clear));
                    }

                }
               }
                holder.llViewImage.addView(view);
            }

            //   }
*/

        for (int i = 0; i < corePlanList.size(); i++) {
                //  for (int j = 0; j < 1; j++) {
                View view = inflater.inflate(R.layout.item_plan_compare_image, holder.llViewImage, false);
                ImageView ivStatus = (ImageView) view.findViewById(R.id.iv_image_status);

             if (corePlanList.get(i).getCorePlanId() == dto.getCorePlanId()) {
                    Log.i(String.valueOf(context), "CCCCCCC : " + corePlanList.get(i).getCorePlanId());
                    if (dto.getAllowStatus()) {
                        ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check));
                    } else {
                        ivStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clear));
                    }

                }

                holder.llViewImage.addView(view);
            }


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
        LinearLayout llItem, llViewImage;
        TextView tvName;
        ImageView ivStatusBasic, ivStatusStandard, ivStatusPremium;

        public ViewHolder(View v) {
            super(v);
            llItem = v.findViewById(R.id.ll_item);
            llViewImage = v.findViewById(R.id.ll_view_image);
            tvName = v.findViewById(R.id.tv_name);


        }
    }


}

