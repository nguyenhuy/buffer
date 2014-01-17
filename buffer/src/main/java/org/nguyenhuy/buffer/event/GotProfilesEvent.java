package org.nguyenhuy.buffer.event;

import org.nguyenhuy.buffer.model.user.Profile;

import java.util.List;

/**
 * Created by nguyenthanhhuy on 1/17/14.
 */
public class GotProfilesEvent {
    private List<Profile> profiles;

    public GotProfilesEvent(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }
}
