package com.github.bubinimara.movies.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by davide.
 * taken from [@link https://gist.githubusercontent.com/ssinss/e06f12ef66c51252563e/raw/cc3029c6806dd2f0939fcac04e9c43e7155fea84/EndlessRecyclerOnScrollListener.java ]
 *
 */

class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private MovieAdapter.OnLoadMore listener;

    public EndlessRecyclerOnScrollListener() {

    }

    public void setListener(MovieAdapter.OnLoadMore listener) {
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if(!(layoutManager instanceof LinearLayoutManager) | listener == null) {
            return;
        }

        LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) layoutManager;
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;


            listener.onLoadMore(current_page);

            loading = true;
        }
    }
}
