package org.nguyenhuy.buffer.job;

import android.os.Handler;
import com.path.android.jobqueue.Params;
import com.squareup.otto.Bus;
import org.nguyenhuy.buffer.event.FailedToGetAccessTokenEvent;
import org.nguyenhuy.buffer.event.GotAccessTokenEvent;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import javax.inject.Inject;

/**
 * Created by nguyenthanhhuy on 1/14/14.
 */
public class GetAccessTokenJob extends Job {
    private Verifier verifier;
    @Inject
    transient OAuthService oAuthService;

    public GetAccessTokenJob(Verifier verifier) {
        super(new Params(JobPriority.UI).requireNetwork());
        this.verifier = verifier;
    }

    @Override
    public void onAdded() {
    }

    @Override
    public void onRun() throws Throwable {
        final Token accessToken = oAuthService.getAccessToken(null, verifier);
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                bus.post(new GotAccessTokenEvent(accessToken));
            }
        });
    }

    @Override
    protected void onCancel() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                bus.post(new FailedToGetAccessTokenEvent());
            }
        });
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        return false;
    }
}
