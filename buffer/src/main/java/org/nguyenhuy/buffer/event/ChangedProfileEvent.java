package org.nguyenhuy.buffer.event;

import org.nguyenhuy.buffer.model.user.Profile;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class ChangedProfileEvent {
    private Profile profile;

    public ChangedProfileEvent(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }
}
