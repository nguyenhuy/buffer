package org.nguyenhuy.buffer;

import android.app.Application;
import dagger.ObjectGraph;
import org.nguyenhuy.buffer.module.ApplicationModule;

/**
 * Created by nguyenthanhhuy on 1/14/14.
 */
public class BufferApplication extends Application {
    private ObjectGraph applicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationGraph = ObjectGraph.create(new ApplicationModule());
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
}
