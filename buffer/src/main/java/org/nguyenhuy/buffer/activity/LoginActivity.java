package org.nguyenhuy.buffer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Subscribe;
import org.nguyenhuy.buffer.R;
import org.nguyenhuy.buffer.controller.UserController;
import org.nguyenhuy.buffer.event.UserAvailableEvent;
import org.nguyenhuy.buffer.event.UserChangedEvent;
import org.nguyenhuy.buffer.fragment.LoginFragment;
import org.nguyenhuy.buffer.fragment.OAuthFragment;
import org.nguyenhuy.buffer.model.user.User;
import org.nguyenhuy.buffer.module.ForActivity;
import org.nguyenhuy.buffer.module.LoginActivityModule;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements LoginFragment.Delegate, OAuthFragment.Delegate {
    @Inject
    UserController userController;
    @Inject
    @ForActivity
    JobManager jobManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        jobManager.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        jobManager.stop();
    }

    @Override
    protected Object[] getModules() {
        return new Object[]{
                new LoginActivityModule(this)
        };
    }

    @Override
    public void login() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new OAuthFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void oAuthFailed() {
        Toast.makeText(this, R.string.prompt_oauth_failed, Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStackImmediate(null, 0);
    }

    @Override
    public void oAuthSuccess(String accessToken) {
        getFragmentManager().popBackStackImmediate(null, 0);
        userController.setUser(new User(accessToken));
    }

    @Subscribe
    public void onUserChanged(UserChangedEvent event) {
        User user = event.getUser();
        if (user != null && user.isAuthenticated()) {
            startMainActivity();
        }
    }

    @Subscribe
    public void onUserAvailable(UserAvailableEvent event) {
        startMainActivity();
    }

    private void startMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
