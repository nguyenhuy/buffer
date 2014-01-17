package org.nguyenhuy.buffer.module;

import com.path.android.jobqueue.BaseJob;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.di.DependencyInjector;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;
import org.nguyenhuy.buffer.activity.MainActivity;
import org.nguyenhuy.buffer.api.BufferService;
import org.nguyenhuy.buffer.controller.ConfigurationController;
import org.nguyenhuy.buffer.controller.ProfilesController;
import org.nguyenhuy.buffer.job.GetConfigurationJob;
import org.nguyenhuy.buffer.job.GetProfilesJob;
import retrofit.RestAdapter;

import javax.inject.Singleton;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
@Module(
        injects = {
                MainActivity.class,
                GetConfigurationJob.class,
                GetProfilesJob.class
        },
        complete = false
)
public class MainActivityModule {
    private MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    ConfigurationController provideConfigurationController(@ForActivity JobManager jobManager,
                                                           Bus bus) {
        return new ConfigurationController(bus, jobManager);
    }

    @Provides
    @Singleton
    ProfilesController provideProfilesController(@ForActivity JobManager jobManager,
                                                 Bus bus) {
        return new ProfilesController(activity, bus, jobManager);
    }

    @Provides
    @ForActivity
    @Singleton
    JobManager provideJobManager() {
        Configuration config = new Configuration.Builder(activity)
                .injector(new DependencyInjector() {
                    @Override
                    public void inject(BaseJob baseJob) {
                        activity.inject(baseJob);
                    }
                })
                .id("MainActivity")
                .build();
        return new JobManager(activity, config);
    }

    @Provides
    BufferService provideBufferService() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setServer("https://api.bufferapp.com/1")
                .build();
        return adapter.create(BufferService.class);
    }
}
