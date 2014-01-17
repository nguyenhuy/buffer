package org.nguyenhuy.buffer.job;

import android.os.Handler;
import com.path.android.jobqueue.Params;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by nguyenthanhhuy on 1/14/14.
 */
public abstract class Job extends com.path.android.jobqueue.Job {
    private static final int DEFAULT_RETRY_LIMIT =  3;

    @Inject
    transient Bus bus;
    @Inject
    transient Handler mainHandler;

    protected Job(Params params) {
        super(params);
    }

    @Override
    protected int getRetryLimit() {
        return DEFAULT_RETRY_LIMIT;
    }
}
