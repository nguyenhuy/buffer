package org.nguyenhuy.buffer.controller;

import org.nguyenhuy.buffer.event.AccessTokenAvailableEvent;

/**
 * This class manages the currrent access token of authenticated user,
 * including saving to and loading from persistent store. When the tpken is
 * changed, a {@link org.nguyenhuy.buffer.event.AccessTokenChangedEvent} will be
 * posted. It also produces {@link org.nguyenhuy.buffer.event.AccessTokenAvailableEvent}
 * to notify new subscribers about last known user. Thus, this controller must
 * be tied to lifecycle of an application or an activity (depends on which
 * object hosts it). So {@link #onStart()} and {@link #onStop()} must be called
 * according to lifecycle of the host.
 */
public interface AccessTokenController {
    void onStart();

    void onStop();

    String get();

    void set(String accessToken);

    boolean isAvailable();

    void clear();

    AccessTokenAvailableEvent produceAccessTokenAvailableEvent();
}
