package com.sdaemon.oakstudiotv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdaemon.oakstudiotv.R;
import com.sdaemon.oakstudiotv.model.ContentInfo;
import com.sdaemon.oakstudiotv.model.SearchData;

import java.util.ArrayList;
import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> implements Filterable {
    private List<SearchData> searchDataList;
    private List<SearchData> searchData;
//    private SearchAdapterListener searchAdapterListener;
    private Context context;

    List<ContentInfo> getcontentInfo=new ArrayList<>();
    private boolean isLoadingAdded = false;






    public SearchItemAdapter(){

    }


//    public SearchItemAdapter(List searchDataList,Context context,SearchAdapterListener listener){
//        this.searchDataList=searchDataList;
//        this.context=context;
//        this.searchAdapterListener=listener;
//        this.searchData=searchDataList;
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchData data = searchData.get(position);
//        Random rnd = new Random();
//        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//        holder.layout.setBackgroundColor(currentColor);
        holder.name.setText(data.getName());
        holder.age.setText(String.valueOf(data.getAge()));
        holder.imageView.setImageResource(data.getImage());


//        Glide.with(context)
//                .load(contact.getImage())
//                .apply(RequestOptions.circleCropTransform())
//                .into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return searchData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,age;
        LinearLayout parent,layout;
        ImageView imageView;
        public  ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);

            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            imageView = itemView.findViewById(R.id.iv_imagessearch);

                itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                        searchAdapterListener.onSearchSelected(searchDataList.get(getAdapterPosition()));
                }
            });
        }
    }
//    public void add(ContentInfo contentInfo) {
//        movieList.add(contentInfo);
//        notifyItemInserted(movieList.size() - 1);
//    }



    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                     searchData=searchDataList;
                }else {
                    List<SearchData> filteredList = new ArrayList<>();
                    for (SearchData row : searchDataList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

//                        || row.getAge().contains(charSequence)
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    searchData = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = searchData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                searchData = (ArrayList<SearchData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
        return getcontentInfo.get(position);
    }




//    private ArrayList<AndroidVersion> data = new ArrayList<>();
////    public void swapData(ArrayList<AndroidVersion> data) {
////        this.data.addAll(data);
////        notifyDataSetChanged();
////    }

//    public interface SearchAdapterListener{
//        void onSearchSelected(SearchData searchData);
//    }



//                searchView=(androidx.appcompat.widget.SearchView)findViewById(R.id.searchview);
//                searchView.setSubmitButtonEnabled(true);
//                searchView.setIconified(false);








//    https://www.androidhive.info/2017/11/android-recyclerview-with-search-filter-functionality/
//    if (androidVersion.getApi().toLowerCase().contains(charString) || androidVersion.getName().toLowerCase().contains(charString) || androidVersion.getVer().toLowerCase().contains(charString)) {
//https://learnpainless.com/android/retrofit2/recyclerview/pagination-in-list-recyclerview-using-youtube-data-api-android
//    http://androidkt.com/rest-api-pagination-paging-library/
    }

