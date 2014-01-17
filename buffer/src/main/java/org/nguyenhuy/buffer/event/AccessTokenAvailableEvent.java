package org.nguyenhuy.buffer.event;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class AccessTokenAvailableEvent {
    private String accessToken;

    public AccessTokenAvailableEvent(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
