package org.nguyenhuy.buffer.controller;

import org.nguyenhuy.buffer.event.UserAvailableEvent;
import org.nguyenhuy.buffer.event.UserChangedEvent;
import org.nguyenhuy.buffer.model.User;

/**
 * This class manages the currrent logged in user, including saving to
 * and loading from persistent store. When the user is changed, a
 * {@link org.nguyenhuy.buffer.event.UserChangedEvent} will be posted.
 * It also produces {@link org.nguyenhuy.buffer.event.UserAvailableEvent} to notify
 * new subscribers about last known user. Thus, this controller must be tied to
 * lifecycle of an application or an activity (depends on which object hosts it).
 * So {@link #onStart()} and {@link #onStop()} must be called according to
 * lifecycle of the host.
 */
public interface UserController {
    void onStart();

    void onStop();

    User getUser();

    void setUser(User user);

    void removeUser();

    UserAvailableEvent produceUserAvailableEvent();
}
