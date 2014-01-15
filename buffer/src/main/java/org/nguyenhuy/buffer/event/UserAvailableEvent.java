package org.nguyenhuy.buffer.event;

import org.nguyenhuy.buffer.model.User;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class UserAvailableEvent {
    private User user;

    public UserAvailableEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
