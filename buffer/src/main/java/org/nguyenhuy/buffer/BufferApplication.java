package org.nguyenhuy.buffer;

import android.app.Application;
import dagger.ObjectGraph;
import org.nguyenhuy.buffer.controller.AccessTokenController;
import org.nguyenhuy.buffer.module.ApplicationModule;

import javax.inject.Inject;

/**
 * Created by nguyenthanhhuy on 1/14/14.
 */
public class BufferApplication extends Application {
    private ObjectGraph applicationGraph;
    @Inject
    AccessTokenController accessTokenController;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationGraph = ObjectGraph.create(new ApplicationModule(this));
        applicationGraph.inject(this);
        accessTokenController.onStart();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        accessTokenController.onStop();
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }

    public void inject(Object object) {
        applicationGraph.inject(object);
    }
}
