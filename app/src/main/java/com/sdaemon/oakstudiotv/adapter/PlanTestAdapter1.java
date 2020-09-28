package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.Periodlist;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.List;

public class PlanTestAdapter1 extends RecyclerView.Adapter<PlanTestAdapter1.ViewHolder>{
    private List<Periodlist> list;
    private Context context;
    private OnItemClickListner.OnClickCallback onClickCallback;


    public PlanTestAdapter1(Context context,List<Periodlist> list,OnItemClickListner.OnClickCallback onClickCallback){
        this.context = context;
        this.list = list;
        this.onClickCallback = onClickCallback;
    }

    @NonNull
    @Override
    public PlanTestAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_compare_final_test1, viewGroup, false);
        ViewHolder holder = new ViewHolder(v);
        v.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanTestAdapter1.ViewHolder viewHolder, int i) {
      viewHolder.tvamount.setText("" +list.get(i).getAmount());
        viewHolder.tvplanname.setText(list.get(i).getName());
        viewHolder.cardView.setOnClickListener(new OnItemClickListner(i, onClickCallback, "test"));

        int tmp = i % 3;
           if(tmp == 0)
           {

               viewHolder.llline.setBackgroundColor(Color.parseColor("#82E0AA"));
//               viewHolder.llline.setBackgroundColor(context.getResources().getColor(R.color.lightGrey));
//               viewHolder.llline.setBackgroundColor(ContextCompat.getColor(context,R.color.lightGrey));
//               holder.tvPlan_name.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_color));
//               holder.tv_plan_details.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_color));


           }
           if(tmp == 1)
           {
//               viewHolder.llline.setBackgroundColor(context.getResources().getColor(R.color.bt_error_red));
               viewHolder.llline.setBackgroundColor(Color.parseColor("#EEA334"));

//               viewHolder.llline.setBackgroundColor(Color.parseColor("#F7DC6F "));

           }
           if(tmp == 2)
           {
//               viewHolder.llline.setBackgroundColor(context.getResources().getColor(R.color.blue));
//               viewHolder.llline.setBackgroundColor(Color.parseColor("#85C1E9 "));
               viewHolder.llline.setBackgroundColor(Color.parseColor("#7E8BCC"));


           }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout llItem,llline;
        CardView cardView;
        TextView tvamount,tvplanname;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_item1);
            llline = itemView.findViewById(R.id.ll_line);

            tvamount = itemView.findViewById(R.id.tv_amount);
            tvplanname = itemView.findViewById(R.id.tv_planname);

        }


    }



}
