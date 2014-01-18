package org.nguyenhuy.buffer.event;

import org.nguyenhuy.buffer.model.response.UpdatesResponse;
import org.nguyenhuy.buffer.model.user.Update;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class GotUpdatesEvent extends UpdatesEvent {
    private int page;
    private int count;
    UpdatesResponse response;

    public GotUpdatesEvent(String profileId, String status, int page, int count,
                           UpdatesResponse response) {
        super(profileId, status);
        this.page = page;
        this.count = count;
        this.response = response;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    public UpdatesResponse getResponse() {
        return response;
    }
}
