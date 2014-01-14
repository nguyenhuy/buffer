package org.nguyenhuy.buffer.module;

import android.os.Handler;
import android.os.Looper;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by nguyenthanhhuy on 1/14/14.
 */
@Module(
        library = true
)
public class ApplicationModule {
    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus(ThreadEnforcer.MAIN);
    }

    @Provides
    Handler provideMainHandler() {
        return new Handler(Looper.getMainLooper());
    }
}
