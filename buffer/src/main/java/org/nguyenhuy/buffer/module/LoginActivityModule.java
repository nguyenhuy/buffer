package org.nguyenhuy.buffer.module;

import com.path.android.jobqueue.BaseJob;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.di.DependencyInjector;
import dagger.Module;
import dagger.Provides;
import org.nguyenhuy.buffer.activity.LoginActivity;
import org.nguyenhuy.buffer.api.ApiConstants;
import org.nguyenhuy.buffer.api.OAuthApi;
import org.nguyenhuy.buffer.fragment.OAuthFragment;
import org.nguyenhuy.buffer.job.GetAccessTokenJob;
import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

import javax.inject.Singleton;

/**
 * Created by nguyenthanhhuy on 1/14/14.
 */
@Module(
        injects = {
                LoginActivity.class,
                OAuthFragment.class,
                GetAccessTokenJob.class
        },
        complete = false
)
public class LoginActivityModule {
    private LoginActivity activity;

    public LoginActivityModule(LoginActivity activity) {
        this.activity = activity;
    }

    @Provides
    ApiConstants provideApiConstants() {
        return new ApiConstants();
    }

    @Provides
    OAuthService provideOAuthService(ApiConstants constants) {
        return new ServiceBuilder()
                .provider(OAuthApi.class)
                .apiKey(constants.getApiKey())
                .apiSecret(constants.getApiSecret())
                .callback(constants.getCallback())
                .build();
    }

    @Provides
    @Singleton
    @ForActivity
    JobManager provideJobManager() {
        Configuration config = new Configuration.Builder(activity)
                .injector(new DependencyInjector() {
                    @Override
                    public void inject(BaseJob baseJob) {
                        activity.inject(baseJob);
                    }
                })
                .id("LoginActivity")
                .build();
        return new JobManager(activity, config);
    }
}
