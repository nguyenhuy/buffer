package org.nguyenhuy.buffer.module;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import dagger.Module;
import dagger.Provides;
import org.nguyenhuy.buffer.BufferApplication;
import org.nguyenhuy.buffer.controller.UserController;
import org.nguyenhuy.buffer.controller.UserControllerImpl;

import javax.inject.Singleton;

/**
 * Created by nguyenthanhhuy on 1/14/14.
 */
@Module(
        injects = {
                BufferApplication.class
        },
        library = true
)
public class ApplicationModule {
    private BufferApplication application;

    public ApplicationModule(BufferApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    UserController provideUserController(UserControllerImpl impl) {
        return impl;
    }

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
