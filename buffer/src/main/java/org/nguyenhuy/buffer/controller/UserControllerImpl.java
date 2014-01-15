package org.nguyenhuy.buffer.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import org.nguyenhuy.buffer.event.UserAvailableEvent;
import org.nguyenhuy.buffer.event.UserChangedEvent;
import org.nguyenhuy.buffer.model.User;
import org.nguyenhuy.buffer.module.ForApplication;

import javax.inject.Inject;

public class UserControllerImpl implements UserController {
    private static final String KEY_USER = "user";

    private Bus bus;
    private SharedPreferences sharedPreferences;
    private User user;

    @Inject
    UserControllerImpl(@ForApplication Context context, Bus bus) {
        this.bus = bus;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.contains(KEY_USER)) {
            String userJson = sharedPreferences.getString(KEY_USER, "");
            user = new Gson().fromJson(userJson, User.class);
        }
    }

    @Override
    public void onStart() {
        bus.register(this);
    }

    @Override
    public void onStop() {
        bus.unregister(this);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        sharedPreferences.edit().putString(KEY_USER, new Gson().toJson(user)).apply();
        bus.post(new UserChangedEvent(user));
    }

    @Override
    public void removeUser() {
        if (user != null) {
            user = null;
            sharedPreferences.edit().remove(KEY_USER).apply();
            bus.post(new UserChangedEvent(user));
        }
    }

    @Override
    @Produce
    public UserAvailableEvent produceUserAvailableEvent() {
        if (user != null) {
            return new UserAvailableEvent(user);
        }
        return null;
    }
}
