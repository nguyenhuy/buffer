package org.nguyenhuy.buffer.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.path.android.jobqueue.JobManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import org.nguyenhuy.buffer.event.DataSource;
import org.nguyenhuy.buffer.event.GotProfilesEvent;
import org.nguyenhuy.buffer.job.GetProfilesJob;
import org.nguyenhuy.buffer.job.JobPriority;
import org.nguyenhuy.buffer.model.user.Profile;
import org.nguyenhuy.buffer.util.LogUtils;

import java.util.Arrays;
import java.util.List;

/**
 * This class manages profiles of current user, including saving to, loading
 * from persistent store and from network.
 * Note that this controller must be tied to lifecycle of an application or
 * an activity (depends on which object hosts it). So {@link #onStart()} and
 * {@link #onStop()} must be called according to lifecycle of the host.
 */
public class ProfilesController {
    private static final String KEY_PROFILES = "profiles";

    private SharedPreferences sharedPreferences;
    private Bus bus;
    private JobManager jobManager;
    private List<Profile> profiles;

    public ProfilesController(Context context, Bus bus, JobManager jobManager) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.bus = bus;
        this.jobManager = jobManager;

        if (sharedPreferences.contains(KEY_PROFILES)) {
            String json = sharedPreferences.getString(KEY_PROFILES, "");
            Profile[] data = new Gson().fromJson(json, Profile[].class);
            profiles = Arrays.asList(data);
        }
    }

    public void onStart() {
        bus.register(this);
    }

    public void onStop() {
        bus.unregister(this);
    }

    /**
     * Loads profiles. Subscribers will receive a
     * {@link org.nguyenhuy.buffer.event.GotProfilesEvent} when loading finished.
     */
    public void loadProfiles() {
        int priority = JobPriority.UI;
        if (profiles != null) {
            bus.post(new GotProfilesEvent(DataSource.CACHED, profiles));
            priority = JobPriority.SYNC;
        }
        jobManager.addJobInBackground(new GetProfilesJob(priority));
        LogUtils.v("GetProfile: add to job manager");
    }

    @Subscribe
    public void onGotProfiles(GotProfilesEvent event) {
        profiles = event.getProfiles();

        Profile[] data = profiles.toArray(new Profile[profiles.size()]);
        String json = new Gson().toJson(data);
        sharedPreferences.edit().putString(KEY_PROFILES, json).apply();
    }
}
