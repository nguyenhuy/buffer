package org.nguyenhuy.buffer.event;

import org.nguyenhuy.buffer.model.request.UpdatesRequest;

/**
 * Created by nguyenthanhhuy on 1/18/14.
 */
public class GettingUpdatesEvent extends UpdatesEvent {

    public GettingUpdatesEvent(UpdatesRequest request) {
        super(request);
    }
}
