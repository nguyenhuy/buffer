package org.nguyenhuy.buffer.job;

import com.path.android.jobqueue.Params;
import org.nguyenhuy.buffer.event.DataSource;
import org.nguyenhuy.buffer.event.FailedToGetProfilesEvent;
import org.nguyenhuy.buffer.event.GotProfilesEvent;
import org.nguyenhuy.buffer.model.user.Profile;
import org.nguyenhuy.buffer.util.LogUtils;

import java.util.List;

/**
 * Created by nguyenthanhhuy on 1/17/14.
 */
public class GetProfilesJob extends AuthenticatedJob {

    public GetProfilesJob(int priority) {
        super(new Params(priority).requireNetwork());
        LogUtils.v("GetProfile: create");
    }

    @Override
    public void onAdded() {
        LogUtils.v("GetProfile: added");
    }

    @Override
    public void onRun() throws Throwable {
        LogUtils.v("GetProfile: " + accessTokenController);
        final List<Profile> profiles = bufferService.getProfiles(accessTokenController.get());
        LogUtils.v("GetProfile: " + profiles);
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                bus.post(new GotProfilesEvent(DataSource.NETWORK, profiles));
            }
        });
    }

    @Override
    protected void onCancel() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                bus.post(new FailedToGetProfilesEvent());
            }
        });
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        LogUtils.e(throwable);
        return false;
    }
}
