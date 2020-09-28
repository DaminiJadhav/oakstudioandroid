package com.sdaemon.oakstudiotv.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.dto.BlogDto;
import com.sdaemon.oakstudiotv.model.BlogList;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> implements Filterable {
//    private List<BlogList> blogLists;
    private OnItemClickListner.OnClickCallback onClickCallback;
    private List<BlogDto> blogLists;
    Context context;
    BlogList blogList;

    public BlogAdapter(Context context, List<BlogDto> blogLists,OnItemClickListner.OnClickCallback onClickCallback){
        this.onClickCallback=onClickCallback;
        this.context=context;
        this.blogLists=blogLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_blog_chapters, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final BlogDto blogList=blogLists.get(position);
        if (blogList!=null){
            holder.tvname.setText(blogList.getSmallTitle());
            holder.tvdirector.setText(blogList.getDirectorName());
            String s=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format( getUTCToLocalDate(blogList.getDate()));
            holder.tvdate.setText((s));
            holder.tvdes.setText(blogList.getSmallDescription());
            holder.ivblog.setImageResource(R.drawable.iv_blog2);
        }
        holder.ivblog.setOnClickListener(new OnItemClickListner(position,onClickCallback,"blog"));
       /* holder.ivblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==4){
                    Intent intent=new Intent(context, BlogArticleActivity.class);
                    context.startActivity(intent);
                }
            }
        });
*/
    }
    public Date getUTCToLocalDate(String date) {
        Date inputDate = new Date();
        if (date != null && !date.isEmpty()) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                inputDate = simpleDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return inputDate;
    }



    @Override
    public int getItemCount() {
        return blogLists.size();
    }

    @Override
    public Filter getFilter() {
             return new Filter() {
                 @Override
                 protected FilterResults performFiltering(CharSequence charSequence) {
                     String charString = charSequence.toString();
                     if (charString.isEmpty()) {

                     }else {
                         List<BlogDto> filteredList = new ArrayList<>();
                         for (BlogDto row : blogLists) {

                             // name match condition. this might differ depending on your requirement
                             // here we are looking for name or phone number match

//                        || row.getAge().contains(charSequence)
                             if (row.getSmallTitle().toLowerCase().contains(charString.toLowerCase())) {
                                 filteredList.add(row);
                             }else {
//                                                     showDialoge(context,"hgdghf","sghdgh");

                             }
                         }

                         blogLists = filteredList;
                     }
                     FilterResults filterResults = new FilterResults();
                     filterResults.values = blogLists;
                     return filterResults;
                 }

                 @Override
                 protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                     blogLists = (ArrayList<BlogDto>) filterResults.values;
                     notifyDataSetChanged();
                 }
             };
    }

    public void showDialoge(Context context, String title, String msg) {
        try {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(context,R.style.dialoge);
            //            String centerNRed = "<div align:'center' ><span style='color:red' >"+title+"</span></div>";
//            builder.setTitle(Html.fromHtml(centerNRed))
            builder.setTitle(Html.fromHtml("<font color='#D93723'>"+title+"</font>"))
                    .setMessage(msg)
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_error_black_24dp)
                    .setPositiveButton(Html.fromHtml("<font color='#9AEB3D'>Search Again !!!!</font>"), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
//                            query1="";
////                            searchView.clearFocus();
//                            searchView.setQuery("", false);
//                            getSearchData();
//                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(Html.fromHtml("<font color='#9AEB3D'>Cancel</font>"), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
//                    System.exit(0);
                        }
                    }).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvname,tvdes,tvdate,tvdirector;
        ImageView ivblog;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.tv_chapters);
            tvdes=itemView.findViewById(R.id.tv_desc);
            ivblog=itemView.findViewById(R.id.iv_blogimages);
            tvdate=itemView.findViewById(R.id.tv_date);
            tvdirector=itemView.findViewById(R.id.tv_director);


        }
    }
}
