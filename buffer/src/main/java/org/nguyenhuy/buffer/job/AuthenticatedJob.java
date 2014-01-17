package org.nguyenhuy.buffer.job;

import com.path.android.jobqueue.Params;
import org.nguyenhuy.buffer.api.BufferService;
import org.nguyenhuy.buffer.controller.UserController;

import javax.inject.Inject;

/**
 * Created by nguyenthanhhuy on 1/17/14.
 *
 * This class represents jobs that can only be executed by an authenticated user.
 */
public abstract class AuthenticatedJob extends Job {
    @Inject
    transient UserController userController;
    @Inject
    transient BufferService bufferService;

    protected AuthenticatedJob(Params params) {
        super(params);
    }
}
