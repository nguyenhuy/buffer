package org.nguyenhuy.buffer.controller;

import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import org.nguyenhuy.buffer.event.GettingUpdatesEvent;
import org.nguyenhuy.buffer.event.GotUpdatesEvent;
import org.nguyenhuy.buffer.job.GetUpdatesJob;
import org.nguyenhuy.buffer.model.request.UpdatesRequest;
import org.nguyenhuy.buffer.model.response.UpdatesResponse;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class manages updates of current user. Data is cached in memory.
 * Note that this controller must be tied to lifecycle of an application or
 * an activity (depends on which object hosts it). So {@link #onStart()} and
 * {@link #onStop()} must be called according to lifecycle of the host.
 */
public class UpdatesController {
    private JobManager jobManager;
    private Bus bus;
    private TreeMap<UpdatesRequest, UpdatesResponse> cache;
    private TreeSet<UpdatesRequest> loadingRequests;

    public UpdatesController(JobManager jobManager, Bus bus) {
        this.jobManager = jobManager;
        this.bus = bus;
        this.cache = new TreeMap<UpdatesRequest, UpdatesResponse>();
        this.loadingRequests = new TreeSet<UpdatesRequest>();
    }

    public void onStart() {
        bus.register(this);
    }

    public void onStop() {
        bus.unregister(this);
    }

    /**
     * Loads updates of a profile. Subscribers will receive a
     * {@link org.nguyenhuy.buffer.event.GotUpdatesEvent} when loading finished.
     */
    public void load(UpdatesRequest request) {
        if (cache.containsKey(request)) {
            bus.post(new GotUpdatesEvent(request, cache.get(request)));
        } else if (loadingRequests.contains(request)) {
            bus.post(new GettingUpdatesEvent(request));
        } else {
            GetUpdatesJob job = new GetUpdatesJob(request);
            jobManager.addJobInBackground(job);
            loadingRequests.add(request);
        }
    }

    @Subscribe
    public void onGotUpdates(GotUpdatesEvent event) {
        loadingRequests.remove(event.getRequest());
        cache.put(event.getRequest(), event.getResponse());
    }

    public void clear() {
        cache.clear();
        loadingRequests.clear();
    }
}
