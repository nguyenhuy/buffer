package org.nguyenhuy.buffer.fragment;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.squareup.otto.Subscribe;
import org.nguyenhuy.buffer.R;
import org.nguyenhuy.buffer.adapter.PendingUpdatesAdapter;
import org.nguyenhuy.buffer.adapter.UpdatesAdapter;
import org.nguyenhuy.buffer.event.ChangedProfileEvent;
import org.nguyenhuy.buffer.event.FailedToGetUpdatesEvent;
import org.nguyenhuy.buffer.event.GettingUpdatesEvent;
import org.nguyenhuy.buffer.event.GotUpdatesEvent;
import org.nguyenhuy.buffer.model.user.Update;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class PendingUpdatesFragment extends UpdatesFragment {

    @Override
    protected ArrayAdapter<Update> initAdapter() {
        return new PendingUpdatesAdapter(getActivity());
    }

    @Override
    protected String getStatusOfUpdates() {
        return Update.STATUS_PENDING;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position) {
        getActivity().openContextMenu(l);
    }

    @Subscribe
    public void onGettingUpdates(GettingUpdatesEvent event) {
        super.onGettingUpdates(event);
    }

    @Subscribe
    public void onGotUpdates(GotUpdatesEvent event) {
        super.onGotUpdates(event);
    }

    @Subscribe
    public void onFailedToGetUpdates(FailedToGetUpdatesEvent event) {
        super.onFailedToGetUpdates(event);
    }

    @Subscribe
    public void onChangedProfile(ChangedProfileEvent event) {
        super.onChangedProfile(event);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerForContextMenu(getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.pending_updates, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info
                = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_share_now:
            case R.id.action_edit:
            case R.id.action_delete:
            default:
                return super.onContextItemSelected(item);
        }
    }
}

