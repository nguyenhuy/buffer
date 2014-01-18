package org.nguyenhuy.buffer.event;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public abstract class UpdatesEvent {
    private String profileId;
    private String status;

    protected UpdatesEvent(String profileId, String status) {
        this.profileId = profileId;
        this.status = status;
    }

    public String getProfileId() {
        return profileId;
    }

    public String getStatus() {
        return status;
    }
}
