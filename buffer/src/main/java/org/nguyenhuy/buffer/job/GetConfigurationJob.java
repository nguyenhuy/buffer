package org.nguyenhuy.buffer.job;

import com.path.android.jobqueue.Params;
import org.nguyenhuy.buffer.api.BufferService;
import org.nguyenhuy.buffer.controller.UserController;
import org.nguyenhuy.buffer.event.ConfigurationAvailableEvent;
import org.nguyenhuy.buffer.event.FailedToGetConfigurationEvent;
import org.nguyenhuy.buffer.model.Configuration;

import javax.inject.Inject;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class GetConfigurationJob extends Job {
    @Inject
    transient BufferService bufferService;
    @Inject
    transient UserController userController;

    public GetConfigurationJob() {
        super(new Params(JobPriority.UI).requireNetwork());
    }

    @Override
    public void onAdded() {
    }

    @Override
    public void onRun() throws Throwable {
        // Assume that user is authenticated before this job is called.
        // May throw exception here to report unauthenticated user.
        String accessToken = userController.getUser().getAccessToken();
        final Configuration config = bufferService.getConfiguration(accessToken);
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                bus.post(new ConfigurationAvailableEvent(config));
            }
        });
    }

    @Override
    protected void onCancel() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                bus.post(new FailedToGetConfigurationEvent());
            }
        });
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
