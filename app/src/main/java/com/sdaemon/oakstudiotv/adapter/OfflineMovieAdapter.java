package com.sdaemon.oakstudiotv.adapter;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.broadcast_receiver.BroadcastReceiverClass;
import com.sdaemon.oakstudiotv.model.VideoDTO;
import com.sdaemon.oakstudiotv.utils.AppSession;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by Rahul Patil on 23-Apr-18.
 */

public class OfflineMovieAdapter extends RecyclerView.Adapter<OfflineMovieAdapter.ItemRowHolder> {
    private static LayoutInflater inflater = null;
    Context mContext;
    Context context;
    Animation animation;
    Dialog dialog;

    public static String itemTypeId = "";
    private ArrayList<VideoDTO> list;
    private OnItemClickListner.OnClickCallback onClickCallback;
    AppSession appSession;
    String strListDate = "", delete = "";
    int removePosition = -1;
    int value;
    Drawable drawable;
    ProgressBar progressBar;
    View v1;
    TextView tvdownloadcount,tvdownloading;
    BroadcastReceiverClass receiver;
    ImageView movieImage;




    public OfflineMovieAdapter(Context mContext, Context context, ArrayList<VideoDTO> list, OnItemClickListner.OnClickCallback onClickCallback) {
        this.mContext = mContext;
        this.context = context;
        this.list = list;
        this.onClickCallback = onClickCallback;
        appSession = AppSession.getInstance(context);
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
        v1 = inflater.inflate(R.layout.item_offline_downloaded_movie, parent, false);
        viewHolder = new ItemRowHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder viewHolder, int position) {
        String url="https://oakstudio.azurewebsites.net/";

        progressBar=v1.findViewById(R.id.circularProgressbar);
        tvdownloadcount=v1.findViewById(R.id.download_percentage);
        tvdownloading=v1.findViewById(R.id.tv_downloading);
        movieImage = (ImageView) v1.findViewById(R.id.movieImage);


        delete = list.get(position).getVideoUrl();
        removePosition = position;
        VideoDTO dto = list.get(position);

        IntentFilter filter = new IntentFilter("Download_Url");
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver1, filter);
//        context.registerReceiver(broadcastReceiver1,filter);



//--------------------------------------------------------
//        Toast.makeText(mContext, ""+list.get(position).getTitle()+""+list.get(position).getImg(), Toast.LENGTH_SHORT).show();
        viewHolder.movieName.setText(list.get(position).getTitle());
        viewHolder.tv_views.setText(list.get(position).getViewcount());
        viewHolder.movieYear.setText("" + list.get(position).getYear());
        final float ratingValue = Float.parseFloat(list.get(position).getRating());
        viewHolder.ratingBar.setRating(ratingValue);
        viewHolder.movieRating.setText(""+ratingValue);


        Glide.with(mContext)
                .load(list.get(position).getImg())
                .placeholder(R.drawable.banner)
                .into(movieImage);


//--------------------------------------------------------

