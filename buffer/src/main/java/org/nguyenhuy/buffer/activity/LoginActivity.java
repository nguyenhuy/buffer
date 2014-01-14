package org.nguyenhuy.buffer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import dagger.ObjectGraph;
import org.nguyenhuy.buffer.BufferApplication;
import org.nguyenhuy.buffer.R;
import org.nguyenhuy.buffer.fragment.LoginFragment;
import org.nguyenhuy.buffer.fragment.OAuthFragment;
import org.nguyenhuy.buffer.module.LoginActivityModule;

public class LoginActivity extends Activity implements LoginFragment.Delegate, OAuthFragment.Delegate {

    private ObjectGraph objectGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BufferApplication application = (BufferApplication) getApplication();
        objectGraph = application.getApplicationGraph().plus(new LoginActivityModule(this));

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
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
        Toast.makeText(this, accessToken, Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack(null, 0);
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

}
