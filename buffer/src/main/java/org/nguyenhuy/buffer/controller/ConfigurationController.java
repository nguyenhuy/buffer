package org.nguyenhuy.buffer.controller;

import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import org.nguyenhuy.buffer.event.DataSource;
import org.nguyenhuy.buffer.event.GotConfigurationEvent;
import org.nguyenhuy.buffer.job.GetConfigurationJob;
import org.nguyenhuy.buffer.model.configuration.Configuration;

/**
 * This class manages {@link org.nguyenhuy.buffer.model.configuration.Configuration}
 * provided by Buffer API, including loading the configuration from network.
 * When the configuration is changed,
 * a {@link org.nguyenhuy.buffer.event.GotConfigurationEvent} will be posted.
 * Note that by design, configuration is cached in memory but not persistent store.
 * Also, this controller must be tied to lifecycle of an application or an activity
 * (depends on which object hosts it). So {@link #onStart()} and {@link #onStop()}
 * must be called according to lifecycle of the host.
 */
public class ConfigurationController {
    private static final String KEY_CONFIG = "config";

    private Configuration configuration;
    private Bus bus;
    private JobManager jobManager;
    private SharedPreferences sharedPreferences;

    public ConfigurationController(Bus bus, JobManager jobManager,
                                   SharedPreferences sharedPreferences) {
        this.bus = bus;
        this.jobManager = jobManager;
        this.sharedPreferences = sharedPreferences;
        if (sharedPreferences.contains(KEY_CONFIG)) {
            String json = sharedPreferences.getString(KEY_CONFIG, "");
            configuration = new Gson().fromJson(json, Configuration.class);
        }
    }

    public void onStart() {
        bus.register(this);
    }

    public void onStop() {
        bus.unregister(this);
    }

    /**
     * Loads configuration. Subscribers will receive
     * a {@link org.nguyenhuy.buffer.event.GotConfigurationEvent} later.
     */
    public void load() {
        if (configuration != null) {
            bus.post(new GotConfigurationEvent(DataSource.CACHED, configuration));
        } else {
            jobManager.addJobInBackground(new GetConfigurationJob());
        }
    }

    @Subscribe
    public void onGotNewConfiguration(GotConfigurationEvent event) {
        configuration = event.getConfiguration();
        String json = new Gson().toJson(configuration);
        sharedPreferences.edit().putString(KEY_CONFIG, json).apply();
    }

    public void clear() {
        configuration = null;
        sharedPreferences.edit().remove(KEY_CONFIG).apply();
    }
}
