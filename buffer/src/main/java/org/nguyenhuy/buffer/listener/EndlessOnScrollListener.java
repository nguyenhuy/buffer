package org.nguyenhuy.buffer.listener;

import android.widget.AbsListView;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public abstract class EndlessOnScrollListener implements AbsListView.OnScrollListener {
    private int visibleThreashold;

    public EndlessOnScrollListener(int visibleThreashold) {
        this.visibleThreashold = visibleThreashold;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Don't take any action on changed
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        totalItemCount = totalItemCount - getHeaderViewsCount() - getFooterViewsCount();
        if (totalItemCount > 0 && !isLoading()
                && firstVisibleItem + visibleItemCount >= totalItemCount - visibleThreashold) {
            loadMoreResults();
        }
    }

    public abstract int getHeaderViewsCount();

    public abstract int getFooterViewsCount();

    public abstract boolean isLoading();

    public abstract void loadMoreResults();
}
