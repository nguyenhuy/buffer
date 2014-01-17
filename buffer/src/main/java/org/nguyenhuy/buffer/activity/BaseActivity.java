package org.nguyenhuy.buffer.activity;

import android.app.Activity;
import android.os.Bundle;
import com.squareup.otto.Bus;
import dagger.ObjectGraph;
import org.nguyenhuy.buffer.BufferApplication;

import javax.inject.Inject;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public abstract class BaseActivity extends Activity {
    private ObjectGraph objectGraph;
    @Inject
    protected Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BufferApplication application = (BufferApplication) getApplication();
        objectGraph = application.getApplicationGraph().plus(getModules());
        objectGraph.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    protected abstract Object[] getModules();
}