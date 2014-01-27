package org.nguyenhuy.buffer.event;

import org.nguyenhuy.buffer.model.request.UpdatesRequest;
import org.nguyenhuy.buffer.model.response.UpdatesResponse;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class GotUpdatesEvent extends UpdatesEvent {
    private UpdatesResponse response;

    public GotUpdatesEvent(UpdatesRequest request, UpdatesResponse response) {
        super(request);
        this.response = response;
    }

    public UpdatesResponse getResponse() {
        return response;
    }
}
