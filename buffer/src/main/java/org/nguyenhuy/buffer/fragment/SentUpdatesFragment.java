package org.nguyenhuy.buffer.fragment;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.squareup.otto.Subscribe;
import org.nguyenhuy.buffer.R;
import org.nguyenhuy.buffer.adapter.UpdatesAdapter;
import org.nguyenhuy.buffer.event.ChangedProfileEvent;
import org.nguyenhuy.buffer.event.FailedToGetUpdatesEvent;
import org.nguyenhuy.buffer.event.GettingUpdatesEvent;
import org.nguyenhuy.buffer.event.GotUpdatesEvent;
import org.nguyenhuy.buffer.model.user.Update;

import javax.inject.Inject;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class SentUpdatesFragment extends UpdatesFragment {

    @Override
    protected ArrayAdapter<Update> initAdapter() {
        return new UpdatesAdapter(getActivity());
    }

    @Override
    protected String getStatusOfUpdates() {
        return Update.STATUS_SENT;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position) {

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
}
