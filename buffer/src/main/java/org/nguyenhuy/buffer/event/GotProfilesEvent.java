package org.nguyenhuy.buffer.event;

import org.nguyenhuy.buffer.model.user.Profile;

import java.util.List;

/**
 * Created by nguyenthanhhuy on 1/17/14.
 */
public class GotProfilesEvent {
    private DataSource source;
    private List<Profile> profiles;

    public GotProfilesEvent(DataSource source, List<Profile> profiles) {
        this.source = source;
        this.profiles = profiles;
    }

    public DataSource getSource() {
        return source;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }
}
