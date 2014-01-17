package org.nguyenhuy.buffer.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import org.nguyenhuy.buffer.event.AccessTokenAvailableEvent;
import org.nguyenhuy.buffer.event.AccessTokenChangedEvent;
import org.nguyenhuy.buffer.module.ForApplication;

import javax.inject.Inject;

public class AccessTokenControllerImpl implements AccessTokenController {
    private static final String KEY_TOKEN = "token";

    private Bus bus;
    private SharedPreferences sharedPreferences;
    private String token;

    @Inject
    AccessTokenControllerImpl(@ForApplication Context context, Bus bus) {
        this.bus = bus;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.contains(KEY_TOKEN)) {
            token = sharedPreferences.getString(KEY_TOKEN, "");
        }
    }

    @Override
    public void onStart() {
        // Must register to event bus to produce AccessTokenAvailableEvent
        bus.register(this);
    }

    @Override
    public void onStop() {
        bus.unregister(this);
    }

    @Override
    public String get() {
        return token;
    }

    @Override
    public void set(String token) {
        if (!TextUtils.isEmpty(token)) {
            this.token = token;
            save();
            bus.post(new AccessTokenChangedEvent(token));
        }
    }

    @Override
    public void remove() {
        if (isAvailable()) {
            token = null;
            sharedPreferences.edit().remove(KEY_TOKEN).apply();
            bus.post(new AccessTokenChangedEvent(token));
        }
    }

    @Override
    public boolean isAvailable() {
        return !TextUtils.isEmpty(token);
    }

    @Produce
    public AccessTokenAvailableEvent produceAccessTokenAvailableEvent() {
        return isAvailable()
                ? new AccessTokenAvailableEvent(token)
                : null;
    }

    private void save() {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply();
    }
}