        if (dto != null) {
//            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
//            String json=sharedPreferences.getString("KEY_REVIEWDATA","N/A");
//            Gson gson=new Gson();
//            ContentDTO contentInfo = gson.fromJson(json, ContentDTO.class);

//            for (int i=0;i<contentInfo.getContentDetail().getContentInfos().size();i++) {
//                viewHolder.movieName.setText(contentInfo.getContentDetail().getContentInfos().get(i).getContentTitle());
//                viewHolder.tv_views.setText(contentInfo.getContentDetail().getContentInfos().get(i).getViewCount());
//                viewHolder.movieYear.setText("" + contentInfo.getContentDetail().getContentInfos().get(i).getYear());
//
//                String rating = String.valueOf(contentInfo.getContentDetail().getAvgRating().get(i).getAvgRating()).toString();
//                final float ratingValue = Float.parseFloat(rating);
//                viewHolder.ratingBar.setRating(ratingValue);
//                viewHolder.movieRating.setText("" + ratingValue);
//
//
//                Glide.with(mContext)
//                        .load(url + contentInfo.getContentDetail().getContentInfos().get(i).getPoster_Image())
//                        .into(viewHolder.movieImage);
//            }


            movieImage.setOnClickListener(new OnItemClickListner(position, onClickCallback, "play"));
            movieImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
//                    viewHolder.checkBox.setVisibility(View.VISIBLE);
//                    viewHolder.checkBox.setChecked(true);
//
//                    viewHolder.checkBox.setSelected(!view.isSelected());
//                    if (viewHolder.checkBox.isSelected()) {
//                        view.setBackgroundColor(ContextCompat.getColor(context, R.color.emailButtonColor));
//                        Log.i("Offline Selected","Select");
//                    } else {
//                        view.setBackgroundColor(Color.WHITE);
//                        Log.i("Offline Selected","None");
//                    }

//                    if (viewHolder.checkBox.isSelected()==true) {
                        delete = list.get(position).getVideoUrl();
                        removePosition = position;
                        dialogConfirm(context.getResources().getString(R.string.are_you_sure_you_want_to_remove_this_video), "Confirm");
                        System.out.println("LongClick: " + removePosition);
//                    }
                    return true;// returning true instead of false, works for me
            }
            });

        }
            strListDate = dto.getDate();
            Calendar c = Calendar.getInstance();
            viewHolder.tv_uploadDate.setText("Uploaded" + " " + strListDate);

            Calendar startDate = Calendar.getInstance();
            int mYear = startDate.get(Calendar.YEAR);
            int mMonth = startDate.get(Calendar.MONTH)+1;
            int mDay = startDate.get(Calendar.DAY_OF_MONTH) ;
            startDate.set(mYear, mMonth, mDay);
            long startDateMillis = startDate.getTimeInMillis();

            if (dto.getDate_time()!=null){
                long differenceMillis = dto.getDate_time() - startDateMillis;
                int daysDifference = (int) (differenceMillis / (1000 * 60 * 60 * 24));
                viewHolder.tv_remDays.setText("Days left: "+String.valueOf(daysDifference));
            }
    }

    @Override
    public int getItemCount(){
            return (null != list ? list.size() : 0);
    }

    class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView movieName, movieYear, movieRating, tv_views, tv_uploadDate, tv_remDays,tvdownloadcount;
        ImageView movieImage;
        RatingBar ratingBar;
        CheckBox checkBox;
        LinearLayout lloffline;
//      ProgressBar progressBar;

        ItemRowHolder(View view) {
            super(view);

            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            Drawable drawable = ratingBar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#FEF400"), PorterDuff.Mode.SRC_ATOP);
            this.movieName = (TextView) view.findViewById(R.id.movieName);
            this.movieYear = (TextView) view.findViewById(R.id.movieYear);
            this.movieRating = (TextView) view.findViewById(R.id.movieRating);
//            this.tvdownloadcount=view.findViewById(R.id.download_percentage);
//            this.progressBar = (ProgressBar) view.findViewById(R.id.circularProgressbar);

            this.ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

            this.tv_views = (TextView) view.findViewById(R.id.tv_views);
            this.tv_uploadDate = (TextView) view.findViewById(R.id.tv_uploadDate);
            this.tv_remDays = (TextView) view.findViewById(R.id.tv_remDays);
//            this.movieImage = (ImageView) view.findViewById(R.id.movieImage);
            this.lloffline = (LinearLayout) view.findViewById(R.id.ll_offlinedownload);


            this.checkBox = (CheckBox) view.findViewById(R.id.checkbox_movie);

        }
    }

    public long returnDays(String startDate, String endDate) {
        long days = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
//            LocalDate  start = LocalDate.parse("2/3/2017",formatter);
//            LocalDate  end = LocalDate.parse("3/3/2017",formatter);

            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            //  System.out.println(ChronoUnit.DAYS.between(start, end)); // 28
            System.out.println(ChronoUnit.DAYS.between(end, start)); // 28
            // Log.i(String.valueOf(this), "@@@@@@@@@@@@@@@ :"+ChronoUnit.DAYS.between(start, end)); // 28
            days = ChronoUnit.DAYS.between(start, end);
        }
        return days;

    }

    public void dialogConfirm(String message,CharSequence title) {
        dialog = new Dialog(context);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_box_yes_no);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
        tv_title.setText(title);
        TextView tv_message = (TextView) window.findViewById(R.id.tv_message);
        tv_message.setText(Html.fromHtml("" + message));
        tv_message.setMovementMethod(new ScrollingMovementMethod());
        window.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        window.findViewById(R.id.tv_yes).setOnClickListener(new OnItemClickListner(removePosition, onClickCallback, "downloaded_delete"));

