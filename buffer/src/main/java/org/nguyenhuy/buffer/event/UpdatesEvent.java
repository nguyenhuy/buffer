package org.nguyenhuy.buffer.event;

import org.nguyenhuy.buffer.model.request.UpdatesRequest;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public abstract class UpdatesEvent {
    private UpdatesRequest request;

    protected UpdatesEvent(UpdatesRequest request) {
        this.request = request;
    }

    public UpdatesRequest getRequest() {
        return request;
    }
}
