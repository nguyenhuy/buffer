package org.nguyenhuy.buffer.event;

import org.nguyenhuy.buffer.model.user.User;

/**
 * Created by nguyenthanhhuy on 1/15/14.
 */
public class UserChangedEvent {
    private User user;

    public UserChangedEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
