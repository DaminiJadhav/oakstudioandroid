package com.sdaemon.oakstudiotv.Pagination;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
//https://oakstudio.azurewebsites.net/Home/IndexWithUser#

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {
    public static final int PAGE_START = 1;
    LinearLayoutManager layoutManager;
    RecyclerView.LayoutManager mlayoutmanager;
    private static final int PAGE_SIZE = 4;

//    /**
//     * Supporting only LinearLayoutManager for now.
//     *
//     * @param layoutManager
//     */
        public PaginationScrollListener(LinearLayoutManager linearLayoutManager) {
//            this.layoutManager=linearLayoutManager;
              this.mlayoutmanager = linearLayoutManager;
        }

        public PaginationScrollListener(StaggeredGridLayoutManager staggeredGridLayoutManager) {
            this.mlayoutmanager=staggeredGridLayoutManager;
        }
        public PaginationScrollListener(GridLayoutManager LinearLayoutManager) {
            this.mlayoutmanager = LinearLayoutManager;
        }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int lastVisibleItemPosition = 0;

        int visibleItemCount = mlayoutmanager.getChildCount();
        int totalItemCount = mlayoutmanager.getItemCount();
//        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();



        if (mlayoutmanager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mlayoutmanager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mlayoutmanager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mlayoutmanager).findLastVisibleItemPosition();
        } else if (mlayoutmanager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mlayoutmanager).findLastVisibleItemPosition();
        }


        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + lastVisibleItemPosition) >= totalItemCount
                    && lastVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }


//        if (!isLoading() && !isLastPage()) {
//            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
//                    && firstVisibleItemPosition >= 0
//                    && totalItemCount >=getTotalPageCount()) {
//                loadMoreItems();
//            }
//        }

    }

//    @Override
//    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//        super.onScrollStateChanged(recyclerView, newState);
//    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }



    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}
