package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.activity.MovieDetailsActivity;
import com.sdaemon.oakstudiotv.model.ContentInfo;

import java.util.ArrayList;
import java.util.List;

public class SearchItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM = 1;
    private static final int LOADING = 0;
    private List<ContentInfo> getcontentInfo;
    private boolean retryPageLoad = false;
    private String errorMsg;
    int itemcount=0;
    private Context context;
    private SearchAdapterListener searchAdapterListener;

    private boolean isLoadingAdded = false;

    public SearchItemsAdapter(Context context,SearchAdapterListener listener) {
        this.context = context;
        getcontentInfo = new ArrayList<>();
        this.searchAdapterListener=listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:

                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list_movie, parent, false);
        viewHolder = new ItemViewHolder(v1);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ContentInfo getcontent = getcontentInfo.get(position); // Movie
        switch (getItemViewType(position)) {
            case ITEM:
                String url="https://oakstudio.azurewebsites.net/";
                final ItemViewHolder itemRowHolder = (ItemViewHolder) holder;
                itemRowHolder.tv_itemName.setText(""+getcontent.getContentTitle());
                itemRowHolder.tv_years.setText("("+getcontent.getYear()+")");
                itemRowHolder.tvview.setText(getcontent.getViewCount());
//                itemRowHolder.ratingBar.setRating(getcontent.getRatings());


                String rating = String.valueOf(getcontent.getRatings()).toString();
                final float ratingValue = Float.parseFloat(rating);
                itemRowHolder.ratingBar.setRating(ratingValue);
                itemRowHolder.tvsetratingcount.setText(""+ratingValue);
//                itemRowHolder.tvsetratingcount.setText(""+getcontent.getCc());

//
//                if (getDish.getOld_price().equals("0.00")) {
//                    itemRowHolder.tv_ProductOldPrice.setVisibility(View.GONE);
//                } else {
//                    itemRowHolder.tv_ProductOldPrice.setVisibility(View.GONE);
//                }
//                itemRowHolder.tv_unit.setText("View Count : " + getcontent.getViewCount());
//
//                itemRowHolder.tv_ProductOldPrice.setPaintFlags(itemRowHolder.tv_ProductOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                                Glide.with(context)
                        .load(url+ getcontent.getSqImage())
                        .into(itemRowHolder.iv_item);

//                                .placeholder(R.mipmap.view)

                ((ItemViewHolder) holder).iv_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle=new Bundle();
//                bundle.putInt("KEY_WISHLCONTID",table.getContentID());
                        bundle.putInt("KEY_CONTENTIDS",getcontent.getContentID());
                        Log.i("Search Content Id",""+getcontent.getContentID());
                        Intent intent=new Intent(context, MovieDetailsActivity.class);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });




//                                itemcount++;
//                                if (itemcount==5){
//                                    int lastid=getcontent.getContentID();
//                                    itemcount=0;
//                                }


//  .error(R.mipmap.ic_launcher)
//.centerCrop()
//  .placeholder(R.drawable.placeholder)
//  .override(300, 200)


                break;

            case LOADING:
                LoadingVH loadingVH= (LoadingVH) holder;
                loadingVH.progressBar.setVisibility(View.VISIBLE);
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return getcontentInfo == null ? 0 : getcontentInfo.size();
    }


    public void add(ContentInfo r) {
        getcontentInfo.add(r);
        notifyItemInserted(getcontentInfo.size() - 1);
    }


    public void addAll(List<ContentInfo> contentResults) {
        for (ContentInfo result : contentResults) {
            add(result);
        }
    }

    public void remove(ContentInfo contentInfo) {
        int position = getcontentInfo.indexOf(contentInfo);
        if (position > -1) {
            getcontentInfo.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ContentInfo());
    }


    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = getcontentInfo.size() - 1;
        ContentInfo result = getItem(position);
        if (result != null) {
            getcontentInfo.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ContentInfo getItem(int position) {
        Log.i("Search Position",""+position);
//        if (position==-1){
//            return getcontentInfo.get(-1);
//        }else {
        return getcontentInfo.get(position);
//    }
    }


    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(getcontentInfo.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    public List<ContentInfo> getItems() {
        return getcontentInfo;
    }



    @Override
    public int getItemViewType(int position) {
        return (position == getcontentInfo.size() - 1 && isLoadingAdded) ? LOADING : ITEM;

    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_item;
        TextView tv_itemName, tv_years,tvview,tvsetratingcount;
        RatingBar ratingBar;

        public ItemViewHolder(View view) {
            super(view);

            this.iv_item = (ImageView) view.findViewById(R.id.movieImage);
            this.tv_itemName = (TextView) view.findViewById(R.id.movieName);
            this.tv_years = (TextView) view.findViewById(R.id.movieYear);
            this.tvview = (TextView) view.findViewById(R.id.tv_views);
            this.tvsetratingcount = (TextView) view.findViewById(R.id.movieRating);
            this.ratingBar=view.findViewById(R.id.ratingBar);






            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchAdapterListener.onSearchSelected(getcontentInfo.get(getAdapterPosition()));

//                        Intent intent = new Intent(context, ProductDetailsActivity.class);
//                        intent.putExtra("productId", getcontent.getContentID());
//                        context.startActivity(intent);
                }
            });
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingVH(View itemView) {
            super(itemView);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);

        }
    }

    public interface SearchAdapterListener{
        void onSearchSelected(ContentInfo contentInfo);
    }


}
