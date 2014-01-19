package org.nguyenhuy.buffer.controller;

import com.path.android.jobqueue.JobManager;
import org.nguyenhuy.buffer.job.GetUpdatesJob;
import org.nguyenhuy.buffer.module.ForActivity;

import javax.inject.Inject;

/**
 * Created by nguyenthanhhuy on 1/19/14.
 * <p/>
 * This class manages updates of current user. Due to several reasons, it doesn't
 * cache data and always load from network when requested.
 */
public class UpdatesController {
    @Inject
    @ForActivity
    JobManager jobManager;

    /**
     * Loads updates of a profile. Subscribers will receive a
     * {@link org.nguyenhuy.buffer.event.GotUpdatesEvent} when loading finished.
     */
    public void load(String profileId, String status, int page, int count) {
        GetUpdatesJob job = new GetUpdatesJob(profileId, status, page, count);
        jobManager.addJobInBackground(job);
    }
}
