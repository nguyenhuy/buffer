package org.nguyenhuy.buffer.model.user;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class User {
    private String accessToken;

    public User(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public boolean isAuthenticated() {
        return !TextUtils.isEmpty(accessToken);
    }
}
