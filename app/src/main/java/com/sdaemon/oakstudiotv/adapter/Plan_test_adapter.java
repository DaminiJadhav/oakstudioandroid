package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.PlanSelectionActivity;
//import com.techindiana.oakstudiotv.activity.YourPlantestActivity;
import com.sdaemon.oakstudiotv.model.Datum;
import com.sdaemon.oakstudiotv.model.DescriptionList;
import com.sdaemon.oakstudiotv.model.Periodlist;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.ArrayList;
import java.util.List;

public class Plan_test_adapter extends RecyclerView.Adapter<Plan_test_adapter.ViewHolder> {
    private ArrayList<Periodlist> periodlists;
    private List<Datum> datumlist;
//    private List<plan_test_DTO> list;
   private Context context;
   private OnItemClickListner.OnClickCallback onClickCallback;

   public Plan_test_adapter(Context context, List<Datum> list,
                            OnItemClickListner.OnClickCallback onClickCallback) {
       this.context = context;
       this.datumlist = list;
       this.onClickCallback = onClickCallback;
   }
   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_compare_final_test, parent, false);
//       ViewHolder holder = new ViewHolder(v);
//       v.setTag(holder);
//       return holder;
       return new ViewHolder(v);

   }
   public void onBindViewHolder(final ViewHolder holder, final int position) {
//       plan_test_DTO dto = list.get(position);
       Datum datum=datumlist.get(position);
      // holder.tv_plan_details.setText(datum.getDescriptionLists().get(position).getName());
       holder.tvPlan_name.setText(datum.getCorePlanName());
       List<String> list1 = new ArrayList<String>();
       List<DescriptionList> descriptionLists=datum.getDescriptionLists();
       for (int i=0;i<descriptionLists.size();i++){

           if (descriptionLists.get(i).getBoolValue().equals("True")){
               if(Integer.parseInt(descriptionLists.get(i).getScreen())>0)
               {
                   list1.add(descriptionLists.get(i).getName().toString().concat("=").concat(descriptionLists.get(i).getScreen().toString()));
               }
               else {
                   list1.add(descriptionLists.get(i).getName());
               }
           }
       }
            String joined= TextUtils.join("\n",list1);
       holder.tv_plan_details.setText(joined);

//           int tmp = position % 3;
//           if(tmp == 0)
//           {
//               holder.tvPlan_name.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_color));
//               holder.tv_plan_details.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_color));
//
//
//           }
//           if(tmp == 1)
//           {
//               holder.tvPlan_name.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_color1));
//               holder.tv_plan_details.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_color1));
//
//           }
//           if(tmp == 2)
//           {
//               holder.tvPlan_name.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_color3));
//               holder.tv_plan_details.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_color3));
//           }





       holder.bind(datum);


       holder.itemView.setOnClickListener(v -> {
           holder.tvPlan_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_up_white_24dp, 0);
           boolean expanded = datum.isExpanded();
           datum.setExpanded(!expanded);
           notifyItemChanged(position);
       });

       holder.tvbuynow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Context contextActivity = v.getContext();
               Bundle bundle=new Bundle();
               periodlists = new ArrayList<>();
               periodlists.addAll(datum.getPeriodlists());
               Intent intent=new Intent(contextActivity,PlanSelectionActivity.class);
               bundle.putParcelableArrayList("arraylist",periodlists);
               bundle.putString("TYPE", datum.getCorePlanid());
               bundle.putString("NAME",datum.getCorePlanName());
               intent.putExtras(bundle);
               contextActivity.startActivity(intent);

           }
       });




//       holder.subItem.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Intent intent = new Intent(context.getApplicationContext(), PlanSelectionActivity.class);
//               context.startActivity(intent);
//               Log.i("Position",datumlist.get(position).getDescriptionLists().get(position).getName());
//
//           }
//       });

//       if (dto != null) {
//           holder.tvPlan_name.setText(dto.getPlan());
//           holder.tv_plan_details.setText(dto.getDetail());
//           if (dto.getValue() == 0) {
//               holder.tv_status.setVisibility(View.GONE);
//
//               GradientDrawable shape =  new GradientDrawable();
//               shape.setCornerRadius( 3 );
//               shape.setColor(Color.WHITE);
//               shape.setStroke(3,Color.GREEN);
//              // holder.llItem.setBackground(context.getResources().getDrawable(R.drawable.round_plan_un_selected));
//               holder.llItem.setBackground(shape);
//           } else {
//              // holder.llItem.setBackground(context.getResources().getDrawable(R.drawable.round_plan_selected));
//           }
//           holder.llItem.setOnClickListener(new OnItemClickListner(position, onClickCallback, "test"));
//       }
   }
   @Override
   public int getItemViewType(int position) {
       return 0;
   }
   @Override
   public int getItemCount() {
       return datumlist.size();
   }


   protected class ViewHolder extends RecyclerView.ViewHolder {
       LinearLayout llItem,subItem;
       TextView tvPlan_name, tv_plan_details,tvbuynow, tv_status;
       public ViewHolder(View v) {
           super(v);
           llItem = v.findViewById(R.id.ll_item);
           subItem = v.findViewById(R.id.sub_item);

           tvPlan_name = v.findViewById(R.id.tv_title);
           tv_plan_details = v.findViewById(R.id.tv_desc);
           tvbuynow=v.findViewById(R.id.tv_buynow);
//           tv_status = v.findViewById(R.id.tv_status1);
       }

       private void bind(Datum datum) {
           boolean expanded = datum.isExpanded();
           if (expanded==false){
            tvPlan_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_down_white_24dp, 0);
           }
           subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
//           tvPlan_name.setBackgroundColor(R.drawable.gradient_background_color);
//           tv_plan_details.setBackgroundColor(R.drawable.gradient_background_color);

//                     tvPlan_name.setText(plan_test_dto.getPlan());
//           tv_plan_details.setText(plan_test_dto.getDetail());
//

                     tvPlan_name.setText(datum.getCorePlanName());

       }
   }
}