//        window.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (appSession != null) {
//                    Toast.makeText(context, "removePosition " + removePosition, Toast.LENGTH_LONG).show();
//                    list.remove(removePosition);
//
//                    DownloadAction downloadAction = HlsDownloadAction.createRemoveAction(Uri.parse(delete), null);
//                    DownloadService.startWithAction(context, DemoDownloadService.class, downloadAction, true);
//                    appSession.setOfflineDownloadList(list);
//                    if (appSession.getOfflineDownloadList() != null && appSession.getOfflineDownloadList().size() < 1) {
//                        Toast.makeText(context, "NO OFFLINE DOWNLOADED VIDEO", Toast.LENGTH_LONG).show();
//                    }
//                }
//                notifyDataSetChanged();
//                dialog.dismiss();
//            }
//        });
        dialog.show();
    }



    public BroadcastReceiver broadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("onReceiver", "onRecive download");
            if (intent.getAction().equals("Download_Url")) {
                if (SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    HashMap<Uri, Integer> hashMap = (HashMap<Uri, Integer>) intent.getSerializableExtra("Download_Map");
                    //int prog4ress1= intent.getIntExtra("progress", 0);
                    //  Log.i(TAG,"notification "+progress1);

                    Set keys = hashMap.keySet();
                    for (Iterator i = keys.iterator(); i.hasNext(); ) {
                        Uri key = (Uri) i.next();
                        value = hashMap.get(key);
                        Log.i("Offline Download %",""+value);

                        movieImage.setAlpha((float) 0.5);
//                        movieImage.setEnabled(false);
                        movieImage.setClickable(false);
                        movieImage.setLongClickable(false);

                        tvdownloadcount.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        tvdownloading.setVisibility(View.VISIBLE);
                        tvdownloading.setText("Downloading...");
                        progressBar.setProgress(value);
                        tvdownloadcount.setText(String.format("%d%%", value));


//                        drawable = resources.getDrawable(R.drawable.circularprogressbar);
//                        progressBar.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
//                        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.emailButtonColor), PorterDuff.Mode.SRC_IN);
//                        progressBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(context, R.color.emailButtonColor), PorterDuff.Mode.SRC_IN);
//                        progressBar.setProgressDrawable(drawable);

                        progressBar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CharSequence title=Html.fromHtml("<font color='#D93723'>"+context.getResources().getString(R.string.download_status)+"</font>");

                                dialogConfirm(context.getResources().getString(R.string.do_you_want_to_stop_download),title);

//                                Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
//                                if (appSession != null) {
//                                    Toast.makeText(context, "removePosition " + removePosition, Toast.LENGTH_LONG).show();
//                                    list.remove(removePosition);
//
//                                    DownloadAction downloadAction = HlsDownloadAction.createRemoveAction(Uri.parse(delete),     null);
//                                    DownloadService.startWithAction(context, DemoDownloadService.class, downloadAction, true);
//                                    appSession.setOfflineDownloadList(list);
//                                    if (appSession.getOfflineDownloadList() != null && appSession.getOfflineDownloadList().size() < 1) {
//                                        Toast.makeText(context, "NO OFFLINE DOWNLOADED VIDEO", Toast.LENGTH_LONG).show();
//
//                                    }
//                                }
//                               notifyDataSetChanged();

                            }
                        });


                        if (value==100){
                            tvdownloadcount.setVisibility(View.GONE);
                            tvdownloading.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
//                            movieImage.setEnabled(true);
                            movieImage.setAlpha((float) 1.0);
                            movieImage.setLongClickable(true);
                            movieImage.setClickable(true);

                        }

                    }
                }
            }
        }
    };


    public void dialogdismiss(){
        dialog.dismiss();
    }




    public void myOnDestroy(){
//        context.unregisterReceiver(broadcastReceiver);
    }




}
