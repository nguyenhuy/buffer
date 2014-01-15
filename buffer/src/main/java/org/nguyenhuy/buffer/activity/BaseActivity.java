package org.nguyenhuy.buffer.activity;

import android.app.Activity;
import android.os.Bundle;
import dagger.ObjectGraph;
import org.nguyenhuy.buffer.BufferApplication;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public abstract class BaseActivity extends Activity {
    private ObjectGraph objectGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BufferApplication application = (BufferApplication) getApplication();
        objectGraph = application.getApplicationGraph().plus(getModules());
        objectGraph.inject(this);
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    protected abstract Object[] getModules();
}