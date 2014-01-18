package org.nguyenhuy.buffer.event;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class FailedToGetUpdatesEvent extends UpdatesEvent {

    public FailedToGetUpdatesEvent(String profileId, String status) {
        super(profileId, status);
    }
}
