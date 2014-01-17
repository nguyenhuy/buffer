package org.nguyenhuy.buffer.event;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class AccessTokenChangedEvent {
    private String accessToken;

    public AccessTokenChangedEvent(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}