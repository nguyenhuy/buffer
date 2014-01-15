package org.nguyenhuy.buffer.activity;

import android.app.Activity;
import android.os.Bundle;
import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import dagger.ObjectGraph;
import org.nguyenhuy.buffer.BufferApplication;
import org.nguyenhuy.buffer.R;
import org.nguyenhuy.buffer.controller.UserController;
import org.nguyenhuy.buffer.event.UserAvailableEvent;
import org.nguyenhuy.buffer.event.UserChangedEvent;
import org.nguyenhuy.buffer.fragment.LoginFragment;
import org.nguyenhuy.buffer.fragment.OAuthFragment;
import org.nguyenhuy.buffer.model.User;
import org.nguyenhuy.buffer.module.ForActivity;
import org.nguyenhuy.buffer.module.LoginActivityModule;

import javax.inject.Inject;

public class LoginActivity extends Activity implements LoginFragment.Delegate, OAuthFragment.Delegate {

    private ObjectGraph objectGraph;
    @Inject
    Bus bus;
    @Inject
    UserController userController;
    @Inject
    @ForActivity
    JobManager jobManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BufferApplication application = (BufferApplication) getApplication();
        objectGraph = application.getApplicationGraph().plus(new LoginActivityModule(this));
        objectGraph.inject(this);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
        jobManager.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
        jobManager.stop();
    }

    @Override
    public void login() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new OAuthFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void inject(OAuthFragment fragment) {
        objectGraph.inject(fragment);
    }

    @Override
    public void oAuthFailed() {
        getFragmentManager().popBackStack(null, 0);
    }

    @Override
    public void oAuthSuccess(String accessToken) {
        getFragmentManager().popBackStack(null, 0);
        userController.setUser(new User(accessToken));
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    @Subscribe
    public void onUserChanged(UserChangedEvent event) {
        User user = event.getUser();
        if (user != null && user.isAuthenticated()) {
            //TODO start main activity
        }
    }

    @Subscribe
    public void onUserAvailable(UserAvailableEvent event) {
        //TODO start main activity
    }
}
