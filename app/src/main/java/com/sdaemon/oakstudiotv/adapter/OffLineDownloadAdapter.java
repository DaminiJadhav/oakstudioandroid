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
import com.sdaemon.oakstudiotv.model.VideoDTO;
import com.sdaemon.oakstudiotv.utils.OnItemClickListner;

import java.util.List;


public class OffLineDownloadAdapter extends RecyclerView.Adapter<OffLineDownloadAdapter.ViewHolder>{
    private List<VideoDTO> list;
    private Context context;
    private OnItemClickListner.OnClickCallback onClickCallback;

//    https://stackoverflow.com/questions/24889575/broadcast-receiver-in-baseadaper
    public OffLineDownloadAdapter(Context context, List<VideoDTO> list,
                                  OnItemClickListner.OnClickCallback onClickCallback) {
        this.context = context;
        this.list = list;
        this.onClickCallback = onClickCallback;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offline_download, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setTag(holder);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        VideoDTO dto = list.get(position);
        if (dto != null) {
            holder.tvTitle.setText(dto.getVideoUrl());
            holder.ivDownload.setOnClickListener(new OnItemClickListner(position, onClickCallback, "remove"));
            holder.tvTitle.setOnClickListener(new OnItemClickListner(position, onClickCallback, "play"));
            holder.ivPause.setOnClickListener(new OnItemClickListner(position, onClickCallback, "pause"));
            holder.ivStart.setOnClickListener(new OnItemClickListner(position, onClickCallback, "start"));
            holder.ivResume.setOnClickListener(new OnItemClickListner(position, onClickCallback, "resume"));
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

        TextView tvTitle;
        ImageView ivThumbnail,ivDownload,ivPause,ivStart,ivResume;

        public ViewHolder(View v) {
            super(v);
            llItem = v.findViewById(R.id.ll_item);
            tvTitle = v.findViewById(R.id.tv_title);
            ivDownload = v.findViewById(R.id.iv_download);
            ivPause = v.findViewById(R.id.iv_pause);
            ivStart = v.findViewById(R.id.iv_start);
            ivResume = v.findViewById(R.id.iv_resume);
            ivThumbnail = v.findViewById(R.id.iv_thumbnail);

        }
    }


}

