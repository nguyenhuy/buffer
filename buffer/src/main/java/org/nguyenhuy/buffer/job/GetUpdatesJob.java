package org.nguyenhuy.buffer.job;

import com.path.android.jobqueue.Params;
import org.nguyenhuy.buffer.event.FailedToGetUpdatesEvent;
import org.nguyenhuy.buffer.event.GettingUpdatesEvent;
import org.nguyenhuy.buffer.event.GotUpdatesEvent;
import org.nguyenhuy.buffer.model.request.UpdatesRequest;
import org.nguyenhuy.buffer.model.response.UpdatesResponse;
import org.nguyenhuy.buffer.model.user.Update;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class GetUpdatesJob extends AuthenticatedJob {
    private UpdatesRequest request;
    private transient UpdatesResponse response;

    public GetUpdatesJob(UpdatesRequest request) {
        super(new Params(JobPriority.UI).requireNetwork());
        this.request = request;
    }

    @Override
    public void onAdded() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                bus.post(new GettingUpdatesEvent(request));
            }
        });
    }

    @Override
    public void onRun() throws Throwable {
        String status = request.getStatus();
        if (status.equals(Update.STATUS_PENDING)) {
            response = bufferService.getPendingUpdates(request.getProfileId(),
                    accessTokenController.get(),
                    request.getPage(),
                    request.getCount());
        } else if (status.equals(Update.STATUS_SENT)) {
            response = bufferService.getSentUpdates(request.getProfileId(),
                    accessTokenController.get(),
                    request.getPage(),
                    request.getCount());
        }
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (response == null) {
                    bus.post(new FailedToGetUpdatesEvent(request));
                } else {
                    bus.post(new GotUpdatesEvent(request, response));
                }
            }
        });
    }

    @Override
    protected void onCancel() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                bus.post(new FailedToGetUpdatesEvent(request));
            }
        });
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
