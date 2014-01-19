package org.nguyenhuy.buffer.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.squareup.otto.Bus;
import org.nguyenhuy.buffer.R;
import org.nguyenhuy.buffer.controller.UpdatesController;
import org.nguyenhuy.buffer.delegate.InjectDelegate;
import org.nguyenhuy.buffer.event.*;
import org.nguyenhuy.buffer.listener.EndlessOnScrollListener;
import org.nguyenhuy.buffer.model.user.Update;

import javax.inject.Inject;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public abstract class UpdatesFragment extends ListFragment {
    private static final int ITEMS_VISIBLE_THRESHOLD = 5;
    private static final int ITEMS_PER_PAGE = 10;

    public static interface Delegate extends InjectDelegate {
        String getCurrentProfileId();
    }

    @Inject
    LayoutInflater layoutInflater;
    @Inject
    Bus bus;
    @Inject
    UpdatesController updatesController;
    private Delegate delegate;
    private ArrayAdapter<Update> listAdapter;
    private View emptyView;
    private View progressContainer;
    private View footerProgressView;
    private boolean isLoading;
    private int currentPage;
    private boolean loadedAllUpdates;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        delegate = (Delegate) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_updates, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyView = view.findViewById(android.R.id.empty);
        progressContainer = view.findViewById(R.id.progress_container);
        getListView().setOnScrollListener(new EndlessOnScrollListener(ITEMS_VISIBLE_THRESHOLD) {
            @Override
            public int getHeaderViewsCount() {
                return getListView().getHeaderViewsCount();
            }

            @Override
            public int getFooterViewsCount() {
                return getListView().getFooterViewsCount();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public void loadMoreResults() {
                loadMore();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        delegate.inject(this);

        // Init loading footer for the list view.
        // Prior to KitKat, this should be done before setting setting adapter
        View footerView = layoutInflater.inflate(R.layout.list_view_progress,
                getListView(), false);
        footerProgressView = footerView.findViewById(R.id.progress_bar);
        getListView().addFooterView(footerView);

        listAdapter = initAdapter();
        setListAdapter(listAdapter);
        updateProgress();
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    public final void onListItemClick(ListView l, View v, int position, long id) {
        if (v != footerProgressView && position >= 0 && position < listAdapter.getCount()) {
            onListItemClick(l, v, position);
        }
    }

    protected void loadMore() {
        if (!loadedAllUpdates && !isLoading) {
            updatesController.load(delegate.getCurrentProfileId(),
                    getStatusOfUpdates(), currentPage + 1, ITEMS_PER_PAGE);
        }
    }

    protected void onGettingUpdates(GettingUpdatesEvent event) {
        if (shouldUpdateView(event)) {
            isLoading = true;
            updateProgress();
        }
    }

    protected void onGotUpdates(GotUpdatesEvent event) {
        if (shouldUpdateView(event)) {
            listAdapter.addAll(event.getResponse().getUpdates());
            loadedAllUpdates = listAdapter.getCount() >= event.getResponse().getTotal();
            isLoading = false;
            currentPage = event.getPage();
            updateProgress();
        }
    }

    protected void onFailedToGetUpdates(FailedToGetUpdatesEvent event) {
        if (shouldUpdateView(event)) {
            Toast.makeText(getActivity(), R.string.prompt_failed_to_get_updates, Toast.LENGTH_LONG)
                    .show();
            isLoading = false;
            updateProgress();
        }
    }

    protected void onChangedProfile(ChangedProfileEvent event) {
        listAdapter.clear();
        isLoading = false;
        currentPage = 0;
        loadedAllUpdates = false;
        updateProgress();
        loadMore();
    }

    private boolean shouldUpdateView(UpdatesEvent event) {
        return delegate.getCurrentProfileId().equals(event.getProfileId())
                && getStatusOfUpdates().equals(event.getStatus());
    }

    private void updateProgress() {
        emptyView.setVisibility(View.GONE);
        progressContainer.setVisibility(View.GONE);
        footerProgressView.setVisibility(View.GONE);
        getListView().setVisibility(View.GONE);

        if (isLoading && listAdapter.getCount() > 0) {
            footerProgressView.setVisibility(View.VISIBLE);
            getListView().setVisibility(View.VISIBLE);
            return;
        }

        if (isLoading) {
            progressContainer.setVisibility(View.VISIBLE);
            return;
        }

        if (listAdapter.getCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            return;
        }

        getListView().setVisibility(View.VISIBLE);
    }

    protected abstract ArrayAdapter<Update> initAdapter();

    /**
     * Returns status of updates that are showed in the fragment. It is used
     * to construct {@link org.nguyenhuy.buffer.job.GetUpdatesJob} when needed
     * and manage loading operations;
     */
    protected abstract String getStatusOfUpdates();

    protected abstract void onListItemClick(ListView l, View v, int position);
}
