package com.sdaemon.oakstudiotv.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.MovieDetailsActivity;
import com.sdaemon.oakstudiotv.activity.Rajendra;
import com.sdaemon.oakstudiotv.dao.RetroClient;
import com.sdaemon.oakstudiotv.dto.DeleteContentViewHistoryDTO;
import com.sdaemon.oakstudiotv.model.RecentlyWatched;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul Patil on 23-Apr-18.
 */

public class RecentlyViewed_list_Adapter extends RecyclerView.Adapter<RecentlyViewed_list_Adapter.ItemRowHolder>  {

    private static LayoutInflater inflater = null;
    Context mContext;
    Animation animation;
    public static  String itemTypeId="";
//    private ArrayList<Recetly_ViewedDetails> movieDetailsArrayList;
    private List<RecentlyWatched> recentlyWatcheds;
    Rajendra rajendra;
    int removePosition=-1;
    int contentid,seenupto;
    private OnItemClickListner.OnClickCallback onClickCallback;
    Dialog dialog;

    long duration;
    long progress;


    public RecentlyViewed_list_Adapter(Context mContext, List<RecentlyWatched> recentlyWatcheds) {
        this.mContext = mContext;
        this.recentlyWatcheds = recentlyWatcheds;
//        this.onClickCallback=onClickCallback;

    }
    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        viewHolder = getViewHolder(viewGroup, inflater);
        return (ItemRowHolder) viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list_movie, parent, false);
        viewHolder = new ItemRowHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder viewHolder, int position) {
        rajendra=new Rajendra();
        String url="https://oakstudio.azurewebsites.net/";

        removePosition=position;

        seenupto=recentlyWatcheds.get(position).getSeenUpTo();
        Log.i("before  click", "onClick: "+recentlyWatcheds.size());

//        duration=player.getDuration();
//        Log.i("Duration ",""+duration);
//        int current = videoView.getCurrentPosition();
//
//        progress = playbackPosition * 100 / duration;
//        Log.i("Duration progress ",""+progress);
//        viewHolder.progressBar.setProgress(progress);

//        int see1= (int) TimeUnit.MILLISECONDS.toSeconds(seenupto);
//        int min = ((seenupto/1000) / 60) % 60;

        int seenUpto= (int) TimeUnit.MILLISECONDS.toMinutes(seenupto);
        Log.i("Seen Up to "+seenupto, ": Minute "+seenUpto);
        if (seenUpto<=100){
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setProgress(seenUpto);
        }

//        viewHolder.progressBar.getProgressDrawable().setColorFilter(Color.RED,android.graphics.PorterDuff.Mode.SRC_IN);

        viewHolder.movieName.setText(String.valueOf(recentlyWatcheds.get(position).getContentTitle()));
        viewHolder.tv_views.setText(String.valueOf(recentlyWatcheds.get(position).getViewCount()));
        viewHolder.movieYear.setText(String.valueOf(recentlyWatcheds.get(position).getYear()));


        String rating = String.valueOf(recentlyWatcheds.get(position).getRatings()).toString();
        final float ratingValue = Float.parseFloat(rating);
        viewHolder.ratingBar.setRating(ratingValue);
        viewHolder.movieRating.setText(""+ratingValue);

        Glide.with(mContext)
                .load(url+ recentlyWatcheds.get(position).getSquare_Image())
                .into(viewHolder.movieImage);




        viewHolder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
//                bundle.putInt("KEY_WISHLCONTID",table.getContentID());

                bundle.putInt("KEY_CONTENTIDS",recentlyWatcheds.get(position).getContentId());
                Log.i("Delete ContentId",""+recentlyWatcheds.get(position).getContentId());
                Log.i("Recently View",""+recentlyWatcheds.get(position).getContentId());
                Intent intent=new Intent(mContext, MovieDetailsActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });




        viewHolder.movieImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                contentid=recentlyWatcheds.get(position).getContentId();
                Log.i("Delete ContentId 1",""+contentid);

                dialogConfirm(mContext.getResources().getString(R.string.are_you_sure_you_want_to_remove_this_recently_view_video),contentid,position);

                return false;
            }
        });
//        viewHolder.movieRating.setText(String.valueOf(recentlyWatcheds.get(position).getMovieRating()));
//        viewHolder.movieImage.setImageResource(movieDetailsArrayList.get(position).getImage());
        //itemTypeId=movieDetailsArrayList.get(position).getId();



    }

    @Override
    public int getItemCount() {
        return (null != recentlyWatcheds ? recentlyWatcheds.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView movieName,movieYear,movieRating,tv_views;
        ImageView movieImage;
        RatingBar ratingBar;
        ProgressBar progressBar;

        ItemRowHolder(View view) {
            super(view);

            ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
            Drawable drawable = ratingBar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);

            this.movieName = (TextView) view.findViewById(R.id.movieName);
            this.movieYear = (TextView) view.findViewById(R.id.movieYear);
            this.movieRating = (TextView) view.findViewById(R.id.movieRating);
            this.tv_views = (TextView) view.findViewById(R.id.tv_views);
            this.movieImage = (ImageView) view.findViewById(R.id.movieImage);
            this.progressBar=(ProgressBar) view.findViewById(R.id.progress_bar_recentlyview);

        }
    }


    public void dialogConfirm(String message,int contentid,int pos){
        dialog = new Dialog(mContext);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_box_yes_no);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText(mContext.getResources().getString(R.string.confirm));
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText(Html.fromHtml("" + message));
        tv_message.setMovementMethod(new ScrollingMovementMethod());
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        String id=String.valueOf(contentid);
//        window.findViewById(R.id.tv_yes).setOnClickListener(new OnItemClickListner(removePosition, onClickCallback, "DeleteRecentlyView"));

        window.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DeleteContentViewHistory(contentid,pos);

//                  notifyDataSetChanged();
                Log.i("after click", "onClick: "+recentlyWatcheds.size());

                 notifyItemRemoved(removePosition);

//                recentlyWatcheds.clear();
//                recentlyWatcheds.remove(removePosition);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void DeleteContentViewHistory(int contentid,int pos){
        AppSession appSession=AppSession.getInstance(mContext);
        int userid=appSession.getUserDTO().getResult().getCustomerId();
        Log.i("Delete History","  "+userid+"   "+contentid);
        Call<DeleteContentViewHistoryDTO> call= RetroClient.sdaemonApi().deleteContentViewHistory(userid,contentid);
        call.enqueue(new Callback<DeleteContentViewHistoryDTO>() {
            @Override
            public void onResponse(Call<DeleteContentViewHistoryDTO> call, Response<DeleteContentViewHistoryDTO> response) {
                DeleteContentViewHistoryDTO deleteContentViewHistoryDTO=response.body();
//                int id=deleteContentViewHistoryDTO.getHistory().getContentId();
//                Log.i("Delete id","  "+id);
                recentlyWatcheds.remove(pos);
                notifyDataSetChanged();

//                Toast.makeText(mContext, ""+response.message(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<DeleteContentViewHistoryDTO> call, Throwable t) {
                Toast.makeText(mContext, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        notifyDataSetChanged();
    }
    public void dialogdismiss(){
        dialog.dismiss();
    }



}
