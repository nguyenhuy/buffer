package org.nguyenhuy.buffer.job;

import com.path.android.jobqueue.Params;
import org.nguyenhuy.buffer.event.FailedToGetUpdatesEvent;
import org.nguyenhuy.buffer.event.GettingUpdatesEvent;
import org.nguyenhuy.buffer.event.GotUpdatesEvent;
import org.nguyenhuy.buffer.model.response.UpdatesResponse;
import org.nguyenhuy.buffer.model.user.Update;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class GetUpdatesJob extends AuthenticatedJob {
    private String profileId;
    private String status;
    private int page;
    private int count;
    private transient UpdatesResponse response;

    public GetUpdatesJob(String profileId, String status, int page, int count) {
        super(new Params(JobPriority.UI).requireNetwork());
        this.profileId = profileId;
        this.status = status;
        this.page = page;
        this.count = count;
    }

    @Override
    public void onAdded() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                bus.post(new GettingUpdatesEvent(profileId, status));
            }
        });
    }

    @Override
    public void onRun() throws Throwable {
        if (status.equals(Update.STATUS_PENDING)) {
            response = bufferService.getPendingUpdates(profileId,
                    accessTokenController.get(), page, count);
        } else if (status.equals(Update.STATUS_SENT)) {
            response = bufferService.getSentUpdates(profileId,
                    accessTokenController.get(), page, count);
        }
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (response == null) {
                    bus.post(new FailedToGetUpdatesEvent(profileId, status));
                } else {
                    bus.post(new GotUpdatesEvent(profileId, status, page, count, response));
                }
            }
        });
    }

    @Override
    protected void onCancel() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                bus.post(new FailedToGetUpdatesEvent(profileId, status));
            }
        });
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
