package org.nguyenhuy.buffer.event;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class GettingUpdatesEvent extends UpdatesEvent {
    public GettingUpdatesEvent(String profileId, String status) {
        super(profileId, status);
    }
}
